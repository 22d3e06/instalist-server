
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
public interface CategoriesResource {


    /**
     * Get a list of categories.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getCategories(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the category.
     * 
     * 
     * @param categoryId
     *     
     */
    @GET
    @Path("{categoryId}")
    @Produces({ "application/json" })
    Response getCategoryById(@PathParam("categoryId") String categoryId) throws Exception;

    /**
     * Updates the category.
     * 
     * 
     * @param categoryId
     *     
     * @param entity
     *      e.g. examples/category.example
     */
    @PUT
    @Path("{categoryId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putCategoryById(@PathParam("categoryId") String categoryId, Category entity)
            throws Exception;

    /**
     * Creates the category.
     * 
     * 
     * @param categoryId
     *     
     * @param entity
     *      e.g. examples/category.example
     */
    @POST
    @Path("{categoryId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postCategoryById(@PathParam("categoryId") String categoryId, Category entity)
        throws Exception;

    /**
     * Deletes the category.
     * 
     * 
     * @param categoryId
     *     
     */
    @DELETE
    @Path("{categoryId}")
    @Produces({ "application/json" })
    Response deleteCategoryById(@PathParam("categoryId") String categoryId) throws Exception;
}
