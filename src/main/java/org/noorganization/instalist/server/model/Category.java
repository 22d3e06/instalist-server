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
    @Column(name = "updated")
    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date _updated) {
        mUpdated = _updated;
    }
}
