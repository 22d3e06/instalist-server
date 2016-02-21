package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.controller.IListController;
import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

class EntryController implements IEntryController {

    private EntityManager mManager;

    @Override
    public void add(int _groupId, UUID _entryUUID, UUID _productUUID, UUID _listUUID,
                    float _amount, int _priority, boolean _struck, Instant _lastChanged)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        ListEntry toCheck = findByGroupAndUUID(group, _entryUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = findDeletedByGroupAndUUID(group, _entryUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }
        IListController listController = ControllerFactory.getListController(mManager);
        ShoppingList newList = listController.findByGroupAndUUID(group, _listUUID);
        IProductController productController = ControllerFactory.getProductController(mManager);
        Product newProduct = productController.findByGroupAndUUID(group, _productUUID);
        if (newProduct == null || newList == null) {
            tx.rollback();
            throw new BadRequestException();
        }

        ListEntry toCreate = new ListEntry().withGroup(group);
        toCreate.setUUID(_entryUUID);
        toCreate.setList(newList);
        toCreate.setProduct(newProduct);
        toCreate.setAmount(_amount);
        toCreate.setPriority(_priority);
        toCreate.setStruck(_struck);
        toCreate.setUpdated(_lastChanged);
        mManager.persist(toCreate);

        tx.commit();
    }

    @Override
    public void update(int _groupId, UUID _entryUUID, UUID _productUUID,
                       UUID _listUUID, Float _amount, Integer _priority, Boolean _struck,
                       Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        ListEntry toUpdate = getEntry(group, _entryUUID, tx);
        if (toUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }
        ShoppingList newList = null;
        if (_listUUID != null) {
            IListController listController = ControllerFactory.getListController(mManager);
            newList = listController.findByGroupAndUUID(group, _listUUID);
            if (newList == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }
        Product newProduct = null;
        if (_productUUID != null) {
            IProductController productController = ControllerFactory.getProductController(mManager);
            newProduct = productController.findByGroupAndUUID(group, _productUUID);
            if (newProduct == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }

        if (newList != null)
            toUpdate.setList(newList);
        if (newProduct != null)
            toUpdate.setProduct(newProduct);
        if (_amount != null)
            toUpdate.setAmount(_amount);
        if (_priority != null)
            toUpdate.setPriority(_priority);
        if (_struck != null)
            toUpdate.setStruck(_struck);
        toUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    @Override
    public void delete(int _groupId, UUID _entryUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        ListEntry toDelete = getEntry(group, _entryUUID, tx);
        DeletedObject oldProduct = new DeletedObject().withGroup(group);
        oldProduct.setUUID(_entryUUID);
        oldProduct.setType(DeletedObject.Type.LISTENTRY);
        oldProduct.setUpdated(Instant.now());
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    EntryController(EntityManager _manager) {
        mManager = _manager;
    }

    private ListEntry getEntry(DeviceGroup _group, UUID _entryUUID, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        try {
            return findOrThrow(_group, _entryUUID);
        } catch (NotFoundException|GoneException _e) {
            _tx.rollback();
            throw  _e;
        }
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<ListEntry> getManagedType() {
        return ListEntry.class;
    }
}
