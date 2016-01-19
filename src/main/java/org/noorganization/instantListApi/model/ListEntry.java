
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
 * A single listEntry representation
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "shoppingListId",
    "productId",
    "amount"
})
public class ListEntry {

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
    @JsonProperty("shoppingListId")
    private String shoppingListId;
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

    public ListEntry withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The shoppingListId
     */
    @JsonProperty("shoppingListId")
    public String getShoppingListId() {
        return shoppingListId;
    }

    /**
     * 
     * (Required)
     * 
     * @param shoppingListId
     *     The shoppingListId
     */
    @JsonProperty("shoppingListId")
    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public ListEntry withShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
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

    public ListEntry withProductId(String productId) {
        this.productId = productId;
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

    public ListEntry withAmount(Double amount) {
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

    public ListEntry withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
