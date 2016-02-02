
package org.noorganization.instalist.server.api;

import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.noorganization.instalist.server.message.Category;


/**
 * Collection of available categories.
 * 
 */
@Path("categories")
public class CategoriesResource {


    /**
     * Get a list of categories.
     *
     * @param _categoryId Optional. The uuid of the category.
     * @param _token Authorization-token for the current user.
     * @param _changedSince Optional. Requests only the elements that changed since the given date.
     *                      ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getCategories(@QueryParam("token") String _token, @QueryParam("changedsince") Date
            _changedSince, @QueryParam("categoryid") String  _categoryId) throws Exception {
        return null;
    }

    /**
     * Updates the category.
     * @param _token Authorization-token for the current user.
     * @param _entity A category with updated information.
     */
    @PUT
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putCategoryById(@QueryParam("token") String _token, Category _entity) throws
            Exception {
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
    Response postCategoryById(@QueryParam("token") String _token, Category _entity) throws
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
    @Produces({ "application/json" })
    Response deleteCategoryById(@QueryParam("token") String _token, @QueryParam("categoryid")
    String _categoryId) throws Exception {
        return null;
    }
}
