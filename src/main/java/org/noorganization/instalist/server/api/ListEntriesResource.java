
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
import org.noorganization.instalist.server.model.ListEntry;


/**
 * Collection of available listEntries.
 * 
 */
@Path("listEntries")
public interface ListEntriesResource {


    /**
     * Get a list of listEntries.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getListEntries(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @GET
    @Path("{listEntryId}")
    @Produces({ "application/json" })
    Response getListEntryById(@PathParam("listEntryId") String listEntryId) throws Exception;

    /**
     * Updates the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     * @param entity
     *      e.g. examples/shoppingList.example
     */
    @PUT
    @Path("{listEntryId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putListEntryById(@PathParam("listEntryId") String listEntryId, ListEntry entity)
            throws Exception;

    /**
     * Creates the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     * @param entity
     *      e.g. examples/shoppingList.example
     */
    @POST
    @Path("{listEntryId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postListEntryById(@PathParam("listEntryId") String listEntryId, ListEntry entity)
            throws Exception;

    /**
     * Deletes the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @DELETE
    @Path("{listEntryId}")
    @Produces({ "application/json" })
    Response deleteListEntryById(@PathParam("listEntryId") String listEntryId) throws Exception;

}
