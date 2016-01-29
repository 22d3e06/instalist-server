
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
    "productId",
    "recipeId",
    "amount",
    "lastChanged"
})
public class Ingredient {

    @JsonProperty("id")
    private String id;
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("recipeId")
    private String recipeId;
    @JsonProperty("amount")
    private Double amount;
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

    public Ingredient withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The productId
     */
    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     *     The productId
     */
    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Ingredient withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    /**
     * 
     * @return
     *     The recipeId
     */
    @JsonProperty("recipeId")
    public String getRecipeId() {
        return recipeId;
    }

    /**
     * 
     * @param recipeId
     *     The recipeId
     */
    @JsonProperty("recipeId")
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public Ingredient withRecipeId(String recipeId) {
        this.recipeId = recipeId;
        return this;
    }

    /**
     * 
     * @return
     *     The amount
     */
    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Ingredient withAmount(Double amount) {
        this.amount = amount;
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

    public Ingredient withLastChanged(String lastChanged) {
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

    public Ingredient withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
