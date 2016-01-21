
package org.noorganization.instantListApi.model;

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
 * A single ingredient representation
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "productId",
    "recipeId",
    "amount"
})
public class Ingredient {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("productId")
    private String productId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("recipeId")
    private String recipeId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("amount")
    private Double amount;
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

    public Ingredient withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
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
     * (Required)
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
     * (Required)
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
     * (Required)
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
     * (Required)
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
     * (Required)
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
