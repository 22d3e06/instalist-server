package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.controller.IRecipeController;
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

class RecipeController implements IRecipeController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _recipeUUID, String _name, Instant _lastChanged)
            throws ConflictException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Recipe toCheck = getRecipeByGroupAndUUID(group, _recipeUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = getDeletedRecipeByGroupAndUUID(group, _recipeUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getTime().
                toInstant())) {
            tx.rollback();
            throw new ConflictException();
        }

        Recipe toCreate = new Recipe().withGroup(group);
        toCreate.setUUID(_recipeUUID);
        toCreate.setName(_name);
        toCreate.setUpdated(_lastChanged);
        mManager.persist(toCreate);

        tx.commit();
    }

    public void update(int _groupId, UUID _recipeUUID, String _name, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Recipe toUpdate = getRecipe(group, _recipeUUID, tx);
        if (toUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }

        if (_name != null)
            toUpdate.setName(_name);
        toUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    public void delete(int _groupId, UUID _recipeUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Recipe toDelete = getRecipe(group, _recipeUUID, tx);

        IIngredientController ingredientController = ControllerFactory.
                getIngredientController(mManager);
        for (Ingredient ingredient: toDelete.getIngredients()) {
            try {
                ingredientController.delete(_groupId, ingredient.getUUID());
            } catch (Exception _e) {}
        }
        DeletedObject oldRecipe = new DeletedObject().withGroup(group);
        oldRecipe.setUUID(_recipeUUID);
        oldRecipe.setType(DeletedObject.Type.RECIPE);
        oldRecipe.setTime(Date.from(Instant.now()));
        mManager.persist(oldRecipe);
        mManager.remove(toDelete);

        tx.commit();
    }

    public Recipe getRecipeByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<Recipe> recipeQuery = mManager.createQuery("select r from Recipe r where " +
                "r.UUID = :uuid and r.group = :group", Recipe.class);
        recipeQuery.setParameter("uuid", _uuid);
        recipeQuery.setParameter("group", _group);
        recipeQuery.setMaxResults(1);
        List<Recipe> recipes = recipeQuery.getResultList();
        if (recipes.size() == 0)
            return null;
        return recipes.get(0);
    }

    public DeletedObject getDeletedRecipeByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delRecipeQuery = mManager.createQuery("select do from " +
                        "DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                        "do.type = :type order by do.time desc",
                DeletedObject.class);
        delRecipeQuery.setParameter("group", _group);
        delRecipeQuery.setParameter("uuid", _uuid);
        delRecipeQuery.setParameter("type", DeletedObject.Type.RECIPE);
        delRecipeQuery.setMaxResults(1);
        List<DeletedObject> delRecipeResult = delRecipeQuery.getResultList();
        if (delRecipeResult.size() == 0)
            return null;
        return delRecipeResult.get(0);
    }

    RecipeController(EntityManager _manager) {
        mManager = _manager;
    }

    private Recipe getRecipe(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        Recipe rtn = getRecipeByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (getDeletedRecipeByGroupAndUUID(_group, _uuid) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }
}
