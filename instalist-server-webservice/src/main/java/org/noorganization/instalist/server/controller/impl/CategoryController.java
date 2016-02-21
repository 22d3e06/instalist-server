package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

class CategoryController implements ICategoryController {

    private EntityManager mManager;

    @Override
    public Category add(int _groupId, UUID _uuid, String _name, Instant _added) throws
            ClientErrorException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        Category existingCategory = findByGroupAndUUID(group, _uuid);
        if (existingCategory != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject deletedCategory = findDeletedByGroupAndUUID(group, _uuid);
        if (deletedCategory != null && _added.isBefore(deletedCategory.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }

        Category rtn = new Category();
        rtn.setUUID(_uuid);
        rtn.setName(_name);
        rtn.setGroup(group);
        rtn.setUpdated(_added);
        mManager.persist(rtn);
        tx.commit();
        mManager.refresh(rtn);

        return rtn;
    }

    @Override
    public void update(int _groupId, UUID _categoryUUID, String _name, Instant _changed) throws
            ClientErrorException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        Category catToEdit = getCategory(group, _categoryUUID, tx);

        if (catToEdit.getUpdated().isAfter(_changed)) {
            tx.rollback();
            throw new ConflictException();
        }
        catToEdit.setName(_name);
        catToEdit.setUpdated(_changed);

        tx.commit();
    }

    @Override
    public void delete(int _groupId, UUID _categoryUUID) throws ConflictException,
            NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        Category catToDelete = getCategory(group, _categoryUUID, tx);
        if (catToDelete.getLists().size() != 0) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject deletedCat = new DeletedObject();
        deletedCat.setType(DeletedObject.Type.CATEGORY);
        deletedCat.setGroup(mManager.find(DeviceGroup.class, _groupId));
        deletedCat.setUUID(_categoryUUID);
        mManager.persist(deletedCat);
        mManager.remove(catToDelete);
        tx.commit();
    }

    private Category getCategory(DeviceGroup _group, UUID _categoryUUID, EntityTransaction _tx)
            throws NotFoundException, GoneException {
        try {
            return findOrThrow(_group, _categoryUUID);
        } catch (NotFoundException|GoneException _e) {
            _tx.rollback();
            throw  _e;
        }
    }

    CategoryController(EntityManager _manager) {
        this.mManager = _manager;
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<Category> getManagedType() {
        return Category.class;
    }
}
