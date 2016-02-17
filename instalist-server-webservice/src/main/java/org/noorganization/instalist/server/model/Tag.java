package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag {
    private int         mId;
    private UUID mUUID;
    private String      mName;
    private Instant mUpdated;
    private DeviceGroup mGroup;

    private Set<TaggedProduct> mTaggedProducts;

    public Tag() {
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

    public Tag withId(int _id) {
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

    public Tag withUUID(UUID _uuid) {
        setUUID(_uuid);
        return this;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public Tag withName(String _name) {
        setName(_name);
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

    public Tag withUpdated(Instant _updated) {
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

    public Tag withGroup(DeviceGroup _group) {
        setGroup(_group);
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
