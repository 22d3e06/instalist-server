
package org.noorganization.instalist.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "unitId",
    "defaultAmount",
    "stepAmount",
    "lastChanged"
})
public class Product {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("unitId")
    private String unitId;
    @JsonProperty("defaultAmount")
    private Double defaultAmount;
    @JsonProperty("stepAmount")
    private Double stepAmount;
    @JsonProperty("lastChanged")
    private String lastChanged;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
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
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
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
     * @return
     *     The unitId
     */
    @JsonProperty("unitId")
    public String getUnitId() {
        return unitId;
    }

    /**
     * 
     * @param unitId
     *     The unitId
     */
    @JsonProperty("unitId")
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Product withUnitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    /**
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
     * @return
     *     The stepAmount
     */
    @JsonProperty("stepAmount")
    public Double getStepAmount() {
        return stepAmount;
    }

    /**
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

    /**
     * 
     * @return
     *     The lastChanged
     */
    @JsonProperty("lastChanged")
    public String getLastChanged() {
        return lastChanged;
    }

    /**
     * 
     * @param lastChanged
     *     The lastChanged
     */
    @JsonProperty("lastChanged")
    public void setLastChanged(String lastChanged) {
        this.lastChanged = lastChanged;
    }

    public Product withLastChanged(String lastChanged) {
        this.lastChanged = lastChanged;
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
