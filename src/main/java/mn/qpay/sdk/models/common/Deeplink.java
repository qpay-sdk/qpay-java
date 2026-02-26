package mn.qpay.sdk.models.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Deeplink {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("link")
    private String link;

    public Deeplink() {
    }

    public Deeplink(String name, String description, String logo, String link) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
