package mn.qpay.sdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for QPayConfig builder validation and fromEnv behavior.
 */
class QPayConfigTest {

    // ==================== Builder ====================

    @Test
    @DisplayName("builder - creates config with all fields")
    void testBuilderSuccess() {
        QPayConfig config = QPayConfig.builder()
                .baseURL("https://merchant.qpay.mn")
                .username("user")
                .password("pass")
                .invoiceCode("INV_CODE")
                .callbackURL("https://example.com/callback")
                .build();

        assertEquals("https://merchant.qpay.mn", config.getBaseURL());
        assertEquals("user", config.getUsername());
        assertEquals("pass", config.getPassword());
        assertEquals("INV_CODE", config.getInvoiceCode());
        assertEquals("https://example.com/callback", config.getCallbackURL());
    }

    @Test
    @DisplayName("builder - throws when baseURL is null")
    void testBuilderMissingBaseURL() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .username("user")
                        .password("pass")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("baseURL"));
    }

    @Test
    @DisplayName("builder - throws when baseURL is blank")
    void testBuilderBlankBaseURL() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("  ")
                        .username("user")
                        .password("pass")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("baseURL"));
    }

    @Test
    @DisplayName("builder - throws when username is null")
    void testBuilderMissingUsername() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .password("pass")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("username"));
    }

    @Test
    @DisplayName("builder - throws when username is blank")
    void testBuilderBlankUsername() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("")
                        .password("pass")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("username"));
    }

    @Test
    @DisplayName("builder - throws when password is null")
    void testBuilderMissingPassword() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("user")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("password"));
    }

    @Test
    @DisplayName("builder - throws when password is blank")
    void testBuilderBlankPassword() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("user")
                        .password("   ")
                        .invoiceCode("INV")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("password"));
    }

    @Test
    @DisplayName("builder - throws when invoiceCode is null")
    void testBuilderMissingInvoiceCode() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("user")
                        .password("pass")
                        .callbackURL("https://example.com/cb")
                        .build()
        );
        assertTrue(ex.getMessage().contains("invoiceCode"));
    }

    @Test
    @DisplayName("builder - throws when callbackURL is null")
    void testBuilderMissingCallbackURL() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("user")
                        .password("pass")
                        .invoiceCode("INV")
                        .build()
        );
        assertTrue(ex.getMessage().contains("callbackURL"));
    }

    @Test
    @DisplayName("builder - throws when callbackURL is blank")
    void testBuilderBlankCallbackURL() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                QPayConfig.builder()
                        .baseURL("https://merchant.qpay.mn")
                        .username("user")
                        .password("pass")
                        .invoiceCode("INV")
                        .callbackURL("")
                        .build()
        );
        assertTrue(ex.getMessage().contains("callbackURL"));
    }

    // ==================== fromEnv ====================

    @Test
    @DisplayName("fromEnv - throws QPayException when env variable is missing")
    void testFromEnvMissingVariable() {
        // QPAY_BASE_URL is unlikely to be set in the test environment
        // If it is, this test would need adjustment. We rely on the fact that
        // at least one required env var is not set.
        QPayException ex = assertThrows(QPayException.class, QPayConfig::fromEnv);
        assertTrue(ex.getMessage().contains("Required environment variable"));
        assertEquals("CONFIGURATION_ERROR", ex.getCode());
        assertEquals(0, ex.getStatusCode());
    }

    // ==================== Static builder() method ====================

    @Test
    @DisplayName("builder() returns a new Builder instance")
    void testStaticBuilderMethod() {
        QPayConfig.Builder builder = QPayConfig.builder();
        assertNotNull(builder);
    }
}
