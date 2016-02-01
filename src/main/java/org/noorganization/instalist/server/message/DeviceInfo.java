package org.noorganization.instalist.server.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "authorized" })
public class DeviceInfo extends EntityObject {

    private Integer mId;
    private String  mName;
    private Boolean mAuthorized;

    @JsonProperty("id")
    public Integer getId() {
        return mId;
    }

    @JsonProperty("id")
    public void setId(Integer _id) {
        mId = _id;
    }

    public DeviceInfo withId(Integer _id) {
        mId = _id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    @JsonProperty("name")
    public void setName(String _name) {
        mName = _name;
    }

    public DeviceInfo withName(String _name) {
        mName = _name;
        return this;
    }

    @JsonProperty("authorized")
    public Boolean getAuthorized() {
        return mAuthorized;
    }

    @JsonProperty("authorized")
    public void setAuthorized(Boolean _authorized) {
        mAuthorized = _authorized;
    }

    public DeviceInfo withAuthorization(Boolean _authorized) {
        mAuthorized = _authorized;
        return this;
    }
}
