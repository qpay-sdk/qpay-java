package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Offset {

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("page_limit")
    private int pageLimit;

    public Offset() {
    }

    public Offset(int pageNumber, int pageLimit) {
        this.pageNumber = pageNumber;
        this.pageLimit = pageLimit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }
}
