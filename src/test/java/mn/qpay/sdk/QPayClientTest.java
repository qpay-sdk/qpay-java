package mn.qpay.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import mn.qpay.sdk.models.*;
import mn.qpay.sdk.models.common.Offset;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for QPayClient using MockWebServer.
 * Covers all API methods, error handling, and automatic token management.
 */
class QPayClientTest {

    private MockWebServer server;
    private QPayClient client;
    private QPayConfig config;
    private ObjectMapper objectMapper;

    // Reusable token JSON -- expires_in and refresh_expires_in set far in the future (epoch seconds)
    private static final long FUTURE_EPOCH = System.currentTimeMillis() / 1000 + 3600;
    private static final long FUTURE_REFRESH_EPOCH = System.currentTimeMillis() / 1000 + 7200;

    private String tokenJson() {
        return """
                {
                  "token_type": "Bearer",
                  "access_token": "test-access-token",
                  "refresh_token": "test-refresh-token",
                  "expires_in": %d,
                  "refresh_expires_in": %d,
                  "scope": "openid",
                  "session_state": "abc123"
                }
                """.formatted(FUTURE_EPOCH, FUTURE_REFRESH_EPOCH);
    }

    /** Enqueue a successful auth token response. */
    private void enqueueToken() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(tokenJson()));
    }

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();

        String baseUrl = server.url("").toString();
        // Remove trailing slash
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        config = QPayConfig.builder()
                .baseURL(baseUrl)
                .username("test_user")
                .password("test_pass")
                .invoiceCode("TEST_INVOICE")
                .callbackURL("https://example.com/callback")
                .build();

        client = new QPayClient(config);
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    // ==================== Auth ====================

    @Test
    @DisplayName("getToken - sends Basic Auth and returns token")
    void testGetToken() throws Exception {
        enqueueToken();

        TokenResponse token = client.getToken();

        assertNotNull(token);
        assertEquals("test-access-token", token.getAccessToken());
        assertEquals("test-refresh-token", token.getRefreshToken());
        assertEquals("Bearer", token.getTokenType());

        RecordedRequest req = server.takeRequest();
        assertEquals("POST", req.getMethod());
        assertEquals("/v2/auth/token", req.getPath());

        String expectedAuth = "Basic " + Base64.getEncoder()
                .encodeToString("test_user:test_pass".getBytes());
        assertEquals(expectedAuth, req.getHeader("Authorization"));
    }

    @Test
    @DisplayName("getToken - error response throws QPayException")
    void testGetTokenError() {
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"AUTHENTICATION_FAILED\",\"message\":\"Invalid credentials\"}"));

        QPayException ex = assertThrows(QPayException.class, () -> client.getToken());
        assertEquals(401, ex.getStatusCode());
        assertEquals("AUTHENTICATION_FAILED", ex.getCode());
        assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    @DisplayName("refreshToken - sends Bearer refresh token")
    void testRefreshToken() throws Exception {
        // First get a token so the client has a refresh token
        enqueueToken();
        client.getToken();

        // Now enqueue the refresh response
        String refreshedTokenJson = """
                {
                  "token_type": "Bearer",
                  "access_token": "new-access-token",
                  "refresh_token": "new-refresh-token",
                  "expires_in": %d,
                  "refresh_expires_in": %d,
                  "scope": "openid",
                  "session_state": "def456"
                }
                """.formatted(FUTURE_EPOCH, FUTURE_REFRESH_EPOCH);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(refreshedTokenJson));

        TokenResponse refreshed = client.refreshToken();

        assertEquals("new-access-token", refreshed.getAccessToken());
        assertEquals("new-refresh-token", refreshed.getRefreshToken());

        // Verify the refresh request
        server.takeRequest(); // skip initial getToken request
        RecordedRequest refreshReq = server.takeRequest();
        assertEquals("POST", refreshReq.getMethod());
        assertEquals("/v2/auth/refresh", refreshReq.getPath());
        assertEquals("Bearer test-refresh-token", refreshReq.getHeader("Authorization"));
    }

    // ==================== Invoice ====================

    @Test
    @DisplayName("createInvoice - success response")
    void testCreateInvoice() throws Exception {
        enqueueToken();

        String invoiceJson = """
                {
                  "invoice_id": "inv_123",
                  "qr_text": "qr-text-data",
                  "qr_image": "base64image",
                  "qPay_shortUrl": "https://qpay.mn/s/abc",
                  "urls": [
                    {"name": "Khan Bank", "description": "Khan Bank app", "logo": "khan.png", "link": "khanbank://..."}
                  ]
                }
                """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(invoiceJson));

        CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                .invoiceCode("TEST_INVOICE")
                .senderInvoiceNo("INV-001")
                .invoiceDescription("Test payment")
                .amount(1000.0)
                .callbackURL("https://example.com/callback")
                .build();

        InvoiceResponse resp = client.createInvoice(req);

        assertNotNull(resp);
        assertEquals("inv_123", resp.getInvoiceId());
        assertEquals("qr-text-data", resp.getQrText());
        assertEquals("base64image", resp.getQrImage());
        assertEquals("https://qpay.mn/s/abc", resp.getQPayShortUrl());
        assertNotNull(resp.getUrls());
        assertEquals(1, resp.getUrls().size());
        assertEquals("Khan Bank", resp.getUrls().get(0).getName());

        // Verify the auth request happened first
        RecordedRequest authReq = server.takeRequest();
        assertEquals("/v2/auth/token", authReq.getPath());

        // Verify the invoice request
        RecordedRequest invoiceReq = server.takeRequest();
        assertEquals("POST", invoiceReq.getMethod());
        assertEquals("/v2/invoice", invoiceReq.getPath());
        assertTrue(invoiceReq.getHeader("Authorization").startsWith("Bearer "));
    }

    @Test
    @DisplayName("createSimpleInvoice - success response")
    void testCreateSimpleInvoice() throws Exception {
        enqueueToken();

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_456\",\"qr_text\":\"qr\",\"qr_image\":\"img\"}"));

        CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                .invoiceCode("TEST_INVOICE")
                .senderInvoiceNo("INV-002")
                .amount(500.0)
                .callbackURL("https://example.com/callback")
                .build();

        InvoiceResponse resp = client.createSimpleInvoice(req);

        assertNotNull(resp);
        assertEquals("inv_456", resp.getInvoiceId());
    }

    @Test
    @DisplayName("createInvoice - error response")
    void testCreateInvoiceError() throws Exception {
        enqueueToken();

        server.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"INVOICE_CODE_INVALID\",\"message\":\"Invalid invoice code\"}"));

        CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                .invoiceCode("BAD_CODE")
                .amount(100.0)
                .callbackURL("https://example.com/callback")
                .build();

        QPayException ex = assertThrows(QPayException.class, () -> client.createInvoice(req));
        assertEquals(400, ex.getStatusCode());
        assertEquals("INVOICE_CODE_INVALID", ex.getCode());
    }

    @Test
    @DisplayName("cancelInvoice - sends DELETE request")
    void testCancelInvoice() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse().setResponseCode(200));

        assertDoesNotThrow(() -> client.cancelInvoice("inv_123"));

        server.takeRequest(); // auth
        RecordedRequest req = server.takeRequest();
        assertEquals("DELETE", req.getMethod());
        assertEquals("/v2/invoice/inv_123", req.getPath());
    }

    @Test
    @DisplayName("cancelInvoice - not found error")
    void testCancelInvoiceNotFound() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"INVOICE_NOTFOUND\",\"message\":\"Invoice not found\"}"));

        QPayException ex = assertThrows(QPayException.class, () -> client.cancelInvoice("bad_id"));
        assertEquals(404, ex.getStatusCode());
        assertEquals("INVOICE_NOTFOUND", ex.getCode());
    }

    // ==================== Payment ====================

    @Test
    @DisplayName("getPayment - success response")
    void testGetPayment() throws Exception {
        enqueueToken();

        String paymentJson = """
                {
                  "payment_id": "pay_123",
                  "payment_status": "PAID",
                  "payment_fee": "0",
                  "payment_amount": "1000",
                  "payment_currency": "MNT",
                  "payment_date": "2024-01-15",
                  "payment_wallet": "QPay",
                  "transaction_type": "P2P",
                  "object_type": "INVOICE",
                  "object_id": "inv_123"
                }
                """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(paymentJson));

        PaymentDetail detail = client.getPayment("pay_123");

        assertNotNull(detail);
        assertEquals("pay_123", detail.getPaymentId());
        assertEquals("PAID", detail.getPaymentStatus());
        assertEquals("1000", detail.getPaymentAmount());
        assertEquals("MNT", detail.getPaymentCurrency());

        server.takeRequest(); // auth
        RecordedRequest req = server.takeRequest();
        assertEquals("GET", req.getMethod());
        assertEquals("/v2/payment/pay_123", req.getPath());
    }

    @Test
    @DisplayName("checkPayment - success response")
    void testCheckPayment() throws Exception {
        enqueueToken();

        String checkJson = """
                {
                  "count": 1,
                  "paid_amount": 1000.0,
                  "rows": [
                    {
                      "payment_id": "pay_123",
                      "payment_status": "PAID",
                      "payment_amount": "1000",
                      "payment_currency": "MNT",
                      "payment_wallet": "Khan Bank"
                    }
                  ]
                }
                """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(checkJson));

        PaymentCheckRequest req = PaymentCheckRequest.builder()
                .objectType("INVOICE")
                .objectId("inv_123")
                .offset(new Offset(1, 10))
                .build();

        PaymentCheckResponse resp = client.checkPayment(req);

        assertNotNull(resp);
        assertEquals(1, resp.getCount());
        assertEquals(1000.0, resp.getPaidAmount());
        assertNotNull(resp.getRows());
        assertEquals(1, resp.getRows().size());
        assertEquals("pay_123", resp.getRows().get(0).getPaymentId());
        assertEquals("PAID", resp.getRows().get(0).getPaymentStatus());

        server.takeRequest(); // auth
        RecordedRequest httpReq = server.takeRequest();
        assertEquals("POST", httpReq.getMethod());
        assertEquals("/v2/payment/check", httpReq.getPath());
    }

    @Test
    @DisplayName("listPayments - success response")
    void testListPayments() throws Exception {
        enqueueToken();

        String listJson = """
                {
                  "count": 2,
                  "rows": [
                    {"payment_id": "pay_1", "payment_status": "PAID", "payment_amount": "500"},
                    {"payment_id": "pay_2", "payment_status": "PAID", "payment_amount": "1500"}
                  ]
                }
                """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(listJson));

        PaymentListRequest req = PaymentListRequest.builder()
                .objectType("INVOICE")
                .objectId("inv_123")
                .startDate("2024-01-01")
                .endDate("2024-12-31")
                .offset(new Offset(1, 20))
                .build();

        PaymentListResponse resp = client.listPayments(req);

        assertNotNull(resp);
        assertEquals(2, resp.getCount());
        assertEquals(2, resp.getRows().size());
        assertEquals("pay_1", resp.getRows().get(0).getPaymentId());
        assertEquals("pay_2", resp.getRows().get(1).getPaymentId());
    }

    @Test
    @DisplayName("cancelPayment - sends DELETE with body")
    void testCancelPayment() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse().setResponseCode(200));

        assertDoesNotThrow(() -> client.cancelPayment("pay_123", "https://cb.example.com", "cancel reason"));

        server.takeRequest(); // auth
        RecordedRequest req = server.takeRequest();
        assertEquals("DELETE", req.getMethod());
        assertEquals("/v2/payment/cancel/pay_123", req.getPath());

        String body = req.getBody().readUtf8();
        assertTrue(body.contains("\"callback_url\""));
        assertTrue(body.contains("\"note\""));
    }

    @Test
    @DisplayName("refundPayment - sends DELETE with body")
    void testRefundPayment() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse().setResponseCode(200));

        assertDoesNotThrow(() -> client.refundPayment("pay_456", "https://cb.example.com", "refund reason"));

        server.takeRequest(); // auth
        RecordedRequest req = server.takeRequest();
        assertEquals("DELETE", req.getMethod());
        assertEquals("/v2/payment/refund/pay_456", req.getPath());
    }

    @Test
    @DisplayName("getPayment - payment not found error")
    void testGetPaymentNotFound() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"PAYMENT_NOTFOUND\",\"message\":\"Payment not found\"}"));

        QPayException ex = assertThrows(QPayException.class, () -> client.getPayment("bad_id"));
        assertEquals(404, ex.getStatusCode());
        assertEquals("PAYMENT_NOTFOUND", ex.getCode());
    }

    // ==================== Ebarimt ====================

    @Test
    @DisplayName("createEbarimt - success response")
    void testCreateEbarimt() throws Exception {
        enqueueToken();

        String ebarimtJson = """
                {
                  "id": "eb_123",
                  "ebarimt_receiver_type": "individual",
                  "ebarimt_receiver": "AA12345678",
                  "amount": "1000",
                  "barimt_status": "CREATED",
                  "status": true
                }
                """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(ebarimtJson));

        CreateEbarimtRequest req = CreateEbarimtRequest.builder()
                .paymentId("pay_123")
                .ebarimtReceiverType("individual")
                .ebarimtReceiver("AA12345678")
                .build();

        EbarimtResponse resp = client.createEbarimt(req);

        assertNotNull(resp);
        assertEquals("eb_123", resp.getId());
        assertEquals("individual", resp.getEbarimtReceiverType());
        assertEquals("1000", resp.getAmount());
        assertTrue(resp.isStatus());

        server.takeRequest(); // auth
        RecordedRequest httpReq = server.takeRequest();
        assertEquals("POST", httpReq.getMethod());
        assertEquals("/v2/ebarimt_v3/create", httpReq.getPath());
    }

    @Test
    @DisplayName("cancelEbarimt - success response")
    void testCancelEbarimt() throws Exception {
        enqueueToken();

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":\"eb_123\",\"barimt_status\":\"CANCELLED\",\"status\":false}"));

        EbarimtResponse resp = client.cancelEbarimt("pay_123");

        assertNotNull(resp);
        assertEquals("eb_123", resp.getId());
        assertEquals("CANCELLED", resp.getBarimtStatus());
        assertFalse(resp.isStatus());

        server.takeRequest(); // auth
        RecordedRequest req = server.takeRequest();
        assertEquals("DELETE", req.getMethod());
        assertEquals("/v2/ebarimt_v3/pay_123", req.getPath());
    }

    // ==================== Auto Token Refresh ====================

    @Test
    @DisplayName("auto token - fetches new token when no token is set")
    void testAutoTokenFetchOnFirstRequest() throws Exception {
        // The first API call should automatically trigger getToken
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_auto\",\"qr_text\":\"qr\"}"));

        CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                .invoiceCode("TEST")
                .amount(100.0)
                .callbackURL("https://example.com/cb")
                .build();

        InvoiceResponse resp = client.createSimpleInvoice(req);
        assertEquals("inv_auto", resp.getInvoiceId());

        // Verify auth was called first
        RecordedRequest authReq = server.takeRequest();
        assertEquals("/v2/auth/token", authReq.getPath());
        assertTrue(authReq.getHeader("Authorization").startsWith("Basic "));
    }

    @Test
    @DisplayName("auto token - reuses valid token on subsequent requests")
    void testAutoTokenReuse() throws Exception {
        // First call: auth + invoice
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_1\"}"));

        // Second call: just invoice (token should be reused)
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_2\"}"));

        CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                .invoiceCode("TEST")
                .amount(100.0)
                .callbackURL("https://example.com/cb")
                .build();

        InvoiceResponse resp1 = client.createSimpleInvoice(req);
        assertEquals("inv_1", resp1.getInvoiceId());

        InvoiceResponse resp2 = client.createSimpleInvoice(req);
        assertEquals("inv_2", resp2.getInvoiceId());

        // Only 3 requests total: 1 auth + 2 API calls
        assertEquals(0, server.getRequestCount() - 3);
    }

    @Test
    @DisplayName("auto token - refreshes expired token using refresh token")
    void testAutoTokenRefreshWhenExpired() throws Exception {
        // Set up a token that is already expired (access), but refresh is still valid
        String expiredTokenJson = """
                {
                  "token_type": "Bearer",
                  "access_token": "expired-access",
                  "refresh_token": "valid-refresh",
                  "expires_in": %d,
                  "refresh_expires_in": %d,
                  "scope": "openid"
                }
                """.formatted(
                System.currentTimeMillis() / 1000 - 100,  // access already expired
                System.currentTimeMillis() / 1000 + 7200   // refresh still valid
        );

        // First: initial auth returns an "expired" access token
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(expiredTokenJson));
        client.getToken();

        // Next call should trigger a refresh attempt
        // Enqueue refresh response
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(tokenJson()));

        // Enqueue the actual API response
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"payment_id\":\"pay_refreshed\",\"payment_status\":\"PAID\",\"payment_amount\":\"1000\"}"));

        PaymentDetail detail = client.getPayment("pay_refreshed");
        assertNotNull(detail);
        assertEquals("pay_refreshed", detail.getPaymentId());

        // Verify: initial auth, then refresh, then API call
        server.takeRequest(); // initial auth
        RecordedRequest refreshReq = server.takeRequest();
        assertEquals("/v2/auth/refresh", refreshReq.getPath());
        assertEquals("Bearer valid-refresh", refreshReq.getHeader("Authorization"));
    }

    @Test
    @DisplayName("auto token - falls back to new auth when refresh fails")
    void testAutoTokenFallbackOnRefreshFailure() throws Exception {
        // Initial token with expired access but valid refresh
        String expiredTokenJson = """
                {
                  "token_type": "Bearer",
                  "access_token": "expired-access",
                  "refresh_token": "bad-refresh",
                  "expires_in": %d,
                  "refresh_expires_in": %d,
                  "scope": "openid"
                }
                """.formatted(
                System.currentTimeMillis() / 1000 - 100,  // access expired
                System.currentTimeMillis() / 1000 + 7200   // refresh still "valid" by time
        );

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(expiredTokenJson));
        client.getToken();

        // Refresh fails
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"AUTHENTICATION_FAILED\",\"message\":\"Refresh token invalid\"}"));

        // Falls back to new auth
        enqueueToken();

        // Then the actual API call
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_fallback\"}"));

        CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                .invoiceCode("TEST")
                .amount(100.0)
                .callbackURL("https://example.com/cb")
                .build();

        InvoiceResponse resp = client.createSimpleInvoice(req);
        assertEquals("inv_fallback", resp.getInvoiceId());
    }

    @Test
    @DisplayName("auto token - full re-auth when both tokens expired")
    void testAutoTokenFullReAuthWhenBothExpired() throws Exception {
        // Both access and refresh are expired
        String fullyExpiredJson = """
                {
                  "token_type": "Bearer",
                  "access_token": "expired-access",
                  "refresh_token": "expired-refresh",
                  "expires_in": %d,
                  "refresh_expires_in": %d,
                  "scope": "openid"
                }
                """.formatted(
                System.currentTimeMillis() / 1000 - 200,
                System.currentTimeMillis() / 1000 - 100
        );

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(fullyExpiredJson));
        client.getToken();

        // Should go straight to new auth (not attempt refresh)
        enqueueToken();

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invoice_id\":\"inv_reauth\"}"));

        CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                .invoiceCode("TEST")
                .amount(100.0)
                .callbackURL("https://example.com/cb")
                .build();

        InvoiceResponse resp = client.createSimpleInvoice(req);
        assertEquals("inv_reauth", resp.getInvoiceId());

        // Verify: initial getToken, new auth, API call (no refresh attempt)
        server.takeRequest(); // initial getToken
        RecordedRequest reAuthReq = server.takeRequest();
        assertEquals("/v2/auth/token", reAuthReq.getPath());
        assertTrue(reAuthReq.getHeader("Authorization").startsWith("Basic "));
    }

    // ==================== Error Handling Edge Cases ====================

    @Test
    @DisplayName("error response with non-JSON body")
    void testErrorResponseNonJsonBody() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        QPayException ex = assertThrows(QPayException.class, () -> client.getPayment("pay_1"));
        assertEquals(500, ex.getStatusCode());
        assertEquals("500", ex.getCode());
        assertNotNull(ex.getRawBody());
    }

    @Test
    @DisplayName("error response with empty error fields falls back to status code")
    void testErrorResponseEmptyFields() throws Exception {
        enqueueToken();
        server.enqueue(new MockResponse()
                .setResponseCode(403)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"\",\"message\":\"\"}"));

        QPayException ex = assertThrows(QPayException.class, () -> client.getPayment("pay_1"));
        assertEquals(403, ex.getStatusCode());
        assertEquals("403", ex.getCode());
    }

    @Test
    @DisplayName("getConfig returns the configuration")
    void testGetConfig() {
        assertNotNull(client.getConfig());
        assertEquals("test_user", client.getConfig().getUsername());
    }
}
