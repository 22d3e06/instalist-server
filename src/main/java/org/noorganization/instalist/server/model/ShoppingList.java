
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
    "categoryId",
    "lastChanged"
})
public class ShoppingList {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("categoryId")
    private String categoryId;
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

    public ShoppingList withId(String id) {
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

    public ShoppingList withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The categoryId
     */
    @JsonProperty("categoryId")
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 
     * @param categoryId
     *     The categoryId
     */
    @JsonProperty("categoryId")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ShoppingList withCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public ShoppingList withLastChanged(String lastChanged) {
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

    public ShoppingList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
