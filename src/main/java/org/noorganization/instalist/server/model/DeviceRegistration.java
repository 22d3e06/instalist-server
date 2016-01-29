package org.noorganization.instalist.server.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damihe on 29.01.16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "groupid", "message" })
public class DeviceRegistration {

    @JsonProperty("groupid")
    private String mGroupId;

    @JsonProperty("secret")
    private String mSecret;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("groupid")
    public String getGroupId() {
        return mGroupId;
    }

    @JsonProperty("secret")
    public String getSecret() {
        return mSecret;
    }

    @JsonProperty("groupid")
    public void setGroupId(String _groupId) {
        mGroupId = _groupId;
    }

    @JsonProperty("secret")
    public void setSecret(String _secret) {
        mSecret = _secret;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public DeviceRegistration withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public DeviceRegistration(){
    };
}
