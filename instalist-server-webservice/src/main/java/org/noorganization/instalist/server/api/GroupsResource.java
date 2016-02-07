
package org.noorganization.instalist.server.api;

import org.glassfish.jersey.internal.util.Base64;
import org.noorganization.instalist.comm.message.DeviceInfo;
import org.noorganization.instalist.comm.message.GroupInfo;
import org.noorganization.instalist.comm.message.TokenInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.IGroupController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.*;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

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
            if (!authHeader.matches("Basic\\s+[^\\s]+")) {
                Error message = new Error();
                message.setMessage("Authorization has wrong format.");
                return ResponseFactory.generateNotAuthorizedWAuth(message);
            }
            String encodedAuthInfo = authHeader.substring("Basic".length() + 1).trim();
            String decodedAuthInfo = Base64.decodeAsString(encodedAuthInfo);

            int colonPos = decodedAuthInfo.indexOf(":");
            if (colonPos < 1) {
                Error message = new Error();
                message.setMessage("Authorization has wrong format.");
                return ResponseFactory.generateNotAuthorizedWAuth(message);
            }
            int deviceId;
            try {
                deviceId = Integer.parseInt(decodedAuthInfo.substring(0, colonPos));
            } catch (NumberFormatException e) {
                Error message = new Error();
                message.setMessage("Authorization has wrong format.");
                return ResponseFactory.generateNotAuthorizedWAuth(message);
            }
            String secret = decodedAuthInfo.substring(colonPos + 1);
            if (secret.length() == 0){
                Error message = new Error();
                message.setMessage("Authorization has wrong format.");
                return ResponseFactory.generateNotAuthorizedWAuth(message);
            }
            EntityManager manager = DatabaseHelper.getInstance().getManager();
            IAuthController authController = ControllerFactory.getAuthController();
            String token = authController.getTokenByHttpAuth(manager,
                    deviceId, secret);
            manager.close();
            if (token == null)
                return ResponseFactory.generateNotAuthorizedWAuth(new Error().withMessage("Login " +
                        "failed"));
            else {
                if (authController.getDeviceByToken(token).getAuthorized())
                    return ResponseFactory.generateOK(new TokenInfo().withToken(token));
                else
                    return ResponseFactory.generateAccepted(new TokenInfo().withToken(token));
            }
        }
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
        if (_registration == null || _registration.getGroupAuth() == null || _registration
                .getGroupAuth().length() != 6 || _registration.getSecret() == null ||
                _registration.getSecret().length() == 0 || _registration.getName() == null)
            return ResponseFactory.generateBadRequest(new Error().withMessage("Sent data was " +
                    "incomplete."));

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IGroupController groupController = ControllerFactory.getGroupController(manager);
        Device newDevice = groupController.addDevice(groupId, _registration.getGroupAuth(),
                _registration.getName(), _registration.getSecret());
        manager.close();

        if (newDevice == null)
            return ResponseFactory.generateBadRequest(new Error().withMessage("Sent data was not " +
                    "correct."));
        else {
            DeviceInfo rtnInfo = new DeviceInfo();
            rtnInfo.setId(newDevice.getId());
            rtnInfo.setAuthorized(newDevice.getAuthorized());
            if (newDevice.getAuthorized())
                return ResponseFactory.generateOK(rtnInfo);
            else
                return ResponseFactory.generateCreated(rtnInfo);
        }
    }

    @GET
    @TokenSecured
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
    @TokenSecured
    @Path("{groupid}/devices/{deviceid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response putDevice(@PathParam("groupid") int _groupId,
                              @PathParam("deviceid") int _deviceId,
                              DeviceInfo _deviceToUpdate) throws Exception {
        if ((_deviceToUpdate.getId() != null && _deviceId != _deviceToUpdate.getId()) ||
                (_deviceToUpdate.getName() != null && _deviceToUpdate.getName().length() == 0))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);
        if (_deviceToUpdate.getName() == null && _deviceToUpdate.getAuthorized() == null)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        Device toUpdate = manager.find(Device.class, _deviceId);
        if (toUpdate == null || toUpdate.getGroup().getId() != _groupId) {
            manager.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The device was not " +
                    "found."));
        }
        IGroupController groupController = ControllerFactory.getGroupController(manager);
        boolean done = groupController.updateDevice(_deviceId, _deviceToUpdate.getName(),
                _deviceToUpdate.getAuthorized());
        manager.close();

        if (done)
            return ResponseFactory.generateOK(null);
        else
            return ResponseFactory.generateServerError(new Error().withMessage("Updating device " +
                    "failed."));

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
        //return null;
    }

    @DELETE
    @TokenSecured
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
    @TokenSecured
    @Path("{groupid}/access_key")
    @Produces({ "application/json" })
    public Response getAccessKey(@PathParam("groupid") int _groupId) throws Exception {
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        String accessKey = ControllerFactory.getGroupController(manager).
                generateAccessKey(_groupId);
        manager.close();

        if (accessKey == null)
            return ResponseFactory.generateServerError(new Error().withMessage("The request seems" +
                    " to be correct, but accesskey could not be generated."));
        return ResponseFactory.generateOK(new GroupInfo().withReadableId(accessKey));
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
