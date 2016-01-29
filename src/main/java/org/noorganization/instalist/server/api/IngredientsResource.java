
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
import org.noorganization.instalist.server.model.Ingredient;


/**
 * Collection of available ingredients.
 * 
 */
@Path("ingredients")
public interface IngredientsResource {


    /**
     * Get a list of ingredients.
     *
     * @param changedSince Requests only the elements that changed since the given date. ISO 8601
     *                     time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getIngredients(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the ingredient.
     *
     * @param ingredientId
     */
    @GET
    @Path("{ingredientId}")
    @Produces({
            "application/json"
    })
    Response getIngredientById(@PathParam("ingredientId") String ingredientId) throws Exception;

    /**
     * Updates the ingredient.
     *
     * @param ingredientId
     * @param entity       e.g. examples/ingredient.example
     */
    @PUT
    @Path("{ingredientId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putIngredientById(@PathParam("ingredientId") String ingredientId, Ingredient entity)
            throws Exception;

    /**
     * Creates the ingredient.
     *
     * @param ingredientId
     * @param entity       e.g. examples/ingredient.example
     */
    @POST
    @Path("{ingredientId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postIngredientById(@PathParam("ingredientId") String ingredientId, Ingredient entity)
            throws Exception;

    /**
     * Deletes the ingredient.
     *
     * @param ingredientId
     */
    @DELETE
    @Path("{ingredientId}")
    @Produces({ "application/json" })
    Response deleteIngredientById(@PathParam("ingredientId") String ingredientId) throws Exception;
}
