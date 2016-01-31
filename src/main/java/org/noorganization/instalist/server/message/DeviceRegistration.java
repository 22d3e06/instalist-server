package org.noorganization.instalist.server.message;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by damihe on 29.01.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "groupid", "secret", "name" })
public class DeviceRegistration extends EntityObject {

    private String mGroupId;

    private String mSecret;

    private String mName;

    @JsonProperty("groupid")
    public String getGroupReadableId() {
        return mGroupId;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    @JsonProperty("secret")
    public String getSecret() {
        return mSecret;
    }

    @JsonProperty("groupid")
    public void setGroupId(String _groupId) {
        mGroupId = _groupId;
    }

    public DeviceRegistration withGroupId(String _groupId) {
        mGroupId = _groupId;
        return this;
    }

    @JsonProperty("name")
    public void setName(String _name) {
        mName = _name;
    }

    public DeviceRegistration withName(String _name) {
        mName = _name;
        return this;
    }

    @JsonProperty("secret")
    public void setSecret(String _secret) {
        mSecret = _secret;
    }

    public DeviceRegistration withSecret(String _secret) {
        mSecret = _secret;
        return this;
    }
}
