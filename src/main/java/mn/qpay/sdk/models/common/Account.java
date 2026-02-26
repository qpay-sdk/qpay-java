package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonProperty("account_bank_code")
    private String accountBankCode;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("iban_number")
    private String ibanNumber;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_currency")
    private String accountCurrency;

    @JsonProperty("is_default")
    private boolean isDefault;

    public Account() {
    }

    private Account(Builder builder) {
        this.accountBankCode = builder.accountBankCode;
        this.accountNumber = builder.accountNumber;
        this.ibanNumber = builder.ibanNumber;
        this.accountName = builder.accountName;
        this.accountCurrency = builder.accountCurrency;
        this.isDefault = builder.isDefault;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAccountBankCode() {
        return accountBankCode;
    }

    public void setAccountBankCode(String accountBankCode) {
        this.accountBankCode = accountBankCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public static class Builder {
        private String accountBankCode;
        private String accountNumber;
        private String ibanNumber;
        private String accountName;
        private String accountCurrency;
        private boolean isDefault;

        public Builder accountBankCode(String accountBankCode) {
            this.accountBankCode = accountBankCode;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder ibanNumber(String ibanNumber) {
            this.ibanNumber = ibanNumber;
            return this;
        }

        public Builder accountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder accountCurrency(String accountCurrency) {
            this.accountCurrency = accountCurrency;
            return this;
        }

        public Builder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
