package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.BaseItem;
import org.noorganization.instalist.server.model.generic.NamedBaseItem;
import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category extends NamedBaseItem<Category> {

    private int         mId;

    private Set<ShoppingList> mLists;

    public Category() {
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

    public Category withId(int _id) {
        setId(_id);
        return this;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH)
    public Set<ShoppingList> getLists() {
        return mLists;
    }

    public void setLists(Set<ShoppingList> _lists) {
        mLists = _lists;
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o)
            return true;
        if (_o == null || getClass() != _o.getClass())
            return false;

        Category category = (Category) _o;

        if (mId != category.mId)
            return false;
        if (getUUID() != null ? !getUUID().equals(category.getUUID()) : category.getUUID() != null)
            return false;
        if (getGroup() != null ? !getGroup().equals(category.getGroup()) :
                category.getGroup() != null)
            return false;
        if (getName() != null ? !getName().equals(category.getName()) : category.getName() != null)
            return false;
        return getUpdated() != null ? getUpdated().equals(category.getUpdated()) :
                category.getUpdated() == null;
    }

    @Override
    public int hashCode() {
        return mId;
    }
}
