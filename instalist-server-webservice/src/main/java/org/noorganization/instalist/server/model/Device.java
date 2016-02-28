package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "devices")
public class Device extends NamedItem<Device> {
    private int         mId;
    private Date        mCreated;
    private boolean     mAuthorized;
    private String      mSecret;
    private DeviceGroup mGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public Device withId(int _id) {
        setId(_id);
        return this;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created", columnDefinition="TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            insertable = false, updatable = false)
    public Date getCreated() {
        if (mCreated != null && mCreated.getClass() == Timestamp.class) {
            Timestamp current = (Timestamp) mCreated;
            return new Date(current.getTime() + (current.getNanos() / 1000000));
        }
        return mCreated;
    }

    public void setCreated(Date _created) {
        mCreated = _created;
    }

    public Device withCreated(Date _created) {
        setCreated(_created);
        return this;
    }

    @Column(name = "authorized", columnDefinition = "TINYINT(1)", nullable = false)
    public boolean getAuthorized() {
        return mAuthorized;
    }

    public void setAuthorized(boolean _authorized) {
        mAuthorized = _authorized;
    }
    public Device withAuthorized(boolean _authorized) {
        setAuthorized(_authorized);
        return this;
    }

    @Column(name = "secret", nullable = false)
    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String _secret) {
        mSecret = _secret;
    }

    public Device withSecret(String _secret) {
        setSecret(_secret);
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

    public Device withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o)
            return true;
        if (_o == null || getClass() != _o.getClass())
            return false;

        Device device = (Device) _o;

        if (mId != device.mId)
            return false;
        if (mAuthorized != device.mAuthorized)
            return false;
        if (getName() != null ? !getName().equals(device.getName()) : device.getName() != null)
            return false;
        if (mCreated != null ? !mCreated.equals(device.mCreated) : device.mCreated != null)
            return false;
        if (mSecret != null ? !mSecret.equals(device.mSecret) : device.mSecret != null)
            return false;
        return mGroup != null ?
                (device.mGroup !=  null && mGroup.getId() == device.mGroup.getId()) :
                device.mGroup == null;
    }

    @Override
    public int hashCode() {
        return mId;
    }
}
