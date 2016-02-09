
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

import org.noorganization.instalist.comm.message.ListInfo;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.message.ShoppingList;


/**
 * Collection of available lists.
 * 
 */
@Path("/groups/{groupid}/lists")
public class ListResource {


    /**
     * Get a list of shopping-lists.
     * @param _groupId The id of the group, containing the lists.
     * @param _changedSince Requests only the elements that changed since the given date. ISO 8601
     *                     time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getLists(@PathParam("groupid") int _groupId,
                             @QueryParam("changedSince") Date _changedSince) throws Exception {
        return null;
    }

    /**
     * Get a single list.
     * @param _groupId The id of the group, containing the list.
     *     
     */
    @GET
    @TokenSecured
    @Path("{listuuid}")
    @Produces({ "application/json" })
    public Response getListById(@PathParam("groupid") int _groupId,
                                @PathParam("listuuid") String _listUUID) throws Exception {
        return null;
    }

    /**
     * Updates a existing list.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list to update.
     * @param _listInfo Information for changing the list. Not all information needs to be set.
     */
    @PUT
    @Path("{listuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putListById(@PathParam("groupid") int _groupId,
                         @PathParam("listuuid") String _listUUID,
                         ListInfo _listInfo) throws Exception {
        return null;
    }

    /**
     * Creates a list in the group.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list to update.
     * @param _listInfo Information for changing the list. Not all information needs to be set.
     */
    @POST
    @Path("{listuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postListById(@PathParam("groupid") int _groupId,
                                 @PathParam("listuuid") String _listUUID,
                                 ListInfo _listInfo) throws Exception {
        return null;
    }

    /**
     * Deletes the list.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list to update.
     */
    @DELETE
    @Path("{listuuid}")
    @Produces({ "application/json" })
    Response deleteListById(@PathParam("groupid") int _groupId,
                            @PathParam("listuuid") String _listUUID) throws Exception {
        return null;
    }

}
