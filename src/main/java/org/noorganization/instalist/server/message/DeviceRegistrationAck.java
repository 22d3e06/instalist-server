package org.noorganization.instalist.server.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by damihe on 29.01.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "deviceid" })
public class DeviceRegistrationAck extends EntityObject {

    private int mDeviceId;

    @JsonProperty("deviceid")
    public int getDeviceId() {
        return mDeviceId;
    }

    @JsonProperty("deviceid")
    public void setDeviceId(int _deviceId) {
        mDeviceId = _deviceId;
    }

    public DeviceRegistrationAck withDeviceId(int _deviceId) {
        mDeviceId = _deviceId;
        return this;
    }
}
