package org.noorganization.instalist.server.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group extends EntityObject {
    private String mReadableId;

    @JsonProperty("groupid")
    public String getReadableId() {
        return mReadableId;
    }

    @JsonProperty("groupid")
    public void setReadableId(String _readableId) {
        mReadableId = _readableId;
    }

    public Group withReadableId(String _id) {
        mReadableId = _id;
        return this;
    }
}
