
package org.noorganization.instalist.server.api;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.noorganization.instalist.comm.support.DateHelper;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.comm.message.CategoryInfo;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
        Date changedSince = null;
        if (_changedSince != null) {
            changedSince = DateHelper.parseDate(_changedSince);
            if (changedSince == null)
                return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);
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
                                    "do.type = :type and do.time > :time",
                            DeletedObject.class);
            deletedCategoriesQuery.setParameter("groupid", group);
            deletedCategoriesQuery.setParameter("time", changedSince);
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
            info.setLastChanged(currentCat.getUpdated());
            info.setDeleted(false);
            rtnPayload.add(info);
        }
        for (DeletedObject currentCat: deletedCategories) {
            CategoryInfo info = new CategoryInfo();
            info.setUUID(currentCat.getUUID());
            info.setLastChanged(currentCat.getTime());
            info.setDeleted(true);
            rtnPayload.add(info);
        }

        return ResponseFactory.generateOK(rtnPayload);
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
//        int groupId;
//        if (_token == null || (groupId = mAuthController.getGroupIdByToken(_token)) < 0)
//            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        return null;
    }

    /**
     * Creates the category.
     * @param _token Authorization-token for the current user.
     * @param _entity Information for the new category.
     *      e.g. examples/category.example
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postCategory(@PathParam("groupid") int _groupId, CategoryInfo _entity) throws
            Exception {
        return null;
    }

    /**
     * Deletes the category.
     * 
     * 
     * @param _categoryId The uuid of the category to delete.
     * @param _token
     *     
     */
    @DELETE
    @TokenSecured
    @Path("{categoryuuid}")
    @Produces({ "application/json" })
    public Response deleteCategory(@PathParam("groupid") int _groupId,
                                   @PathParam("categoryuuid") String _uuid) throws Exception {
        return null;
    }

}
