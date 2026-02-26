package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EbarimtResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("ebarimt_by")
    private String ebarimtBy;

    @JsonProperty("g_wallet_id")
    private String gWalletId;

    @JsonProperty("g_wallet_customer_id")
    private String gWalletCustomerId;

    @JsonProperty("ebarimt_receiver_type")
    private String ebarimtReceiverType;

    @JsonProperty("ebarimt_receiver")
    private String ebarimtReceiver;

    @JsonProperty("ebarimt_district_code")
    private String ebarimtDistrictCode;

    @JsonProperty("ebarimt_bill_type")
    private String ebarimtBillType;

    @JsonProperty("g_merchant_id")
    private String gMerchantId;

    @JsonProperty("merchant_branch_code")
    private String merchantBranchCode;

    @JsonProperty("merchant_terminal_code")
    private String merchantTerminalCode;

    @JsonProperty("merchant_staff_code")
    private String merchantStaffCode;

    @JsonProperty("merchant_register_no")
    private String merchantRegisterNo;

    @JsonProperty("g_payment_id")
    private String gPaymentId;

    @JsonProperty("paid_by")
    private String paidBy;

    @JsonProperty("object_type")
    private String objectType;

    @JsonProperty("object_id")
    private String objectId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("vat_amount")
    private String vatAmount;

    @JsonProperty("city_tax_amount")
    private String cityTaxAmount;

    @JsonProperty("ebarimt_qr_data")
    private String ebarimtQrData;

    @JsonProperty("ebarimt_lottery")
    private String ebarimtLottery;

    @JsonProperty("note")
    private String note;

    @JsonProperty("barimt_status")
    private String barimtStatus;

    @JsonProperty("barimt_status_date")
    private String barimtStatusDate;

    @JsonProperty("ebarimt_sent_email")
    private String ebarimtSentEmail;

    @JsonProperty("ebarimt_receiver_phone")
    private String ebarimtReceiverPhone;

    @JsonProperty("tax_type")
    private String taxType;

    @JsonProperty("merchant_tin")
    private String merchantTin;

    @JsonProperty("ebarimt_receipt_id")
    private String ebarimtReceiptId;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_date")
    private String updatedDate;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("barimt_items")
    private List<EbarimtItem> barimtItems;

    @JsonProperty("barimt_transactions")
    private List<Object> barimtTransactions;

    @JsonProperty("barimt_histories")
    private List<EbarimtHistory> barimtHistories;

    public EbarimtResponse() {
    }

    // --- Getters and Setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEbarimtBy() { return ebarimtBy; }
    public void setEbarimtBy(String ebarimtBy) { this.ebarimtBy = ebarimtBy; }

    public String getGWalletId() { return gWalletId; }
    public void setGWalletId(String gWalletId) { this.gWalletId = gWalletId; }

    public String getGWalletCustomerId() { return gWalletCustomerId; }
    public void setGWalletCustomerId(String gWalletCustomerId) { this.gWalletCustomerId = gWalletCustomerId; }

    public String getEbarimtReceiverType() { return ebarimtReceiverType; }
    public void setEbarimtReceiverType(String ebarimtReceiverType) { this.ebarimtReceiverType = ebarimtReceiverType; }

    public String getEbarimtReceiver() { return ebarimtReceiver; }
    public void setEbarimtReceiver(String ebarimtReceiver) { this.ebarimtReceiver = ebarimtReceiver; }

    public String getEbarimtDistrictCode() { return ebarimtDistrictCode; }
    public void setEbarimtDistrictCode(String ebarimtDistrictCode) { this.ebarimtDistrictCode = ebarimtDistrictCode; }

    public String getEbarimtBillType() { return ebarimtBillType; }
    public void setEbarimtBillType(String ebarimtBillType) { this.ebarimtBillType = ebarimtBillType; }

    public String getGMerchantId() { return gMerchantId; }
    public void setGMerchantId(String gMerchantId) { this.gMerchantId = gMerchantId; }

    public String getMerchantBranchCode() { return merchantBranchCode; }
    public void setMerchantBranchCode(String merchantBranchCode) { this.merchantBranchCode = merchantBranchCode; }

    public String getMerchantTerminalCode() { return merchantTerminalCode; }
    public void setMerchantTerminalCode(String merchantTerminalCode) { this.merchantTerminalCode = merchantTerminalCode; }

    public String getMerchantStaffCode() { return merchantStaffCode; }
    public void setMerchantStaffCode(String merchantStaffCode) { this.merchantStaffCode = merchantStaffCode; }

    public String getMerchantRegisterNo() { return merchantRegisterNo; }
    public void setMerchantRegisterNo(String merchantRegisterNo) { this.merchantRegisterNo = merchantRegisterNo; }

    public String getGPaymentId() { return gPaymentId; }
    public void setGPaymentId(String gPaymentId) { this.gPaymentId = gPaymentId; }

    public String getPaidBy() { return paidBy; }
    public void setPaidBy(String paidBy) { this.paidBy = paidBy; }

    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getVatAmount() { return vatAmount; }
    public void setVatAmount(String vatAmount) { this.vatAmount = vatAmount; }

    public String getCityTaxAmount() { return cityTaxAmount; }
    public void setCityTaxAmount(String cityTaxAmount) { this.cityTaxAmount = cityTaxAmount; }

    public String getEbarimtQrData() { return ebarimtQrData; }
    public void setEbarimtQrData(String ebarimtQrData) { this.ebarimtQrData = ebarimtQrData; }

    public String getEbarimtLottery() { return ebarimtLottery; }
    public void setEbarimtLottery(String ebarimtLottery) { this.ebarimtLottery = ebarimtLottery; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getBarimtStatus() { return barimtStatus; }
    public void setBarimtStatus(String barimtStatus) { this.barimtStatus = barimtStatus; }

    public String getBarimtStatusDate() { return barimtStatusDate; }
    public void setBarimtStatusDate(String barimtStatusDate) { this.barimtStatusDate = barimtStatusDate; }

    public String getEbarimtSentEmail() { return ebarimtSentEmail; }
    public void setEbarimtSentEmail(String ebarimtSentEmail) { this.ebarimtSentEmail = ebarimtSentEmail; }

    public String getEbarimtReceiverPhone() { return ebarimtReceiverPhone; }
    public void setEbarimtReceiverPhone(String ebarimtReceiverPhone) { this.ebarimtReceiverPhone = ebarimtReceiverPhone; }

    public String getTaxType() { return taxType; }
    public void setTaxType(String taxType) { this.taxType = taxType; }

    public String getMerchantTin() { return merchantTin; }
    public void setMerchantTin(String merchantTin) { this.merchantTin = merchantTin; }

    public String getEbarimtReceiptId() { return ebarimtReceiptId; }
    public void setEbarimtReceiptId(String ebarimtReceiptId) { this.ebarimtReceiptId = ebarimtReceiptId; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public List<EbarimtItem> getBarimtItems() { return barimtItems; }
    public void setBarimtItems(List<EbarimtItem> barimtItems) { this.barimtItems = barimtItems; }

    public List<Object> getBarimtTransactions() { return barimtTransactions; }
    public void setBarimtTransactions(List<Object> barimtTransactions) { this.barimtTransactions = barimtTransactions; }

    public List<EbarimtHistory> getBarimtHistories() { return barimtHistories; }
    public void setBarimtHistories(List<EbarimtHistory> barimtHistories) { this.barimtHistories = barimtHistories; }

    // --- Nested types ---

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EbarimtItem {

        @JsonProperty("id")
        private String id;

        @JsonProperty("barimt_id")
        private String barimtId;

        @JsonProperty("merchant_product_code")
        private String merchantProductCode;

        @JsonProperty("tax_product_code")
        private String taxProductCode;

        @JsonProperty("bar_code")
        private String barCode;

        @JsonProperty("name")
        private String name;

        @JsonProperty("unit_price")
        private String unitPrice;

        @JsonProperty("quantity")
        private String quantity;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("city_tax_amount")
        private String cityTaxAmount;

        @JsonProperty("vat_amount")
        private String vatAmount;

        @JsonProperty("note")
        private String note;

        @JsonProperty("created_by")
        private String createdBy;

        @JsonProperty("created_date")
        private String createdDate;

        @JsonProperty("updated_by")
        private String updatedBy;

        @JsonProperty("updated_date")
        private String updatedDate;

        @JsonProperty("status")
        private boolean status;

        public EbarimtItem() {
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getBarimtId() { return barimtId; }
        public void setBarimtId(String barimtId) { this.barimtId = barimtId; }
        public String getMerchantProductCode() { return merchantProductCode; }
        public void setMerchantProductCode(String merchantProductCode) { this.merchantProductCode = merchantProductCode; }
        public String getTaxProductCode() { return taxProductCode; }
        public void setTaxProductCode(String taxProductCode) { this.taxProductCode = taxProductCode; }
        public String getBarCode() { return barCode; }
        public void setBarCode(String barCode) { this.barCode = barCode; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUnitPrice() { return unitPrice; }
        public void setUnitPrice(String unitPrice) { this.unitPrice = unitPrice; }
        public String getQuantity() { return quantity; }
        public void setQuantity(String quantity) { this.quantity = quantity; }
        public String getAmount() { return amount; }
        public void setAmount(String amount) { this.amount = amount; }
        public String getCityTaxAmount() { return cityTaxAmount; }
        public void setCityTaxAmount(String cityTaxAmount) { this.cityTaxAmount = cityTaxAmount; }
        public String getVatAmount() { return vatAmount; }
        public void setVatAmount(String vatAmount) { this.vatAmount = vatAmount; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
        public String getCreatedBy() { return createdBy; }
        public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
        public String getCreatedDate() { return createdDate; }
        public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
        public String getUpdatedBy() { return updatedBy; }
        public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
        public String getUpdatedDate() { return updatedDate; }
        public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
        public boolean isStatus() { return status; }
        public void setStatus(boolean status) { this.status = status; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EbarimtHistory {

        @JsonProperty("id")
        private String id;

        @JsonProperty("barimt_id")
        private String barimtId;

        @JsonProperty("ebarimt_receiver_type")
        private String ebarimtReceiverType;

        @JsonProperty("ebarimt_receiver")
        private String ebarimtReceiver;

        @JsonProperty("ebarimt_register_no")
        private String ebarimtRegisterNo;

        @JsonProperty("ebarimt_bill_id")
        private String ebarimtBillId;

        @JsonProperty("ebarimt_date")
        private String ebarimtDate;

        @JsonProperty("ebarimt_mac_address")
        private String ebarimtMacAddress;

        @JsonProperty("ebarimt_internal_code")
        private String ebarimtInternalCode;

        @JsonProperty("ebarimt_bill_type")
        private String ebarimtBillType;

        @JsonProperty("ebarimt_qr_data")
        private String ebarimtQrData;

        @JsonProperty("ebarimt_lottery")
        private String ebarimtLottery;

        @JsonProperty("ebarimt_lottery_msg")
        private String ebarimtLotteryMsg;

        @JsonProperty("ebarimt_error_code")
        private String ebarimtErrorCode;

        @JsonProperty("ebarimt_error_msg")
        private String ebarimtErrorMsg;

        @JsonProperty("ebarimt_response_code")
        private String ebarimtResponseCode;

        @JsonProperty("ebarimt_response_msg")
        private String ebarimtResponseMsg;

        @JsonProperty("note")
        private String note;

        @JsonProperty("barimt_status")
        private String barimtStatus;

        @JsonProperty("barimt_status_date")
        private String barimtStatusDate;

        @JsonProperty("ebarimt_sent_email")
        private String ebarimtSentEmail;

        @JsonProperty("ebarimt_receiver_phone")
        private String ebarimtReceiverPhone;

        @JsonProperty("tax_type")
        private String taxType;

        @JsonProperty("created_by")
        private String createdBy;

        @JsonProperty("created_date")
        private String createdDate;

        @JsonProperty("updated_by")
        private String updatedBy;

        @JsonProperty("updated_date")
        private String updatedDate;

        @JsonProperty("status")
        private boolean status;

        public EbarimtHistory() {
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getBarimtId() { return barimtId; }
        public void setBarimtId(String barimtId) { this.barimtId = barimtId; }
        public String getEbarimtReceiverType() { return ebarimtReceiverType; }
        public void setEbarimtReceiverType(String ebarimtReceiverType) { this.ebarimtReceiverType = ebarimtReceiverType; }
        public String getEbarimtReceiver() { return ebarimtReceiver; }
        public void setEbarimtReceiver(String ebarimtReceiver) { this.ebarimtReceiver = ebarimtReceiver; }
        public String getEbarimtRegisterNo() { return ebarimtRegisterNo; }
        public void setEbarimtRegisterNo(String ebarimtRegisterNo) { this.ebarimtRegisterNo = ebarimtRegisterNo; }
        public String getEbarimtBillId() { return ebarimtBillId; }
        public void setEbarimtBillId(String ebarimtBillId) { this.ebarimtBillId = ebarimtBillId; }
        public String getEbarimtDate() { return ebarimtDate; }
        public void setEbarimtDate(String ebarimtDate) { this.ebarimtDate = ebarimtDate; }
        public String getEbarimtMacAddress() { return ebarimtMacAddress; }
        public void setEbarimtMacAddress(String ebarimtMacAddress) { this.ebarimtMacAddress = ebarimtMacAddress; }
        public String getEbarimtInternalCode() { return ebarimtInternalCode; }
        public void setEbarimtInternalCode(String ebarimtInternalCode) { this.ebarimtInternalCode = ebarimtInternalCode; }
        public String getEbarimtBillType() { return ebarimtBillType; }
        public void setEbarimtBillType(String ebarimtBillType) { this.ebarimtBillType = ebarimtBillType; }
        public String getEbarimtQrData() { return ebarimtQrData; }
        public void setEbarimtQrData(String ebarimtQrData) { this.ebarimtQrData = ebarimtQrData; }
        public String getEbarimtLottery() { return ebarimtLottery; }
        public void setEbarimtLottery(String ebarimtLottery) { this.ebarimtLottery = ebarimtLottery; }
        public String getEbarimtLotteryMsg() { return ebarimtLotteryMsg; }
        public void setEbarimtLotteryMsg(String ebarimtLotteryMsg) { this.ebarimtLotteryMsg = ebarimtLotteryMsg; }
        public String getEbarimtErrorCode() { return ebarimtErrorCode; }
        public void setEbarimtErrorCode(String ebarimtErrorCode) { this.ebarimtErrorCode = ebarimtErrorCode; }
        public String getEbarimtErrorMsg() { return ebarimtErrorMsg; }
        public void setEbarimtErrorMsg(String ebarimtErrorMsg) { this.ebarimtErrorMsg = ebarimtErrorMsg; }
        public String getEbarimtResponseCode() { return ebarimtResponseCode; }
        public void setEbarimtResponseCode(String ebarimtResponseCode) { this.ebarimtResponseCode = ebarimtResponseCode; }
        public String getEbarimtResponseMsg() { return ebarimtResponseMsg; }
        public void setEbarimtResponseMsg(String ebarimtResponseMsg) { this.ebarimtResponseMsg = ebarimtResponseMsg; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
        public String getBarimtStatus() { return barimtStatus; }
        public void setBarimtStatus(String barimtStatus) { this.barimtStatus = barimtStatus; }
        public String getBarimtStatusDate() { return barimtStatusDate; }
        public void setBarimtStatusDate(String barimtStatusDate) { this.barimtStatusDate = barimtStatusDate; }
        public String getEbarimtSentEmail() { return ebarimtSentEmail; }
        public void setEbarimtSentEmail(String ebarimtSentEmail) { this.ebarimtSentEmail = ebarimtSentEmail; }
        public String getEbarimtReceiverPhone() { return ebarimtReceiverPhone; }
        public void setEbarimtReceiverPhone(String ebarimtReceiverPhone) { this.ebarimtReceiverPhone = ebarimtReceiverPhone; }
        public String getTaxType() { return taxType; }
        public void setTaxType(String taxType) { this.taxType = taxType; }
        public String getCreatedBy() { return createdBy; }
        public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
        public String getCreatedDate() { return createdDate; }
        public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
        public String getUpdatedBy() { return updatedBy; }
        public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
        public String getUpdatedDate() { return updatedDate; }
        public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
        public boolean isStatus() { return status; }
        public void setStatus(boolean status) { this.status = status; }
    }
}
