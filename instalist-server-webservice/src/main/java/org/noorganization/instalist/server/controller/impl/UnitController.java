package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IUnitController;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Unit;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UnitController implements IUnitController{

    private EntityManager mManager;

    public void add(int _groupId, UUID _newUUID, String _name, Instant _created)
            throws ConflictException {

    }

    public void update(int _groupId, UUID _uuid, String _name, Instant _updated)
            throws ConflictException, NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        Unit toUpdate = getUnitByGroupAndUUID(group, _uuid);
        if (toUpdate == null) {
            if (getDeletedUnitByGroupAndUUID(group, _uuid) == null) {
                tx.rollback();
                throw new NotFoundException();
            }
            tx.rollback();
            throw new GoneException();
        }
        if (toUpdate.getUpdated().isAfter(_updated)) {
            tx.rollback();
            throw new ConflictException();
        }

        if (_name != null)
            toUpdate.setName(_name);
        toUpdate.setUpdated(_updated);
        tx.commit();
    }

    public void delete(int _groupId, UUID _uuid) throws NotFoundException, GoneException {

    }

    public Unit getUnitByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<Unit> unitQuery = mManager.createQuery("select u from Unit u where " +
                "u.group = :group and u.UUID = :uuid", Unit.class);
        unitQuery.setParameter("group", _group);
        unitQuery.setParameter("uuid", _uuid);
        List<Unit> unitResult = unitQuery.getResultList();
        if (unitResult.size() == 0)
            return null;
        return unitResult.get(0);
    }

    public DeletedObject getDeletedUnitByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delUnitQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid and do.type = :type",
                DeletedObject.class);
        delUnitQuery.setParameter("group", _group);
        delUnitQuery.setParameter("uuid", _uuid);
        delUnitQuery.setParameter("type", DeletedObject.Type.UNIT);
        List<DeletedObject> delUnitResult = delUnitQuery.getResultList();
        if (delUnitResult.size() == 0)
            return null;
        return delUnitResult.get(0);
    }

    UnitController(EntityManager _manager) {
        mManager = _manager;
    }
}
