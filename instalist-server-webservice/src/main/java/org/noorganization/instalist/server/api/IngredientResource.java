
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

package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.comm.message.IngredientInfo;
import org.noorganization.instalist.server.support.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Path("/groups/{groupid}/ingredients")
public class IngredientResource {

    /**
     * Get a list of ingredients.
     * @param _groupId The id of the group containing various ingredients.
     * @param _changedSince Limits the result to elements that changed since the given date. ISO
     *                      8601 time e.g. 2016-01-19T11:54:07+0100. Optional.
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getIngredients(@PathParam("groupid") int _groupId,
                                   @QueryParam("changedsince") String _changedSince)
            throws Exception {
        List<Ingredient> ingedients;
        List<DeletedObject> deletedIngredients;
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (_changedSince != null) {
            Instant changedSince;
            try {
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).
                        toInstant();
            } catch (ParseException _e) {
                manager.close();
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_CHANGEDATE);
            }

            TypedQuery<Ingredient> IngredientsQuery = manager.createQuery("select i from " +
                            "Ingredient i where i.group = :group and i.updated > :updated",
                    Ingredient.class);
            IngredientsQuery.setParameter("group", group);
            IngredientsQuery.setParameter("updated", changedSince);
            ingedients = IngredientsQuery.getResultList();

            TypedQuery<DeletedObject> deletedIngredientsQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.updated > :updated and " +
                    "do.type = :type", DeletedObject.class);
            deletedIngredientsQuery.setParameter("group", group);
            deletedIngredientsQuery.setParameter("updated", changedSince);
            deletedIngredientsQuery.setParameter("type", DeletedObject.Type.INGREDIENT);
            deletedIngredients = deletedIngredientsQuery.getResultList();
        } else {
            ingedients = new ArrayList<Ingredient>(group.getIngredients());

            TypedQuery<DeletedObject> deletedIngredientsQuery = manager.createQuery("select do from " +
                    "DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedIngredientsQuery.setParameter("group", group);
            deletedIngredientsQuery.setParameter("type", DeletedObject.Type.INGREDIENT);
            deletedIngredients = deletedIngredientsQuery.getResultList();
        }
        manager.close();

        ArrayList<IngredientInfo> rtn = new ArrayList<IngredientInfo>(ingedients.size() +
                deletedIngredients.size());
        for (Ingredient current : ingedients) {
            IngredientInfo toAdd = new IngredientInfo().withDeleted(false);
            toAdd.setUUID(current.getUUID());
            toAdd.setProductUUID(current.getProduct().getUUID());
            toAdd.setRecipeUUID(current.getRecipe().getUUID());
            toAdd.setAmount(current.getAmount());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }
        for (DeletedObject current : deletedIngredients) {
            IngredientInfo toAdd = new IngredientInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            toAdd.setDeleted(true);
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Returns a single ingredient.
     * @param _groupId The id of the group containing the ingredient.
     * @param _entryUUID The uuid of the ingredient itself.
     */
    @GET
    @TokenSecured
    @Path("{entryuuid}")
    @Produces({ "application/json" })
    public Response getIngredient(@PathParam("groupid") int _groupId,
                             @PathParam("entryuuid") String _entryUUID) throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_entryUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        IIngredientController ingredientController = ControllerFactory.
                getIngredientController(manager);

        Ingredient foundIngredient = ingredientController.findByGroupAndUUID(group,
                toFind);
        if (foundIngredient == null) {
            if (ingredientController.findDeletedByGroupAndUUID(group, toFind) != null) {
                manager.close();
                return ResponseFactory.generateGone(new Error().withMessage("The requested " +
                        "ingredient has been deleted."));
            }
            manager.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                    "ingredient was not found."));
        }
        manager.close();

        IngredientInfo rtn = new IngredientInfo().withDeleted(false);
        rtn.setUUID(foundIngredient.getUUID());
        rtn.setProductUUID(foundIngredient.getProduct().getUUID());
        rtn.setRecipeUUID(foundIngredient.getRecipe().getUUID());
        rtn.setAmount(foundIngredient.getAmount());
        rtn.setLastChanged(Date.from(foundIngredient.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates an ingredient.
     * @param _groupId The id of the group containing the ingredient.
     * @param _ingredientUUID The uuid of the ingredient itself.
     * @param _entity Data for updating the ingredient.
     */
    @PUT
    @TokenSecured
    @Path("{ingredientuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putIngredient(@PathParam("groupid") int _groupId,
                                  @PathParam("ingredientuuid") String _ingredientUUID,
                                  IngredientInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_ingredientUUID)) ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getAmount() != null && _entity.getAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATA);

        UUID toUpdate;
        UUID productUUID = null;
        UUID recipeUUID = null;
        try {
            toUpdate = UUID.fromString(_ingredientUUID);
            if (_entity.getProductUUID() != null)
                productUUID = UUID.fromString(_entity.getProductUUID());
            if (_entity.getRecipeUUID() != null)
                recipeUUID = UUID.fromString(_entity.getRecipeUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant updated;
        if (_entity.getLastChanged() != null) {
            updated = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(updated))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_CHANGEDATE);
        } else
            updated = Instant.now();

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IIngredientController ingredientController = ControllerFactory.
                getIngredientController(manager);
        try {
            ingredientController.update(_groupId, toUpdate, recipeUUID, productUUID,
                    _entity.getAmount(), updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The ingredient was " +
                    "not found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The ingredient has been " +
                    "deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved ingredient."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "product or recipe was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the ingredient.
     * @param _groupId The id of the group that will contain the ingredient.
     * @param _entity Data for the created ingredient.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postIngredient(@PathParam("groupid") int _groupId, IngredientInfo _entity)
            throws Exception {
        if (_entity.getUUID() == null || _entity.getRecipeUUID() == null ||
                _entity.getProductUUID() == null ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getAmount() != null && _entity.getAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATA);

        UUID toCreate;
        UUID productUUID;
        UUID recipeUUID;
        try {
            toCreate = UUID.fromString(_entity.getUUID());
            productUUID = UUID.fromString(_entity.getProductUUID());
            recipeUUID = UUID.fromString(_entity.getRecipeUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant created;
        if (_entity.getLastChanged() != null) {
            created = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(created))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_CHANGEDATE);
        } else
            created = Instant.now();
        float amount = (_entity.getAmount() != null ? _entity.getAmount() : 1f);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IIngredientController ingredientController = ControllerFactory.getIngredientController(manager);
        try {
            ingredientController.add(_groupId, toCreate, recipeUUID, productUUID, amount, created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved ingredient."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "recipe or product was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the ingredient.
     * @param _groupId The id of the group still containing the ingredient.
     * @param _ingredientUUID The uuid of the ingredient itself.
     */
    @DELETE
    @TokenSecured
    @Path("{ingredientuuid}")
    @Produces({ "application/json" })
    public Response deleteIngredient(@PathParam("groupid") int _groupId,
                                     @PathParam("ingredientuuid") String _ingredientUUID)
            throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_ingredientUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IIngredientController ingredientController = ControllerFactory.
                getIngredientController(manager);
        try {
            ingredientController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The ingredient was " +
                    "not found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The ingredient has been " +
                    "deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
