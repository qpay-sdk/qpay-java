package mn.qpay.sdk;

/**
 * Exception thrown when a QPay API call fails.
 */
public class QPayException extends RuntimeException {

    private final int statusCode;
    private final String code;
    private final String rawBody;

    public QPayException(int statusCode, String code, String message, String rawBody) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
        this.rawBody = rawBody;
    }

    public QPayException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
        this.code = null;
        this.rawBody = null;
    }

    /**
     * HTTP status code returned by the QPay API, or 0 if not an HTTP error.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * QPay error code (e.g. "INVOICE_NOTFOUND"), or null if not available.
     */
    public String getCode() {
        return code;
    }

    /**
     * Raw response body from the QPay API, or null if not available.
     */
    public String getRawBody() {
        return rawBody;
    }

    /**
     * Checks if the given exception is a QPayException and returns it, or null if not.
     */
    public static QPayException asQPayError(Throwable t) {
        if (t instanceof QPayException qe) {
            return qe;
        }
        return null;
    }

    @Override
    public String toString() {
        return "qpay: " + (code != null ? code : "UNKNOWN") + " - " + getMessage() + " (status " + statusCode + ")";
    }
}
