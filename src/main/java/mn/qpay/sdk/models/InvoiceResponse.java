package mn.qpay.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import mn.qpay.sdk.models.common.Deeplink;

import java.util.List;

public class InvoiceResponse {

    @JsonProperty("invoice_id")
    private String invoiceId;

    @JsonProperty("qr_text")
    private String qrText;

    @JsonProperty("qr_image")
    private String qrImage;

    @JsonProperty("qPay_shortUrl")
    private String qPayShortUrl;

    @JsonProperty("urls")
    private List<Deeplink> urls;

    public InvoiceResponse() {
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getQrText() {
        return qrText;
    }

    public void setQrText(String qrText) {
        this.qrText = qrText;
    }

    public String getQrImage() {
        return qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }

    public String getQPayShortUrl() {
        return qPayShortUrl;
    }

    public void setQPayShortUrl(String qPayShortUrl) {
        this.qPayShortUrl = qPayShortUrl;
    }

    public List<Deeplink> getUrls() {
        return urls;
    }

    public void setUrls(List<Deeplink> urls) {
        this.urls = urls;
    }
}
