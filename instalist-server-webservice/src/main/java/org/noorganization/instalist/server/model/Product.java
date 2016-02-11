package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    private int         mId;
    private UUID        mUUID;
    private String      mName;
    private Unit        mUnit;
    private float       mDefaultAmount;
    private float       mStepAmount;
    private Date        mUpdated;
    private DeviceGroup mGroup;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    @Column(name = "uuid", nullable = false)
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    @ManyToOne
    @JoinColumn(name = "unit_uuid", nullable = true)
    public Unit getUnit() {
        return mUnit;
    }

    public void setUnit(Unit _unit) {
        mUnit = _unit;
    }

    @Column(name = "defaultamount", nullable = false)
    public float getDefaultAmount() {
        return mDefaultAmount;
    }

    public void setDefaultAmount(float _defaultAmount) {
        mDefaultAmount = _defaultAmount;
    }

    @Column(name = "stepamount", nullable = false)
    public float getStepAmount() {
        return mStepAmount;
    }

    public void setStepAmount(float _stepAmount) {
        mStepAmount = _stepAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Date getUpdated() {
        if (mUpdated != null && mUpdated.getClass() == Timestamp.class) {
            Timestamp updated = (Timestamp) mUpdated;
            return new Date(updated.getTime() + (updated.getNanos() / 1000000));
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
