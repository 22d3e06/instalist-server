
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
import org.noorganization.instalist.server.message.ShoppingList;


/**
 * Collection of available lists.
 * 
 */
@Path("lists")
public interface ListResource {


    /**
     * Get a list of lists.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getLists(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the list.
     * 
     * 
     * @param listId
     *     
     */
    @GET
    @Path("{listId}")
    @Produces({ "application/json" })
    Response getListById(@PathParam("listId") String listId) throws Exception;

    /**
     * Updates the list.
     * 
     * 
     * @param listId
     *     
     * @param entity
     *      e.g. examples/shoppingList.example
     */
    @PUT
    @Path("{listId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putListById(@PathParam("listId") String listId, ShoppingList entity) throws Exception;

    /**
     * Creates the list.
     * 
     * 
     * @param listId
     *     
     * @param entity
     *      e.g. examples/shoppingList.example
     */
    @POST
    @Path("{listId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postListById(@PathParam("listId") String listId, ShoppingList entity) throws Exception;

    /**
     * Deletes the list.
     * 
     * 
     * @param listId
     *     
     */
    @DELETE
    @Path("{listId}")
    @Produces({ "application/json" })
    Response deleteListById(@PathParam("listId") String listId) throws Exception;

}
