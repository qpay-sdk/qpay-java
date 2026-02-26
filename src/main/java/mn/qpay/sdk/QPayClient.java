package mn.qpay.sdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import mn.qpay.sdk.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

/**
 * Main client for interacting with the QPay V2 API.
 * Handles automatic token management with synchronized access.
 */
public class QPayClient {

    private static final long TOKEN_BUFFER_SECONDS = 30;

    private final QPayConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final Object tokenLock = new Object();
    private String accessToken;
    private String refreshToken;
    private long expiresAt;
    private long refreshExpiresAt;

    /**
     * Creates a new QPayClient with the given configuration.
     *
     * @param config the QPay configuration
     */
    public QPayClient(QPayConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.objectMapper = createObjectMapper();
    }

    /**
     * Creates a new QPayClient with the given configuration and custom HttpClient.
     *
     * @param config     the QPay configuration
     * @param httpClient a custom HttpClient instance
     */
    public QPayClient(QPayConfig config, HttpClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
        this.objectMapper = createObjectMapper();
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    // ==================== Auth ====================

    /**
     * Authenticates with QPay using Basic Auth and returns a new token pair.
     * POST /v2/auth/token
     *
     * @return the token response
     * @throws QPayException if the request fails
     */
    public TokenResponse getToken() {
        TokenResponse token = doBasicAuthRequest("POST", "/v2/auth/token", TokenResponse.class);
        synchronized (tokenLock) {
            storeToken(token);
        }
        return token;
    }

    /**
     * Uses the current refresh token to obtain a new access token.
     * POST /v2/auth/refresh
     *
     * @return the token response
     * @throws QPayException if the request fails
     */
    public TokenResponse refreshToken() {
        String refreshTok;
        synchronized (tokenLock) {
            refreshTok = this.refreshToken;
        }
        TokenResponse token = doRefreshTokenHTTP(refreshTok);
        synchronized (tokenLock) {
            storeToken(token);
        }
        return token;
    }

    // ==================== Invoice ====================

    /**
     * Creates a detailed invoice with full options.
     * POST /v2/invoice
     *
     * @param request the create invoice request
     * @return the invoice response
     * @throws QPayException if the request fails
     */
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {
        return doRequest("POST", "/v2/invoice", request, InvoiceResponse.class);
    }

    /**
     * Creates a simple invoice with minimal fields.
     * POST /v2/invoice
     *
     * @param request the create simple invoice request
     * @return the invoice response
     * @throws QPayException if the request fails
     */
    public InvoiceResponse createSimpleInvoice(CreateSimpleInvoiceRequest request) {
        return doRequest("POST", "/v2/invoice", request, InvoiceResponse.class);
    }

    /**
     * Cancels an existing invoice by ID.
     * DELETE /v2/invoice/{id}
     *
     * @param invoiceId the invoice ID to cancel
     * @throws QPayException if the request fails
     */
    public void cancelInvoice(String invoiceId) {
        doRequest("DELETE", "/v2/invoice/" + invoiceId, null, Void.class);
    }

    // ==================== Payment ====================

    /**
     * Retrieves payment details by payment ID.
     * GET /v2/payment/{id}
     *
     * @param paymentId the payment ID
     * @return the payment detail
     * @throws QPayException if the request fails
     */
    public PaymentDetail getPayment(String paymentId) {
        return doRequest("GET", "/v2/payment/" + paymentId, null, PaymentDetail.class);
    }

    /**
     * Checks if a payment has been made for an invoice.
     * POST /v2/payment/check
     *
     * @param request the payment check request
     * @return the payment check response
     * @throws QPayException if the request fails
     */
    public PaymentCheckResponse checkPayment(PaymentCheckRequest request) {
        return doRequest("POST", "/v2/payment/check", request, PaymentCheckResponse.class);
    }

    /**
     * Returns a list of payments matching the given criteria.
     * POST /v2/payment/list
     *
     * @param request the payment list request
     * @return the payment list response
     * @throws QPayException if the request fails
     */
    public PaymentListResponse listPayments(PaymentListRequest request) {
        return doRequest("POST", "/v2/payment/list", request, PaymentListResponse.class);
    }

    /**
     * Cancels a payment (card transactions only).
     * DELETE /v2/payment/cancel/{id}
     *
     * @param paymentId   the payment ID to cancel
     * @param callbackURL optional callback URL
     * @param note        optional note
     * @throws QPayException if the request fails
     */
    public void cancelPayment(String paymentId, String callbackURL, String note) {
        PaymentActionRequest body = new PaymentActionRequest(callbackURL, note);
        doRequest("DELETE", "/v2/payment/cancel/" + paymentId, body, Void.class);
    }

    /**
     * Refunds a payment (card transactions only).
     * DELETE /v2/payment/refund/{id}
     *
     * @param paymentId   the payment ID to refund
     * @param callbackURL optional callback URL
     * @param note        optional note
     * @throws QPayException if the request fails
     */
    public void refundPayment(String paymentId, String callbackURL, String note) {
        PaymentActionRequest body = new PaymentActionRequest(callbackURL, note);
        doRequest("DELETE", "/v2/payment/refund/" + paymentId, body, Void.class);
    }

    // ==================== Ebarimt ====================

    /**
     * Creates an ebarimt (electronic tax receipt) for a payment.
     * POST /v2/ebarimt_v3/create
     *
     * @param request the create ebarimt request
     * @return the ebarimt response
     * @throws QPayException if the request fails
     */
    public EbarimtResponse createEbarimt(CreateEbarimtRequest request) {
        return doRequest("POST", "/v2/ebarimt_v3/create", request, EbarimtResponse.class);
    }

    /**
     * Cancels an ebarimt by payment ID.
     * DELETE /v2/ebarimt_v3/{id}
     *
     * @param paymentId the payment ID
     * @return the ebarimt response
     * @throws QPayException if the request fails
     */
    public EbarimtResponse cancelEbarimt(String paymentId) {
        return doRequest("DELETE", "/v2/ebarimt_v3/" + paymentId, null, EbarimtResponse.class);
    }

    // ==================== Token Management ====================

    private void ensureToken() {
        String refreshTok;
        boolean canRefresh;

        synchronized (tokenLock) {
            long now = System.currentTimeMillis() / 1000;

            // Access token still valid
            if (accessToken != null && !accessToken.isEmpty() && now < expiresAt - TOKEN_BUFFER_SECONDS) {
                return;
            }

            // Determine strategy: refresh or full auth
            canRefresh = refreshToken != null && !refreshToken.isEmpty()
                    && now < refreshExpiresAt - TOKEN_BUFFER_SECONDS;
            refreshTok = this.refreshToken;
        }

        if (canRefresh) {
            try {
                TokenResponse token = doRefreshTokenHTTP(refreshTok);
                synchronized (tokenLock) {
                    storeToken(token);
                }
                return;
            } catch (QPayException e) {
                // Refresh failed, fall through to get new token
            }
        }

        // Both expired or no tokens, get new token
        TokenResponse token = doBasicAuthRequest("POST", "/v2/auth/token", TokenResponse.class);
        synchronized (tokenLock) {
            storeToken(token);
        }
    }

    private void storeToken(TokenResponse token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.expiresAt = token.getExpiresIn();
        this.refreshExpiresAt = token.getRefreshExpiresIn();
    }

    // ==================== HTTP Helpers ====================

    private <T> T doRequest(String method, String path, Object body, Class<T> responseType) {
        ensureToken();

        String currentToken;
        synchronized (tokenLock) {
            currentToken = this.accessToken;
        }

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(config.getBaseURL() + path))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + currentToken)
                    .timeout(Duration.ofSeconds(30));

            if (body != null) {
                String jsonBody = objectMapper.writeValueAsString(body);
                requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody));
            } else {
                requestBuilder.method(method, HttpRequest.BodyPublishers.noBody());
            }

            HttpResponse<String> response = httpClient.send(
                    requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throwQPayException(response);
            }

            if (responseType == Void.class || response.body() == null || response.body().isEmpty()) {
                return null;
            }

            return objectMapper.readValue(response.body(), responseType);
        } catch (QPayException e) {
            throw e;
        } catch (IOException e) {
            throw new QPayException("Request failed: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new QPayException("Request interrupted", e);
        }
    }

    private <T> T doBasicAuthRequest(String method, String path, Class<T> responseType) {
        try {
            String credentials = config.getUsername() + ":" + config.getPassword();
            String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getBaseURL() + path))
                    .header("Authorization", "Basic " + encoded)
                    .method(method, HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throwQPayException(response);
            }

            if (responseType == Void.class || response.body() == null || response.body().isEmpty()) {
                return null;
            }

            return objectMapper.readValue(response.body(), responseType);
        } catch (QPayException e) {
            throw e;
        } catch (IOException e) {
            throw new QPayException("Request failed: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new QPayException("Request interrupted", e);
        }
    }

    private TokenResponse doRefreshTokenHTTP(String refreshTok) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getBaseURL() + "/v2/auth/refresh"))
                    .header("Authorization", "Bearer " + refreshTok)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throwQPayException(response);
            }

            return objectMapper.readValue(response.body(), TokenResponse.class);
        } catch (QPayException e) {
            throw e;
        } catch (IOException e) {
            throw new QPayException("Request failed: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new QPayException("Request interrupted", e);
        }
    }

    private void throwQPayException(HttpResponse<String> response) {
        String rawBody = response.body();
        String code = null;
        String message = null;

        try {
            ErrorBody errorBody = objectMapper.readValue(rawBody, ErrorBody.class);
            code = errorBody.error;
            message = errorBody.message;
        } catch (Exception ignored) {
            // Could not parse error body
        }

        if (code == null || code.isEmpty()) {
            code = String.valueOf(response.statusCode());
        }
        if (message == null || message.isEmpty()) {
            message = rawBody;
        }

        throw new QPayException(response.statusCode(), code, message, rawBody);
    }

    /**
     * Returns the underlying configuration.
     */
    public QPayConfig getConfig() {
        return config;
    }

    // ==================== Internal Types ====================

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class PaymentActionRequest {
        @JsonProperty("callback_url")
        String callbackURL;

        @JsonProperty("note")
        String note;

        PaymentActionRequest(String callbackURL, String note) {
            this.callbackURL = callbackURL;
            this.note = note;
        }
    }

    private static class ErrorBody {
        @JsonProperty("error")
        String error;

        @JsonProperty("message")
        String message;
    }
}
