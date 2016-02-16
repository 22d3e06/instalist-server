package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.IUnitController;
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

class ProductController implements IProductController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _newUUID, String _name, float _defaultAmount,
                    float _stepAmount, UUID _unitUUID, Instant _created)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Product toCheck = getProductByGroupAndUUID(group, _newUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = getDeletedProductByGroupAndUUID(group, _newUUID);
        if (previousDeleted != null && _created.isBefore(previousDeleted.getTime().toInstant())) {
            tx.rollback();
            throw new ConflictException();
        }
        Unit newUnit = null;
        if (_unitUUID != null) {
            IUnitController unitController = ControllerFactory.getUnitController(mManager);
            newUnit = unitController.getUnitByGroupAndUUID(group, _unitUUID);
            if (newUnit == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }

        Product toCreate = new Product().withGroup(group);
        toCreate.setUUID(_newUUID);
        toCreate.setName(_name);
        toCreate.setDefaultAmount(_defaultAmount);
        toCreate.setStepAmount(_stepAmount);
        toCreate.setUnit(newUnit);
        toCreate.setUpdated(_created);
        mManager.persist(toCreate);

        tx.commit();
    }

    public void update(int _groupId, UUID _uuid, String _name, Float _defaultAmount,
                       Float _stepAmount, UUID _unitUUID, boolean _removeUnit, Instant _updated)
            throws ConflictException, NotFoundException, GoneException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Product toUpdate = getProduct(group, _uuid, tx);
        if (toUpdate.getUpdated().isAfter(_updated)) {
            tx.rollback();
            throw new ConflictException();
        }
        Unit newUnit = null;
        if (!_removeUnit && _unitUUID != null) {
            IUnitController unitController = ControllerFactory.getUnitController(mManager);
            newUnit = unitController.getUnitByGroupAndUUID(group, _unitUUID);
            if (newUnit == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }

        if (_name != null)
            toUpdate.setName(_name);
        if (_defaultAmount != null)
            toUpdate.setDefaultAmount(_defaultAmount);
        if (_stepAmount != null)
            toUpdate.setStepAmount(_stepAmount);
        if (_removeUnit)
            toUpdate.setUnit(null);
        else if (newUnit != null)
            toUpdate.setUnit(newUnit);
        toUpdate.setUpdated(_updated);

        tx.commit();
    }

    public void delete(int _groupId, UUID _uuid) throws NotFoundException, GoneException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Product toDelete = getProduct(group, _uuid, tx);

        IEntryController entryController = ControllerFactory.getEntryController(mManager);
        for (ListEntry entry: toDelete.getEntries()) {
            try {
                entryController.delete(_groupId, entry.getUUID());
            } catch (Exception _e) {}
        }
        IIngredientController ingredientController = ControllerFactory.
                getIngredientController(mManager);
        for (Ingredient ingredient: toDelete.getIngredients()) {
            try {
                ingredientController.delete(_groupId, ingredient.getUUID());
            } catch (Exception _e) {}
        }
        DeletedObject oldProduct = new DeletedObject().withGroup(group);
        oldProduct.setUUID(_uuid);
        oldProduct.setType(DeletedObject.Type.PRODUCT);
        oldProduct.setTime(Date.from(Instant.now()));
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    public Product getProductByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<Product> productQuery = mManager.createQuery("select p from Product p where " +
                "p.UUID = :uuid and p.group = :group", Product.class);
        productQuery.setParameter("uuid", _uuid);
        productQuery.setParameter("group", _group);
        productQuery.setMaxResults(1);
        List<Product> products = productQuery.getResultList();
        if (products.size() == 0)
            return null;
        return products.get(0);
    }

    public DeletedObject getDeletedProductByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delProductQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                "do.type = :type order by do.time desc",
                DeletedObject.class);
        delProductQuery.setParameter("group", _group);
        delProductQuery.setParameter("uuid", _uuid);
        delProductQuery.setParameter("type", DeletedObject.Type.PRODUCT);
        delProductQuery.setMaxResults(1);
        List<DeletedObject> delProductResult = delProductQuery.getResultList();
        if (delProductResult.size() == 0)
            return null;
        return delProductResult.get(0);
    }

    ProductController(EntityManager _manager) {
        mManager = _manager;
    }

    private Product getProduct(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        Product rtn = getProductByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (getDeletedProductByGroupAndUUID(_group, _uuid) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }
}
