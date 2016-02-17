package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.BaseItem;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseItem<Ingredient> {

    private int mId;
    private Recipe mRecipe;
    private Product mProduct;
    private float mAmount;

    public Ingredient() {
        super();
        mAmount = 1.0f;
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
}
