/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
