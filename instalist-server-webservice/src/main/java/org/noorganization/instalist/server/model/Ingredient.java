package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    private int mId;
    private UUID mUUID;
    private Recipe mRecipe;
    private Product mProduct;
    private float mAmount;
    private Instant mUpdated;
    private DeviceGroup mGroup;

    public Ingredient() {
        mAmount = 1.0f;
        mUpdated = Instant.now();
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public Ingredient withId(int _id) {
        setId(_id);
        return this;
    }

    @Column(name = "uuid", columnDefinition = "BINARY(16)", nullable = false)
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    public Ingredient withUUID(UUID _uuid) {
        setUUID(_uuid);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    public Recipe getRecipe() {
        return mRecipe;
    }

    public void setRecipe(Recipe _recipe) {
        mRecipe = _recipe;
    }

    public Ingredient withRecipe(Recipe _recipe) {
        setRecipe(_recipe);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product _product) {
        mProduct = _product;
    }

    public Ingredient withProduct(Product _product) {
        setProduct(_product);
        return this;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float _amount) {
        mAmount = _amount;
    }

    public Ingredient withAmount(float _amount) {
        setAmount(_amount);
        return this;
    }

    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Instant getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Instant _updated) {
        mUpdated = _updated;
    }

    public Ingredient withUpdated(Instant _updated) {
        setUpdated(_updated);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }

    public Ingredient withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
