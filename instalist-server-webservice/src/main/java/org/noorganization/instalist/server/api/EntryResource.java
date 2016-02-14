
package org.noorganization.instalist.server.api;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.EntryInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;


/**
 * Collection of available listEntries.
 * 
 */
@Path("/groups/{groupid}/listentries")
public class EntryResource {

    /**
     * Get a list of listEntries.
     * @param _groupId The id of the group containing various list-entries.
     * @param _changedSince Limits the result to elements that changed since the given date. ISO
     *                      8601 time e.g. 2016-01-19T11:54:07+0100
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getEntries(@PathParam("groupid") int _groupId,
                               @QueryParam("changedsince") String _changedSince) throws Exception {
        List<ListEntry> foundEntries;
        List<DeletedObject> foundDeleted;
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (_changedSince != null) {
            Instant changedSince;
            try {
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).
                        toInstant();
            } catch (ParseException _e) {
                manager.close();
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
            }

            TypedQuery<ListEntry> foundEntriesQuery = manager.createQuery("select le from " +
                            "ListEntry le where le.group = :group and le.updated > :updated",
                    ListEntry.class);
            foundEntriesQuery.setParameter("group", group);
            foundEntriesQuery.setParameter("updated", changedSince);
            foundEntries = foundEntriesQuery.getResultList();

            TypedQuery<DeletedObject> foundDeletedEntriesQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.time > :updated and " +
                    "do.type = :type", DeletedObject.class);
            foundDeletedEntriesQuery.setParameter("group", group);
            foundDeletedEntriesQuery.setParameter("updated", Date.from(changedSince));
            foundDeletedEntriesQuery.setParameter("type", DeletedObject.Type.LISTENTRY);
            foundDeleted = foundDeletedEntriesQuery.getResultList();
        } else {
            foundEntries = new ArrayList<ListEntry>(group.getListEntries());

            TypedQuery<DeletedObject> deletedEntriesQuery = manager.createQuery("select do from " +
                    "DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedEntriesQuery.setParameter("group", group);
            deletedEntriesQuery.setParameter("type", DeletedObject.Type.LISTENTRY);
            foundDeleted = deletedEntriesQuery.getResultList();
        }
        manager.close();

        ArrayList<EntryInfo> rtn = new ArrayList<EntryInfo>(foundEntries.size() +
                foundDeleted.size());
        for (ListEntry current : foundEntries) {
            EntryInfo toAdd = new EntryInfo().withDeleted(false);
            toAdd.setUUID(current.getUUID());
            toAdd.setProductUUID(current.getProduct().getUUID());
            toAdd.setListUUID(current.getList().getUUID());
            toAdd.setAmount(current.getAmount());
            toAdd.setPriority(current.getPriority());
            toAdd.setStruck(current.getStruck());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }
        for (DeletedObject current : foundDeleted) {
            EntryInfo toAdd = new EntryInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(current.getTime());
            toAdd.setDeleted(true);
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Returns a single list-entry.
     * @param _groupId The id of the group containing the entry.
     * @param _entryUUID The uuid of the entry itself.
     */
    @GET
    @TokenSecured
    @Path("{entryuuid}")
    @Produces({ "application/json" })
    public Response getEntry(@PathParam("groupid") int _groupId,
                             @PathParam("entryuuid") String _entryUUID) throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_entryUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        IEntryController entryController = ControllerFactory.getEntryController(manager);

        ListEntry foundEntry = entryController.getEntryByGroupAndUUID(group, toFind);
        if (foundEntry == null) {
            if (entryController.getDeletedEntryByGroupAndUUID(group, toFind) != null) {
                manager.close();
                return ResponseFactory.generateGone(new Error().withMessage("The requested " +
                        "listentry has been deleted."));
            }
            manager.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                    "listentry was not found."));
        }
        manager.close();

        EntryInfo rtn = new EntryInfo().withDeleted(false);
        rtn.setUUID(foundEntry.getUUID());
        rtn.setProductUUID(foundEntry.getProduct().getUUID());
        rtn.setListUUID(foundEntry.getList().getUUID());
        rtn.setAmount(foundEntry.getAmount());
        rtn.setPriority(foundEntry.getPriority());
        rtn.setStruck(foundEntry.getStruck());
        rtn.setLastChanged(Date.from(foundEntry.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates an entry.
     * @param _groupId The id of the group containing the entry.
     * @param _entryUUID The uuid of the entry itself.
     * @param _entity Data for updating the entry.
     */
    @PUT
    @TokenSecured
    @Path("{entryuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putEntry(@PathParam("groupid") int _groupId,
                             @PathParam("entryuuid") String _entryUUID,
                             EntryInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_entryUUID)) ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getAmount() != null && _entity.getAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toUpdate;
        UUID productUUID = null;
        UUID listUUID = null;
        try {
            toUpdate = UUID.fromString(_entryUUID);
            if (_entity.getProductUUID() != null)
                productUUID = UUID.fromString(_entity.getProductUUID());
            if (_entity.getListUUID() != null)
                listUUID = UUID.fromString(_entity.getListUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant updated;
        if (_entity.getLastChanged() != null) {
            updated = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(updated))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            updated = Instant.now();

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IEntryController entryController = ControllerFactory.getEntryController(manager);
        try {
            entryController.update(_groupId, toUpdate, productUUID, listUUID, _entity.getAmount(),
                    _entity.getPriority(), _entity.getStruck(), updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The entry was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The entry has been " +
                    "deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved list entry."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "product or list was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the listEntry.
     * @param _groupId The id of the group containing the entry.
     * @param _entity Data for created the entry.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postEntry(@PathParam("groupid") int _groupId, EntryInfo _entity)
            throws Exception {
        if (_entity.getUUID() == null || _entity.getListUUID() == null ||
                _entity.getProductUUID() == null ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getAmount() != null && _entity.getAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toCreate;
        UUID productUUID;
        UUID listUUID;
        try {
            toCreate = UUID.fromString(_entity.getUUID());
            productUUID = UUID.fromString(_entity.getProductUUID());
            listUUID = UUID.fromString(_entity.getListUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant created;
        if (_entity.getLastChanged() != null) {
            created = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(created))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            created = Instant.now();
        float amount = (_entity.getAmount() != null ? _entity.getAmount() : 1f);
        int priority = (_entity.getPriority() != null ? _entity.getPriority() : 0);
        boolean struck = (_entity.getStruck() != null ? _entity.getStruck() : false);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IEntryController entryController = ControllerFactory.getEntryController(manager);
        try {
            entryController.add(_groupId, toCreate, productUUID, listUUID, amount, priority, struck,
                    created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved list entry."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "product or list was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the entry.
     * @param _groupId The id of the group containing the entry.
     * @param _entryUUID The uuid of the entry itself.
     */
    @DELETE
    @TokenSecured
    @Path("{entryuuid}")
    @Produces({ "application/json" })
    public Response deleteListEntryById(@PathParam("groupid") int _groupId,
                                        @PathParam("entryuuid") String _entryUUID)
            throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_entryUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IEntryController entryController = ControllerFactory.getEntryController(manager);
        try {
            entryController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The entry was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The entry has been " +
                    "deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
