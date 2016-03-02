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
@Table(name = "lists")
public class ShoppingList extends NamedBaseItem<ShoppingList> {
    private int         mId;
    private Category    mCategory;

    private Set<ListEntry> mEntries;

    public ShoppingList() {
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

    public ShoppingList withId(int _id) {
        setId(_id);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category _category) {
        mCategory = _category;
    }

    public ShoppingList withCategrory(Category _category) {
        setCategory(_category);
        return this;
    }

    @OneToMany(mappedBy = "list")
    public Set<ListEntry> getEntries() {
        return mEntries;
    }

    public void setEntries(Set<ListEntry> _entries) {
        mEntries = _entries;
    }
}
