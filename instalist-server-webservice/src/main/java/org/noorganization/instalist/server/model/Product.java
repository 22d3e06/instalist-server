package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    private int         mId;
    private UUID        mUUID;
    private String      mName;
    private Unit        mUnit;
    private float       mDefaultAmount;
    private float       mStepAmount;
    private Instant     mUpdated;
    private DeviceGroup mGroup;

    public Product() {
        mUpdated       = Instant.now();
        mDefaultAmount = 1.0f;
        mStepAmount    = 1.0f;
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

    public Product withId(int _id) {
        setId(_id);
        return this;
    }

    @Column(name = "uuid", nullable = false)
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    public Product withUUID(UUID _uuid) {
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

    public Product withName(String _name) {
        setName(_name);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = true)
    public Unit getUnit() {
        return mUnit;
    }

    public void setUnit(Unit _unit) {
        mUnit = _unit;
    }

    public Product withUnit(Unit _unit) {
        setUnit(_unit);
        return this;
    }

    @Column(name = "defaultamount", nullable = false)
    public float getDefaultAmount() {
        return mDefaultAmount;
    }

    public void setDefaultAmount(float _defaultAmount) {
        mDefaultAmount = _defaultAmount;
    }

    public Product withDefaultAmount(float _defaultAmount) {
        setDefaultAmount(_defaultAmount);
        return this;
    }

    @Column(name = "stepamount", nullable = false)
    public float getStepAmount() {
        return mStepAmount;
    }

    public void setStepAmount(float _stepAmount) {
        mStepAmount = _stepAmount;
    }

    public Product withStepAmount(float _stepAmount) {
        setStepAmount(_stepAmount);
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

    public Product withUpdated(Instant _updated) {
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

    public Product withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
