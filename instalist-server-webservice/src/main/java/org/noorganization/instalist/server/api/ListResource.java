
package org.noorganization.instalist.server.api;

import java.util.Date;
import java.util.UUID;
import javax.persistence.EntityManager;
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
import org.noorganization.instalist.comm.support.DateHelper;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IListController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;


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
    public Response getList(@PathParam("groupid") int _groupId,
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
    @TokenSecured
    @Path("{listuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putList(@PathParam("groupid") int _groupId,
                     @PathParam("listuuid") String _listUUID,
                     ListInfo _listInfo) throws Exception {
        return null;
    }

    /**
     * Creates a list in the group.
     * @param _groupId The id of the group containing the list.
     * @param _listInfo Information for changing the list. Not all information needs to be set.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postList(@PathParam("groupid") int _groupId,
                             ListInfo _listInfo) throws Exception {
        if ((_listInfo.getDeleted() != null && _listInfo.getDeleted()) ||
                _listInfo.getName() == null || _listInfo.getName().length() == 0 ||
                _listInfo.getUUID() == null)
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID listUUID;
        UUID categoryUUID = null;
        try {
            listUUID = UUID.fromString(_listInfo.getUUID());
            if (_listInfo.getCategoryUUID() != null)
                categoryUUID = UUID.fromString(_listInfo.getCategoryUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Date created;
        if (_listInfo.getLastChanged() != null) {
            created = DateHelper.parseDate(_listInfo.getLastChanged());
            if (created == null || created.after(new Date(System.currentTimeMillis())))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            created = new Date(System.currentTimeMillis());

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IListController listController = ControllerFactory.getListController(manager);
        try {
            listController.add(_groupId, listUUID, _listInfo.getName(), categoryUUID, created);
        } catch(ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("A list with this " +
                    "uuid already exists."));
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the list.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list to update.
     */
    @DELETE
    @TokenSecured
    @Path("{listuuid}")
    @Produces({ "application/json" })
    public Response deleteList(@PathParam("groupid") int _groupId,
                        @PathParam("listuuid") String _listUUID) throws Exception {
        return null;
    }

}
