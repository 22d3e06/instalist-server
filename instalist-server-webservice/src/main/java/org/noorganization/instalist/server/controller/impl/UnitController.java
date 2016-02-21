package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.IUnitController;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Product;
import org.noorganization.instalist.server.model.Unit;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

class UnitController implements IUnitController {

    private EntityManager mManager;

    @Override
    public void add(int _groupId, UUID _newUUID, String _name, Instant _created)
            throws ConflictException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Unit existingUnit = findByGroupAndUUID(group, _newUUID);
        if (existingUnit != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject deletedUnit = findDeletedByGroupAndUUID(group, _newUUID);
        if (deletedUnit != null && _created.isBefore(deletedUnit.getUpdated())) {
             tx.rollback();
            throw new ConflictException();
        }

        Unit newUnit = new Unit().withGroup(group);
        newUnit.setUUID(_newUUID);
        newUnit.setName(_name);
        newUnit.setUpdated(_created);
        mManager.persist(newUnit);

        tx.commit();
    }

    @Override
    public void update(int _groupId, UUID _uuid, String _name, Instant _updated)
            throws ConflictException, NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Unit toUpdate = getUnit(_uuid, tx, group);
        if (toUpdate.getUpdated().isAfter(_updated)) {
            tx.rollback();
            throw new ConflictException();
        }

        if (_name != null)
            toUpdate.setName(_name);
        toUpdate.setUpdated(_updated);
        tx.commit();
    }

    @Override
    public void delete(int _groupId, UUID _uuid) throws NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        DeletedObject oldUnit = new DeletedObject().withGroup(group);
        Unit toDelete = getUnit(_uuid, tx, group);

        IProductController productController = ControllerFactory.getProductController(mManager);
        for (Product productToUnlink: toDelete.getProducts()) {
            try {
                productController.update(_groupId, productToUnlink.getUUID(), null, null, null,
                        null, true, Instant.now());
            } catch (Exception ignored) {}
        }
        oldUnit.setUUID(toDelete.getUUID());
        oldUnit.setType(DeletedObject.Type.UNIT);
        mManager.persist(oldUnit);
        mManager.remove(toDelete);

        tx.commit();
    }

    private Unit getUnit(UUID _uuid, EntityTransaction _tx, DeviceGroup _group)
            throws NotFoundException, GoneException {
        try {
            return findOrThrow(_group, _uuid);
        } catch (NotFoundException|GoneException _e) {
            _tx.rollback();
            throw  _e;
        }
    }

    UnitController(EntityManager _manager) {
        mManager = _manager;
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<Unit> getManagedType() {
        return Unit.class;
    }
}
