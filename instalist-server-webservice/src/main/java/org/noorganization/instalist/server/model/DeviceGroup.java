package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "devicegroups")
public class DeviceGroup {
    private int    mId;
    private String mReadableId;
    private Date   mUpdated;
    private Date   mCreated;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group", orphanRemoval = true)
    private Collection<Device> mDevices;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public DeviceGroup withId(int _id) {
        setId(_id);
        return this;
    }

    @Column(name = "readableid", nullable = true, length = 6, columnDefinition = "char(6)")
    public String getReadableId() {
        return mReadableId;
    }

    public void setReadableId(String _readableId) {
        mReadableId = _readableId;
    }

    public DeviceGroup withReadableId(String _readableId) {
        setReadableId(_readableId);
        return this;
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

    public DeviceGroup withUpdated(Date _updated) {
        setUpdated(_updated);
        return this;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", columnDefinition="TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            insertable = false, updatable = false)
    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date _created) {
        mCreated = _created;
    }

    public DeviceGroup withCreated(Date _created) {
        setCreated(_created);
        return this;
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o)
            return true;
        if (_o == null || getClass() != _o.getClass())
            return false;

        DeviceGroup that = (DeviceGroup) _o;

        if (mId != that.mId)
            return false;
        if (mReadableId != null ? !mReadableId.equals(that.mReadableId) : that.mReadableId != null)
            return false;
        if (mUpdated != null ? !mUpdated.equals(that.mUpdated) : that.mUpdated != null)
            return false;
        return mCreated != null ? mCreated.equals(that.mCreated) : that.mCreated == null;
    }

    @Override
    public int hashCode() {
        return mId;
    }

    @Override
    public String toString() {
        return "DeviceGroup{" +
                "mId=" + mId +
                ", mReadableId='" + mReadableId + '\'' +
                ", mUpdated=" + mUpdated +
                ", mCreated=" + mCreated +
                '}';
    }
}
