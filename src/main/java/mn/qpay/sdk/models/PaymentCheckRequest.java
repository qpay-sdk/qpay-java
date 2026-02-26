package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.Offset;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentCheckRequest {

    @JsonProperty("object_type")
    private String objectType;

    @JsonProperty("object_id")
    private String objectId;

    @JsonProperty("offset")
    private Offset offset;

    public PaymentCheckRequest() {
    }

    private PaymentCheckRequest(Builder builder) {
        this.objectType = builder.objectType;
        this.objectId = builder.objectId;
        this.offset = builder.offset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }

    public Offset getOffset() { return offset; }
    public void setOffset(Offset offset) { this.offset = offset; }

    public static class Builder {
        private String objectType;
        private String objectId;
        private Offset offset;

        public Builder objectType(String objectType) { this.objectType = objectType; return this; }
        public Builder objectId(String objectId) { this.objectId = objectId; return this; }
        public Builder offset(Offset offset) { this.offset = offset; return this; }

        public PaymentCheckRequest build() {
            return new PaymentCheckRequest(this);
        }
    }
}
