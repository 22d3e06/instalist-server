package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damihe on 03.02.16.
 */
@Entity
@Table(name = "devices")
public class Device {
    private int         mId;
    private String      mName;
    private Date        mCreated;
    private boolean     mAuthorized;
    private String      mSecret;
    private DeviceGroup mGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public Device withName(String _name) {
        setName(_name);
        return this;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created")
    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date _created) {
        mCreated = _created;
    }

    public Device withCreated(Date _created) {
        setCreated(_created);
        return this;
    }

    @Column(name = "authorized", nullable = false)
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
}
