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
import java.util.Date;
import java.util.List;
import java.util.UUID;

class CategoryController implements ICategoryController {

    private EntityManager mManager;

    public Category add(int _groupId, UUID _uuid, String _name, Date _added) throws
            ClientErrorException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        Category existingCategory = getCategoryByGroupAndUUID(_groupId, _uuid);
        if (existingCategory != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject deletedCategory = getDeletedCategoryByGroupAndUUID(_groupId, _uuid);
        if (deletedCategory != null && deletedCategory.getTime().after(_added)) {
            tx.rollback();
            throw new ConflictException();
        }

        Category rtn = new Category();
        rtn.setUUID(_uuid);
        rtn.setName(_name);
        rtn.setGroup(mManager.find(DeviceGroup.class, _groupId));
        rtn.setUpdated(_added);
        mManager.persist(rtn);
        tx.commit();
        mManager.refresh(rtn);

        return rtn;
    }

    public void update(int _groupId, UUID _categoryUUID, String _name, Date _changed) throws
            ClientErrorException {
        mManager.getTransaction().begin();
        Category catToEdit = getCategoryByGroupAndUUID(_groupId, _categoryUUID);
        if (catToEdit == null) {
            if (getDeletedCategoryByGroupAndUUID(_groupId, _categoryUUID) == null) {
                mManager.getTransaction().rollback();
                throw new NotFoundException();
            } else {
                mManager.getTransaction().rollback();
                throw new GoneException();
            }
        }
        if (catToEdit.getUpdated().after(_changed)) {
            mManager.getTransaction().rollback();
            throw new ConflictException();
        }
        catToEdit.setName(_name);
        catToEdit.setUpdated(_changed);
        mManager.getTransaction().commit();
    }


    public void delete(int _groupId, UUID _categoryUUID) throws NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        Category catToDelete = getCategoryByGroupAndUUID(_groupId, _categoryUUID);
        if (catToDelete == null) {
            if (getDeletedCategoryByGroupAndUUID(_groupId, _categoryUUID) == null) {
                tx.rollback();
                throw new NotFoundException();
            } else {
                tx.rollback();
                throw new GoneException();
            }
        }
        DeletedObject deletedCat = new DeletedObject();
        deletedCat.setType(DeletedObject.Type.CATEGORY);
        deletedCat.setGroup(mManager.find(DeviceGroup.class, _groupId));
        deletedCat.setUUID(_categoryUUID);
        mManager.persist(deletedCat);
        mManager.remove(catToDelete);
        tx.commit();
    }

    CategoryController(EntityManager _manager) {
        this.mManager = _manager;
    }

    private Category getCategoryByGroupAndUUID(int _groupId, UUID
            _catUUID) {
        TypedQuery<Category> categoryToChangeQuery = mManager.createQuery("select c from " +
                "Category c where c.group = :group and c.UUID = :uuid", Category.class);
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        categoryToChangeQuery.setParameter("group", group);
        categoryToChangeQuery.setParameter("uuid", _catUUID);
        List<Category> foundCategory = categoryToChangeQuery.getResultList();
        if (foundCategory.size() == 1)
            return foundCategory.get(0);
        return null;
    }

    private DeletedObject getDeletedCategoryByGroupAndUUID(int _groupId, UUID _catUUID) {
        TypedQuery<DeletedObject> deletedCategoryQuery = mManager.createQuery("select do from" +
                        " DeletedObject do where do.group = :group and do.UUID = :uuid order by " +
                        "do.time desc",
                DeletedObject.class);
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        deletedCategoryQuery.setParameter("group", group);
        deletedCategoryQuery.setParameter("uuid", _catUUID);
        List<DeletedObject> delCat = deletedCategoryQuery.getResultList();
        if (delCat.size() == 0)
            return null;
        return delCat.get(0);
    }
}
