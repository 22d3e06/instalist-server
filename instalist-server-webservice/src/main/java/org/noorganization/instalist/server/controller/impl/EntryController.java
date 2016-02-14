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
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EntryController implements IEntryController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _entryUUID, UUID _productUUID, UUID _listUUID,
                    float _amount, int _priority, boolean _struck, Instant _lastChanged)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        ListEntry toCheck = getEntryByGroupAndUUID(group, _entryUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = getDeletedEntryByGroupAndUUID(group, _entryUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getTime().toInstant())) {
            tx.rollback();
            throw new ConflictException();
        }
        IListController listController = ControllerFactory.getListController(mManager);
        ShoppingList newList = listController.getListByGroupAndUUID(group, _listUUID);
        IProductController productController = ControllerFactory.getProductController(mManager);
        Product newProduct = productController.getProductByGroupAndUUID(group, _productUUID);
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
            newList = listController.getListByGroupAndUUID(group, _listUUID);
            if (newList == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }
        Product newProduct = null;
        if (_productUUID != null) {
            IProductController productController = ControllerFactory.getProductController(mManager);
            newProduct = productController.getProductByGroupAndUUID(group, _productUUID);
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

    public void delete(int _groupId, UUID _entryUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        ListEntry toDelete = getEntry(group, _entryUUID, tx);
        DeletedObject oldProduct = new DeletedObject().withGroup(group);
        oldProduct.setUUID(_entryUUID);
        oldProduct.setType(DeletedObject.Type.LISTENTRY);
        oldProduct.setTime(Date.from(Instant.now()));
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    public ListEntry getEntryByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<ListEntry> entryQuery = mManager.createQuery("select le from ListEntry le " +
                "where le.UUID = :uuid and le.group = :group", ListEntry.class);
        entryQuery.setParameter("uuid", _uuid);
        entryQuery.setParameter("group", _group);
        List<ListEntry> entryResult = entryQuery.getResultList();
        if (entryResult.size() == 0)
            return null;
        return entryResult.get(0);
    }

    public DeletedObject getDeletedEntryByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delEntryQuery = mManager.createQuery("select do from " +
                        "DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                        "do.type = :type order by do.time desc",
                DeletedObject.class);
        delEntryQuery.setParameter("group", _group);
        delEntryQuery.setParameter("uuid", _uuid);
        delEntryQuery.setParameter("type", DeletedObject.Type.LISTENTRY);
        delEntryQuery.setMaxResults(1);
        List<DeletedObject> delEntryResult = delEntryQuery.getResultList();
        if (delEntryResult.size() == 0)
            return null;
        return delEntryResult.get(0);
    }

    EntryController(EntityManager _manager) {
        mManager = _manager;
    }

    private ListEntry getEntry(DeviceGroup _group, UUID _entryUUID, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        ListEntry entry = getEntryByGroupAndUUID(_group, _entryUUID);
        if (entry == null) {
            if (getDeletedEntryByGroupAndUUID(_group, _entryUUID) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return entry;
    }
}
