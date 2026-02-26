package mn.qpay.sdk;

/**
 * QPay API error code constants.
 */
public final class ErrorCodes {

    private ErrorCodes() {
    }

    public static final String ACCOUNT_BANK_DUPLICATED = "ACCOUNT_BANK_DUPLICATED";
    public static final String ACCOUNT_SELECTION_INVALID = "ACCOUNT_SELECTION_INVALID";
    public static final String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
    public static final String BANK_ACCOUNT_NOTFOUND = "BANK_ACCOUNT_NOTFOUND";
    public static final String BANK_MCC_ALREADY_ADDED = "BANK_MCC_ALREADY_ADDED";
    public static final String BANK_MCC_NOT_FOUND = "BANK_MCC_NOT_FOUND";
    public static final String CARD_TERMINAL_NOTFOUND = "CARD_TERMINAL_NOTFOUND";
    public static final String CLIENT_NOTFOUND = "CLIENT_NOTFOUND";
    public static final String CLIENT_USERNAME_DUPLICATED = "CLIENT_USERNAME_DUPLICATED";
    public static final String CUSTOMER_DUPLICATE = "CUSTOMER_DUPLICATE";
    public static final String CUSTOMER_NOTFOUND = "CUSTOMER_NOTFOUND";
    public static final String CUSTOMER_REGISTER_INVALID = "CUSTOMER_REGISTER_INVALID";
    public static final String EBARIMT_CANCEL_NOTSUPPERDED = "EBARIMT_CANCEL_NOTSUPPERDED";
    public static final String EBARIMT_NOT_REGISTERED = "EBARIMT_NOT_REGISTERED";
    public static final String EBARIMT_QR_CODE_INVALID = "EBARIMT_QR_CODE_INVALID";
    public static final String INFORM_NOTFOUND = "INFORM_NOTFOUND";
    public static final String INPUT_CODE_REGISTERED = "INPUT_CODE_REGISTERED";
    public static final String INPUT_NOTFOUND = "INPUT_NOTFOUND";
    public static final String INVALID_AMOUNT = "INVALID_AMOUNT";
    public static final String INVALID_OBJECT_TYPE = "INVALID_OBJECT_TYPE";
    public static final String INVOICE_ALREADY_CANCELED = "INVOICE_ALREADY_CANCELED";
    public static final String INVOICE_CODE_INVALID = "INVOICE_CODE_INVALID";
    public static final String INVOICE_CODE_REGISTERED = "INVOICE_CODE_REGISTERED";
    public static final String INVOICE_LINE_REQUIRED = "INVOICE_LINE_REQUIRED";
    public static final String INVOICE_NOTFOUND = "INVOICE_NOTFOUND";
    public static final String INVOICE_PAID = "INVOICE_PAID";
    public static final String INVOICE_RECEIVER_DATA_ADDRESS_REQUIRED = "INVOICE_RECEIVER_DATA_ADDRESS_REQUIRED";
    public static final String INVOICE_RECEIVER_DATA_EMAIL_REQUIRED = "INVOICE_RECEIVER_DATA_EMAIL_REQUIRED";
    public static final String INVOICE_RECEIVER_DATA_PHONE_REQUIRED = "INVOICE_RECEIVER_DATA_PHONE_REQUIRED";
    public static final String INVOICE_RECEIVER_DATA_REQUIRED = "INVOICE_RECEIVER_DATA_REQUIRED";
    public static final String MAX_AMOUNT_ERR = "MAX_AMOUNT_ERR";
    public static final String MCC_NOTFOUND = "MCC_NOTFOUND";
    public static final String MERCHANT_ALREADY_REGISTERED = "MERCHANT_ALREADY_REGISTERED";
    public static final String MERCHANT_INACTIVE = "MERCHANT_INACTIVE";
    public static final String MERCHANT_NOTFOUND = "MERCHANT_NOTFOUND";
    public static final String MIN_AMOUNT_ERR = "MIN_AMOUNT_ERR";
    public static final String NO_CREDENTIALS = "NO_CREDENDIALS";
    public static final String OBJECT_DATA_ERROR = "OBJECT_DATA_ERROR";
    public static final String P2P_TERMINAL_NOTFOUND = "P2P_TERMINAL_NOTFOUND";
    public static final String PAYMENT_ALREADY_CANCELED = "PAYMENT_ALREADY_CANCELED";
    public static final String PAYMENT_NOT_PAID = "PAYMENT_NOT_PAID";
    public static final String PAYMENT_NOTFOUND = "PAYMENT_NOTFOUND";
    public static final String PERMISSION_DENIED = "PERMISSION_DENIED";
    public static final String QRACCOUNT_INACTIVE = "QRACCOUNT_INACTIVE";
    public static final String QRACCOUNT_NOTFOUND = "QRACCOUNT_NOTFOUND";
    public static final String QRCODE_NOTFOUND = "QRCODE_NOTFOUND";
    public static final String QRCODE_USED = "QRCODE_USED";
    public static final String SENDER_BRANCH_DATA_REQUIRED = "SENDER_BRANCH_DATA_REQUIRED";
    public static final String TAX_LINE_REQUIRED = "TAX_LINE_REQUIRED";
    public static final String TAX_PRODUCT_CODE_REQUIRED = "TAX_PRODUCT_CODE_REQUIRED";
    public static final String TRANSACTION_NOT_APPROVED = "TRANSACTION_NOT_APPROVED";
    public static final String TRANSACTION_REQUIRED = "TRANSACTION_REQUIRED";
}
