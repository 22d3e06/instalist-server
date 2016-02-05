
package org.noorganization.instalist.server.api;

import org.noorganization.instalist.comm.message.DeviceInfo;
import org.noorganization.instalist.server.message.*;
import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.message.Error;
import org.noorganization.instalist.server.support.AuthHelper;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Path("/user")
public class UserResource {

    private AuthHelper mAuthHelper;

    /**
     * Get the auth token.
     * Needs basic authentication with server-side-generated id as user and client-sided secret as
     * client. For Encoding-method view RFC 2617
     */
    @GET
    @Path("token")
    @Produces({ "application/json" })
    public Response getUserToken(@Context HttpHeaders _headers) throws Exception {
        String authHeader = _headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            Error message = new Error();
            message.setMessage("Authentication needed.");
            return ResponseFactory.generateNotAuthorizedWAuth(message);
        } else {
            Connection db = DatabaseHelper.getInstance().getConnection();
            String token = mAuthHelper.getTokenByHttpAuth(db, authHeader);
            db.close();
            if (token == null) {
                Error message = new Error();
                message.setMessage("Authentication wrong.");
                return ResponseFactory.generateNotAuthorizedWAuth(message);
            } else if (!mAuthHelper.getIsAuthorizedToGroup(token)) {
                Error message = new Error();
                message.setMessage("Not authorized to group.");
                return ResponseFactory.generateAccepted(message);
            } else {
                Token message = new Token();
                message.setToken(token);
                return ResponseFactory.generateOK(message);
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
    @Path("register_device")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postUserRegisterDevice(DeviceRegistration _registration) throws Exception {
        if (_registration == null || _registration.getGroupReadableId() == null || _registration
                .getGroupReadableId().length() != 6 || _registration.getSecret() == null ||
                _registration.getSecret().length() == 0 || _registration.getName() == null)
            return ResponseFactory.generateBadRequest(new Error().withMessage("Sent data was " +
                    "incomplete."));

        Connection db = DatabaseHelper.getInstance().getConnection();
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
            return ResponseFactory.generateCreated(rtnEntity);
    }

    @GET
    @Path("group/devices")
    @Produces({ "application/json" })
    public Response getUserGroupDevices(@QueryParam("token") String _token) throws Exception {
        if (_token == null || !mAuthHelper.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        int groupId = mAuthHelper.getGroupIdByToken(_token);

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
        return ResponseFactory.generateOK(devices);
    }

    @PUT
    @Path("group/devices")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response putUserGroupDevices(@QueryParam("token") String _token, DeviceInfo[]
            _devicesToUpdate) throws Exception {
        if (_token == null || !mAuthHelper.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        if (_devicesToUpdate == null || _devicesToUpdate.length == 0)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);

        int groupId = mAuthHelper.getGroupIdByToken(_token);
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

        return ResponseFactory.generateOK(null);
    }

    @DELETE
    @Path("group/devices")
    @Produces({ "application/json" })
    public Response deleteUserGroupDevices(@QueryParam("token") String _token,
                                           @QueryParam("deviceid") Integer _deviceToDelete)
            throws Exception {
        if (_token == null || !mAuthHelper.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        if (_deviceToDelete == null)
            return ResponseFactory.generateBadRequest(CommonEntity.sNoData);



        Connection db = DatabaseHelper.getInstance().getConnection();
        PreparedStatement checkDeviceInGroupStmt = db.prepareStatement("SELECT COUNT(id) FROM " +
                "devices WHERE devicegroup_id = ? AND id = ?");
        int groupId = mAuthHelper.getGroupIdByToken(_token);
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

        return ResponseFactory.generateOK(null);
    }

    /**
     * The action to get a temporary access key to a group.
     * 
     */
    @GET
    @Path("group/access_key")
    @Produces({ "application/json" })
    public Response getUserGroupAccessKey(@QueryParam("token") String _token) throws Exception {
        if (_token == null || !mAuthHelper.getIsAuthorizedToGroup(_token))
            return ResponseFactory.generateNotAuthorized(CommonEntity.sNotAuthorized);

        Connection db = DatabaseHelper.getInstance().getConnection();
        String newReadableId = getNewGroupReadableId(db);
        PreparedStatement updateGroupStmt = db.prepareStatement("UPDATE devicegroups SET " +
                "readableid = ? WHERE id = ?");
        updateGroupStmt.setString(1, newReadableId);
        int group = mAuthHelper.getGroupIdByToken(_token);
        updateGroupStmt.setInt(2, mAuthHelper.getGroupIdByToken(_token));
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
        return ResponseFactory.generateOK(new Group().withReadableId(newReadableId));
    }

    /**
     * The action to create a new group of devices sharing lists etc.
     * 
     */
    @POST
    @Path("register_group")
    @Produces({ "application/json" })
    public Response postUserRegisterGroup() throws Exception {
        Connection db = DatabaseHelper.getInstance().getConnection();
        String newGroupReadableId = getNewGroupReadableId(db);
        PreparedStatement createGroupStmt = db.prepareStatement("INSERT INTO devicegroups " +
                "(readableid) VALUES (?)");
        createGroupStmt.setString(1, newGroupReadableId);
        if(createGroupStmt.executeUpdate() != 1) {
            createGroupStmt.close();
            db.close();
            System.err.println("Could not insert new group.");
            return ResponseFactory.generateServerError(new Error().withMessage("Creating new " +
                    "group failed."));
        }
        createGroupStmt.close();
        db.close();
        return ResponseFactory.generateOK(new Group().withReadableId(newGroupReadableId));
    }

    public UserResource() {
        mAuthHelper = AuthHelper.getInstance();
    }

    private String getNewGroupReadableId(Connection _db) throws SQLException {
        final char[] acceptableChars =
                new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
                        '4', '5', '6', '7', '8', '9'};
        SecureRandom random = new SecureRandom();
        StringBuilder idBuilder = new StringBuilder(6);
        PreparedStatement checkStmt = _db.prepareStatement("SELECT COUNT(id) FROM devicegroups " +
                "WHERE readableid = ?");
        while (true) {
            idBuilder.setLength(0);
            while (idBuilder.length() < 6)
                idBuilder.append(acceptableChars[random.nextInt(acceptableChars.length)]);
            checkStmt.setString(1, idBuilder.toString());
            ResultSet checkRS = checkStmt.executeQuery();
            checkRS.first();
            int result = checkRS.getInt(1);
            checkRS.close();
            if (result == 0)
                break;
        }
        checkStmt.close();

        return idBuilder.toString();
    }
}
