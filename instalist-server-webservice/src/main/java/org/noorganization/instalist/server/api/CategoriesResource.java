
package org.noorganization.instalist.server.api;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.server.support.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.comm.message.CategoryInfo;
import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.exceptions.GoneException;
import org.noorganization.instalist.server.support.ResponseFactory;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Collection of available categories.
 * 
 */
@Path("/groups/{groupid}/categories")
public class CategoriesResource {


    /**
     * Get a list of categories.
     *
     * @param _groupId The id of the group.
     * @param _changedSince Optional. Requests only the elements that changed since the given date.
     *                      ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getCategories(@PathParam("groupid") int _groupId,
                                  @QueryParam("changedsince") String _changedSince)
            throws Exception {
        try {


        Instant changedSince = null;
        if (_changedSince != null) {
            try {
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).toInstant();
            } catch (ParseException _e) {
                return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);
            }
        }

        List<Category> categories;
        List<DeletedObject> deletedCategories;
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (changedSince != null) {
            TypedQuery<Category> categoriesQuery =
                    manager.createQuery("select c from Category c " +
                                    "where c.group = :groupid and c.updated > :updated",
                            Category.class);
            categoriesQuery.setParameter("groupid", group);
            categoriesQuery.setParameter("updated", changedSince);
            categories = categoriesQuery.getResultList();

            TypedQuery<DeletedObject> deletedCategoriesQuery =
                    manager.createQuery("select do " +
                                    "from DeletedObject do where do.group = :groupid and " +
                                    "do.type = :type and do.updated > :updated",
                            DeletedObject.class);
            deletedCategoriesQuery.setParameter("groupid", group);
            deletedCategoriesQuery.setParameter("updated", changedSince);
            deletedCategoriesQuery.setParameter("type", DeletedObject.Type.CATEGORY);
            deletedCategories = deletedCategoriesQuery.getResultList();
        } else {
            TypedQuery<Category> categoriesQuery =
                    manager.createQuery("select c from Category c " +
                            "where c.group = :groupid", Category.class);
            categoriesQuery.setParameter("groupid", group);
            categories = categoriesQuery.getResultList();

            TypedQuery<DeletedObject> deletedCategoriesQuery =
                    manager.createQuery("select do " +
                                    "from DeletedObject do where do.group = :groupid and " +
                                    "do.type = :type",
                            DeletedObject.class);
            deletedCategoriesQuery.setParameter("groupid", group);
            deletedCategoriesQuery.setParameter("type", DeletedObject.Type.CATEGORY);
            deletedCategories = deletedCategoriesQuery.getResultList();
        }
        manager.close();

        List<CategoryInfo> rtnPayload = new ArrayList<CategoryInfo>(categories.size() +
                deletedCategories.size());
        for (Category currentCat: categories) {
            CategoryInfo info = new CategoryInfo();
            info.setUUID(currentCat.getUUID());
            info.setName(currentCat.getName());
            info.setLastChanged(Date.from(currentCat.getUpdated()));
            info.setDeleted(false);
            rtnPayload.add(info);
        }
        for (DeletedObject currentCat: deletedCategories) {
            CategoryInfo info = new CategoryInfo();
            info.setUUID(currentCat.getUUID());
            info.setLastChanged(Date.from(currentCat.getUpdated()));
            info.setDeleted(true);
            rtnPayload.add(info);
        }

        return ResponseFactory.generateOK(rtnPayload);
        }catch (Exception _e) {
            _e.printStackTrace();
            throw _e;
        }
    }

    /**
     * Get a list of categories.
     *
     * @param _groupId The id of the group.
     * @param _categoryUUID The uuid of the category to fetch.
     */
    @GET
    @TokenSecured
    @Path("{categoryuuid}")
    @Produces({ "application/json" })
    public Response getCategory(@PathParam("groupid") int _groupId,
                                @PathParam("categoryuuid") String _categoryUUID)
            throws Exception {
        UUID categoryUUID;
        try {
            categoryUUID = UUID.fromString(_categoryUUID);
        } catch (IllegalArgumentException e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }


        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        TypedQuery<Category> categoriesQuery =
                manager.createQuery("select c from Category c " +
                        "where c.group = :groupid and c.UUID = :uuid", Category.class);
        categoriesQuery.setParameter("groupid", group);
        categoriesQuery.setParameter("uuid", categoryUUID);
        List<Category> categories = categoriesQuery.getResultList();
        if (categories.size() != 1) {
            TypedQuery<DeletedObject> deletedCategoriesQuery =
                    manager.createQuery("select do " +
                                    "from DeletedObject do where do.group = :groupid and " +
                                    "do.type = :type and do.UUID = :uuid",
                            DeletedObject.class);
            deletedCategoriesQuery.setParameter("groupid", group);
            deletedCategoriesQuery.setParameter("type", DeletedObject.Type.CATEGORY);
            deletedCategoriesQuery.setParameter("uuid", categoryUUID);
            List<DeletedObject> deletedCategories = deletedCategoriesQuery.getResultList();
            manager.close();
            if (deletedCategories.size() == 1) {
                CategoryInfo catInfo = new CategoryInfo();
                catInfo.setDeleted(true);
                catInfo.setLastChanged(Date.from(deletedCategories.get(0).getUpdated()));
                catInfo.setUUID(categoryUUID);
                return ResponseFactory.generateGone(catInfo);
            } else {
                return ResponseFactory.generateNotFound(new Error().withMessage("Category was not" +
                        " found."));
            }
        }
        manager.close();
        CategoryInfo catInfo = new CategoryInfo();
        catInfo.setDeleted(false);
        catInfo.setLastChanged(Date.from(categories.get(0).getUpdated()));
        catInfo.setName(categories.get(0).getName());
        catInfo.setUUID(categoryUUID);

        return ResponseFactory.generateOK(catInfo);
    }

    /**
     * Updates the category.
     * @param _uuid The uuid of the category to update.
     * @param _entity A category with updated information.
     */
    @PUT
    @TokenSecured
    @Path("{categoryuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putCategory(@PathParam("groupid") int _groupId,
                                @PathParam("categoryuuid") String _uuid,
                                CategoryInfo _entity) throws Exception {
        if (_entity.getName() == null)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_uuid)) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        Instant changedDate = Instant.now();
        if (_entity.getLastChanged() != null) {
            changedDate = _entity.getLastChanged().toInstant();
            if (changedDate.isAfter(Instant.now()))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        }

        UUID categoryUUID;
        try {
            categoryUUID = UUID.fromString(_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ICategoryController categoryController =
                ControllerFactory.getCategoryController(manager);
        try {
            categoryController.update(_groupId, categoryUUID, _entity.getName(), changedDate);
        } catch (NotFoundException e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("Category was not " +
                            "found."));
        } catch (GoneException e) {
            return ResponseFactory.generateGone(new Error().withMessage("Category was already " +
                            "deleted."));
        } catch (ConflictException e) {
            return ResponseFactory.generateConflict(new Error().withMessage("Sent sategory is in " +
                    "conflict with saved one."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the category.
     * @param _groupId The group to add the category to.
     * @param _entity Information for the new category.
     *      e.g. examples/category.example
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postCategory(@PathParam("groupid") int _groupId, CategoryInfo _entity) throws
            Exception {
        if (_entity.getUUID() == null || _entity.getName() == null ||
                _entity.getName().length() == 0 || (_entity.getDeleted() != null &&
                _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);
        Instant lastChanged;
        if (_entity.getLastChanged() != null) {
            lastChanged = _entity.getLastChanged().toInstant();
            if (lastChanged.isAfter(Instant.now()))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            lastChanged = Instant.now();

        UUID newCatUUID;
        try {
            newCatUUID = UUID.fromString(_entity.getUUID());
        } catch (IllegalArgumentException e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ICategoryController categoryController = ControllerFactory.getCategoryController(manager);
        try {
            categoryController.add(_groupId, newCatUUID, _entity.getName(), lastChanged);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The new category " +
                    "stands in conflict with existing one."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the category.
     * 
     * @param _groupId The group of the category to delete.
     * @param _uuid The uuid of the category to delete.
     */
    @DELETE
    @TokenSecured
    @Path("{categoryuuid}")
    @Produces({ "application/json" })
    public Response deleteCategory(@PathParam("groupid") int _groupId,
                                   @PathParam("categoryuuid") String _uuid) throws Exception {
        UUID newCatUUID;
        try {
            newCatUUID = UUID.fromString(_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseFactory.generateNotFound(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ICategoryController categoryController = ControllerFactory.getCategoryController(manager);
        try {
            categoryController.delete(_groupId, newCatUUID);
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The category " +
                    "was already deleted"));
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The category " +
                    "was not found."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The category still " +
                    "contains lists."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
