package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEbarimtRequest {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("ebarimt_receiver_type")
    private String ebarimtReceiverType;

    @JsonProperty("ebarimt_receiver")
    private String ebarimtReceiver;

    @JsonProperty("district_code")
    private String districtCode;

    @JsonProperty("classification_code")
    private String classificationCode;

    public CreateEbarimtRequest() {
    }

    private CreateEbarimtRequest(Builder builder) {
        this.paymentId = builder.paymentId;
        this.ebarimtReceiverType = builder.ebarimtReceiverType;
        this.ebarimtReceiver = builder.ebarimtReceiver;
        this.districtCode = builder.districtCode;
        this.classificationCode = builder.classificationCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getEbarimtReceiverType() { return ebarimtReceiverType; }
    public void setEbarimtReceiverType(String ebarimtReceiverType) { this.ebarimtReceiverType = ebarimtReceiverType; }

    public String getEbarimtReceiver() { return ebarimtReceiver; }
    public void setEbarimtReceiver(String ebarimtReceiver) { this.ebarimtReceiver = ebarimtReceiver; }

    public String getDistrictCode() { return districtCode; }
    public void setDistrictCode(String districtCode) { this.districtCode = districtCode; }

    public String getClassificationCode() { return classificationCode; }
    public void setClassificationCode(String classificationCode) { this.classificationCode = classificationCode; }

    public static class Builder {
        private String paymentId;
        private String ebarimtReceiverType;
        private String ebarimtReceiver;
        private String districtCode;
        private String classificationCode;

        public Builder paymentId(String paymentId) { this.paymentId = paymentId; return this; }
        public Builder ebarimtReceiverType(String ebarimtReceiverType) { this.ebarimtReceiverType = ebarimtReceiverType; return this; }
        public Builder ebarimtReceiver(String ebarimtReceiver) { this.ebarimtReceiver = ebarimtReceiver; return this; }
        public Builder districtCode(String districtCode) { this.districtCode = districtCode; return this; }
        public Builder classificationCode(String classificationCode) { this.classificationCode = classificationCode; return this; }

        public CreateEbarimtRequest build() {
            return new CreateEbarimtRequest(this);
        }
    }
}
