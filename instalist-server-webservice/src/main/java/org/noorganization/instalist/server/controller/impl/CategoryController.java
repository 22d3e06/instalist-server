package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class CategoryController implements ICategoryController {

    private EntityManager mManager;

    public Category add(int _groupId, UUID _uuid, String _name) throws ClientErrorException {
        return null;
    }

    public void update(int _groupId, UUID _categoryUUID, String _name, Date _changed) throws
            ClientErrorException {
        mManager.getTransaction().begin();
        Category catToEdit = getCategoryByGroupAndUUID(mManager, _groupId, _categoryUUID);
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


    public void delete(int _id) throws ClientErrorException {

    }

    CategoryController(EntityManager _manager) {
        this.mManager = _manager;
    }

    private Category getCategoryByGroupAndUUID(EntityManager _manager, int _groupId, UUID
            _catUUID) {
        TypedQuery<Category> categoryToChangeQuery = _manager.createQuery("select c from " +
                "Category c where c.group = :group and c.UUID = :uuid", Category.class);
        DeviceGroup group = _manager.find(DeviceGroup.class, _groupId);
        categoryToChangeQuery.setParameter("group", group);
        categoryToChangeQuery.setParameter("uuid", _catUUID);
        List<Category> foundCategory = categoryToChangeQuery.getResultList();
        if (foundCategory.size() == 1)
            return foundCategory.get(0);
        return null;
    }

    private DeletedObject getDeletedCategoryByGroupAndUUID(int _groupId, UUID _catUUID) {
        TypedQuery<DeletedObject> deletedCategoryQuery = mManager.createQuery("select do from" +
                        " DeletedObject do where do.group = :group and do.UUID = :uuid",
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
