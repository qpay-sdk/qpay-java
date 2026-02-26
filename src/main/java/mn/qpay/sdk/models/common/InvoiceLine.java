package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceLine {

    @JsonProperty("tax_product_code")
    private String taxProductCode;

    @JsonProperty("line_description")
    private String lineDescription;

    @JsonProperty("line_quantity")
    private String lineQuantity;

    @JsonProperty("line_unit_price")
    private String lineUnitPrice;

    @JsonProperty("note")
    private String note;

    @JsonProperty("discounts")
    private List<TaxEntry> discounts;

    @JsonProperty("surcharges")
    private List<TaxEntry> surcharges;

    @JsonProperty("taxes")
    private List<TaxEntry> taxes;

    public InvoiceLine() {
    }

    private InvoiceLine(Builder builder) {
        this.taxProductCode = builder.taxProductCode;
        this.lineDescription = builder.lineDescription;
        this.lineQuantity = builder.lineQuantity;
        this.lineUnitPrice = builder.lineUnitPrice;
        this.note = builder.note;
        this.discounts = builder.discounts;
        this.surcharges = builder.surcharges;
        this.taxes = builder.taxes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTaxProductCode() {
        return taxProductCode;
    }

    public void setTaxProductCode(String taxProductCode) {
        this.taxProductCode = taxProductCode;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }

    public String getLineQuantity() {
        return lineQuantity;
    }

    public void setLineQuantity(String lineQuantity) {
        this.lineQuantity = lineQuantity;
    }

    public String getLineUnitPrice() {
        return lineUnitPrice;
    }

    public void setLineUnitPrice(String lineUnitPrice) {
        this.lineUnitPrice = lineUnitPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<TaxEntry> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<TaxEntry> discounts) {
        this.discounts = discounts;
    }

    public List<TaxEntry> getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(List<TaxEntry> surcharges) {
        this.surcharges = surcharges;
    }

    public List<TaxEntry> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxEntry> taxes) {
        this.taxes = taxes;
    }

    public static class Builder {
        private String taxProductCode;
        private String lineDescription;
        private String lineQuantity;
        private String lineUnitPrice;
        private String note;
        private List<TaxEntry> discounts;
        private List<TaxEntry> surcharges;
        private List<TaxEntry> taxes;

        public Builder taxProductCode(String taxProductCode) {
            this.taxProductCode = taxProductCode;
            return this;
        }

        public Builder lineDescription(String lineDescription) {
            this.lineDescription = lineDescription;
            return this;
        }

        public Builder lineQuantity(String lineQuantity) {
            this.lineQuantity = lineQuantity;
            return this;
        }

        public Builder lineUnitPrice(String lineUnitPrice) {
            this.lineUnitPrice = lineUnitPrice;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder discounts(List<TaxEntry> discounts) {
            this.discounts = discounts;
            return this;
        }

        public Builder surcharges(List<TaxEntry> surcharges) {
            this.surcharges = surcharges;
            return this;
        }

        public Builder taxes(List<TaxEntry> taxes) {
            this.taxes = taxes;
            return this;
        }

        public InvoiceLine build() {
            return new InvoiceLine(this);
        }
    }

    /**
     * Tax, discount, or surcharge entry used within invoice lines.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TaxEntry {

        @JsonProperty("tax_code")
        private String taxCode;

        @JsonProperty("discount_code")
        private String discountCode;

        @JsonProperty("surcharge_code")
        private String surchargeCode;

        @JsonProperty("description")
        private String description;

        @JsonProperty("amount")
        private double amount;

        @JsonProperty("note")
        private String note;

        public TaxEntry() {
        }

        private TaxEntry(TaxEntryBuilder builder) {
            this.taxCode = builder.taxCode;
            this.discountCode = builder.discountCode;
            this.surchargeCode = builder.surchargeCode;
            this.description = builder.description;
            this.amount = builder.amount;
            this.note = builder.note;
        }

        public static TaxEntryBuilder builder() {
            return new TaxEntryBuilder();
        }

        public String getTaxCode() {
            return taxCode;
        }

        public void setTaxCode(String taxCode) {
            this.taxCode = taxCode;
        }

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
        }

        public String getSurchargeCode() {
            return surchargeCode;
        }

        public void setSurchargeCode(String surchargeCode) {
            this.surchargeCode = surchargeCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public static class TaxEntryBuilder {
            private String taxCode;
            private String discountCode;
            private String surchargeCode;
            private String description;
            private double amount;
            private String note;

            public TaxEntryBuilder taxCode(String taxCode) {
                this.taxCode = taxCode;
                return this;
            }

            public TaxEntryBuilder discountCode(String discountCode) {
                this.discountCode = discountCode;
                return this;
            }

            public TaxEntryBuilder surchargeCode(String surchargeCode) {
                this.surchargeCode = surchargeCode;
                return this;
            }

            public TaxEntryBuilder description(String description) {
                this.description = description;
                return this;
            }

            public TaxEntryBuilder amount(double amount) {
                this.amount = amount;
                return this;
            }

            public TaxEntryBuilder note(String note) {
                this.note = note;
                return this;
            }

            public TaxEntry build() {
                return new TaxEntry(this);
            }
        }
    }
}
