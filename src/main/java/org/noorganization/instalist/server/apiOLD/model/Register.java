
package org.noorganization.instalist.server.apiOLD.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A represenation of the register request body.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "eMail",
    "password"
})
public class Register {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("eMail")
    private String eMail;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("password")
    private String password;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The eMail
     */
    @JsonProperty("eMail")
    public String getEMail() {
        return eMail;
    }

    /**
     * 
     * (Required)
     * 
     * @param eMail
     *     The eMail
     */
    @JsonProperty("eMail")
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public Register withEMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * 
     * (Required)
     * 
     * @param password
     *     The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public Register withPassword(String password) {
        this.password = password;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Register withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
