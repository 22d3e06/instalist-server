package org.noorganization.instalist.server.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.DeviceInfo;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.message.*;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.AuthHelper;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import static org.junit.Assert.*;

public class UserResourceTest extends JerseyTest{

    private CommonData mData;
    private int mDeviceWAuth;
    private int mDeviceWOAuth;
    private int mGroup;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(UserResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mData = new CommonData();

        DeviceGroup defaultDevicegroup = new DeviceGroup();
        defaultDevicegroup.setReadableId("123456");
        Device dev1 = new Device().withName("dev1").withAuthorized(true).withSecret(mData
                .mEncryptedSecret).withGroup(defaultDevicegroup);
        Device dev2 = new Device().withName("dev2").withAuthorized(false).withSecret(mData
                .mEncryptedSecret).withGroup(defaultDevicegroup);

        EntityManager em = mData.mEntities.createEntityManager();
        em.getTransaction().begin();
        em.persist(defaultDevicegroup);
        em.persist(dev1);
        em.persist(dev2);
        em.getTransaction().commit();

//                mDb.prepareStatement("INSERT INTO devicegroups " +
//                "(readableid) VALUES ('123456')", Statement.RETURN_GENERATED_KEYS);
//        if (groupStmt.executeUpdate() != 1) {
//            groupStmt.close();
//            fail();
//        }
//        ResultSet groupRS = groupStmt.getGeneratedKeys();
//        groupRS.next();
//        mGroup = groupRS.getInt(1);
//        groupRS.close();
//        groupStmt.close();
//
//        PreparedStatement deviceStmt = mData.mDb.prepareStatement("INSERT INTO devices (name, " +
//                "devicegroup_id, autorizedtogroup, secret) VALUES ('dev1', ?, TRUE, ?), ('dev2', " +
//                "?, FALSE, ?)", Statement.RETURN_GENERATED_KEYS);
//        deviceStmt.setInt(1, mGroup);
//        deviceStmt.setString(2, mData.mEncryptedSecret);
//        deviceStmt.setInt(3, mGroup);
//        deviceStmt.setString(4, mData.mEncryptedSecret);
//        if (deviceStmt.executeUpdate() != 2) {
//            deviceStmt.close();
//            fail();
//        }
//        ResultSet deviceRS = deviceStmt.getGeneratedKeys();
//        deviceRS.next();
//        mDeviceWAuth = deviceRS.getInt(1);
//        deviceRS.next();
//        mDeviceWOAuth = deviceRS.getInt(1);
//        deviceRS.close();
//        deviceStmt.close();
    }

    @After
    public void tearDown() throws Exception {
//        PreparedStatement cleanUp = mData.mDb.prepareStatement("DELETE FROM devicegroups WHERE id = ?");
//        cleanUp.setInt(1, mGroup);
//        cleanUp.executeUpdate();
//        mData.mDb.close();
        super.tearDown();
    }

    @Test
    public void testGetUserToken() throws Exception {
        final String url = "/user/token";
        Response authNeededResponse = target(url).request().get();
        assertEquals(401, authNeededResponse.getStatus());
        assertTrue(authNeededResponse.getStringHeaders().containsKey(HttpHeaders.WWW_AUTHENTICATE));

        Response rightAuthNeededResponse = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWAuth + ":notTheRightPassword")).get();
        assertEquals(401, rightAuthNeededResponse.getStatus());

        Response notAuthorizedToGroup = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWOAuth + ":" + mData.mSecret)).get();
        assertEquals(202, notAuthorizedToGroup.getStatus());

        Response okResponse = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWAuth + ":" + mData.mSecret)).get();
        assertEquals(200, okResponse.getStatus());
        Token acceptedToken = okResponse.readEntity(Token.class);
        assertTrue(acceptedToken.getToken().length() == 32);
    }

    @Test
    public void testPostUserRegisterDevice() throws Exception {
        final String url = "/user/register_device";
        Response dataNeeded = target(url).request().post(null);
        assertEquals(400, dataNeeded.getStatus());

        Response groupReadableIdNeeded = target(url).request().post(
                Entity.json(new DeviceRegistration().withSecret(mData.mSecret)));
        assertEquals(400, groupReadableIdNeeded.getStatus());

        Response secretNeeded = target(url).request().post(
                Entity.json(new DeviceRegistration().withGroupId("123456")));
        assertEquals(400, secretNeeded.getStatus());

//        Response validDevice = target(url).request().post(Entity.json(new DeviceRegistration().
//                withGroupId("123456").withSecret(mData.mSecret).withName("dev3")));
//        assertEquals(201, validDevice.getStatus());
//        DeviceRegistrationAck dev3Ack = validDevice.readEntity(DeviceRegistrationAck.class);
//        PreparedStatement devCheckStmt = mData.mDb.prepareStatement("SELECT name, " +
//                "devicegroup_id, autorizedtogroup FROM devices WHERE id = ?");
//        devCheckStmt.setInt(1, dev3Ack.getDeviceId());
//        ResultSet dev3CheckRS = devCheckStmt.executeQuery();
//        assertTrue(dev3CheckRS.first());
//        assertEquals(mGroup, dev3CheckRS.getInt("devicegroup_id"));
//        assertFalse(dev3CheckRS.getBoolean("autorizedtogroup"));
//        dev3CheckRS.close();
//
//        PreparedStatement tempGroupStmt = mData.mDb.prepareStatement("INSERT INTO devicegroups " +
//                "(readableid) VALUES ('123456')", Statement.RETURN_GENERATED_KEYS);
//        assertEquals(1, tempGroupStmt.executeUpdate());
//        ResultSet tempGroupRS = tempGroupStmt.getGeneratedKeys();
//        tempGroupRS.first();
//        int tempGroupId = tempGroupRS.getInt(1);
//        try {
//            Response validDevice2 = target(url).request().post(Entity.json(new DeviceRegistration().
//                    withGroupId("123456").withSecret(mData.mSecret).withName("dev4")));
//            assertEquals(200, validDevice2.getStatus());
//            DeviceRegistrationAck dev4Ack = validDevice2.readEntity(DeviceRegistrationAck.class);
//            devCheckStmt.setInt(1, dev4Ack.getDeviceId());
//            ResultSet dev4CheckRS = devCheckStmt.executeQuery();
//            assertTrue(dev4CheckRS.first());
//            assertEquals(tempGroupId, dev4CheckRS.getInt("devicegroup_id"));
//            assertTrue(dev4CheckRS.getBoolean("autorizedtogroup"));
//            dev4CheckRS.close();
//        } finally {
//            PreparedStatement tempGroupCleanUp = mData.mDb.prepareStatement("DELETE FROM " +
//                    "devicegroups WHERE id = ?");
//            tempGroupCleanUp.setInt(1, tempGroupId);
//            tempGroupCleanUp.executeUpdate();
//            tempGroupCleanUp.close();
//        }
//        devCheckStmt.close();
    }

    @Test
    public void testGetUserGroupAccessKey() throws Exception {
        final String url = "/user/group/access_key";
        Response authNeededResponse = target(url).request().get();
        assertEquals(401, authNeededResponse.getStatus());
//        PreparedStatement checkGroup1 = mData.mDb.prepareStatement("SELECT readableid FROM " +
//                "devicegroups WHERE id = ?");
//        checkGroup1.setInt(1, mGroup);
//        ResultSet groupRS1 = checkGroup1.executeQuery();
//        groupRS1.next();
//        String savedId1 = groupRS1.getString("readableid");
//        groupRS1.close();
//        checkGroup1.close();
//        assertEquals("123456", savedId1);
//
//        String invalidToken = AuthHelper.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDeviceWOAuth + ":" + mData.mSecret));
//        Response rightAuthNeededResponse = target(url).queryParam("token", invalidToken).request()
//                .get();
//        assertEquals(401, rightAuthNeededResponse.getStatus());
//        PreparedStatement checkGroup2 = mData.mDb.prepareStatement("SELECT readableid FROM " +
//                "devicegroups WHERE id = ?");
//        checkGroup2.setInt(1, mGroup);
//        ResultSet groupRS2 = checkGroup2.executeQuery();
//        groupRS2.next();
//        String savedId2 = groupRS2.getString("readableid");
//        groupRS2.close();
//        checkGroup2.close();
//        assertEquals("123456", savedId2);
//
//        String validToken = AuthHelper.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDeviceWAuth + ":" + mData.mSecret));
//        Response okResponse = target(url).queryParam("token", validToken).request().get();
//        assertEquals(200, okResponse.getStatus());
//        Group recvdGroup = okResponse.readEntity(Group.class);
//        assertEquals(6, recvdGroup.getReadableId().length());
//        PreparedStatement checkGroup3 = mData.mDb.prepareStatement("SELECT readableid FROM " +
//                "devicegroups WHERE id = ?");
//        checkGroup3.setInt(1, mGroup);
//        ResultSet groupRS3 = checkGroup3.executeQuery();
//        groupRS3.next();
//        String savedId3 = groupRS3.getString("readableid");
//        groupRS3.close();
//        checkGroup3.close();
//        assertEquals(recvdGroup.getReadableId(), savedId3);
    }

    @Test
    public void testPostUserRegisterGroup() throws Exception {
        final String url = "/user/register_group";
        Response okResponse = target(url).request().post(null);
        assertEquals(200, okResponse.getStatus());
        Group newGroup = okResponse.readEntity(Group.class);
        assertEquals(6, newGroup.getReadableId().length());

//        PreparedStatement checkStmt = mData.mDb.prepareStatement("SELECT id, created FROM " +
//                "devicegroups WHERE readableid = ?");
//        checkStmt.setString(1, newGroup.getReadableId());
//        ResultSet checkRS = checkStmt.executeQuery();
//        assertTrue(checkRS.first());
//        int newGroupId = checkRS.getInt("id");
//        Date newGroupCreated = checkRS.getDate("created");
//        checkRS.close();
//        PreparedStatement cleanUpStmt = mData.mDb.prepareStatement("DELETE FROM devicegroups " +
//                "WHERE id = ?");
//        cleanUpStmt.setInt(1, newGroupId);
//        cleanUpStmt.executeUpdate();
//        cleanUpStmt.close();
//        assertTrue(newGroupCreated.after(new Date(System.currentTimeMillis() - 15000)));
    }

    @Test
    public void testGetUserGroupDevices() throws Exception {
        final String url = "/user/group/devices";

        Response noTokenResponse = target(url).request().get();
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(url).queryParam("token", "invalidtoken").request().get();
        assertEquals(401, badTokenResponse.getStatus());

//        String token = AuthHelper.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " + Base64
//                .encodeAsString(mDeviceWAuth + ":" + mData.mSecret));
//        Response okResponse = target(url).queryParam("token", token).request().get();
//        assertEquals(200, okResponse.getStatus());
//        DeviceInfo[] deviceInfos = okResponse.readEntity(DeviceInfo[].class);
//        assertEquals(2, deviceInfos.length);
//        for (DeviceInfo info : deviceInfos) {
//            if (info.getId() == null)
//                fail();
//            if (info.getId() == mDeviceWAuth) {
//                assertEquals("dev1", info.getName());
//                assertTrue(info.getAuthorized());
//            } else if(info.getId() == mDeviceWOAuth) {
//                assertEquals("dev2", info.getName());
//                assertFalse(info.getAuthorized());
//            } else {
//                fail("Unknown id.");
//            }
//        }
    }

    @Test
    public void testPutUserGroupDevices() throws Exception {
        final String url = "/user/group/devices";

        Response noTokenResponse = target(url).request().put(Entity.json(new DeviceInfo[0]));
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(url).queryParam("token", "invalidtoken").request().
                put(Entity.json(new DeviceInfo[0]));
        assertEquals(401, badTokenResponse.getStatus());

//        String token = AuthHelper.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " + Base64
//                .encodeAsString(mDeviceWAuth + ":" + mData.mSecret));
//        Response noDataResponse = target(url).queryParam("token", token).request().
//                put(Entity.json(new DeviceInfo[0]));
//        assertEquals(400, noDataResponse.getStatus());
//
//        Response wrongDataResponse = target(url).queryParam("token", token).request().put(
//                Entity.json(new DeviceInfo[]{
//                        new DeviceInfo().withAuthorization(true).withName("none")}));
//        assertEquals(400, wrongDataResponse.getStatus());
//
//        Response okResponse = target(url).queryParam("token", token).request().put(
//                Entity.json(new DeviceInfo[]{
//                        new DeviceInfo().withId(mDeviceWOAuth).withAuthorization(true)
//                }));
//        assertEquals(200, okResponse.getStatus());
//        PreparedStatement checkChangedDev2Stmt = mData.mDb.prepareStatement("SELECT name, " +
//                "devicegroup_id, autorizedtogroup, secret FROM devices WHERE id = ?");
//        checkChangedDev2Stmt.setInt(1, mDeviceWOAuth);
//        ResultSet checkChangedDev2RS = checkChangedDev2Stmt.executeQuery();
//        assertTrue(checkChangedDev2RS.first());
//        assertEquals("dev2", checkChangedDev2RS.getString("name"));
//        assertEquals(mGroup, checkChangedDev2RS.getInt("devicegroup_id"));
//        assertTrue(checkChangedDev2RS.getBoolean("autorizedtogroup"));
//        assertEquals(mData.mEncryptedSecret, checkChangedDev2RS.getString("secret"));
//        checkChangedDev2RS.close();
//        checkChangedDev2Stmt.close();
    }

    @Test
    public void testDeleteUserGroupDevices() throws Exception {
        final String url = "/user/group/devices";

        Response noTokenResponse = target(url).queryParam("deviceid", 0).request().delete();
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(url).queryParam("token", "invalidtoken").
                queryParam("deviceid", 0).request().delete();
        assertEquals(401, badTokenResponse.getStatus());

//        String token = AuthHelper.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " + Base64.
//                encodeAsString(mDeviceWAuth + ":" + mData.mSecret));
//        Response noDataResponse = target(url).queryParam("token", token).request().delete();
//        assertEquals(400, noDataResponse.getStatus());
//
//        Response okResponse = target(url).queryParam("token", token).
//                queryParam("deviceid", mDeviceWOAuth).request().delete();
//        assertEquals(200, okResponse.getStatus());
//        PreparedStatement checkRemovedDev2Stmt = mData.mDb.prepareStatement("SELECT COUNT(id) " +
//                "FROM devices WHERE id = ?");
//        checkRemovedDev2Stmt.setInt(1, mDeviceWOAuth);
//        ResultSet checkRemovedDev2RS = checkRemovedDev2Stmt.executeQuery();
//        checkRemovedDev2RS.first();
//        assertEquals(0, checkRemovedDev2RS.getInt(1));
//        checkRemovedDev2RS.close();
//        checkRemovedDev2Stmt.close();
//
//        Response okResponse2 = target(url).queryParam("token", token).
//                queryParam("deviceid", mDeviceWAuth).request().delete();
//        assertEquals(200, okResponse2.getStatus());
//        PreparedStatement checkRemovedGroupStmt = mData.mDb.prepareStatement("SELECT COUNT(id) " +
//                "FROM devicegroups WHERE id = ?");
//        checkRemovedGroupStmt.setInt(1, mGroup);
//        ResultSet checkRemovedGroupRS = checkRemovedGroupStmt.executeQuery();
//        checkRemovedGroupRS.first();
//        assertEquals(0, checkRemovedGroupRS.getInt(1));
//        checkRemovedGroupRS.close();
//        checkRemovedGroupStmt.close();
    }
}