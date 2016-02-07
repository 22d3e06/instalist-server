package org.noorganization.instalist.server.controller.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.IGroupController;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.List;

class GroupController implements IGroupController {

    private EntityManager mManager;

    public DeviceGroup addGroup() {
        String newGroupReadableId;
        newGroupReadableId = getNewGroupReadableId(mManager);

        mManager.getTransaction().begin();
        DeviceGroup createdGroup = new DeviceGroup().withReadableId(newGroupReadableId);
        mManager.persist(createdGroup);
        mManager.getTransaction().commit();
        mManager.refresh(createdGroup);

        return createdGroup;
    }

    public Device addDevice(int _groupId, String _groupAuth, String _name, String _secret) {

        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        if (group == null || group.getReadableId() == null || !group.getReadableId().
                equals(_groupAuth))
            return null;

        mManager.getTransaction().begin();
        TypedQuery<Device> otherDeviceQuery =
                mManager.createQuery("select d from Device d where " +
                        "d.group = :dg", Device.class);
        otherDeviceQuery.setParameter("dg", group);
        otherDeviceQuery.setMaxResults(1);
        List<Device> otherDevices = otherDeviceQuery.getResultList();

        group.setReadableId(null);
        mManager.merge(group);
        Device rtn = new Device();
        rtn.setName(_name);
        rtn.setGroup(group);
        rtn.setSecret(BCrypt.hashpw(_secret, BCrypt.gensalt(10)));
        rtn.setAuthorized(otherDevices.size() == 0);
        mManager.persist(rtn);
        mManager.getTransaction().commit();
        mManager.refresh(rtn);

        return rtn;
    }

    public String generateAccessKey(int _groupId) {
        String newAccessKey = getNewGroupReadableId(mManager);

        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        if (group == null)
            return null;
        mManager.getTransaction().begin();
        group.setReadableId(newAccessKey);
        mManager.merge(group);
        mManager.getTransaction().commit();

        return newAccessKey;
    }

    public boolean updateDevice(int _deviceId, String _name, Boolean _authorized) {
        mManager.getTransaction().begin();
        Device toChange = mManager.find(Device.class, _deviceId);
        if (toChange == null)
            return false;
        if (_name != null)
            toChange.setName(_name);
        if (_authorized != null)
            toChange.setAuthorized(_authorized);
        mManager.merge(toChange);
        mManager.getTransaction().commit();

        IAuthController authController = ControllerFactory.getAuthController();
        authController.revalidateDevice(mManager, _deviceId);

        return true;
    }

    private String getNewGroupReadableId(EntityManager _manager) {
        final char[] acceptableChars =
                new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
                        '4', '5', '6', '7', '8', '9'};
        SecureRandom random = new SecureRandom();
        StringBuilder idBuilder = new StringBuilder(6);

        while (true) {
            idBuilder.setLength(0);
            while (idBuilder.length() < 6)
                idBuilder.append(acceptableChars[random.nextInt(acceptableChars.length)]);

            Query dgQuery = _manager.createQuery("select dg from DeviceGroup dg where dg" +
                    ".readableId = :rid");
            dgQuery.setParameter("rid", idBuilder.toString());
            dgQuery.setMaxResults(1);
            if (dgQuery.getResultList().size() == 0)
                break;
        }

        return idBuilder.toString();
    }

    GroupController(EntityManager _manager) {
        mManager = _manager;
    }
}
