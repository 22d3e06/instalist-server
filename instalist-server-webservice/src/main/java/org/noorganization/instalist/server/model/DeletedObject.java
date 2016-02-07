package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "deletion_log")
public class DeletedObject {

    public enum Type {
        CATEGORY,
        INGREDIENT,
        LIST,
        LISTENTRY,
        PRODUCT,
        RECIPE,
        TAG,
        TAGGEDPRODUCT,
        UNIT
    }

    private int         mId;
    private UUID        mUUID;
    private Date        mTime;
    private Type        mType;
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

    public DeletedObject withId(int _id) {
        setId(_id);
        return this;
    }

    @Column(name = "uuid", columnDefinition = "BINARY(16)", nullable = false)
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _uuid) {
        mUUID = _uuid;
    }

    public DeletedObject withUUID(UUID _uuid) {
        setUUID(_uuid);
        return this;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "time", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false, insertable = false, updatable = false)
    public Date getTime() {
        return mTime;
    }

    public void setTime(Date _time) {
        mTime = _time;
    }

    public DeletedObject withTime(Date _time) {
        setTime(_time);
        return this;
    }

    @Enumerated
    @Column(name = "type", nullable = false)
    public Type getType() {
        return mType;
    }

    public void setType(Type _type) {
        mType = _type;
    }

    public DeletedObject withType(Type _type) {
        setType(_type);
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

    public DeletedObject withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
