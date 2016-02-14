
package org.noorganization.instalist.server.api;

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

import org.noorganization.instalist.comm.message.RecipeInfo;
import org.noorganization.instalist.server.TokenSecured;


/**
 * Collection of available recipes.
 * 
 */
@Path("/groups/{groupid}/recipes")
public class RecipeResource {


    /**
     * Get a list of recipes.
     * 
     * 
     * @param _changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getRecipes(@PathParam("groupid") int _groupId,
                               @QueryParam("changedsince") String _changedSince) throws Exception {
        return null;
    }

    /**
     * Get a list of ingredients.
     * 
     * 
     * @param _recipeUUID
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Path("{recipeuuid}")
    @Produces({ "application/json" })
    public Response getRecipeIngredients(@PathParam("groupid") int _groupId,
                                         @PathParam("recipeuuid") String _recipeUUID) throws
            Exception {
        return null;
    }

    /**
     * Updates the recipe.
     * 
     * 
     * @param recipeId
     *     
     * @param entity
     *      e.g. examples/recipe.example
     */
    @PUT
    @TokenSecured
    @Path("{recipeuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putRecipeById(@PathParam("groupid") int _groupId,
                           @PathParam("recipeuuid") String _recipeUUID, RecipeInfo entity) throws
            Exception {
        return null;
    }

    /**
     * Creates the recipe.
     * 
     * 
     * @param recipeId
     *     
     * @param entity
     *      e.g. examples/recipe.example
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postRecipeById(@PathParam("groupid") int _groupId,
                            @PathParam("recipeuuid") String _recipeUUID, RecipeInfo entity) throws
            Exception {
        return null;
    }

    /**
     * Deletes the recipe.
     * 
     * 
     * @param recipeId
     *     
     */
    @DELETE
    @TokenSecured
    @Path("{recipeuuid}")
    @Produces({ "application/json" })
    public Response deleteRecipeById(@PathParam("groupid") int _groupId,
                              @PathParam("recipeuuid") String _recipeUUID) throws Exception {
        return null;
    }

}
