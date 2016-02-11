package org.noorganization.instalist.server.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.ListInfo;
import org.noorganization.instalist.comm.message.UnitInfo;
import org.noorganization.instalist.comm.support.DateHelper;
import org.noorganization.instalist.server.AuthenticationFilter;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by damihe on 10.02.16.
 */
public class UnitResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    Unit mUnit;
    Unit mNAUnit;
    DeletedObject mDeletedUnit;
    Product mProductWU;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(UnitResource.class);
        rc.register(AuthenticationFilter.class);
        return rc;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        CommonData data = new CommonData();
        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();

        mGroup = new DeviceGroup();
        mUnit = new Unit().withGroup(mGroup).withName("unit1").withUUID(UUID.randomUUID());
        mProductWU = new Product().withGroup(mGroup).withName("product1").withUnit(mUnit).
                withUUID(UUID.randomUUID());

        mDeletedUnit = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.UNIT);
        mNAGroup = new DeviceGroup();
        mNAUnit = new Unit().withGroup(mNAGroup).withName("unit2").withUUID(UUID.randomUUID());

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mUnit);
        mManager.persist(mDeletedUnit);
        mManager.persist(mNAGroup);
        mManager.persist(mNAUnit);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mUnit);
        mManager.refresh(mDeletedUnit);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAUnit);
        mManager.refresh(authorizedDevice);

        mToken = ControllerFactory.getAuthController().getTokenByHttpAuth(mManager,
                authorizedDevice.getId(), data.mSecret);
        assertNotNull(mToken);
    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void testGetUnits() throws Exception {
        String url = "/groups/%d/units";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        UnitInfo[] allUnitInfo = okResponse1.readEntity(UnitInfo[].class);
        assertEquals(3, allUnitInfo.length);
        for(UnitInfo current: allUnitInfo) {
            if (mUnit.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("unit1", current.getName());
                assertNotNull(current.getLastChanged());
                assertFalse(current.getDeleted());
            } else if (mDeletedUnit.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getName());
                assertNotNull(current.getLastChanged());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected unit.");
        }

        Thread.sleep(1000);
        mManager.getTransaction().begin();
        mUnit.setUpdated(new Date(System.currentTimeMillis()));
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", DateHelper.writeDate(new Date(System.
                        currentTimeMillis() - 500))).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        UnitInfo[] oneUnitInfo = okResponse2.readEntity(UnitInfo[].class);
        assertEquals(1, oneUnitInfo.length);
        assertEquals(mUnit.getUUID(), UUID.fromString(oneUnitInfo[0].getUUID()));
        assertEquals("unit1", oneUnitInfo[0].getName());
        assertFalse(oneUnitInfo[0].getDeleted());
    }

    @Test
    public void testGetUnit() throws Exception {
        String url = "/groups/%d/units/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        UnitInfo returnedUnitInfo = okResponse1.readEntity(UnitInfo.class);
        assertNotNull(returnedUnitInfo);
        assertEquals(mUnit.getUUID(), UUID.fromString(returnedUnitInfo.getUUID()));
        assertEquals("unit1", returnedUnitInfo.getName());
        assertNotNull(returnedUnitInfo.getLastChanged());
        assertFalse(returnedUnitInfo.getDeleted());
    }

    @Test
    public void testPostUnit() throws Exception {
        String url = "/groups/%d/units";
        UnitInfo newUnit = new UnitInfo().withUUID(mUnit.getUUID()).withName("unit4");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newUnit));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newUnit));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newUnit));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newUnit));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mUnit);
        assertEquals("unit1", mUnit.getName());

        newUnit.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newUnit));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<Unit> savedUnitQuery = mManager.createQuery("select u from " +
                "Unit u where u.group = :group and u.UUID = :uuid", Unit.class);
        savedUnitQuery.setParameter("group", mGroup);
        savedUnitQuery.setParameter("uuid", UUID.fromString(newUnit.getUUID()));
        List<Unit> savedUnits = savedUnitQuery.getResultList();
        assertEquals(1, savedUnits.size());
        assertEquals("unit4", savedUnits.get(0).getName());
        assertTrue(new Date(System.currentTimeMillis() - 10000).before(
                savedUnits.get(0).getUpdated()));
    }

    @Test
    public void testPutUnit() throws Exception {
        String url = "/groups/%d/units/%s";
        Date preUpdate = mUnit.getUpdated();
        ListInfo updatedList = new ListInfo().withDeleted(false).withName("changedunit");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().put(Entity.json(updatedList));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedList));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mUnit);
        assertEquals("unit1", mUnit.getName());

        updatedList.setLastChanged(new Date(preUpdate.getTime() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mUnit);
        assertEquals("unit1", mUnit.getName());

        Thread.sleep(1000);
        updatedList.setLastChanged(new Date(System.currentTimeMillis()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mUnit);
        assertEquals("changedunit", mUnit.getName());
        assertTrue(preUpdate.getTime() + " is not before " + mUnit.getUpdated().getTime(),
                preUpdate.before(mUnit.getUpdated()));
    }

    @Test
    public void testDeleteUnit() throws Exception {
        String url = "/groups/%d/units/%s";
        Date preDelete = mUnit.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mUnit.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<Unit> savedUnitQuery = mManager.createQuery("select u from Unit u where " +
                "u.group = :group and u.UUID = :uuid", Unit.class);
        savedUnitQuery.setParameter("group", mGroup);
        savedUnitQuery.setParameter("uuid", mUnit.getUUID());
        List<Unit> savedUnits = savedUnitQuery.getResultList();
        assertEquals(0, savedUnits.size());
        TypedQuery<DeletedObject> savedDeletedUnitQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid and do.type = :type",
                DeletedObject.class);
        savedDeletedUnitQuery.setParameter("group", mGroup);
        savedDeletedUnitQuery.setParameter("uuid", mUnit.getUUID());
        savedDeletedUnitQuery.setParameter("type", DeletedObject.Type.UNIT);
        List<DeletedObject> savedDeletedUnits = savedDeletedUnitQuery.getResultList();
        assertEquals(1, savedDeletedUnits.size());
        assertTrue(preDelete.before(savedDeletedUnits.get(0).getTime()));
    }
}
