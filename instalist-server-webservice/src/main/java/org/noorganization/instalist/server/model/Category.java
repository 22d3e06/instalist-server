package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    private int         mId;
    private UUID        mUUID;
    private DeviceGroup mGroup;
    private String      mName;
    private Date        mUpdated;

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

    @Column(name = "uuid", nullable = false, columnDefinition = "BINARY(16)")
    public UUID getUUID(){
        return mUUID;
    }

    public void setUUID(UUID _uuid){
        mUUID = _uuid;
    }

    public Category withUUID(UUID _uuid) {
        setUUID(_uuid);
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

    public Category withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public Category withName(String _name) {
        setName(_name);
        return this;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON " +
            "UPDATE CURRENT_TIMESTAMP(3)", insertable = false, updatable = false, nullable = false)
    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date _updated) {
        mUpdated = _updated;
    }

    public Category withUpdated(Date _updated) {
        setUpdated(_updated);
        return this;
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
        if (mUUID != null ? !mUUID.equals(category.mUUID) : category.mUUID != null)
            return false;
        if (mGroup != null ? !mGroup.equals(category.mGroup) : category.mGroup != null)
            return false;
        if (mName != null ? !mName.equals(category.mName) : category.mName != null)
            return false;
        return mUpdated != null ? mUpdated.equals(category.mUpdated) : category.mUpdated == null;
    }

    @Override
    public int hashCode() {
        return mId;
    }
}
