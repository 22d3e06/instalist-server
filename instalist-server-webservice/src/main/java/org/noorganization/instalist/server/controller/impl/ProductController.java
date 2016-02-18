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
import java.util.List;
import java.util.UUID;

class ProductController implements IProductController {

    private EntityManager mManager;

    @Override
    public void add(int _groupId, UUID _newUUID, String _name, float _defaultAmount,
                    float _stepAmount, UUID _unitUUID, Instant _created)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Product toCheck = findByGroupAndUUID(group, _newUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = findDeletedByGroupAndUUID(group, _newUUID);
        if (previousDeleted != null && _created.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }
        Unit newUnit = null;
        if (_unitUUID != null) {
            IUnitController unitController = ControllerFactory.getUnitController(mManager);
            newUnit = unitController.findByGroupAndUUID(group, _unitUUID);
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

    @Override
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
            newUnit = unitController.findByGroupAndUUID(group, _unitUUID);
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

    @Override
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
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    ProductController(EntityManager _manager) {
        mManager = _manager;
    }

    private Product getProduct(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        Product rtn = findByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (findDeletedByGroupAndUUID(_group, _uuid) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<Product> getManagedType() {
        return Product.class;
    }
}
