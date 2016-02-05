
package org.noorganization.instalist.server.message;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "message" })
public class Error extends EntityObject {

    @JsonProperty("message")
    private String message;

    /**
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public Error withMessage(String message) {
        this.message = message;
        return this;
    }
}
