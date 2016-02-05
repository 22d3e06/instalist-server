
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
import org.noorganization.instalist.server.message.Recipe;


/**
 * Collection of available recipes.
 * 
 */
@Path("recipes")
public interface RecipesResource {


    /**
     * Get a list of recipes.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getRecipes(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Get a list of ingredients.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Path("ingredients")
    @Produces({ "application/json" })
    Response getRecipeIngredients(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the recipe.
     * 
     * 
     * @param recipeId
     *     
     */
    @GET
    @Path("{recipeId}")
    @Produces({ "application/json" })
    Response getRecipeById(@PathParam("recipeId") String recipeId) throws Exception;

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
    @Path("{recipeId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putRecipeById(@PathParam("recipeId") String recipeId, Recipe entity) throws Exception;

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
    @Path("{recipeId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postRecipeById(@PathParam("recipeId") String recipeId, Recipe entity) throws Exception;

    /**
     * Deletes the recipe.
     * 
     * 
     * @param recipeId
     *     
     */
    @DELETE
    @Path("{recipeId}")
    @Produces({ "application/json" })
    Response deleteRecipeById(@PathParam("recipeId") String recipeId) throws Exception;

}
