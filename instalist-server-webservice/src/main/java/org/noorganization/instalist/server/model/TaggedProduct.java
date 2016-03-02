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
@Table(name = "tagged_products")
public class TaggedProduct extends BaseItem<TaggedProduct> {

    private int mId;
    private Tag mTag;
    private Product mProduct;

    public TaggedProduct() {
        super();
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

    public TaggedProduct withId(int _id) {
        setId(_id);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    public Tag getTag() {
        return mTag;
    }

    public void setTag(Tag _tag) {
        mTag = _tag;
    }

    public TaggedProduct withTag(Tag _tag) {
        setTag(_tag);
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

    public TaggedProduct withProduct(Product _product) {
        setProduct(_product);
        return this;
    }

}
