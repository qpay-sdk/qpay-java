package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.Offset;

public class PaymentListRequest {

    @JsonProperty("object_type")
    private String objectType;

    @JsonProperty("object_id")
    private String objectId;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("offset")
    private Offset offset;

    public PaymentListRequest() {
    }

    private PaymentListRequest(Builder builder) {
        this.objectType = builder.objectType;
        this.objectId = builder.objectId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.offset = builder.offset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Offset getOffset() { return offset; }
    public void setOffset(Offset offset) { this.offset = offset; }

    public static class Builder {
        private String objectType;
        private String objectId;
        private String startDate;
        private String endDate;
        private Offset offset;

        public Builder objectType(String objectType) { this.objectType = objectType; return this; }
        public Builder objectId(String objectId) { this.objectId = objectId; return this; }
        public Builder startDate(String startDate) { this.startDate = startDate; return this; }
        public Builder endDate(String endDate) { this.endDate = endDate; return this; }
        public Builder offset(Offset offset) { this.offset = offset; return this; }

        public PaymentListRequest build() {
            return new PaymentListRequest(this);
        }
    }
}
