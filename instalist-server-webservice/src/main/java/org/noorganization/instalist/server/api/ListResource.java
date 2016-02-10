
package org.noorganization.instalist.server.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.noorganization.instalist.comm.message.ListInfo;
import org.noorganization.instalist.comm.support.DateHelper;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IListController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.ShoppingList;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;


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
                             @QueryParam("changedsince") String _changedSince) throws Exception {
        List<ShoppingList> foundLists;
        List<DeletedObject> foundDeleted;
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (_changedSince != null) {
            Date changedSince = DateHelper.parseDate(_changedSince);
            if (changedSince == null) {
                manager.close();
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
            }

            TypedQuery<ShoppingList> foundListsQuery = manager.createQuery("select sl from " +
                            "ShoppingList sl where sl.group = :group and sl.updated > :updated",
                    ShoppingList.class);
            foundListsQuery.setParameter("group", group);
            foundListsQuery.setParameter("updated", changedSince);
            foundLists = foundListsQuery.getResultList();

            TypedQuery<DeletedObject> foundDeletedListsQuery = manager.createQuery("select do from" +
                            " DeletedObject do where do.group = :group and do.time > :updated and" +
                            " do.type = :type",
                    DeletedObject.class);
            foundDeletedListsQuery.setParameter("group", group);
            foundDeletedListsQuery.setParameter("updated", changedSince);
            foundDeletedListsQuery.setParameter("type", DeletedObject.Type.LIST);
            foundDeleted = foundDeletedListsQuery.getResultList();
        } else {
            TypedQuery<ShoppingList> foundListsQuery = manager.createQuery("select sl from " +
                            "ShoppingList sl where sl.group = :group", ShoppingList.class);
            foundListsQuery.setParameter("group", group);
            foundLists = foundListsQuery.getResultList();

            TypedQuery<DeletedObject> foundDeletedListsQuery = manager.createQuery("select do from" +
                            " DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            foundDeletedListsQuery.setParameter("group", group);
            foundDeletedListsQuery.setParameter("type", DeletedObject.Type.LIST);
            foundDeleted = foundDeletedListsQuery.getResultList();
        }
        manager.close();

        ArrayList<ListInfo> rtn = new ArrayList<ListInfo>(foundLists.size() + foundDeleted.size());
        for (ShoppingList current: foundLists) {
            ListInfo toAdd = new ListInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setName(current.getName());
            if (current.getCategory() != null)
                toAdd.setCategoryUUID(current.getCategory().getUUID());
            toAdd.setLastChanged(current.getUpdated());
            toAdd.setDeleted(false);
            rtn.add(toAdd);
        }
        for (DeletedObject current: foundDeleted) {
            ListInfo toAdd = new ListInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(current.getTime());
            toAdd.setDeleted(true);
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
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
        UUID listUUID;
        try {
            listUUID = UUID.fromString(_listUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        IListController listController = ControllerFactory.getListController(manager);

        ShoppingList foundList = listController.getListByGroupAndUUID(group, listUUID);
        if (foundList == null) {
            if (listController.getDeletedListByGroupAndUUID(group, listUUID) == null) {
                manager.close();
                return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                        "list was not found."));
            }
            manager.close();
            return ResponseFactory.generateGone(new Error().withMessage("The requested list was " +
                    "deleted."));
        }

        ListInfo rtn = new ListInfo();
        rtn.setUUID(foundList.getUUID());
        rtn.setName(foundList.getName());
        if (foundList.getCategory() != null)
            rtn.setCategoryUUID(foundList.getCategory().getUUID());
        rtn.setLastChanged(foundList.getUpdated());
        rtn.setDeleted(false);

        return ResponseFactory.generateOK(rtn);
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
        if ((_listInfo.getDeleted() != null && _listInfo.getDeleted()) ||
                (_listInfo.getName() != null && _listInfo.getName().length() == 0) ||
                (_listInfo.getUUID() != null && !_listInfo.getUUID().equals(_listUUID)))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID listUUID;
        UUID categoryUUID = null;
        boolean removeCategory = false;
        try {
            listUUID = UUID.fromString(_listUUID);
            if (_listInfo.getCategoryUUID() != null)
                categoryUUID = UUID.fromString(_listInfo.getCategoryUUID());
            else if(_listInfo.getRemoveCategory() != null && _listInfo.getRemoveCategory())
                removeCategory = true;
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Date updated = parseDate(_listInfo);
        if (updated == null)
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IListController listController = ControllerFactory.getListController(manager);
        try {
            listController.update(_groupId, listUUID, _listInfo.getName(), categoryUUID,
                    removeCategory, updated);
        } catch(ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("A list with this " +
                    "uuid already exists."));
        } catch(NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The list was not " +
                    "found."));
        } catch(GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The list was " +
                    "deleted already."));
        } finally {
            manager.close();
        }
        return ResponseFactory.generateOK(null);
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
        Date created = parseDate(_listInfo);
        if (created == null)
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IListController listController = ControllerFactory.getListController(manager);
        try {
            listController.add(_groupId, listUUID, _listInfo.getName(), categoryUUID, created);
        } catch(ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("A list with this " +
                    "uuid already exists."));
        } finally {
            manager.close();
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
        UUID listUUID;
        try {
            listUUID = UUID.fromString(_listUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IListController listController = ControllerFactory.getListController(manager);
        try {
            listController.delete(_groupId, listUUID);
        } catch(NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("A list with this " +
                    "uuid was not found."));
        } catch(GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("A list with this " +
                    "uuid was already deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    private Date parseDate(ListInfo _info) {
        Date rtn;
        if (_info.getLastChanged() != null) {
            rtn = DateHelper.parseDate(_info.getLastChanged());
            if (rtn == null || rtn.after(new Date(System.currentTimeMillis())))
                return null;
        } else
            rtn = new Date(System.currentTimeMillis());
        return rtn;
    }
}
