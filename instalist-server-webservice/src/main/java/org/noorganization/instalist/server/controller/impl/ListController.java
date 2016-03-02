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

class ListController implements IListController {

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
        ShoppingList listToUpdate = getList(group, _listUUID, tx);
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
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        ShoppingList listToDelete = getList(group, _listUUID, tx);

        IEntryController entryController = ControllerFactory.getEntryController(mManager);
        for (ListEntry entry: listToDelete.getEntries()) {
            try {
                entryController.delete(_groupId, entry.getUUID());
            } catch (Exception ignored) {}
        }

        DeletedObject deletedList = new DeletedObject().withType(DeletedObject.Type.LIST);
        deletedList.setUUID(_listUUID);
        deletedList.setGroup(group);
        mManager.persist(deletedList);
        mManager.remove(listToDelete);

        tx.commit();
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

    private ShoppingList getList(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            NotFoundException, GoneException {
        try {
            return findOrThrow(_group, _uuid);
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
    public Class<ShoppingList> getManagedType() {
        return ShoppingList.class;
    }
}
