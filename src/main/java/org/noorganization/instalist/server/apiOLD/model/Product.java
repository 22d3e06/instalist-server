
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
 * A single product representation
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "unit_id",
    "name",
    "defaultAmount",
    "stepAmount"
})
public class Product {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private String id;
    @JsonProperty("unit_id")
    private String unitId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("defaultAmount")
    private Double defaultAmount;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stepAmount")
    private Double stepAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Product withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The unitId
     */
    @JsonProperty("unit_id")
    public String getUnitId() {
        return unitId;
    }

    /**
     * 
     * @param unitId
     *     The unit_id
     */
    @JsonProperty("unit_id")
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Product withUnitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Product withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The defaultAmount
     */
    @JsonProperty("defaultAmount")
    public Double getDefaultAmount() {
        return defaultAmount;
    }

    /**
     * 
     * (Required)
     * 
     * @param defaultAmount
     *     The defaultAmount
     */
    @JsonProperty("defaultAmount")
    public void setDefaultAmount(Double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public Product withDefaultAmount(Double defaultAmount) {
        this.defaultAmount = defaultAmount;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The stepAmount
     */
    @JsonProperty("stepAmount")
    public Double getStepAmount() {
        return stepAmount;
    }

    /**
     * 
     * (Required)
     * 
     * @param stepAmount
     *     The stepAmount
     */
    @JsonProperty("stepAmount")
    public void setStepAmount(Double stepAmount) {
        this.stepAmount = stepAmount;
    }

    public Product withStepAmount(Double stepAmount) {
        this.stepAmount = stepAmount;
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

    public Product withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
