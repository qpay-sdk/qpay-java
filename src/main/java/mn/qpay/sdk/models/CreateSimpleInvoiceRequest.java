package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSimpleInvoiceRequest {

    @JsonProperty("invoice_code")
    private String invoiceCode;

    @JsonProperty("sender_invoice_no")
    private String senderInvoiceNo;

    @JsonProperty("invoice_receiver_code")
    private String invoiceReceiverCode;

    @JsonProperty("invoice_description")
    private String invoiceDescription;

    @JsonProperty("sender_branch_code")
    private String senderBranchCode;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("callback_url")
    private String callbackURL;

    public CreateSimpleInvoiceRequest() {
    }

    private CreateSimpleInvoiceRequest(Builder builder) {
        this.invoiceCode = builder.invoiceCode;
        this.senderInvoiceNo = builder.senderInvoiceNo;
        this.invoiceReceiverCode = builder.invoiceReceiverCode;
        this.invoiceDescription = builder.invoiceDescription;
        this.senderBranchCode = builder.senderBranchCode;
        this.amount = builder.amount;
        this.callbackURL = builder.callbackURL;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; }

    public String getSenderInvoiceNo() { return senderInvoiceNo; }
    public void setSenderInvoiceNo(String senderInvoiceNo) { this.senderInvoiceNo = senderInvoiceNo; }

    public String getInvoiceReceiverCode() { return invoiceReceiverCode; }
    public void setInvoiceReceiverCode(String invoiceReceiverCode) { this.invoiceReceiverCode = invoiceReceiverCode; }

    public String getInvoiceDescription() { return invoiceDescription; }
    public void setInvoiceDescription(String invoiceDescription) { this.invoiceDescription = invoiceDescription; }

    public String getSenderBranchCode() { return senderBranchCode; }
    public void setSenderBranchCode(String senderBranchCode) { this.senderBranchCode = senderBranchCode; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCallbackURL() { return callbackURL; }
    public void setCallbackURL(String callbackURL) { this.callbackURL = callbackURL; }

    public static class Builder {
        private String invoiceCode;
        private String senderInvoiceNo;
        private String invoiceReceiverCode;
        private String invoiceDescription;
        private String senderBranchCode;
        private double amount;
        private String callbackURL;

        public Builder invoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; return this; }
        public Builder senderInvoiceNo(String senderInvoiceNo) { this.senderInvoiceNo = senderInvoiceNo; return this; }
        public Builder invoiceReceiverCode(String invoiceReceiverCode) { this.invoiceReceiverCode = invoiceReceiverCode; return this; }
        public Builder invoiceDescription(String invoiceDescription) { this.invoiceDescription = invoiceDescription; return this; }
        public Builder senderBranchCode(String senderBranchCode) { this.senderBranchCode = senderBranchCode; return this; }
        public Builder amount(double amount) { this.amount = amount; return this; }
        public Builder callbackURL(String callbackURL) { this.callbackURL = callbackURL; return this; }

        public CreateSimpleInvoiceRequest build() {
            return new CreateSimpleInvoiceRequest(this);
        }
    }
}
