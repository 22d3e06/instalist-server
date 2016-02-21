package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.IRecipeController;
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

class IngredientController implements IIngredientController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
                    float _amount, Instant _lastChanged)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Ingredient toCheck = findByGroupAndUUID(group, _ingredientUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = findDeletedByGroupAndUUID(group, _ingredientUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }
        IRecipeController recipeController = ControllerFactory.getRecipeController(mManager);
        Recipe newRecipe = recipeController.findByGroupAndUUID(group, _recipeUUID);
        IProductController productController = ControllerFactory.getProductController(mManager);
        Product newProduct = productController.findByGroupAndUUID(group, _productUUID);
        if (newProduct == null || newRecipe == null) {
            tx.rollback();
            throw new BadRequestException();
        }

        Ingredient toCreate = new Ingredient().withGroup(group);
        toCreate.setUUID(_ingredientUUID);
        toCreate.setRecipe(newRecipe);
        toCreate.setProduct(newProduct);
        toCreate.setAmount(_amount);
        toCreate.setUpdated(_lastChanged);
        mManager.persist(toCreate);

        tx.commit();
    }

    public void update(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
                       Float _amount, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Ingredient toUpdate = getIngredient(group, _ingredientUUID, tx);
        if (toUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }
        Recipe newRecipe = null;
        if (_recipeUUID != null) {
            IRecipeController recipeController = ControllerFactory.getRecipeController(mManager);
            newRecipe = recipeController.findByGroupAndUUID(group, _recipeUUID);
            if (newRecipe == null) {
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

        if (newRecipe != null)
            toUpdate.setRecipe(newRecipe);
        if (newProduct != null)
            toUpdate.setProduct(newProduct);
        if (_amount != null)
            toUpdate.setAmount(_amount);
        toUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    public void delete(int _groupId, UUID _ingredientUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Ingredient toDelete = getIngredient(group, _ingredientUUID, tx);
        DeletedObject oldProduct = new DeletedObject().withGroup(group);
        oldProduct.setUUID(_ingredientUUID);
        oldProduct.setType(DeletedObject.Type.INGREDIENT);
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    IngredientController(EntityManager _manager) {
        mManager = _manager;
    }

    private Ingredient getIngredient(DeviceGroup _group, UUID _ingredientUUID,
                                     EntityTransaction _tx)
            throws GoneException, NotFoundException {
        try {
            return findOrThrow(_group, _ingredientUUID);
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
    public Class<Ingredient> getManagedType() {
        return Ingredient.class;
    }
}
