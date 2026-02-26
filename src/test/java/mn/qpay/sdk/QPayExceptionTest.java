package mn.qpay.sdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for QPayException constructors, fields, and utility methods.
 */
class QPayExceptionTest {

    @Test
    @DisplayName("HTTP error constructor - all fields populated")
    void testHttpErrorConstructor() {
        QPayException ex = new QPayException(404, "INVOICE_NOTFOUND", "Invoice not found", "{\"error\":\"INVOICE_NOTFOUND\"}");

        assertEquals(404, ex.getStatusCode());
        assertEquals("INVOICE_NOTFOUND", ex.getCode());
        assertEquals("Invoice not found", ex.getMessage());
        assertEquals("{\"error\":\"INVOICE_NOTFOUND\"}", ex.getRawBody());
    }

    @Test
    @DisplayName("HTTP error constructor - null code and rawBody")
    void testHttpErrorConstructorNulls() {
        QPayException ex = new QPayException(500, null, "Server error", null);

        assertEquals(500, ex.getStatusCode());
        assertNull(ex.getCode());
        assertEquals("Server error", ex.getMessage());
        assertNull(ex.getRawBody());
    }

    @Test
    @DisplayName("Cause constructor - wraps underlying exception")
    void testCauseConstructor() {
        IOException cause = new IOException("connection reset");
        QPayException ex = new QPayException("Request failed: connection reset", cause);

        assertEquals("Request failed: connection reset", ex.getMessage());
        assertEquals(cause, ex.getCause());
        assertEquals(0, ex.getStatusCode());
        assertNull(ex.getCode());
        assertNull(ex.getRawBody());
    }

    @Test
    @DisplayName("toString - includes code, message, and status")
    void testToString() {
        QPayException ex = new QPayException(401, "AUTHENTICATION_FAILED", "Invalid credentials", null);

        String str = ex.toString();
        assertTrue(str.contains("AUTHENTICATION_FAILED"));
        assertTrue(str.contains("Invalid credentials"));
        assertTrue(str.contains("401"));
        assertTrue(str.startsWith("qpay: "));
    }

    @Test
    @DisplayName("toString - uses UNKNOWN when code is null")
    void testToStringNullCode() {
        QPayException ex = new QPayException(500, null, "Server error", null);

        String str = ex.toString();
        assertTrue(str.contains("UNKNOWN"));
        assertTrue(str.contains("Server error"));
        assertTrue(str.contains("500"));
    }

    @Test
    @DisplayName("toString - cause-only exception")
    void testToStringCauseOnly() {
        QPayException ex = new QPayException("Request failed", new RuntimeException("timeout"));

        String str = ex.toString();
        assertTrue(str.contains("UNKNOWN"));
        assertTrue(str.contains("Request failed"));
        assertTrue(str.contains("status 0"));
    }

    @Test
    @DisplayName("asQPayError - returns exception if QPayException")
    void testAsQPayErrorWithQPayException() {
        QPayException original = new QPayException(400, "INVALID_AMOUNT", "Bad amount", null);
        QPayException result = QPayException.asQPayError(original);

        assertNotNull(result);
        assertSame(original, result);
    }

    @Test
    @DisplayName("asQPayError - returns null for non-QPayException")
    void testAsQPayErrorWithOtherException() {
        RuntimeException other = new RuntimeException("something else");
        QPayException result = QPayException.asQPayError(other);

        assertNull(result);
    }

    @Test
    @DisplayName("asQPayError - returns null for null input")
    void testAsQPayErrorWithNull() {
        QPayException result = QPayException.asQPayError(null);
        assertNull(result);
    }

    @Test
    @DisplayName("QPayException is a RuntimeException")
    void testIsRuntimeException() {
        QPayException ex = new QPayException(400, "TEST", "test", null);
        assertInstanceOf(RuntimeException.class, ex);
    }

    // ==================== ErrorCodes constants ====================

    @Test
    @DisplayName("ErrorCodes - common constants are defined")
    void testErrorCodeConstants() {
        assertEquals("AUTHENTICATION_FAILED", ErrorCodes.AUTHENTICATION_FAILED);
        assertEquals("INVOICE_NOTFOUND", ErrorCodes.INVOICE_NOTFOUND);
        assertEquals("INVOICE_PAID", ErrorCodes.INVOICE_PAID);
        assertEquals("INVOICE_ALREADY_CANCELED", ErrorCodes.INVOICE_ALREADY_CANCELED);
        assertEquals("INVOICE_CODE_INVALID", ErrorCodes.INVOICE_CODE_INVALID);
        assertEquals("PAYMENT_NOTFOUND", ErrorCodes.PAYMENT_NOTFOUND);
        assertEquals("PAYMENT_NOT_PAID", ErrorCodes.PAYMENT_NOT_PAID);
        assertEquals("PAYMENT_ALREADY_CANCELED", ErrorCodes.PAYMENT_ALREADY_CANCELED);
        assertEquals("PERMISSION_DENIED", ErrorCodes.PERMISSION_DENIED);
        assertEquals("INVALID_AMOUNT", ErrorCodes.INVALID_AMOUNT);
        assertEquals("MERCHANT_NOTFOUND", ErrorCodes.MERCHANT_NOTFOUND);
        assertEquals("MERCHANT_INACTIVE", ErrorCodes.MERCHANT_INACTIVE);
        assertEquals("NO_CREDENDIALS", ErrorCodes.NO_CREDENTIALS);
        assertEquals("EBARIMT_NOT_REGISTERED", ErrorCodes.EBARIMT_NOT_REGISTERED);
        assertEquals("EBARIMT_CANCEL_NOTSUPPERDED", ErrorCodes.EBARIMT_CANCEL_NOTSUPPERDED);
        assertEquals("CUSTOMER_NOTFOUND", ErrorCodes.CUSTOMER_NOTFOUND);
        assertEquals("CUSTOMER_DUPLICATE", ErrorCodes.CUSTOMER_DUPLICATE);
    }

    @Test
    @DisplayName("ErrorCodes - cannot be instantiated (private constructor)")
    void testErrorCodesNotInstantiable() {
        // Verify that ErrorCodes has only a private constructor
        var constructors = ErrorCodes.class.getDeclaredConstructors();
        assertEquals(1, constructors.length);
        assertFalse(constructors[0].canAccess(null));
    }

    // Inner class to use in cause constructor test
    private static class IOException extends Exception {
        IOException(String msg) {
            super(msg);
        }
    }
}
