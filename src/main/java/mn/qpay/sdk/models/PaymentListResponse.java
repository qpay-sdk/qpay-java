package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentListResponse {

    @JsonProperty("count")
    private int count;

    @JsonProperty("rows")
    private List<PaymentListItem> rows;

    public PaymentListResponse() {
    }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public List<PaymentListItem> getRows() { return rows; }
    public void setRows(List<PaymentListItem> rows) { this.rows = rows; }

    public static class PaymentListItem {

        @JsonProperty("payment_id")
        private String paymentId;

        @JsonProperty("payment_date")
        private String paymentDate;

        @JsonProperty("payment_status")
        private String paymentStatus;

        @JsonProperty("payment_fee")
        private String paymentFee;

        @JsonProperty("payment_amount")
        private String paymentAmount;

        @JsonProperty("payment_currency")
        private String paymentCurrency;

        @JsonProperty("payment_wallet")
        private String paymentWallet;

        @JsonProperty("payment_name")
        private String paymentName;

        @JsonProperty("payment_description")
        private String paymentDescription;

        @JsonProperty("qr_code")
        private String qrCode;

        @JsonProperty("paid_by")
        private String paidBy;

        @JsonProperty("object_type")
        private String objectType;

        @JsonProperty("object_id")
        private String objectId;

        public PaymentListItem() {
        }

        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

        public String getPaymentDate() { return paymentDate; }
        public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

        public String getPaymentFee() { return paymentFee; }
        public void setPaymentFee(String paymentFee) { this.paymentFee = paymentFee; }

        public String getPaymentAmount() { return paymentAmount; }
        public void setPaymentAmount(String paymentAmount) { this.paymentAmount = paymentAmount; }

        public String getPaymentCurrency() { return paymentCurrency; }
        public void setPaymentCurrency(String paymentCurrency) { this.paymentCurrency = paymentCurrency; }

        public String getPaymentWallet() { return paymentWallet; }
        public void setPaymentWallet(String paymentWallet) { this.paymentWallet = paymentWallet; }

        public String getPaymentName() { return paymentName; }
        public void setPaymentName(String paymentName) { this.paymentName = paymentName; }

        public String getPaymentDescription() { return paymentDescription; }
        public void setPaymentDescription(String paymentDescription) { this.paymentDescription = paymentDescription; }

        public String getQrCode() { return qrCode; }
        public void setQrCode(String qrCode) { this.qrCode = qrCode; }

        public String getPaidBy() { return paidBy; }
        public void setPaidBy(String paidBy) { this.paidBy = paidBy; }

        public String getObjectType() { return objectType; }
        public void setObjectType(String objectType) { this.objectType = objectType; }

        public String getObjectId() { return objectId; }
        public void setObjectId(String objectId) { this.objectId = objectId; }
    }
}
