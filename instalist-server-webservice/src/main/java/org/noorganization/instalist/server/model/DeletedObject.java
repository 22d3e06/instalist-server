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
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "deletion_log")
public class DeletedObject extends BaseItem<DeletedObject> {

    public enum Type {
        CATEGORY,
        INGREDIENT,
        LIST,
        LISTENTRY,
        PRODUCT,
        RECIPE,
        TAG,
        TAGGEDPRODUCT,
        UNIT
    }

    private int         mId;
    private Type        mType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public DeletedObject withId(int _id) {
        setId(_id);
        return this;
    }

    @Enumerated
    @Column(name = "type", nullable = false)
    public Type getType() {
        return mType;
    }

    public void setType(Type _type) {
        mType = _type;
    }

    public DeletedObject withType(Type _type) {
        setType(_type);
        return this;
    }

    @Transient
    public static Type mapType(Class _class) {
        if (_class == Category.class)
            return Type.CATEGORY;
        if (_class == Ingredient.class)
            return Type.INGREDIENT;
        if (_class == ShoppingList.class)
            return Type.LIST;
        if (_class == ListEntry.class)
            return Type.LISTENTRY;
        if (_class == Product.class)
            return Type.PRODUCT;
        if (_class == Recipe.class)
            return Type.RECIPE;
        if (_class == Tag.class)
            return Type.TAG;
        if (_class == TaggedProduct.class)
            return Type.TAGGEDPRODUCT;
        if (_class == Unit.class)
            return Type.UNIT;
        return null;
    }
}
