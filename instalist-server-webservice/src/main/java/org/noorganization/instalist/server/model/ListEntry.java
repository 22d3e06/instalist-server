package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "list_entries")
public class ListEntry {

    private int mId;
    private UUID mUUID;
    private ShoppingList mList;
    //private Product mProduct;
    private float mAmount;
    private boolean mStruck;
    private int mPriority;
    private Date mUpdated;
    private DeviceGroup mGroup;

    public ListEntry() {
        mAmount = 1.0f;
        mStruck = false;
        mPriority = 0;
        mUpdated = new Date(System.currentTimeMillis());
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

    @Column(name = "uuid", columnDefinition = "BINARY(16)", nullable = false)
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    public ShoppingList getList() {
        return mList;
    }

    public void setList(ShoppingList _list) {
        mList = _list;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float _amount) {
        mAmount = _amount;
    }

    @Column(name = "struck", nullable = false)
    public boolean isStruck() {
        return mStruck;
    }

    public void setStruck(boolean _struck) {
        mStruck = _struck;
    }

    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int _priority) {
        mPriority = _priority;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Date getUpdated() {
        if (mUpdated != null && mUpdated.getClass() == Timestamp.class) {
            Timestamp current = (Timestamp) mUpdated;
            return new Date(current.getTime() + (current.getNanos() / 1000000));
        }
        return mUpdated;
    }

    public void setUpdated(Date _updated) {
        mUpdated = _updated;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }
}
