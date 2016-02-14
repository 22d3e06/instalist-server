package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "list_entries")
public class ListEntry {

    private int mId;
    private UUID mUUID;
    private ShoppingList mList;
    private Product mProduct;
    private float mAmount;
    private boolean mStruck;
    private int mPriority;
    private Instant mUpdated;
    private DeviceGroup mGroup;

    public ListEntry() {
        mAmount = 1.0f;
        mStruck = false;
        mPriority = 0;
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

    public ListEntry withId(int _id) {
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

    public ListEntry withUUID(UUID _uuid) {
        setUUID(_uuid);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    public ShoppingList getList() {
        return mList;
    }

    public void setList(ShoppingList _list) {
        mList = _list;
    }

    public ListEntry withList(ShoppingList __list) {
        setList(__list);
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

    public ListEntry withProduct(Product _product) {
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

    public ListEntry withAmount(float _amount) {
        setAmount(_amount);
        return this;
    }

    @Column(name = "struck", columnDefinition = "TINYINT(1)", nullable = false)
    public boolean getStruck() {
        return mStruck;
    }

    public void setStruck(boolean _struck) {
        mStruck = _struck;
    }

    public ListEntry withStruck(boolean _struck) {
        setStruck(_struck);
        return this;
    }

    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int _priority) {
        mPriority = _priority;
    }

    public ListEntry withPriority(int _priority) {
        setPriority(_priority);
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

    public ListEntry withUpdated(Instant _updated) {
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

    public ListEntry withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
