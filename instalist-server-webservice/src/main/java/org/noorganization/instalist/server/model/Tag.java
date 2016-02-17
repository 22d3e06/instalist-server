package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.NamedBaseItem;
import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag extends NamedBaseItem<Tag> {
    private int         mId;

    private Set<TaggedProduct> mTaggedProducts;

    public Tag() {
        super();
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

    public Tag withId(int _id) {
        setId(_id);
        return this;
    }

    @OneToMany(mappedBy = "tag")
    public Set<TaggedProduct> getTaggedProducts() {
        return mTaggedProducts;
    }

    public void setTaggedProducts(Set<TaggedProduct> _taggedProducts) {
        mTaggedProducts = _taggedProducts;
    }
}
