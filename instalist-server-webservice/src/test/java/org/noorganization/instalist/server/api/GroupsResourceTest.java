package org.noorganization.instalist.server.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.comm.message.DeviceInfo;
import org.noorganization.instalist.comm.message.GroupInfo;
import org.noorganization.instalist.comm.message.TokenInfo;
import org.noorganization.instalist.server.AuthenticationFilter;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.message.*;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.Date;

import static org.junit.Assert.*;

public class GroupsResourceTest extends JerseyTest{

    private CommonData mData;
    private Device mDeviceWAuth;
    private Device mDeviceWOAuth;
    private DeviceGroup mGroup;

    private EntityManager mManager;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        ResourceConfig rc = new ResourceConfig(GroupsResource.class);
        rc.register(AuthenticationFilter.class);
        return rc;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mData = new CommonData();

        mGroup = new DeviceGroup().withReadableId("123456");
        mDeviceWAuth = new Device().withName("dev1").withAuthorized(true).withSecret(mData
                .mEncryptedSecret).withGroup(mGroup);
        mDeviceWOAuth = new Device().withName("dev2").withAuthorized(false).withSecret(mData
                .mEncryptedSecret).withGroup(mGroup);

        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();
        mManager.persist(mGroup);
        mManager.persist(mDeviceWAuth);
        mManager.persist(mDeviceWOAuth);
        mManager.getTransaction().commit();
        mManager.refresh(mGroup);
        mManager.refresh(mDeviceWAuth);
        mManager.refresh(mDeviceWOAuth);

    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void testGetDeviceToken() throws Exception {
        final String url = "/groups/" + mGroup.getId() + "/devices/token";
        Response authNeededResponse = target(url).request().get();
        assertEquals(401, authNeededResponse.getStatus());
        assertTrue(authNeededResponse.getStringHeaders().containsKey(HttpHeaders.WWW_AUTHENTICATE));

        Response rightAuthNeededResponse = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWAuth.getId() + ":notTheRightPassword")).
                get();
        assertEquals(401, rightAuthNeededResponse.getStatus());

        Response notAuthorizedToGroup = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWOAuth.getId() + ":" + mData.mSecret)).
                get();
        assertEquals(202, notAuthorizedToGroup.getStatus());

        Response okResponse = target(url).request().header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.encodeAsString(mDeviceWAuth.getId() + ":" + mData.mSecret)).get();
        assertEquals(200, okResponse.getStatus());
        TokenInfo acceptedToken = okResponse.readEntity(TokenInfo.class);
        assertTrue(acceptedToken.getToken().length() == 32);
    }

    @Test
    public void testPostDevice() throws Exception {
        final String url = "/groups/%d/devices";
        Response dataNeeded = target(String.format(url, mGroup.getId())).request().post(null);
        assertEquals(400, dataNeeded.getStatus());

        Response groupReadableIdNeeded = target(String.format(url, mGroup.getId())).request().post(
                Entity.json(new DeviceRegistration().withSecret(mData.mSecret)));
        assertEquals(400, groupReadableIdNeeded.getStatus());

        Response secretNeeded = target(String.format(url, mGroup.getId())).request().post(
                Entity.json(new DeviceRegistration().withGroupAuth("123456")));
        assertEquals(400, secretNeeded.getStatus());

        Response validDevice = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(new DeviceRegistration().withGroupAuth("123456").
                        withSecret(mData.mSecret).withName("dev3")));
        assertEquals(201, validDevice.getStatus());
        DeviceInfo dev3Ack = validDevice.readEntity(DeviceInfo.class);

        //mData.flushEntityManager(mManager);
        mManager.getTransaction().begin();
        mManager.flush();
        mManager.getTransaction().commit();
        Device savedDev3 = mManager.find(Device.class, dev3Ack.getId());
        assertNotNull(savedDev3);
        assertEquals(mGroup, savedDev3.getGroup());
        assertFalse(savedDev3.getAuthorized());
        assertEquals("dev3", savedDev3.getName());
        assertTrue(BCrypt.checkpw(mData.mSecret, savedDev3.getSecret()));
        assertTrue(new Date(System.currentTimeMillis() - 10000).before(savedDev3.getCreated()));

        DeviceGroup newDevGroup = new DeviceGroup().withReadableId("123456");
        mManager.getTransaction().begin();
        mManager.persist(newDevGroup);
        mManager.getTransaction().commit();
        mManager.refresh(newDevGroup);
        Response validDevice2 = target(String.format(url, newDevGroup.getId())).request().
                post(Entity.json(new DeviceRegistration().withGroupAuth("123456").
                        withSecret(mData.mSecret).withName("dev4")));
        assertEquals(200, validDevice2.getStatus());
        DeviceInfo dev4Ack = validDevice2.readEntity(DeviceInfo.class);

        mData.flushEntityManager(mManager);
        Device savedDev4 = mManager.find(Device.class, dev4Ack.getId());
        assertNotNull(savedDev4);
        assertEquals(newDevGroup, savedDev4.getGroup());
        assertTrue(savedDev4.getAuthorized());

    }

    @Test
    public void testGetAccessKey() throws Exception {
        final String url = "/groups/%d/access_key";
        Response authNeededResponse = target(String.format(url, mGroup.getId())).request().get();
        assertEquals(401, authNeededResponse.getStatus());

        mData.flushEntityManager(mManager);
        mManager.refresh(mGroup);
        assertEquals("123456", mGroup.getReadableId());

        IAuthController authController = ControllerFactory.getAuthController();
        String invalidToken = authController.getTokenByHttpAuth(mManager, mDeviceWOAuth.getId(),
                mData.mSecret);
        Response rightAuthNeededResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + invalidToken).get();
        assertEquals(401, rightAuthNeededResponse.getStatus());
        mData.flushEntityManager(mManager);
        mManager.refresh(mGroup);
        assertEquals("123456", mGroup.getReadableId());

        Thread.sleep(300); // For checking whether "updated" gets really updated.
        String validToken = authController.getTokenByHttpAuth(mManager, mDeviceWAuth.getId(),
                mData.mSecret);
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + validToken).get();
        GroupInfo recvdGroup = okResponse.readEntity(GroupInfo.class);
        assertEquals(6, recvdGroup.getReadableId().length());
        mData.flushEntityManager(mManager);
        mManager.refresh(mGroup);
        assertEquals(recvdGroup.getReadableId(), mGroup.getReadableId());
        assertTrue(new Date(System.currentTimeMillis() - 10000).before(mGroup.getUpdated()));
        assertNotEquals(mGroup.getCreated(), mGroup.getUpdated());
    }

    @Test
    public void testPostGroup() throws Exception {
        final String url = "/groups";
        Response okResponse = target(url).request().post(null);
        assertEquals(200, okResponse.getStatus());
        GroupInfo newGroupInfo = okResponse.readEntity(GroupInfo.class);
        assertEquals(6, newGroupInfo.getReadableId().length());
        assertNotEquals(mGroup.getId(), (int) newGroupInfo.getId());

        mManager.getTransaction().begin();
        mManager.flush();
        mManager.getTransaction().commit();

        DeviceGroup savedGroup = mManager.find(DeviceGroup.class, newGroupInfo.getId());
        assertNotNull(savedGroup);
        assertEquals(newGroupInfo.getReadableId(), savedGroup.getReadableId());
        assertTrue(savedGroup.getCreated().after(
                new Date(System.currentTimeMillis() - 10000)));
    }

    @Test
    public void testGetDevices() throws Exception {
        final String url = "/groups/%d/devices";

        Response noTokenResponse = target(String.format(url, mGroup.getId())).request().get();
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token invalidtoken").get();
        assertEquals(401, badTokenResponse.getStatus());

        String token = ControllerFactory.getAuthController().
                getTokenByHttpAuth(mManager, mDeviceWAuth.getId(), mData.mSecret);
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + token).get();
        assertEquals(200, okResponse.getStatus());
        DeviceInfo[] deviceInfos = okResponse.readEntity(DeviceInfo[].class);
        assertEquals(2, deviceInfos.length);
        for (DeviceInfo info : deviceInfos) {
            if (info.getId() == null)
                fail();
            if (info.getId() == mDeviceWAuth.getId()) {
                assertEquals("dev1", info.getName());
                assertTrue(info.getAuthorized());
            } else if(info.getId() == mDeviceWOAuth.getId()) {
                assertEquals("dev2", info.getName());
                assertFalse(info.getAuthorized());
            } else {
                fail("Unknown id.");
            }
        }
    }

    @Test
    public void testPutDevice() throws Exception {
        final String url = "/groups/%d/devices/%d";

        Response noTokenResponse = target(String.format(url, mGroup.getId(), 0)).request().
                put(Entity.json(new DeviceInfo()));
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(String.format(url, mGroup.getId(), 0)).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token invalidtoken").
                put(Entity.json(new DeviceInfo()));
        assertEquals(401, badTokenResponse.getStatus());

        IAuthController authController = ControllerFactory.getAuthController();
        String token = authController.getTokenByHttpAuth(mManager, mDeviceWAuth.getId(),
                mData.mSecret);

        Response noDataResponse = target(String.format(url, mGroup.getId(), mDeviceWOAuth.getId()))
                .request().header(HttpHeaders.AUTHORIZATION, "X-Token " + token).
                put(Entity.json(new DeviceInfo()));
        assertEquals(400, noDataResponse.getStatus());
        mManager.refresh(mDeviceWOAuth);
        assertEquals("dev2", mDeviceWOAuth.getName());
        assertFalse(mDeviceWOAuth.getAuthorized());
        assertEquals(mData.mEncryptedSecret, mDeviceWOAuth.getSecret());

        Response okResponse = target(String.format(url, mGroup.getId(), mDeviceWOAuth.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + token).
                put(Entity.json(new DeviceInfo().withAuthorized(true).withName("none")));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mDeviceWOAuth);
        assertEquals("none", mDeviceWOAuth.getName());
        assertTrue(mDeviceWOAuth.getAuthorized());
        assertEquals(mData.mEncryptedSecret, mDeviceWOAuth.getSecret());
    }

    @Test
    public void testDeleteUserGroupDevices() throws Exception {
        final String url = "/groups/%d/devices/%d";

        Response noTokenResponse = target(String.format(url, mGroup.getId(), mDeviceWOAuth.getId())).
                request().delete();
        assertEquals(401, noTokenResponse.getStatus());
        Response badTokenResponse = target(String.format(url, mGroup.getId(),
                mDeviceWOAuth.getId())).request().header(HttpHeaders.AUTHORIZATION, "X-Token " +
                "invalid").delete();
        assertEquals(401, badTokenResponse.getStatus());

        String token = ControllerFactory.getAuthController().
                getTokenByHttpAuth(mManager, mDeviceWAuth.getId(), mData.mSecret);
        Response okResponse = target(String.format(url, mGroup.getId(), mDeviceWOAuth.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + token).delete();
        assertEquals(200, okResponse.getStatus());
        mData.flushEntityManager(mManager);
        assertNull(mManager.find(Device.class, mDeviceWOAuth.getId()));

        Response okResponse2 = target(String.format(url, mGroup.getId(), mDeviceWAuth.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + token).delete();
        assertEquals(200, okResponse2.getStatus());
        mData.flushEntityManager(mManager);
        assertNull(mManager.find(Device.class, mDeviceWOAuth.getId()));
        assertNull(mManager.find(DeviceGroup.class, mGroup.getId()));
    }
}