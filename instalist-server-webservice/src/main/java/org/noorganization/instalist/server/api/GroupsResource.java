
package org.noorganization.instalist.server.api;

import org.glassfish.jersey.internal.util.Base64;
import org.noorganization.instalist.comm.message.DeviceInfo;
import org.noorganization.instalist.comm.message.GroupInfo;
import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.IGroupController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.*;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/groups")
public class GroupsResource {

    /**
     * Get the auth token.
     * Needs basic authentication with server-side-generated id as user and client-sided secret as
     * client. For Encoding-method view RFC 2617
     */
    @GET
    @Path("{groupid}/devices/token")
    @Produces({ "application/json" })
    public Response getDeviceToken(@Context HttpHeaders _headers,
                                   @PathParam("groupid") int _groupId) throws Exception {
        String authHeader = _headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            Error message = new Error();
            message.setMessage("Authentication needed.");
            return ResponseFactory.generateNotAuthorizedWAuth(message);
        } else {
//            if (!_authHeader.matches("Basic\\s+[^\\s]+"))
//                return null;
//            String authInfo = _authHeader.replaceFirst("Basic\\s+", "");
//            authInfo = Base64.decodeAsString(authInfo);
//
//            int colonPos = authInfo.indexOf(":");
//            if (colonPos < 1)
//                return null;
//            int deviceId;
//            try {
//                deviceId = Integer.parseInt(authInfo.substring(0, colonPos));
//            } catch (NumberFormatException e) {
//                return null;
//            }
//            String secret = authInfo.substring(colonPos + 1);
//            if (secret.length() == 0)
//                return null;
//            mAuthController.getTokenByHttpAuth()
//            Connection db = DatabaseHelper.getInstance().getConnection();
//            String token = mAuthController.getTokenByHttpAuth(db, authHeader);
//            db.close();
//            if (token == null) {
//                Error message = new Error();
//                message.setMessage("Authentication wrong.");
//                return ResponseFactory.generateNotAuthorizedWAuth(message);
//            } else if (!mAuthController.getIsAuthorizedToGroup(token)) {
//                Error message = new Error();
//                message.setMessage("Not authorized to group.");
//                return ResponseFactory.generateAccepted(message);
//            } else {
//                TokenInfo message = new TokenInfo();
//                message.setToken(token);
//                return ResponseFactory.generateOK(message);
//            }
        }
        return null;
    }

    /**
     * The action to connect a new device with a group.
     * 
     * @param _registration A JSON-Object containing all needed information for registering.
     *      e.g. {
     *       "groupid" : "AB7Zbm",
     *       "secret" : "dkjhfsdcbuiufien=--ihrienncdjXXCndjjFJJED"
     *     }
     *     
     */
    @POST
    @Path("{groupid}/devices")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postDevice(@PathParam("groupid") int groupId,
                               DeviceRegistration _registration) throws Exception {

        /*if (_registration == null || _registration.getGroupReadableId() == null || _registration
                .getGroupReadableId().length() != 6 || _registration.getSecret() == null ||
                _registration.getSecret().length() == 0 || _registration.getName() == null)
            return ResponseFactory.generateBadRequest(new Error().withMessage("Sent data was " +
                    "incomplete."));

        Connection db = DatabaseHelper.getInstance().getManager();
        db.setAutoCommit(false);
        PreparedStatement groupIdStmt = db.prepareStatement("SELECT devicegroups.id AS dgid, " +
                "devices.id AS did FROM devicegroups LEFT JOIN devices ON devicegroups.id = " +
                "devices.devicegroup_id WHERE devicegroups.readableid = ?;");
        groupIdStmt.setString(1, _registration.getGroupReadableId());
        ResultSet groupIdRS = groupIdStmt.executeQuery();
        if (!groupIdRS.first()) {
            groupIdRS.close();
            db.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                    "group-id for registration was not found."));
        }
        int groupId = groupIdRS.getInt("dgid");
        boolean firstDev = (groupIdRS.getInt("did") == 0 && groupIdRS.wasNull());
        groupIdRS.close();
        groupIdStmt.close();


        PreparedStatement deviceCreationStmt = db.prepareStatement("INSERT INTO devices (name, " +
                "devicegroup_id, autorizedtogroup, secret) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        deviceCreationStmt.setString(1, _registration.getName());
        deviceCreationStmt.setInt(2, groupId);
        deviceCreationStmt.setBoolean(3, firstDev);
        deviceCreationStmt.setString(4, BCrypt.hashpw(_registration.getSecret(), BCrypt.
                gensalt(10)));
        if (deviceCreationStmt.executeUpdate() != 1) {
            deviceCreationStmt.close();
            db.close();
            System.err.println("Could not add device to group " + groupId);
            return ResponseFactory.generateServerError(new Error().withMessage("Could not add " +
                    "device to group."));
        }
        ResultSet createdDeviceIdRS = deviceCreationStmt.getGeneratedKeys();
        createdDeviceIdRS.first();
        DeviceRegistrationAck rtnEntity = new DeviceRegistrationAck();
        rtnEntity.setDeviceId(createdDeviceIdRS.getInt(1));
        createdDeviceIdRS.close();
        deviceCreationStmt.close();

        PreparedStatement groupSecureStmt = db.prepareStatement("UPDATE devicegroups SET " +
                "readableid = NULL WHERE id = ?");
        groupSecureStmt.setInt(1, groupId);
        if (groupSecureStmt.executeUpdate() != 1) {
            groupSecureStmt.close();
            db.rollback();
            db.close();
            System.err.println("Could not secure devicegroup after adding a device.");
            return ResponseFactory.generateServerError(new Error().withMessage("Could not add " +
                    "device to group."));
        }
        groupSecureStmt.close();
        db.commit();
        db.close();

        if (firstDev)
            return ResponseFactory.generateOK(rtnEntity);
        else
            return ResponseFactory.generateCreated(rtnEntity);*/
        return null;
    }

    @GET
    @Path("{groupid}/devices")
    @Produces({ "application/json" })
    public Response getDevices(@PathParam("groupid") int _groupId) throws Exception {
        /*if (_token == null || !mAuthController.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        int groupId = mAuthController.getDeviceGroupByToken(_token);

        Connection db = DatabaseHelper.getInstance().getConnection();
        PreparedStatement devicesStmt = db.prepareStatement("SELECT id, name, autorizedtogroup FROM " +
                "devices WHERE devicegroup_id = ?");
        devicesStmt.setInt(1, groupId);
        ResultSet devicesRS = devicesStmt.executeQuery();
        if (!devicesRS.first()) {
            devicesRS.close();
            devicesStmt.close();
            db.close();

            System.err.println("Found group without devices. Something is wrong with database!");
            return ResponseFactory.generateServerError(new Error().withMessage("Could not " +
                    "retrieve devices for group."));
        }

        List<DeviceInfo> devices = new LinkedList<DeviceInfo>();
        do {
            DeviceInfo currentInfo = new DeviceInfo();
            currentInfo.setId(devicesRS.getInt("id"));
            currentInfo.setName(devicesRS.getString("name"));
            currentInfo.setAuthorized(devicesRS.getBoolean("autorizedtogroup"));
            devices.add(currentInfo);
        } while (devicesRS.next());

        devicesRS.close();
        devicesStmt.close();
        db.close();
        return ResponseFactory.generateOK(devices);*/
        return null;
    }

    @GET
    @Path("{groupid}/devices/{deviceid}")
    @Produces({ "application/json" })
    public Response getDevice(@PathParam("groupid") int _groupId,
                              @PathParam("deviceid") int _deviceId) throws Exception {
        return null;
    }

    @PUT
    @Path("{groupid}/devices/{deviceid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response putDevice(@PathParam("groupid") int _groupId,
                              @PathParam("deviceid") int _deviceId,
                              DeviceInfo _deviceToUpdate) throws Exception {
        /*if (_token == null || !mAuthController.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        if (_devicesToUpdate == null || _devicesToUpdate.length == 0)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);

        int groupId = mAuthController.getDeviceGroupByToken(_token);
        Connection db = DatabaseHelper.getInstance().getConnection();
        db.setAutoCommit(false);
        PreparedStatement checkDeviceInGroupStmt = db.prepareStatement("SELECT COUNT(id) FROM " +
                "devices WHERE devicegroup_id = ? AND id = ?");
        checkDeviceInGroupStmt.setInt(1, groupId);
        boolean error = false;
        for (DeviceInfo currentInfo : _devicesToUpdate) {
            Integer deviceId = currentInfo.getId();
            if (deviceId == null) {
                error = true;
                break;
            }

            checkDeviceInGroupStmt.setInt(2, deviceId);
            ResultSet checkDeviceInGroupRS = checkDeviceInGroupStmt.executeQuery();
            checkDeviceInGroupRS.first();
            if (checkDeviceInGroupRS.getInt(1) != 1) {
                checkDeviceInGroupRS.close();
                error = true;
                break;
            }
            checkDeviceInGroupRS.close();

            PreparedStatement updateDeviceStmt;
            if (currentInfo.getName() != null && currentInfo.getAuthorized() != null) {
                updateDeviceStmt = db.prepareStatement("UPDATE devices SET name = ?, " +
                        "autorizedtogroup = ? WHERE id = ?");
                updateDeviceStmt.setString(1, currentInfo.getName());
                updateDeviceStmt.setBoolean(2, currentInfo.getAuthorized());
                updateDeviceStmt.setInt(3, currentInfo.getId());
            } else if (currentInfo.getName() != null) {
                updateDeviceStmt = db.prepareStatement("UPDATE devices SET name = ? WHERE id = ?");
                updateDeviceStmt.setString(1, currentInfo.getName());
                updateDeviceStmt.setInt(2, currentInfo.getId());
            } else if (currentInfo.getAuthorized() != null) {
                updateDeviceStmt = db.prepareStatement("UPDATE devices SET autorizedtogroup = ? " +
                        "WHERE id = ?");
                updateDeviceStmt.setBoolean(1, currentInfo.getAuthorized());
                updateDeviceStmt.setInt(2, currentInfo.getId());
            } else {
                error = true;
                break;
            }
            updateDeviceStmt.executeUpdate();
            updateDeviceStmt.close();
        }
        checkDeviceInGroupStmt.close();
        if (error) {
            db.rollback();
            db.close();

            return ResponseFactory.generateBadRequest(new Error().withMessage("Invalid data for " +
                    "device-changes provided."));
        }
        db.commit();
        db.close();

        return ResponseFactory.generateOK(null);*/
        return null;
    }

    @DELETE
    @Path("{groupid}/devices/{deviceid}")
    @Produces({ "application/json" })
    public Response deleteDevice(@PathParam("groupid") int _groupId,
                                 @PathParam("deviceid") int _deviceId,
                                 @QueryParam("deviceid") Integer _deviceToDelete)
            throws Exception {
        /*if (_token == null || !mAuthController.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        if (_deviceToDelete == null)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);



        Connection db = DatabaseHelper.getInstance().getConnection();
        PreparedStatement checkDeviceInGroupStmt = db.prepareStatement("SELECT COUNT(id) FROM " +
                "devices WHERE devicegroup_id = ? AND id = ?");
        int groupId = mAuthController.getDeviceGroupByToken(_token);
        checkDeviceInGroupStmt.setInt(1, groupId);
        checkDeviceInGroupStmt.setInt(2, _deviceToDelete);
        ResultSet checkDeviceInGroupRS = checkDeviceInGroupStmt.executeQuery();
        checkDeviceInGroupRS.first();
        if (checkDeviceInGroupRS.getInt(1) != 1) {
            checkDeviceInGroupRS.close();
            checkDeviceInGroupStmt.close();
            db.close();

            return ResponseFactory.generateNotFound(new Error().withMessage("Device not found or " +
                    "not in same group."));
        }
        checkDeviceInGroupRS.close();
        checkDeviceInGroupStmt.close();

        PreparedStatement deleteDeviceStmt = db.prepareStatement("DELETE FROM devices WHERE " +
                "id = ?");
        deleteDeviceStmt.setInt(1, _deviceToDelete);
        deleteDeviceStmt.executeUpdate();
        deleteDeviceStmt.close();

        PreparedStatement checkEmptyGroupStmt = db.prepareStatement("SELECT COUNT(id) FROM " +
                "devices WHERE devicegroup_id = ?");
        checkEmptyGroupStmt.setInt(1, groupId);
        ResultSet checkEmptyGroupRS = checkEmptyGroupStmt.executeQuery();
        checkEmptyGroupRS.first();
        if (checkEmptyGroupRS.getInt(1) == 0) {
            PreparedStatement deleteGroupStmt = db.prepareStatement("DELETE FROM devicegroups " +
                    "WHERE id = ?");
            deleteGroupStmt.setInt(1, groupId);
            deleteGroupStmt.executeUpdate();
            deleteGroupStmt.close();
        }
        checkEmptyGroupRS.close();
        checkEmptyGroupStmt.close();
        db.close();

        return ResponseFactory.generateOK(null);*/
        return null;
    }

    /**
     * The action to get a temporary access key to a group.
     * 
     */
    @GET
    @Path("{groupid}/access_key")
    @Produces({ "application/json" })
    public Response getAccessKey(@PathParam("groupid") int _groupId) throws Exception {
        /*if (_token == null || !mAuthController.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        Connection db = DatabaseHelper.getInstance().getConnection();
        String newReadableId = getNewGroupReadableId(db);
        PreparedStatement updateGroupStmt = db.prepareStatement("UPDATE devicegroups SET " +
                "readableid = ? WHERE id = ?");
        updateGroupStmt.setString(1, newReadableId);
        int group = mAuthController.getDeviceGroupByToken(_token);
        updateGroupStmt.setInt(2, mAuthController.getDeviceGroupByToken(_token));
        if (updateGroupStmt.executeUpdate() != 1) {
            updateGroupStmt.close();
            db.close();
            System.err.println("Changing access-key for group " + group + " did not change one " +
                    "line in database.");
            return ResponseFactory.generateServerError(new Error().withMessage("Saving new " +
                    "group-id failed."));
        }
        updateGroupStmt.close();
        db.close();
        return ResponseFactory.generateOK(new GroupInfo().withReadableId(newReadableId));*/
        return null;
    }

    /**
     * The action to create a new group of devices sharing lists etc.
     * 
     */
    @POST
    @Produces({ "application/json" })
    public Response postGroups() throws Exception {
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IGroupController groupController = ControllerFactory.getGroupController(manager);

        DeviceGroup newDeviceGroup = groupController.addGroup();
        GroupInfo answer = new GroupInfo().withId(newDeviceGroup.getId()).
                withReadableId(newDeviceGroup.getReadableId());
        manager.close();

        return ResponseFactory.generateOK(answer);
    }

    public GroupsResource() {
    }

}
