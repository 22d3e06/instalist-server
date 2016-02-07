
package org.noorganization.instalist.server.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.comm.message.CategoryInfo;


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
        /*int groupId;
        if (_token == null || (groupId = mAuthController.getDeviceGroupByToken(_token)) < 0)
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);


        Date filterDate = null;
        if (_changedSince != null && (filterDate = DateHelper.parseDate(_changedSince)) == null)
            return ResponseFactory.generateBadRequest(new Error().withMessage("Provided date is " +
                    "invalid."));

        UUID filterUUID = null;
        try {
            if(_categoryId != null)
                filterUUID = UUID.fromString(_categoryId);
        } catch (IllegalArgumentException e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("Provided uuid is " +
                    "invalid."));
        }

        Connection db = DatabaseHelper.getInstance().getConnection();
        PreparedStatement categoriesStmt;
        if (filterDate != null && filterUUID != null) {
            categoriesStmt = db.prepareStatement("SELECT uuid, name, updated, FALSE as deleted " +
                    "FROM categories WHERE devicegroup_id = ? AND uuid = ? AND updated > ? UNION " +
                    "SELECT uuid, NULL as name, time, TRUE as deleted FROM deletion_log WHERE " +
                    "devicegroup_id = ? AND uuid = ? AND time > ?");
            categoriesStmt.setString(2, filterUUID.toString());
            categoriesStmt.setTimestamp(3, new Timestamp(filterDate.getTime()));
            categoriesStmt.setInt(4, groupId);
            categoriesStmt.setString(5, filterUUID.toString());
            categoriesStmt.setTimestamp(6, new Timestamp(filterDate.getTime()));
        } else if (filterDate != null) {
            categoriesStmt = db.prepareStatement("SELECT uuid, name, updated, FALSE as deleted " +
                    "FROM categories WHERE devicegroup_id = ? AND updated > ? UNION " +
                    "SELECT uuid, NULL as name, time, TRUE as deleted FROM deletion_log WHERE " +
                    "devicegroup_id = ? AND time > ?");
            System.out.println("Sql: " + new java.sql.Date(filterDate.getTime()).getTime());
            categoriesStmt.setTimestamp(2, new Timestamp(filterDate.getTime()));
            categoriesStmt.setInt(3, groupId);
            categoriesStmt.setTimestamp(4, new Timestamp(filterDate.getTime()));
        } else if (filterUUID != null) {
            categoriesStmt = db.prepareStatement("SELECT uuid, name, updated, FALSE as deleted " +
                    "FROM categories WHERE devicegroup_id = ? AND uuid = ? UNION " +
                    "SELECT uuid, NULL as name, time, TRUE as deleted FROM deletion_log WHERE " +
                    "devicegroup_id = ? AND uuid = ?");
            categoriesStmt.setString(2, filterUUID.toString());
            categoriesStmt.setInt(3, groupId);
            categoriesStmt.setString(4, filterUUID.toString());
        } else {
            categoriesStmt = db.prepareStatement("SELECT uuid, name, updated, FALSE as deleted " +
                    "FROM categories WHERE devicegroup_id = ? UNION " +
                    "SELECT uuid, NULL as name, time, TRUE as deleted FROM deletion_log WHERE " +
                    "devicegroup_id = ?");
            categoriesStmt.setInt(2, groupId);
        }
        categoriesStmt.setInt(1, groupId);
        ResultSet categoriesRS = categoriesStmt.executeQuery();
        List<Category> categoriesResult = new LinkedList<Category>();
        while (categoriesRS.next()) {
            Category toRtn = new Category();
            toRtn.setUUID(categoriesRS.getString("uuid"));
            toRtn.setName(categoriesRS.getString("name"));
            toRtn.setLastChanged(categoriesRS.getDate("updated"));
            toRtn.setDeleted(categoriesRS.getBoolean("deleted"));
            categoriesResult.add(toRtn);
        }
        categoriesRS.close();
        categoriesStmt.close();
        db.close();

        return ResponseFactory.generateOK(categoriesResult);*/
        return null;
    }

    /**
     * Updates the category.
     * @param _uuid The uuid of the category to update.
     * @param _entity A category with updated information.
     */
    @PUT
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
    @Path("{categoryuuid}")
    @Produces({ "application/json" })
    public Response deleteCategory(@PathParam("groupid") int _groupId,
                                   @PathParam("categoryuuid") String _uuid) throws Exception {
        return null;
    }

}
