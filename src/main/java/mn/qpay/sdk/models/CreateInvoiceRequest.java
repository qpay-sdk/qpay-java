package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.Address;
import mn.qpay.sdk.models.common.InvoiceLine;
import mn.qpay.sdk.models.common.Transaction;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateInvoiceRequest {

    @JsonProperty("invoice_code")
    private String invoiceCode;

    @JsonProperty("sender_invoice_no")
    private String senderInvoiceNo;

    @JsonProperty("sender_branch_code")
    private String senderBranchCode;

    @JsonProperty("sender_branch_data")
    private SenderBranchData senderBranchData;

    @JsonProperty("sender_staff_data")
    private SenderStaffData senderStaffData;

    @JsonProperty("sender_staff_code")
    private String senderStaffCode;

    @JsonProperty("invoice_receiver_code")
    private String invoiceReceiverCode;

    @JsonProperty("invoice_receiver_data")
    private InvoiceReceiverData invoiceReceiverData;

    @JsonProperty("invoice_description")
    private String invoiceDescription;

    @JsonProperty("enable_expiry")
    private String enableExpiry;

    @JsonProperty("allow_partial")
    private Boolean allowPartial;

    @JsonProperty("minimum_amount")
    private Double minimumAmount;

    @JsonProperty("allow_exceed")
    private Boolean allowExceed;

    @JsonProperty("maximum_amount")
    private Double maximumAmount;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("callback_url")
    private String callbackURL;

    @JsonProperty("sender_terminal_code")
    private String senderTerminalCode;

    @JsonProperty("sender_terminal_data")
    private Object senderTerminalData;

    @JsonProperty("allow_subscribe")
    private Boolean allowSubscribe;

    @JsonProperty("subscription_interval")
    private String subscriptionInterval;

    @JsonProperty("subscription_webhook")
    private String subscriptionWebhook;

    @JsonProperty("note")
    private String note;

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    @JsonProperty("lines")
    private List<InvoiceLine> lines;

    public CreateInvoiceRequest() {
    }

    private CreateInvoiceRequest(Builder builder) {
        this.invoiceCode = builder.invoiceCode;
        this.senderInvoiceNo = builder.senderInvoiceNo;
        this.senderBranchCode = builder.senderBranchCode;
        this.senderBranchData = builder.senderBranchData;
        this.senderStaffData = builder.senderStaffData;
        this.senderStaffCode = builder.senderStaffCode;
        this.invoiceReceiverCode = builder.invoiceReceiverCode;
        this.invoiceReceiverData = builder.invoiceReceiverData;
        this.invoiceDescription = builder.invoiceDescription;
        this.enableExpiry = builder.enableExpiry;
        this.allowPartial = builder.allowPartial;
        this.minimumAmount = builder.minimumAmount;
        this.allowExceed = builder.allowExceed;
        this.maximumAmount = builder.maximumAmount;
        this.amount = builder.amount;
        this.callbackURL = builder.callbackURL;
        this.senderTerminalCode = builder.senderTerminalCode;
        this.senderTerminalData = builder.senderTerminalData;
        this.allowSubscribe = builder.allowSubscribe;
        this.subscriptionInterval = builder.subscriptionInterval;
        this.subscriptionWebhook = builder.subscriptionWebhook;
        this.note = builder.note;
        this.transactions = builder.transactions;
        this.lines = builder.lines;
    }

    public static Builder builder() {
        return new Builder();
    }

    // --- Getters and Setters ---

    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; }

    public String getSenderInvoiceNo() { return senderInvoiceNo; }
    public void setSenderInvoiceNo(String senderInvoiceNo) { this.senderInvoiceNo = senderInvoiceNo; }

    public String getSenderBranchCode() { return senderBranchCode; }
    public void setSenderBranchCode(String senderBranchCode) { this.senderBranchCode = senderBranchCode; }

    public SenderBranchData getSenderBranchData() { return senderBranchData; }
    public void setSenderBranchData(SenderBranchData senderBranchData) { this.senderBranchData = senderBranchData; }

    public SenderStaffData getSenderStaffData() { return senderStaffData; }
    public void setSenderStaffData(SenderStaffData senderStaffData) { this.senderStaffData = senderStaffData; }

    public String getSenderStaffCode() { return senderStaffCode; }
    public void setSenderStaffCode(String senderStaffCode) { this.senderStaffCode = senderStaffCode; }

    public String getInvoiceReceiverCode() { return invoiceReceiverCode; }
    public void setInvoiceReceiverCode(String invoiceReceiverCode) { this.invoiceReceiverCode = invoiceReceiverCode; }

    public InvoiceReceiverData getInvoiceReceiverData() { return invoiceReceiverData; }
    public void setInvoiceReceiverData(InvoiceReceiverData invoiceReceiverData) { this.invoiceReceiverData = invoiceReceiverData; }

    public String getInvoiceDescription() { return invoiceDescription; }
    public void setInvoiceDescription(String invoiceDescription) { this.invoiceDescription = invoiceDescription; }

    public String getEnableExpiry() { return enableExpiry; }
    public void setEnableExpiry(String enableExpiry) { this.enableExpiry = enableExpiry; }

    public Boolean getAllowPartial() { return allowPartial; }
    public void setAllowPartial(Boolean allowPartial) { this.allowPartial = allowPartial; }

    public Double getMinimumAmount() { return minimumAmount; }
    public void setMinimumAmount(Double minimumAmount) { this.minimumAmount = minimumAmount; }

    public Boolean getAllowExceed() { return allowExceed; }
    public void setAllowExceed(Boolean allowExceed) { this.allowExceed = allowExceed; }

    public Double getMaximumAmount() { return maximumAmount; }
    public void setMaximumAmount(Double maximumAmount) { this.maximumAmount = maximumAmount; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCallbackURL() { return callbackURL; }
    public void setCallbackURL(String callbackURL) { this.callbackURL = callbackURL; }

    public String getSenderTerminalCode() { return senderTerminalCode; }
    public void setSenderTerminalCode(String senderTerminalCode) { this.senderTerminalCode = senderTerminalCode; }

    public Object getSenderTerminalData() { return senderTerminalData; }
    public void setSenderTerminalData(Object senderTerminalData) { this.senderTerminalData = senderTerminalData; }

    public Boolean getAllowSubscribe() { return allowSubscribe; }
    public void setAllowSubscribe(Boolean allowSubscribe) { this.allowSubscribe = allowSubscribe; }

    public String getSubscriptionInterval() { return subscriptionInterval; }
    public void setSubscriptionInterval(String subscriptionInterval) { this.subscriptionInterval = subscriptionInterval; }

    public String getSubscriptionWebhook() { return subscriptionWebhook; }
    public void setSubscriptionWebhook(String subscriptionWebhook) { this.subscriptionWebhook = subscriptionWebhook; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public List<InvoiceLine> getLines() { return lines; }
    public void setLines(List<InvoiceLine> lines) { this.lines = lines; }

    // --- Nested types ---

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SenderBranchData {

        @JsonProperty("register")
        private String register;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private Address address;

        public SenderBranchData() {
        }

        private SenderBranchData(SenderBranchDataBuilder builder) {
            this.register = builder.register;
            this.name = builder.name;
            this.email = builder.email;
            this.phone = builder.phone;
            this.address = builder.address;
        }

        public static SenderBranchDataBuilder builder() {
            return new SenderBranchDataBuilder();
        }

        public String getRegister() { return register; }
        public void setRegister(String register) { this.register = register; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }

        public static class SenderBranchDataBuilder {
            private String register;
            private String name;
            private String email;
            private String phone;
            private Address address;

            public SenderBranchDataBuilder register(String register) { this.register = register; return this; }
            public SenderBranchDataBuilder name(String name) { this.name = name; return this; }
            public SenderBranchDataBuilder email(String email) { this.email = email; return this; }
            public SenderBranchDataBuilder phone(String phone) { this.phone = phone; return this; }
            public SenderBranchDataBuilder address(Address address) { this.address = address; return this; }

            public SenderBranchData build() {
                return new SenderBranchData(this);
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SenderStaffData {

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        public SenderStaffData() {
        }

        private SenderStaffData(SenderStaffDataBuilder builder) {
            this.name = builder.name;
            this.email = builder.email;
            this.phone = builder.phone;
        }

        public static SenderStaffDataBuilder builder() {
            return new SenderStaffDataBuilder();
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public static class SenderStaffDataBuilder {
            private String name;
            private String email;
            private String phone;

            public SenderStaffDataBuilder name(String name) { this.name = name; return this; }
            public SenderStaffDataBuilder email(String email) { this.email = email; return this; }
            public SenderStaffDataBuilder phone(String phone) { this.phone = phone; return this; }

            public SenderStaffData build() {
                return new SenderStaffData(this);
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InvoiceReceiverData {

        @JsonProperty("register")
        private String register;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private Address address;

        public InvoiceReceiverData() {
        }

        private InvoiceReceiverData(InvoiceReceiverDataBuilder builder) {
            this.register = builder.register;
            this.name = builder.name;
            this.email = builder.email;
            this.phone = builder.phone;
            this.address = builder.address;
        }

        public static InvoiceReceiverDataBuilder builder() {
            return new InvoiceReceiverDataBuilder();
        }

        public String getRegister() { return register; }
        public void setRegister(String register) { this.register = register; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }

        public static class InvoiceReceiverDataBuilder {
            private String register;
            private String name;
            private String email;
            private String phone;
            private Address address;

            public InvoiceReceiverDataBuilder register(String register) { this.register = register; return this; }
            public InvoiceReceiverDataBuilder name(String name) { this.name = name; return this; }
            public InvoiceReceiverDataBuilder email(String email) { this.email = email; return this; }
            public InvoiceReceiverDataBuilder phone(String phone) { this.phone = phone; return this; }
            public InvoiceReceiverDataBuilder address(Address address) { this.address = address; return this; }

            public InvoiceReceiverData build() {
                return new InvoiceReceiverData(this);
            }
        }
    }

    // --- Builder ---

    public static class Builder {
        private String invoiceCode;
        private String senderInvoiceNo;
        private String senderBranchCode;
        private SenderBranchData senderBranchData;
        private SenderStaffData senderStaffData;
        private String senderStaffCode;
        private String invoiceReceiverCode;
        private InvoiceReceiverData invoiceReceiverData;
        private String invoiceDescription;
        private String enableExpiry;
        private Boolean allowPartial;
        private Double minimumAmount;
        private Boolean allowExceed;
        private Double maximumAmount;
        private double amount;
        private String callbackURL;
        private String senderTerminalCode;
        private Object senderTerminalData;
        private Boolean allowSubscribe;
        private String subscriptionInterval;
        private String subscriptionWebhook;
        private String note;
        private List<Transaction> transactions;
        private List<InvoiceLine> lines;

        public Builder invoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; return this; }
        public Builder senderInvoiceNo(String senderInvoiceNo) { this.senderInvoiceNo = senderInvoiceNo; return this; }
        public Builder senderBranchCode(String senderBranchCode) { this.senderBranchCode = senderBranchCode; return this; }
        public Builder senderBranchData(SenderBranchData senderBranchData) { this.senderBranchData = senderBranchData; return this; }
        public Builder senderStaffData(SenderStaffData senderStaffData) { this.senderStaffData = senderStaffData; return this; }
        public Builder senderStaffCode(String senderStaffCode) { this.senderStaffCode = senderStaffCode; return this; }
        public Builder invoiceReceiverCode(String invoiceReceiverCode) { this.invoiceReceiverCode = invoiceReceiverCode; return this; }
        public Builder invoiceReceiverData(InvoiceReceiverData invoiceReceiverData) { this.invoiceReceiverData = invoiceReceiverData; return this; }
        public Builder invoiceDescription(String invoiceDescription) { this.invoiceDescription = invoiceDescription; return this; }
        public Builder enableExpiry(String enableExpiry) { this.enableExpiry = enableExpiry; return this; }
        public Builder allowPartial(Boolean allowPartial) { this.allowPartial = allowPartial; return this; }
        public Builder minimumAmount(Double minimumAmount) { this.minimumAmount = minimumAmount; return this; }
        public Builder allowExceed(Boolean allowExceed) { this.allowExceed = allowExceed; return this; }
        public Builder maximumAmount(Double maximumAmount) { this.maximumAmount = maximumAmount; return this; }
        public Builder amount(double amount) { this.amount = amount; return this; }
        public Builder callbackURL(String callbackURL) { this.callbackURL = callbackURL; return this; }
        public Builder senderTerminalCode(String senderTerminalCode) { this.senderTerminalCode = senderTerminalCode; return this; }
        public Builder senderTerminalData(Object senderTerminalData) { this.senderTerminalData = senderTerminalData; return this; }
        public Builder allowSubscribe(Boolean allowSubscribe) { this.allowSubscribe = allowSubscribe; return this; }
        public Builder subscriptionInterval(String subscriptionInterval) { this.subscriptionInterval = subscriptionInterval; return this; }
        public Builder subscriptionWebhook(String subscriptionWebhook) { this.subscriptionWebhook = subscriptionWebhook; return this; }
        public Builder note(String note) { this.note = note; return this; }
        public Builder transactions(List<Transaction> transactions) { this.transactions = transactions; return this; }
        public Builder lines(List<InvoiceLine> lines) { this.lines = lines; return this; }

        public CreateInvoiceRequest build() {
            return new CreateInvoiceRequest(this);
        }
    }
}
