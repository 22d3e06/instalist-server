package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;

/**
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
}
