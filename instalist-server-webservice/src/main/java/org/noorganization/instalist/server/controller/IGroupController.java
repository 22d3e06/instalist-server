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

package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;

/**
 * Controller for managing groups and devices.
 * Created by damihe on 05.02.16.
 */
public interface IGroupController {
    /**
     * Creates a new group.
     * @return The new group.
     */
    DeviceGroup addGroup();

    /**
     * Creates a new device in a authenticated group.
     * @param _groupId The identified group to add the device to.
     * @param _groupAuth Authorization for making sure the device gets added to the right group.
     * @param _name A human readable name for the device.
     * @param _secret A client generated authentication for future authentication.
     * @return Either a new device or null, if group is not existent or _groupAuth was invalid.
     */
    Device addDevice(int _groupId, String _groupAuth, String _name, String _secret);

    /**
     * Generates a new access-key for adding devices to the group.
     * @param _groupId The id of the group to regenerate the access key.
     * @return The new access key. Usually 6 characters excluding capital 'O'.
     */
    String generateAccessKey(int _groupId);

    /**
     * Changes a device.
     * @param _deviceId The id of the device to change.
     * @param _name A new name for the device. Optional.
     * @param _authorized A new authorization-status for the device. Optional.
     * @return Whether the update was successful.
     */
    boolean updateDevice(int _deviceId, String _name, Boolean _authorized);

    /**
     * Deletes a device.
     * @param _deviceId The id of the device to delete.
     */
    void deleteDevice(int _deviceId);
}
