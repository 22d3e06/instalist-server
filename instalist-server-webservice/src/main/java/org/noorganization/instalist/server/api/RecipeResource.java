
package org.noorganization.instalist.server.api;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.RecipeInfo;
import org.noorganization.instalist.server.support.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IRecipeController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Recipe;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/groups/{groupid}/recipes")
public class RecipeResource {


    /**
     * Get a list of recipes.
     * @param _groupId The id of the group containing the recipes.
     * @param _changedSince Limits the request to elements that changed since the given date. ISO
     *                      8601 time e.g. 2016-01-19T11:54:07+0100. Optional.
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getRecipes(@PathParam("groupid") int _groupId,
                               @QueryParam("changedsince") String _changedSince) throws Exception {
        Instant changedSince = null;
        try {
            if (_changedSince != null)
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).
                    toInstant();
        } catch (ParseException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_CHANGEDATE);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        List<Recipe> recipes;
        List<DeletedObject> deletedRecipes;
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (changedSince != null) {
            TypedQuery<Recipe> recipeQuery = manager.createQuery("select r from Recipe r where " +
                    "r.group = :group and r.updated > :updated", Recipe.class);
            recipeQuery.setParameter("group", group);
            recipeQuery.setParameter("updated", changedSince);
            recipes = recipeQuery.getResultList();

            TypedQuery<DeletedObject> deletedRecipesQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.updated > :updated and " +
                    "do.type = :type", DeletedObject.class);
            deletedRecipesQuery.setParameter("group", group);
            deletedRecipesQuery.setParameter("updated", changedSince);
            deletedRecipesQuery.setParameter("type", DeletedObject.Type.RECIPE);
            deletedRecipes = deletedRecipesQuery.getResultList();
        } else {
            recipes = new ArrayList<Recipe>(group.getRecipes());

            TypedQuery<DeletedObject> deletedRecipesQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedRecipesQuery.setParameter("group", group);
            deletedRecipesQuery.setParameter("type", DeletedObject.Type.RECIPE);
            deletedRecipes = deletedRecipesQuery.getResultList();
        }
        manager.close();

        ArrayList<RecipeInfo> rtn = new ArrayList<RecipeInfo>(recipes.size() +
                deletedRecipes.size());
        for (Recipe current: recipes) {
            RecipeInfo toAdd = new RecipeInfo().withDeleted(false);
            toAdd.setUUID(current.getUUID());
            toAdd.setName(current.getName());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }
        for (DeletedObject current: deletedRecipes) {
            RecipeInfo toAdd = new RecipeInfo().withDeleted(true);
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Get a single recipe.
     * @param _groupId The id of the group containing the recipe.
     * @param _recipeUUID The uuid of the requested recipe.
     */
    @GET
    @TokenSecured
    @Path("{recipeuuid}")
    @Produces({ "application/json" })
    public Response getRecipe(@PathParam("groupid") int _groupId,
                              @PathParam("recipeuuid") String _recipeUUID) throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_recipeUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IRecipeController recipeController = ControllerFactory.getRecipeController(manager);
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        Recipe current = recipeController.findByGroupAndUUID(group, toFind);
        if (current == null) {
            if (recipeController.findDeletedByGroupAndUUID(group, toFind) == null) {
                manager.close();
                return ResponseFactory.generateNotFound(new Error().withMessage("Recipe was not " +
                        "found."));
            }
            manager.close();
            return ResponseFactory.generateGone(new Error().withMessage("Recipe was deleted " +
                    "before."));
        }
        manager.close();

        RecipeInfo rtn = new RecipeInfo().withDeleted(false);
        rtn.setUUID(current.getUUID());
        rtn.setName(current.getName());
        rtn.setLastChanged(Date.from(current.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates the recipe.
     * @param _groupId The id of the group containing the recipe to change.
     * @param _recipeUUID The uuid of the recipe identifying it in the group.
     * @param _entity Data to change.
     */
    @PUT
    @TokenSecured
    @Path("{recipeuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putRecipe(@PathParam("groupid") int _groupId,
                              @PathParam("recipeuuid") String _recipeUUID,
                              RecipeInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_recipeUUID)) ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATA);

        UUID toUpdate;
        try {
            toUpdate = UUID.fromString(_recipeUUID);
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
        IRecipeController recipeController = ControllerFactory.getRecipeController(manager);
        try {
            recipeController.update(_groupId, toUpdate, _entity.getName(), updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The recipe was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The recipe has been " +
                    "deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved recipe."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the recipe.
     * @param _groupId The id of the group that should contain the new recipe.
     * @param _entity Data to change.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postRecipe(@PathParam("groupid") int _groupId,
                               RecipeInfo _entity) throws Exception {
        try {


        if (_entity.getUUID() == null ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATA);

        UUID toCreate;
        try {
            toCreate = UUID.fromString(_entity.getUUID());
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

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IRecipeController recipeController = ControllerFactory.getRecipeController(manager);
        try {
            recipeController.add(_groupId, toCreate, _entity.getName(), created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved recipe."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
        }catch (Exception _e) {
            _e.printStackTrace();
            throw _e;
        }
    }

    /**
     * Deletes the recipe.
     * @param _groupId The id of the group still containing the recipe.
     * @param _recipeUUID the uuid of the recipe to delete.
     */
    @DELETE
    @TokenSecured
    @Path("{recipeuuid}")
    @Produces({ "application/json" })
    public Response deleteRecipe(@PathParam("groupid") int _groupId,
                                 @PathParam("recipeuuid") String _recipeUUID) throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_recipeUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IRecipeController recipeController = ControllerFactory.getRecipeController(manager);
        try {
            recipeController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The recipe was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The recipe has been " +
                    "deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
