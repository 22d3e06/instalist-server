package org.noorganization.instalist.server.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the positive answer for a client asking for a token.
 * Created by damihe on 31.01.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token extends EntityObject {

    @JsonProperty("token")
    public String getToken() {
        return mToken;
    }

    @JsonProperty("token")
    public void setToken(String _token) {
        mToken = _token;
    }

    private String mToken;

}
