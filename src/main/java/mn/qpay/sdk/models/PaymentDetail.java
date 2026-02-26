package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.CardTransaction;
import mn.qpay.sdk.models.common.P2PTransaction;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDetail {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("payment_fee")
    private String paymentFee;

    @JsonProperty("payment_amount")
    private String paymentAmount;

    @JsonProperty("payment_currency")
    private String paymentCurrency;

    @JsonProperty("payment_date")
    private String paymentDate;

    @JsonProperty("payment_wallet")
    private String paymentWallet;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("object_type")
    private String objectType;

    @JsonProperty("object_id")
    private String objectId;

    @JsonProperty("next_payment_date")
    private String nextPaymentDate;

    @JsonProperty("next_payment_datetime")
    private String nextPaymentDatetime;

    @JsonProperty("card_transactions")
    private List<CardTransaction> cardTransactions;

    @JsonProperty("p2p_transactions")
    private List<P2PTransaction> p2pTransactions;

    public PaymentDetail() {
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentFee() { return paymentFee; }
    public void setPaymentFee(String paymentFee) { this.paymentFee = paymentFee; }

    public String getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(String paymentAmount) { this.paymentAmount = paymentAmount; }

    public String getPaymentCurrency() { return paymentCurrency; }
    public void setPaymentCurrency(String paymentCurrency) { this.paymentCurrency = paymentCurrency; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentWallet() { return paymentWallet; }
    public void setPaymentWallet(String paymentWallet) { this.paymentWallet = paymentWallet; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }

    public String getNextPaymentDate() { return nextPaymentDate; }
    public void setNextPaymentDate(String nextPaymentDate) { this.nextPaymentDate = nextPaymentDate; }

    public String getNextPaymentDatetime() { return nextPaymentDatetime; }
    public void setNextPaymentDatetime(String nextPaymentDatetime) { this.nextPaymentDatetime = nextPaymentDatetime; }

    public List<CardTransaction> getCardTransactions() { return cardTransactions; }
    public void setCardTransactions(List<CardTransaction> cardTransactions) { this.cardTransactions = cardTransactions; }

    public List<P2PTransaction> getP2pTransactions() { return p2pTransactions; }
    public void setP2pTransactions(List<P2PTransaction> p2pTransactions) { this.p2pTransactions = p2pTransactions; }
}
