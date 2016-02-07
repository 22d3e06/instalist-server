package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by damihe on 03.02.16.
 */
@Entity
@Table(name = "categories")
public class Category {

    private int         mId;
    private UUID        mUuid;
    private DeviceGroup mGroup;
    private String      mName;
    private Date mUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    @Column(name = "uuid", nullable = false)
    public UUID getUuid(){
        return mUuid;
    }

    public void setUuid(UUID _uuid){
        mUuid = _uuid;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON " +
            "UPDATE CURRENT_TIMESTAMP(3)", insertable = false, updatable = false)
    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date _updated) {
        mUpdated = _updated;
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
        if (mUuid != null ? !mUuid.equals(category.mUuid) : category.mUuid != null)
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
