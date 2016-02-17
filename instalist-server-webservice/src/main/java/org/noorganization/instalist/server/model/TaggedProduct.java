package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tagged_products")
public class TaggedProduct {

    private int mId;
    private UUID mUUID;
    private Tag mTag;
    private Product mProduct;
    private Instant mUpdated;
    private DeviceGroup mGroup;

    public TaggedProduct() {
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

    public TaggedProduct withId(int _id) {
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

    public TaggedProduct withUUID(UUID _uuid) {
        setUUID(_uuid);
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

    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Instant getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Instant _updated) {
        mUpdated = _updated;
    }

    public TaggedProduct withUpdated(Instant _updated) {
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

    public TaggedProduct withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
