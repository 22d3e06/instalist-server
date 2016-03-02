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

import org.noorganization.instalist.server.model.generic.NamedBaseItem;
import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product extends NamedBaseItem<Product> {
    private int         mId;
    private Unit        mUnit;
    private float       mDefaultAmount;
    private float       mStepAmount;

    private Set<ListEntry> mEntries;
    private Set<Ingredient> mIngredients;

    public Product() {
        super();
        mDefaultAmount = 1.0f;
        mStepAmount    = 1.0f;
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

    public Product withId(int _id) {
        setId(_id);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = true)
    public Unit getUnit() {
        return mUnit;
    }

    public void setUnit(Unit _unit) {
        mUnit = _unit;
    }

    public Product withUnit(Unit _unit) {
        setUnit(_unit);
        return this;
    }

    @Column(name = "defaultamount", nullable = false)
    public float getDefaultAmount() {
        return mDefaultAmount;
    }

    public void setDefaultAmount(float _defaultAmount) {
        mDefaultAmount = _defaultAmount;
    }

    public Product withDefaultAmount(float _defaultAmount) {
        setDefaultAmount(_defaultAmount);
        return this;
    }

    @Column(name = "stepamount", nullable = false)
    public float getStepAmount() {
        return mStepAmount;
    }

    public void setStepAmount(float _stepAmount) {
        mStepAmount = _stepAmount;
    }

    public Product withStepAmount(float _stepAmount) {
        setStepAmount(_stepAmount);
        return this;
    }

    @OneToMany(mappedBy = "product")
    public Set<ListEntry> getEntries() {
        return mEntries;
    }

    public void setEntries(Set<ListEntry> _entries) {
        mEntries = _entries;
    }

    @OneToMany(mappedBy = "product")
    public Set<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(Set<Ingredient> _ingredients) {
        mIngredients = _ingredients;
    }
}
