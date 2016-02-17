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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
