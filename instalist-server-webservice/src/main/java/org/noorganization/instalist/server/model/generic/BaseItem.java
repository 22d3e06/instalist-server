package org.noorganization.instalist.server.model.generic;

import org.noorganization.instalist.server.model.DeviceGroup;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by damihe on 17.02.16.
 */
@MappedSuperclass
public abstract class BaseItem<T extends BaseItem> {
    UUID        mUUID;
    Instant     mUpdated;
    DeviceGroup mGroup;

    public BaseItem() {
        mUpdated = Instant.now();
    }

    @Column(name = "uuid", nullable = false, columnDefinition = "BINARY(16)")
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    public T withUUID(UUID _uuid) {
        setUUID(_uuid);
        return (T) this;
    }

    @Column(name = "updated", nullable = false, columnDefinition =
            "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
    public Instant getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Instant _updated) {
        mUpdated = _updated;
    }

    public T withUpdated(Instant _updated) {
        setUpdated(_updated);
        return (T) this;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }

    public T withGroup(DeviceGroup _group) {
        setGroup(_group);
        return (T) this;
    }
}
