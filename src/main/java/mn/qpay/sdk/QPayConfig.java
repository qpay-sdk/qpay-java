package mn.qpay.sdk;

/**
 * Configuration for the QPay client.
 */
public class QPayConfig {

    private final String baseURL;
    private final String username;
    private final String password;
    private final String invoiceCode;
    private final String callbackURL;

    private QPayConfig(Builder builder) {
        this.baseURL = builder.baseURL;
        this.username = builder.username;
        this.password = builder.password;
        this.invoiceCode = builder.invoiceCode;
        this.callbackURL = builder.callbackURL;
    }

    /**
     * Loads configuration from environment variables.
     * <p>
     * Required environment variables:
     * <ul>
     *   <li>QPAY_BASE_URL</li>
     *   <li>QPAY_USERNAME</li>
     *   <li>QPAY_PASSWORD</li>
     *   <li>QPAY_INVOICE_CODE</li>
     *   <li>QPAY_CALLBACK_URL</li>
     * </ul>
     *
     * @return a new QPayConfig instance
     * @throws QPayException if any required environment variable is missing
     */
    public static QPayConfig fromEnv() {
        String baseURL = requireEnv("QPAY_BASE_URL");
        String username = requireEnv("QPAY_USERNAME");
        String password = requireEnv("QPAY_PASSWORD");
        String invoiceCode = requireEnv("QPAY_INVOICE_CODE");
        String callbackURL = requireEnv("QPAY_CALLBACK_URL");

        return new Builder()
                .baseURL(baseURL)
                .username(username)
                .password(password)
                .invoiceCode(invoiceCode)
                .callbackURL(callbackURL)
                .build();
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new QPayException(0, "CONFIGURATION_ERROR",
                    "Required environment variable " + name + " is not set", null);
        }
        return value;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String baseURL;
        private String username;
        private String password;
        private String invoiceCode;
        private String callbackURL;

        public Builder baseURL(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder invoiceCode(String invoiceCode) {
            this.invoiceCode = invoiceCode;
            return this;
        }

        public Builder callbackURL(String callbackURL) {
            this.callbackURL = callbackURL;
            return this;
        }

        public QPayConfig build() {
            if (baseURL == null || baseURL.isBlank()) {
                throw new IllegalArgumentException("baseURL is required");
            }
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("username is required");
            }
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("password is required");
            }
            if (invoiceCode == null || invoiceCode.isBlank()) {
                throw new IllegalArgumentException("invoiceCode is required");
            }
            if (callbackURL == null || callbackURL.isBlank()) {
                throw new IllegalArgumentException("callbackURL is required");
            }
            return new QPayConfig(this);
        }
    }
}
