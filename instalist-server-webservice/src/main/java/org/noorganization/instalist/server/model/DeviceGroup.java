package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "devicegroups")
public class DeviceGroup {
    private int    mId;
    private String mReadableId;
    private Date   mUpdated;
    private Date   mCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    @Column(name = "readableid", nullable = true, length = 6, columnDefinition = "char")
    public String getReadableId() {
        return mReadableId;
    }

    public void setReadableId(String _readableId) {
        mReadableId = _readableId;
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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date _created) {
        mCreated = _created;
    }
}
