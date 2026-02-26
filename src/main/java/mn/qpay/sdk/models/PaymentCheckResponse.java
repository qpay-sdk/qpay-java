package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.CardTransaction;
import mn.qpay.sdk.models.common.P2PTransaction;

import java.util.List;

public class PaymentCheckResponse {

    @JsonProperty("count")
    private int count;

    @JsonProperty("paid_amount")
    private double paidAmount;

    @JsonProperty("rows")
    private List<PaymentCheckRow> rows;

    public PaymentCheckResponse() {
    }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }

    public List<PaymentCheckRow> getRows() { return rows; }
    public void setRows(List<PaymentCheckRow> rows) { this.rows = rows; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaymentCheckRow {

        @JsonProperty("payment_id")
        private String paymentId;

        @JsonProperty("payment_status")
        private String paymentStatus;

        @JsonProperty("payment_amount")
        private String paymentAmount;

        @JsonProperty("trx_fee")
        private String trxFee;

        @JsonProperty("payment_currency")
        private String paymentCurrency;

        @JsonProperty("payment_wallet")
        private String paymentWallet;

        @JsonProperty("payment_type")
        private String paymentType;

        @JsonProperty("next_payment_date")
        private String nextPaymentDate;

        @JsonProperty("next_payment_datetime")
        private String nextPaymentDatetime;

        @JsonProperty("card_transactions")
        private List<CardTransaction> cardTransactions;

        @JsonProperty("p2p_transactions")
        private List<P2PTransaction> p2pTransactions;

        public PaymentCheckRow() {
        }

        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

        public String getPaymentAmount() { return paymentAmount; }
        public void setPaymentAmount(String paymentAmount) { this.paymentAmount = paymentAmount; }

        public String getTrxFee() { return trxFee; }
        public void setTrxFee(String trxFee) { this.trxFee = trxFee; }

        public String getPaymentCurrency() { return paymentCurrency; }
        public void setPaymentCurrency(String paymentCurrency) { this.paymentCurrency = paymentCurrency; }

        public String getPaymentWallet() { return paymentWallet; }
        public void setPaymentWallet(String paymentWallet) { this.paymentWallet = paymentWallet; }

        public String getPaymentType() { return paymentType; }
        public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

        public String getNextPaymentDate() { return nextPaymentDate; }
        public void setNextPaymentDate(String nextPaymentDate) { this.nextPaymentDate = nextPaymentDate; }

        public String getNextPaymentDatetime() { return nextPaymentDatetime; }
        public void setNextPaymentDatetime(String nextPaymentDatetime) { this.nextPaymentDatetime = nextPaymentDatetime; }

        public List<CardTransaction> getCardTransactions() { return cardTransactions; }
        public void setCardTransactions(List<CardTransaction> cardTransactions) { this.cardTransactions = cardTransactions; }

        public List<P2PTransaction> getP2pTransactions() { return p2pTransactions; }
        public void setP2pTransactions(List<P2PTransaction> p2pTransactions) { this.p2pTransactions = p2pTransactions; }
    }
}
