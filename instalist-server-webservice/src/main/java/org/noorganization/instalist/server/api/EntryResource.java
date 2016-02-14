
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
import org.noorganization.instalist.comm.message.EntryInfo;
import org.noorganization.instalist.server.TokenSecured;


/**
 * Collection of available listEntries.
 * 
 */
@Path("/groups/{groupid}/listentries")
public class EntryResource {

    /**
     * Get a list of listEntries.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getEntries(@PathParam("groupid") int _groupId,
                               @QueryParam("changedsince") String _changedSince) throws Exception {
        return null;
    }

    /**
     * Returns the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @GET
    @TokenSecured
    @Path("{entryuuid}")
    @Produces({ "application/json" })
    public Response getEntry(@PathParam("groupid") int _groupId,
                             @PathParam("entryuuid") String _entryUUID) throws Exception {
        return null;
    }

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
    @TokenSecured
    @Path("{entryuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putListEntryById(@PathParam("groupid") int _groupId,
                                     @PathParam("entryuuid") String _entryUUID,
                                     EntryInfo _entity) throws Exception {
        return null;
    }

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
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postListEntryById(@PathParam("groupid") int _groupId, EntryInfo entity)
            throws Exception {
        return null;
    }

    /**
     * Deletes the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @DELETE
    @Path("{entryuuid}")
    @Produces({ "application/json" })
    public Response deleteListEntryById(@PathParam("groupid") int _groupId,
                                        @PathParam("entryuuid") String _entryUUID)
            throws Exception {
        return null;
    }

}
