package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.controller.IListController;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

public class ListController implements IListController {

    private EntityManager mManager;

    ListController(EntityManager _manager) {
        mManager = _manager;
    }

    public void add(int _groupId, UUID _listUUID, String _name, UUID _category, Instant _lastChanged)
            throws ConflictException {
        ICategoryController categoryController = ControllerFactory.getCategoryController(mManager);

        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        ShoppingList found = findByGroupAndUUID(group, _listUUID);
        DeletedObject deletedList = findDeletedByGroupAndUUID(group, _listUUID);
        if (found != null || (deletedList != null && deletedList.getUpdated().
                isAfter(_lastChanged))) {
            tx.rollback();
            throw new ConflictException();
        }
        Category cat = null;
        if (_category != null) {
            cat = getCategory(group, _category, categoryController, tx);
        }

        ShoppingList newList = new ShoppingList().withGroup(group);
        newList.setName(_name);
        newList.setUUID(_listUUID);
        newList.setCategory(cat);
        newList.setUpdated(_lastChanged);
        mManager.persist(newList);
        tx.commit();
    }

    public void update(int _groupId, UUID _listUUID, String _name, UUID _category,
                       boolean _removeCategory, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException {
        ICategoryController categoryController = ControllerFactory.getCategoryController(mManager);

        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        ShoppingList listToUpdate = findByGroupAndUUID(group, _listUUID);
        if (listToUpdate == null) {
            if (findDeletedByGroupAndUUID(group, _listUUID) != null) {
                tx.rollback();
                throw new GoneException();
            }
            tx.rollback();
            throw new NotFoundException();
        }
        if (listToUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }

        Category cat = null;
        if (_category != null)
            cat = getCategory(group, _category, categoryController, tx);

        if (_name != null)
            listToUpdate.setName(_name);
        if (cat != null)
            listToUpdate.setCategory(cat);
        else if (_removeCategory)
            listToUpdate.setCategory(null);
        listToUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    public void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException {
        mManager.getTransaction().begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        ShoppingList listToDelete = findByGroupAndUUID(group, _listUUID);
        if (listToDelete == null) {
            if (findDeletedByGroupAndUUID(group, _listUUID) != null) {
                mManager.getTransaction().rollback();
                throw new GoneException();
            }
            mManager.getTransaction().rollback();
            throw new NotFoundException();
        }

        IEntryController entryController = ControllerFactory.getEntryController(mManager);
        for (ListEntry entry: listToDelete.getEntries()) {
            try {
                entryController.delete(_groupId, entry.getUUID());
            } catch (Exception _e) {}
        }

        DeletedObject deletedList = new DeletedObject().withType(DeletedObject.Type.LIST);
        deletedList.setUUID(_listUUID);
        deletedList.setGroup(group);
        mManager.persist(deletedList);
        mManager.remove(listToDelete);

        mManager.getTransaction().commit();
    }

    private Category getCategory(DeviceGroup _group, UUID _category,
                                 ICategoryController _categoryController, EntityTransaction _tx)
            throws BadRequestException  {
        Category cat;
        cat = _categoryController.findByGroupAndUUID(_group, _category);
        if (cat == null) {
            _tx.rollback();
            throw new BadRequestException();
        }
        return cat;
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<ShoppingList> getManagedType() {
        return ShoppingList.class;
    }
}
