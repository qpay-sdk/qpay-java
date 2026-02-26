package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class P2PTransaction {

    @JsonProperty("transaction_bank_code")
    private String transactionBankCode;

    @JsonProperty("account_bank_code")
    private String accountBankCode;

    @JsonProperty("account_bank_name")
    private String accountBankName;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("settlement_status")
    private String settlementStatus;

    public P2PTransaction() {
    }

    public String getTransactionBankCode() {
        return transactionBankCode;
    }

    public void setTransactionBankCode(String transactionBankCode) {
        this.transactionBankCode = transactionBankCode;
    }

    public String getAccountBankCode() {
        return accountBankCode;
    }

    public void setAccountBankCode(String accountBankCode) {
        this.accountBankCode = accountBankCode;
    }

    public String getAccountBankName() {
        return accountBankName;
    }

    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }
}
