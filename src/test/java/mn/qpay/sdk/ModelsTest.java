package mn.qpay.sdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import mn.qpay.sdk.models.*;
import mn.qpay.sdk.models.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for JSON serialization/deserialization of key models.
 */
class ModelsTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // ==================== TokenResponse ====================

    @Nested
    @DisplayName("TokenResponse")
    class TokenResponseTests {

        @Test
        @DisplayName("deserialize - all fields")
        void testDeserialize() throws Exception {
            String json = """
                    {
                      "token_type": "Bearer",
                      "access_token": "abc123",
                      "refresh_token": "def456",
                      "expires_in": 1700000000,
                      "refresh_expires_in": 1700003600,
                      "scope": "openid",
                      "not-before-policy": "0",
                      "session_state": "sess-1"
                    }
                    """;

            TokenResponse token = mapper.readValue(json, TokenResponse.class);

            assertEquals("Bearer", token.getTokenType());
            assertEquals("abc123", token.getAccessToken());
            assertEquals("def456", token.getRefreshToken());
            assertEquals(1700000000L, token.getExpiresIn());
            assertEquals(1700003600L, token.getRefreshExpiresIn());
            assertEquals("openid", token.getScope());
            assertEquals("0", token.getNotBeforePolicy());
            assertEquals("sess-1", token.getSessionState());
        }

        @Test
        @DisplayName("serialize and round-trip")
        void testSerializeRoundTrip() throws Exception {
            TokenResponse token = new TokenResponse();
            token.setTokenType("Bearer");
            token.setAccessToken("token123");
            token.setRefreshToken("refresh456");
            token.setExpiresIn(3600L);
            token.setRefreshExpiresIn(7200L);
            token.setScope("openid");

            String json = mapper.writeValueAsString(token);
            TokenResponse deserialized = mapper.readValue(json, TokenResponse.class);

            assertEquals(token.getAccessToken(), deserialized.getAccessToken());
            assertEquals(token.getRefreshToken(), deserialized.getRefreshToken());
            assertEquals(token.getExpiresIn(), deserialized.getExpiresIn());
        }

        @Test
        @DisplayName("deserialize - ignores unknown fields")
        void testIgnoresUnknown() throws Exception {
            String json = """
                    {
                      "access_token": "abc",
                      "unknown_field": "should be ignored",
                      "expires_in": 100
                    }
                    """;

            TokenResponse token = mapper.readValue(json, TokenResponse.class);
            assertEquals("abc", token.getAccessToken());
            assertEquals(100L, token.getExpiresIn());
        }
    }

    // ==================== CreateInvoiceRequest ====================

    @Nested
    @DisplayName("CreateInvoiceRequest")
    class CreateInvoiceRequestTests {

        @Test
        @DisplayName("serialize - builder with all fields")
        void testSerializeBuilder() throws Exception {
            CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                    .invoiceCode("INV_CODE")
                    .senderInvoiceNo("INV-001")
                    .invoiceDescription("Test payment")
                    .amount(5000.0)
                    .callbackURL("https://example.com/callback")
                    .allowPartial(true)
                    .minimumAmount(1000.0)
                    .allowExceed(false)
                    .maximumAmount(10000.0)
                    .note("test note")
                    .build();

            String json = mapper.writeValueAsString(req);

            assertTrue(json.contains("\"invoice_code\":\"INV_CODE\""));
            assertTrue(json.contains("\"sender_invoice_no\":\"INV-001\""));
            assertTrue(json.contains("\"amount\":5000.0"));
            assertTrue(json.contains("\"allow_partial\":true"));
            assertTrue(json.contains("\"minimum_amount\":1000.0"));
            assertTrue(json.contains("\"note\":\"test note\""));
        }

        @Test
        @DisplayName("serialize - null fields are excluded (NON_NULL)")
        void testNullFieldsExcluded() throws Exception {
            CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                    .invoiceCode("INV_CODE")
                    .amount(1000.0)
                    .callbackURL("https://example.com/cb")
                    .build();

            String json = mapper.writeValueAsString(req);

            assertFalse(json.contains("sender_branch_code"));
            assertFalse(json.contains("sender_staff_data"));
            assertFalse(json.contains("transactions"));
            assertFalse(json.contains("lines"));
        }

        @Test
        @DisplayName("serialize - with nested sender branch data")
        void testNestedSenderBranchData() throws Exception {
            Address addr = Address.builder()
                    .city("Ulaanbaatar")
                    .district("SBD")
                    .street("Peace Ave")
                    .build();

            CreateInvoiceRequest.SenderBranchData branchData = CreateInvoiceRequest.SenderBranchData.builder()
                    .name("Branch 1")
                    .email("branch@test.com")
                    .phone("99001122")
                    .address(addr)
                    .build();

            CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                    .invoiceCode("INV")
                    .amount(1000.0)
                    .callbackURL("https://example.com/cb")
                    .senderBranchData(branchData)
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"sender_branch_data\""));
            assertTrue(json.contains("\"city\":\"Ulaanbaatar\""));
            assertTrue(json.contains("\"email\":\"branch@test.com\""));
        }

        @Test
        @DisplayName("serialize - with invoice lines")
        void testWithInvoiceLines() throws Exception {
            InvoiceLine line = InvoiceLine.builder()
                    .taxProductCode("TAX001")
                    .lineDescription("Widget A")
                    .lineQuantity("2")
                    .lineUnitPrice("500")
                    .build();

            CreateInvoiceRequest req = CreateInvoiceRequest.builder()
                    .invoiceCode("INV")
                    .amount(1000.0)
                    .callbackURL("https://example.com/cb")
                    .lines(List.of(line))
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"lines\""));
            assertTrue(json.contains("\"tax_product_code\":\"TAX001\""));
            assertTrue(json.contains("\"line_description\":\"Widget A\""));
        }

        @Test
        @DisplayName("deserialize - round trip")
        void testRoundTrip() throws Exception {
            CreateInvoiceRequest original = CreateInvoiceRequest.builder()
                    .invoiceCode("INV_CODE")
                    .senderInvoiceNo("INV-001")
                    .amount(5000.0)
                    .callbackURL("https://example.com/cb")
                    .build();

            String json = mapper.writeValueAsString(original);
            CreateInvoiceRequest deserialized = mapper.readValue(json, CreateInvoiceRequest.class);

            assertEquals(original.getInvoiceCode(), deserialized.getInvoiceCode());
            assertEquals(original.getSenderInvoiceNo(), deserialized.getSenderInvoiceNo());
            assertEquals(original.getAmount(), deserialized.getAmount());
        }
    }

    // ==================== CreateSimpleInvoiceRequest ====================

    @Nested
    @DisplayName("CreateSimpleInvoiceRequest")
    class CreateSimpleInvoiceRequestTests {

        @Test
        @DisplayName("serialize - builder")
        void testSerialize() throws Exception {
            CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                    .invoiceCode("SIMPLE_INV")
                    .senderInvoiceNo("S-001")
                    .invoiceReceiverCode("RECV-001")
                    .invoiceDescription("Simple payment")
                    .amount(2000.0)
                    .callbackURL("https://example.com/cb")
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"invoice_code\":\"SIMPLE_INV\""));
            assertTrue(json.contains("\"amount\":2000.0"));
            assertTrue(json.contains("\"invoice_description\":\"Simple payment\""));
        }

        @Test
        @DisplayName("deserialize - round trip")
        void testRoundTrip() throws Exception {
            CreateSimpleInvoiceRequest req = CreateSimpleInvoiceRequest.builder()
                    .invoiceCode("SIMPLE")
                    .amount(500.0)
                    .callbackURL("https://example.com/cb")
                    .build();

            String json = mapper.writeValueAsString(req);
            CreateSimpleInvoiceRequest deserialized = mapper.readValue(json, CreateSimpleInvoiceRequest.class);

            assertEquals("SIMPLE", deserialized.getInvoiceCode());
            assertEquals(500.0, deserialized.getAmount());
        }
    }

    // ==================== InvoiceResponse ====================

    @Nested
    @DisplayName("InvoiceResponse")
    class InvoiceResponseTests {

        @Test
        @DisplayName("deserialize - full response")
        void testDeserialize() throws Exception {
            String json = """
                    {
                      "invoice_id": "inv_abc",
                      "qr_text": "qr-data",
                      "qr_image": "base64img",
                      "qPay_shortUrl": "https://qpay.mn/s/xyz",
                      "urls": [
                        {"name": "Khan Bank", "description": "Pay with Khan", "logo": "khan.png", "link": "khanbank://pay"}
                      ]
                    }
                    """;

            InvoiceResponse resp = mapper.readValue(json, InvoiceResponse.class);

            assertEquals("inv_abc", resp.getInvoiceId());
            assertEquals("qr-data", resp.getQrText());
            assertEquals("base64img", resp.getQrImage());
            assertEquals("https://qpay.mn/s/xyz", resp.getQPayShortUrl());
            assertNotNull(resp.getUrls());
            assertEquals(1, resp.getUrls().size());

            Deeplink dl = resp.getUrls().get(0);
            assertEquals("Khan Bank", dl.getName());
            assertEquals("Pay with Khan", dl.getDescription());
            assertEquals("khan.png", dl.getLogo());
            assertEquals("khanbank://pay", dl.getLink());
        }

        @Test
        @DisplayName("deserialize - empty urls list")
        void testEmptyUrls() throws Exception {
            String json = """
                    {"invoice_id": "inv_1", "urls": []}
                    """;

            InvoiceResponse resp = mapper.readValue(json, InvoiceResponse.class);
            assertNotNull(resp.getUrls());
            assertTrue(resp.getUrls().isEmpty());
        }
    }

    // ==================== PaymentCheckRequest / Response ====================

    @Nested
    @DisplayName("PaymentCheck")
    class PaymentCheckTests {

        @Test
        @DisplayName("PaymentCheckRequest - serialize with offset")
        void testRequestSerialize() throws Exception {
            PaymentCheckRequest req = PaymentCheckRequest.builder()
                    .objectType("INVOICE")
                    .objectId("inv_123")
                    .offset(new Offset(1, 10))
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"object_type\":\"INVOICE\""));
            assertTrue(json.contains("\"object_id\":\"inv_123\""));
            assertTrue(json.contains("\"page_number\":1"));
            assertTrue(json.contains("\"page_limit\":10"));
        }

        @Test
        @DisplayName("PaymentCheckResponse - deserialize with rows")
        void testResponseDeserialize() throws Exception {
            String json = """
                    {
                      "count": 2,
                      "paid_amount": 3000.0,
                      "rows": [
                        {
                          "payment_id": "p1",
                          "payment_status": "PAID",
                          "payment_amount": "1000",
                          "payment_currency": "MNT",
                          "payment_wallet": "Khan Bank",
                          "payment_type": "P2P"
                        },
                        {
                          "payment_id": "p2",
                          "payment_status": "PAID",
                          "payment_amount": "2000",
                          "payment_currency": "MNT"
                        }
                      ]
                    }
                    """;

            PaymentCheckResponse resp = mapper.readValue(json, PaymentCheckResponse.class);
            assertEquals(2, resp.getCount());
            assertEquals(3000.0, resp.getPaidAmount());
            assertEquals(2, resp.getRows().size());
            assertEquals("p1", resp.getRows().get(0).getPaymentId());
            assertEquals("PAID", resp.getRows().get(0).getPaymentStatus());
            assertEquals("Khan Bank", resp.getRows().get(0).getPaymentWallet());
        }
    }

    // ==================== PaymentDetail ====================

    @Nested
    @DisplayName("PaymentDetail")
    class PaymentDetailTests {

        @Test
        @DisplayName("deserialize - full detail")
        void testDeserialize() throws Exception {
            String json = """
                    {
                      "payment_id": "pay_789",
                      "payment_status": "PAID",
                      "payment_fee": "50",
                      "payment_amount": "5000",
                      "payment_currency": "MNT",
                      "payment_date": "2024-06-15T10:30:00",
                      "payment_wallet": "QPay Wallet",
                      "transaction_type": "P2P",
                      "object_type": "INVOICE",
                      "object_id": "inv_abc",
                      "p2p_transactions": [
                        {
                          "transaction_bank_code": "050000",
                          "account_bank_code": "050000",
                          "account_bank_name": "Khan Bank",
                          "account_number": "5001234567",
                          "status": "APPROVED",
                          "amount": "5000",
                          "currency": "MNT"
                        }
                      ]
                    }
                    """;

            PaymentDetail detail = mapper.readValue(json, PaymentDetail.class);

            assertEquals("pay_789", detail.getPaymentId());
            assertEquals("PAID", detail.getPaymentStatus());
            assertEquals("5000", detail.getPaymentAmount());
            assertEquals("P2P", detail.getTransactionType());
            assertNotNull(detail.getP2pTransactions());
            assertEquals(1, detail.getP2pTransactions().size());

            P2PTransaction p2p = detail.getP2pTransactions().get(0);
            assertEquals("050000", p2p.getTransactionBankCode());
            assertEquals("Khan Bank", p2p.getAccountBankName());
            assertEquals("5000", p2p.getAmount());
        }

        @Test
        @DisplayName("deserialize - with card transactions")
        void testWithCardTransactions() throws Exception {
            String json = """
                    {
                      "payment_id": "pay_card",
                      "payment_status": "PAID",
                      "payment_amount": "10000",
                      "transaction_type": "CARD",
                      "card_transactions": [
                        {
                          "card_number": "****1234",
                          "card_type": "VISA",
                          "amount": "10000",
                          "currency": "MNT",
                          "status": "APPROVED"
                        }
                      ]
                    }
                    """;

            PaymentDetail detail = mapper.readValue(json, PaymentDetail.class);
            assertNotNull(detail.getCardTransactions());
            assertEquals(1, detail.getCardTransactions().size());

            CardTransaction card = detail.getCardTransactions().get(0);
            assertEquals("****1234", card.getCardNumber());
            assertEquals("VISA", card.getCardType());
            assertEquals("10000", card.getAmount());
        }
    }

    // ==================== PaymentListRequest / Response ====================

    @Nested
    @DisplayName("PaymentList")
    class PaymentListTests {

        @Test
        @DisplayName("PaymentListRequest - serialize")
        void testRequestSerialize() throws Exception {
            PaymentListRequest req = PaymentListRequest.builder()
                    .objectType("INVOICE")
                    .objectId("inv_123")
                    .startDate("2024-01-01")
                    .endDate("2024-12-31")
                    .offset(new Offset(1, 20))
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"start_date\":\"2024-01-01\""));
            assertTrue(json.contains("\"end_date\":\"2024-12-31\""));
            assertTrue(json.contains("\"page_limit\":20"));
        }

        @Test
        @DisplayName("PaymentListResponse - deserialize")
        void testResponseDeserialize() throws Exception {
            String json = """
                    {
                      "count": 1,
                      "rows": [
                        {
                          "payment_id": "pay_list_1",
                          "payment_date": "2024-06-01",
                          "payment_status": "PAID",
                          "payment_amount": "1500",
                          "payment_currency": "MNT",
                          "payment_wallet": "Social Pay",
                          "payment_name": "Order #123",
                          "object_type": "INVOICE",
                          "object_id": "inv_456"
                        }
                      ]
                    }
                    """;

            PaymentListResponse resp = mapper.readValue(json, PaymentListResponse.class);
            assertEquals(1, resp.getCount());
            assertEquals(1, resp.getRows().size());

            PaymentListResponse.PaymentListItem item = resp.getRows().get(0);
            assertEquals("pay_list_1", item.getPaymentId());
            assertEquals("PAID", item.getPaymentStatus());
            assertEquals("Social Pay", item.getPaymentWallet());
            assertEquals("Order #123", item.getPaymentName());
        }
    }

    // ==================== CreateEbarimtRequest ====================

    @Nested
    @DisplayName("CreateEbarimtRequest")
    class CreateEbarimtRequestTests {

        @Test
        @DisplayName("serialize - builder")
        void testSerialize() throws Exception {
            CreateEbarimtRequest req = CreateEbarimtRequest.builder()
                    .paymentId("pay_123")
                    .ebarimtReceiverType("individual")
                    .ebarimtReceiver("AA12345678")
                    .districtCode("01")
                    .classificationCode("CL001")
                    .build();

            String json = mapper.writeValueAsString(req);
            assertTrue(json.contains("\"payment_id\":\"pay_123\""));
            assertTrue(json.contains("\"ebarimt_receiver_type\":\"individual\""));
            assertTrue(json.contains("\"ebarimt_receiver\":\"AA12345678\""));
            assertTrue(json.contains("\"district_code\":\"01\""));
            assertTrue(json.contains("\"classification_code\":\"CL001\""));
        }

        @Test
        @DisplayName("round trip")
        void testRoundTrip() throws Exception {
            CreateEbarimtRequest req = CreateEbarimtRequest.builder()
                    .paymentId("pay_rt")
                    .ebarimtReceiverType("organization")
                    .ebarimtReceiver("5001234")
                    .build();

            String json = mapper.writeValueAsString(req);
            CreateEbarimtRequest deserialized = mapper.readValue(json, CreateEbarimtRequest.class);

            assertEquals("pay_rt", deserialized.getPaymentId());
            assertEquals("organization", deserialized.getEbarimtReceiverType());
        }
    }

    // ==================== EbarimtResponse ====================

    @Nested
    @DisplayName("EbarimtResponse")
    class EbarimtResponseTests {

        @Test
        @DisplayName("deserialize - full response")
        void testDeserialize() throws Exception {
            String json = """
                    {
                      "id": "eb_001",
                      "ebarimt_by": "system",
                      "ebarimt_receiver_type": "individual",
                      "ebarimt_receiver": "AA12345678",
                      "amount": "5000",
                      "vat_amount": "500",
                      "barimt_status": "CREATED",
                      "ebarimt_qr_data": "qr-data-here",
                      "ebarimt_lottery": "LOTTERY123",
                      "status": true,
                      "barimt_items": [
                        {
                          "id": "item_1",
                          "name": "Widget A",
                          "amount": "5000",
                          "quantity": "1",
                          "status": true
                        }
                      ],
                      "barimt_histories": [
                        {
                          "id": "hist_1",
                          "barimt_status": "CREATED",
                          "ebarimt_date": "2024-06-15"
                        }
                      ]
                    }
                    """;

            EbarimtResponse resp = mapper.readValue(json, EbarimtResponse.class);

            assertEquals("eb_001", resp.getId());
            assertEquals("individual", resp.getEbarimtReceiverType());
            assertEquals("5000", resp.getAmount());
            assertEquals("500", resp.getVatAmount());
            assertEquals("CREATED", resp.getBarimtStatus());
            assertEquals("qr-data-here", resp.getEbarimtQrData());
            assertEquals("LOTTERY123", resp.getEbarimtLottery());
            assertTrue(resp.isStatus());

            assertNotNull(resp.getBarimtItems());
            assertEquals(1, resp.getBarimtItems().size());
            assertEquals("Widget A", resp.getBarimtItems().get(0).getName());

            assertNotNull(resp.getBarimtHistories());
            assertEquals(1, resp.getBarimtHistories().size());
            assertEquals("CREATED", resp.getBarimtHistories().get(0).getBarimtStatus());
        }
    }

    // ==================== Common Models ====================

    @Nested
    @DisplayName("Common Models")
    class CommonModelTests {

        @Test
        @DisplayName("Address - builder and serialization")
        void testAddress() throws Exception {
            Address addr = Address.builder()
                    .city("Ulaanbaatar")
                    .district("Sukhbaatar")
                    .street("Peace Ave 22")
                    .building("Tower A")
                    .zipcode("14200")
                    .longitude("106.9057")
                    .latitude("47.9186")
                    .build();

            String json = mapper.writeValueAsString(addr);
            Address deserialized = mapper.readValue(json, Address.class);

            assertEquals("Ulaanbaatar", deserialized.getCity());
            assertEquals("Sukhbaatar", deserialized.getDistrict());
            assertEquals("Peace Ave 22", deserialized.getStreet());
            assertEquals("Tower A", deserialized.getBuilding());
            assertEquals("14200", deserialized.getZipcode());
        }

        @Test
        @DisplayName("Offset - constructor and serialization")
        void testOffset() throws Exception {
            Offset offset = new Offset(2, 25);

            String json = mapper.writeValueAsString(offset);
            assertTrue(json.contains("\"page_number\":2"));
            assertTrue(json.contains("\"page_limit\":25"));

            Offset deserialized = mapper.readValue(json, Offset.class);
            assertEquals(2, deserialized.getPageNumber());
            assertEquals(25, deserialized.getPageLimit());
        }

        @Test
        @DisplayName("Deeplink - constructor and serialization")
        void testDeeplink() throws Exception {
            Deeplink dl = new Deeplink("Khan Bank", "Pay with Khan Bank", "khan.png", "khanbank://qpay?q=abc");

            String json = mapper.writeValueAsString(dl);
            Deeplink deserialized = mapper.readValue(json, Deeplink.class);

            assertEquals("Khan Bank", deserialized.getName());
            assertEquals("Pay with Khan Bank", deserialized.getDescription());
            assertEquals("khan.png", deserialized.getLogo());
            assertEquals("khanbank://qpay?q=abc", deserialized.getLink());
        }

        @Test
        @DisplayName("Account - builder and serialization")
        void testAccount() throws Exception {
            Account account = Account.builder()
                    .accountBankCode("050000")
                    .accountNumber("5001234567")
                    .accountName("Test Account")
                    .accountCurrency("MNT")
                    .isDefault(true)
                    .build();

            String json = mapper.writeValueAsString(account);
            Account deserialized = mapper.readValue(json, Account.class);

            assertEquals("050000", deserialized.getAccountBankCode());
            assertEquals("5001234567", deserialized.getAccountNumber());
            assertEquals("Test Account", deserialized.getAccountName());
            assertTrue(deserialized.isDefault());
        }

        @Test
        @DisplayName("Transaction - builder with accounts")
        void testTransaction() throws Exception {
            Account acc = Account.builder()
                    .accountBankCode("050000")
                    .accountNumber("1234567890")
                    .build();

            Transaction txn = Transaction.builder()
                    .description("Payment split")
                    .amount("5000")
                    .accounts(List.of(acc))
                    .build();

            String json = mapper.writeValueAsString(txn);
            assertTrue(json.contains("\"description\":\"Payment split\""));
            assertTrue(json.contains("\"amount\":\"5000\""));
            assertTrue(json.contains("\"accounts\""));
        }

        @Test
        @DisplayName("InvoiceLine - builder with tax entries")
        void testInvoiceLine() throws Exception {
            InvoiceLine.TaxEntry tax = InvoiceLine.TaxEntry.builder()
                    .taxCode("VAT")
                    .description("Value Added Tax")
                    .amount(100.0)
                    .build();

            InvoiceLine line = InvoiceLine.builder()
                    .taxProductCode("PROD001")
                    .lineDescription("Widget A")
                    .lineQuantity("2")
                    .lineUnitPrice("500")
                    .taxes(List.of(tax))
                    .build();

            String json = mapper.writeValueAsString(line);
            assertTrue(json.contains("\"tax_product_code\":\"PROD001\""));
            assertTrue(json.contains("\"line_description\":\"Widget A\""));
            assertTrue(json.contains("\"taxes\""));
            assertTrue(json.contains("\"tax_code\":\"VAT\""));
        }

        @Test
        @DisplayName("CardTransaction - deserialization")
        void testCardTransaction() throws Exception {
            String json = """
                    {
                      "card_merchant_code": "MC001",
                      "card_terminal_code": "TC001",
                      "card_number": "****5678",
                      "card_type": "MASTERCARD",
                      "is_cross_border": false,
                      "amount": "10000",
                      "currency": "MNT",
                      "status": "APPROVED"
                    }
                    """;

            CardTransaction card = mapper.readValue(json, CardTransaction.class);
            assertEquals("MC001", card.getCardMerchantCode());
            assertEquals("****5678", card.getCardNumber());
            assertEquals("MASTERCARD", card.getCardType());
            assertFalse(card.isCrossBorder());
            assertEquals("10000", card.getAmount());
            assertEquals("APPROVED", card.getStatus());
        }

        @Test
        @DisplayName("P2PTransaction - deserialization")
        void testP2PTransaction() throws Exception {
            String json = """
                    {
                      "transaction_bank_code": "050000",
                      "account_bank_code": "050000",
                      "account_bank_name": "Khan Bank",
                      "account_number": "5009876543",
                      "status": "APPROVED",
                      "amount": "3000",
                      "currency": "MNT",
                      "settlement_status": "SETTLED"
                    }
                    """;

            P2PTransaction p2p = mapper.readValue(json, P2PTransaction.class);
            assertEquals("050000", p2p.getTransactionBankCode());
            assertEquals("Khan Bank", p2p.getAccountBankName());
            assertEquals("5009876543", p2p.getAccountNumber());
            assertEquals("APPROVED", p2p.getStatus());
            assertEquals("3000", p2p.getAmount());
            assertEquals("SETTLED", p2p.getSettlementStatus());
        }
    }
}
