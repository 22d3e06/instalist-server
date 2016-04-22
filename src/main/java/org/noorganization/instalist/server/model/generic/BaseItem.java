/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
