package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardTransaction {

    @JsonProperty("card_merchant_code")
    private String cardMerchantCode;

    @JsonProperty("card_terminal_code")
    private String cardTerminalCode;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("is_cross_border")
    private boolean isCrossBorder;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("transaction_amount")
    private String transactionAmount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("date")
    private String date;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("settlement_status")
    private String settlementStatus;

    @JsonProperty("settlement_status_date")
    private String settlementStatusDate;

    public CardTransaction() {
    }

    public String getCardMerchantCode() {
        return cardMerchantCode;
    }

    public void setCardMerchantCode(String cardMerchantCode) {
        this.cardMerchantCode = cardMerchantCode;
    }

    public String getCardTerminalCode() {
        return cardTerminalCode;
    }

    public void setCardTerminalCode(String cardTerminalCode) {
        this.cardTerminalCode = cardTerminalCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isCrossBorder() {
        return isCrossBorder;
    }

    public void setCrossBorder(boolean crossBorder) {
        isCrossBorder = crossBorder;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getSettlementStatusDate() {
        return settlementStatusDate;
    }

    public void setSettlementStatusDate(String settlementStatusDate) {
        this.settlementStatusDate = settlementStatusDate;
    }
}
