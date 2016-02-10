package org.noorganization.instalist.server.controller.impl;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.controller.IListController;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.ShoppingList;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListController implements IListController {

    private EntityManager mManager;

    ListController(EntityManager _manager) {
        mManager = _manager;
    }

    public void add(int _groupId, UUID _listUUID, String _name, UUID _category, Date _lastChanged)
            throws ConflictException {
        ICategoryController categoryController = ControllerFactory.getCategoryController(mManager);

        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);
        ShoppingList found = getListByGroupAndUUID(group, _listUUID);
        if (found != null) {
            tx.rollback();
            throw new ConflictException();
        }
        Category cat = null;
        if (_category != null) {
            cat = categoryController.getCategoryByGroupAndUUID(_groupId, _category);
            if (cat == null) {
                tx.rollback();
                throw new ConflictException();
            }
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
                       Date _lastChanged)
            throws ConflictException, GoneException, NotFoundException {

    }

    public void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException {

    }

    ShoppingList getListByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<ShoppingList> listQuery = mManager.createQuery("select sl from ShoppingList sl" +
                " where sl.group = :group and sl.UUID = :uuid", ShoppingList.class);
        listQuery.setParameter("group", _group);
        listQuery.setParameter("uuid", _uuid);
        List<ShoppingList> lists = listQuery.getResultList();
        if (lists.size() == 0)
            return null;
        return lists.get(0);
    }
}
