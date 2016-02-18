
package org.noorganization.instalist.server.api;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.UnitInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IUnitController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Unit;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Collection of available units.
 * 
 */
@Path("/groups/{groupid}/units")
public class UnitResource {


    /**
     * Get a list of units.
     * @param _groupId The id of the group containing the requested units.
     * @param _changedSince Requests only the elements that changed since the given date. ISO
     *                     8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getUnits(@PathParam("groupid") int _groupId,
                             @QueryParam("changedsince") String _changedSince) throws Exception {
        Instant changedSince = null;
        try {
            if (_changedSince != null)
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).toInstant();
        } catch(ParseException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        List<Unit> resultUnits;
        List<DeletedObject> resultDeletedUnits;

        if (changedSince == null) {
            resultUnits = new ArrayList<Unit>(group.getUnits());
            TypedQuery<DeletedObject> deletedUnitsQuery = manager.createQuery("select do from " +
                    "DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedUnitsQuery.setParameter("group", group);
            deletedUnitsQuery.setParameter("type", DeletedObject.Type.UNIT);
            resultDeletedUnits = deletedUnitsQuery.getResultList();
        } else {
            TypedQuery<Unit> unitsQuery = manager.createQuery("select u from Unit u where " +
                    "u.group = :group and u.updated > :updated", Unit.class);
            unitsQuery.setParameter("group", group);
            unitsQuery.setParameter("updated", changedSince);
            resultUnits = unitsQuery.getResultList();

            TypedQuery<DeletedObject> deletedUnitsQuery = manager.createQuery("select do from " +
                            "DeletedObject do where do.group = :group and do.type = :type and " +
                            "do.updated > :updated",
                    DeletedObject.class);
            deletedUnitsQuery.setParameter("group", group);
            deletedUnitsQuery.setParameter("type", DeletedObject.Type.UNIT);
            deletedUnitsQuery.setParameter("updated", changedSince);
            resultDeletedUnits = deletedUnitsQuery.getResultList();
        }
        manager.close();

        List<UnitInfo> rtn = new ArrayList<UnitInfo>(resultUnits.size() +
                resultDeletedUnits.size());
        for (Unit current: resultUnits) {
            UnitInfo info = new UnitInfo().withDeleted(false);
            info.setName(current.getName());
            info.setUUID(current.getUUID());
            info.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(info);
        }
        for (DeletedObject current: resultDeletedUnits) {
            UnitInfo info = new UnitInfo().withDeleted(true);
            info.setUUID(current.getUUID());
            info.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(info);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Returns the unit.
     * @param _groupId The croup containing the requested unit.
     * @param _unitUUID The uuid of the requested unit.
     */
    @GET
    @TokenSecured
    @Path("{unituuid}")
    @Produces({ "application/json" })
    public Response getUnit(@PathParam("groupid") int _groupId,
                            @PathParam("unituuid") String _unitUUID) throws Exception {
        try {


        UUID toFind;
        try {
            toFind = UUID.fromString(_unitUUID);
        } catch(IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IUnitController unitController = ControllerFactory.getUnitController(manager);
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        Unit resultUnit = unitController.findByGroupAndUUID(group, toFind);
        if (resultUnit == null) {
            if (unitController.findDeletedByGroupAndUUID(group,toFind) == null) {
                manager.close();
                return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                        "unit was not found."));
            }
            manager.close();
            return ResponseFactory.generateGone(new Error().withMessage("The requested unit has " +
                    "been deleted."));
        }
        manager.close();

        UnitInfo rtn = new UnitInfo().withDeleted(false);
        rtn.setName(resultUnit.getName());
        rtn.setUUID(resultUnit.getUUID());
        rtn.setLastChanged(Date.from(resultUnit.getUpdated()));

        return ResponseFactory.generateOK(rtn);
        } catch (Exception _e) {
            _e.printStackTrace();
            throw _e;
        }
    }

    /**
     * Updates the unit.
     * @param _groupId The id of the group containing the unit.
     * @param _unitUUID The existing Unit-UUID to update.
     * @param _entity The data for update.
     */
    @PUT
    @TokenSecured
    @Path("{unituuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putUnit(@PathParam("groupid") int _groupId,
                            @PathParam("unituuid") String _unitUUID,
                            UnitInfo _entity) throws Exception {
        UUID toUpdate;
        try {
            toUpdate = UUID.fromString(_unitUUID);
        } catch(IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant changeDate;
        if (_entity.getLastChanged() == null)
            changeDate = Instant.now();
        else {
            changeDate = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(changeDate))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        }
        if ((_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getUUID() != null && !_entity.getUUID().equals(_unitUUID)))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IUnitController unitController = ControllerFactory.getUnitController(manager);
        try {
            unitController.update(_groupId, toUpdate, _entity.getName(), changeDate);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The unit to change " +
                    "was not found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The unit to change was " +
                    "not found since it has been deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "lead to a conflict with saved unit."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the unit.
     * @param _groupId The group-id the new Unit should belong to.
     * @param _entity Data of the new unit.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postUnit(@PathParam("groupid") int _groupId, UnitInfo _entity) throws
            Exception {
        if (_entity.getUUID() == null ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                _entity.getName() == null || _entity.getName().length() == 0)
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toInsert;
        try {
            toInsert = UUID.fromString(_entity.getUUID());
        } catch(IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        Instant insertDate;
        if (_entity.getLastChanged() == null)
            insertDate = Instant.now();
        else {
            insertDate = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(insertDate))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IUnitController unitController = ControllerFactory.getUnitController(manager);
        try {
            unitController.add(_groupId, toInsert, _entity.getName(), insertDate);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "lead to a conflict with saved unit."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the unit.
     * @param _groupId The groups id containing the existing unit.
     * @param _unitUUID The uuid of the unit to delete.
     */
    @DELETE
    @TokenSecured
    @Path("{unituuid}")
    @Produces({ "application/json" })
    public Response deleteUnit(@PathParam("groupid") int _groupId,
                               @PathParam("unituuid") String _unitUUID) throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_unitUUID);
        } catch(IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IUnitController unitController = ControllerFactory.getUnitController(manager);
        try {
            unitController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The unit was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The unit has been " +
                    "already deleted before."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
