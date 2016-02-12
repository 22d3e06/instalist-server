package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.ListInfo;
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

public class ListResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    ShoppingList mListWOC;
    ShoppingList mListWC;
    ShoppingList mNAList;
    DeletedObject mDeletedList;
    Category mCat;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(ListResource.class);
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
        mCat = new Category().withGroup(mGroup).withName("").withUUID(UUID.randomUUID());
        mListWC = new ShoppingList().withGroup(mGroup).withName("list1").withCategrory(mCat).
                withUUID(UUID.randomUUID());
        mListWOC = new ShoppingList().withGroup(mGroup).withName("list2").withUUID(UUID.
                randomUUID());
        mDeletedList = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.LIST);
        mNAGroup = new DeviceGroup();
        mNAList = new ShoppingList().withGroup(mNAGroup).withName("list3").withUUID(UUID.
                randomUUID());

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mCat);
        mManager.persist(mListWC);
        mManager.persist(mListWOC);
        mManager.persist(mDeletedList);
        mManager.persist(mNAGroup);
        mManager.persist(mNAList);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mCat);
        mManager.refresh(mListWC);
        mManager.refresh(mListWOC);
        mManager.refresh(mDeletedList);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAList);
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
    public void testGetLists() throws Exception {
        String url = "/groups/%d/lists";

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
        ListInfo[] allListInfo = okResponse1.readEntity(ListInfo[].class);
        assertEquals(3, allListInfo.length);
        for(ListInfo current: allListInfo) {
            if (mListWC.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("list1", current.getName());
                assertEquals(mCat.getUUID(), UUID.fromString(current.getCategoryUUID()));
                assertNotNull(current.getLastChanged());
                assertFalse(current.getDeleted());
            } else if (mListWOC.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("list2", current.getName());
                assertNull(current.getCategoryUUID());
                assertNotNull(current.getLastChanged());
                assertFalse(current.getDeleted());
            } else if (mDeletedList.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getName());
                assertNull(current.getCategoryUUID());
                assertNotNull(current.getLastChanged());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected list.");
        }

        Thread.sleep(1000);
        mManager.getTransaction().begin();
        mListWC.setUpdated(new Date(System.currentTimeMillis()));
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(new Date(
                        mListWC.getUpdated().getTime() - 500), true))
                .request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        ListInfo[] oneListInfo = okResponse2.readEntity(ListInfo[].class);
        assertEquals(1, oneListInfo.length);
        assertEquals(mListWC.getUUID(), UUID.fromString(oneListInfo[0].getUUID()));
        assertEquals("list1", oneListInfo[0].getName());
        assertEquals(mCat.getUUID(), UUID.fromString(oneListInfo[0].getCategoryUUID()));
        assertFalse(oneListInfo[0].getDeleted());
    }

    @Test
    public void testGetList() throws Exception {
        String url = "/groups/%d/lists/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        ListInfo returnedListInfo = okResponse1.readEntity(ListInfo.class);
        assertNotNull(returnedListInfo);
        assertEquals(mListWC.getUUID(), UUID.fromString(returnedListInfo.getUUID()));
        assertEquals("list1", returnedListInfo.getName());
        assertEquals(mCat.getUUID(), UUID.fromString(returnedListInfo.getCategoryUUID()));
        assertNotNull(returnedListInfo.getLastChanged());
        assertFalse(returnedListInfo.getDeleted());
    }

    @Test
    public void testPostList() throws Exception {
        String url = "/groups/%d/lists";
        ListInfo newList = new ListInfo().withUUID(mListWC.getUUID()).withName("list4");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newList));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newList));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newList));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newList));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mListWC);
        assertEquals("list1", mListWC.getName());

        newList.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newList));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<ShoppingList> savedListQuery = mManager.createQuery("select sl from " +
                "ShoppingList sl where sl.group = :group and sl.UUID = :uuid", ShoppingList.class);
        savedListQuery.setParameter("group", mGroup);
        savedListQuery.setParameter("uuid", UUID.fromString(newList.getUUID()));
        List<ShoppingList> savedLists = savedListQuery.getResultList();
        assertEquals(1, savedLists.size());
        assertEquals("list4", savedLists.get(0).getName());
        assertNull(savedLists.get(0).getCategory());
        assertTrue(new Date(System.currentTimeMillis() - 10000).before(
                savedLists.get(0).getUpdated()));
    }

    @Test
    public void testPutList() throws Exception {
        String url = "/groups/%d/lists/%s";
        Date preUpdate = mListWC.getUpdated();
        ListInfo updatedList = new ListInfo().withDeleted(false).withName("changedlist");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().put(Entity.json(updatedList));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedList));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mListWC);
        assertEquals("list1", mListWC.getName());

        updatedList.setLastChanged(new Date(preUpdate.getTime() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mListWC);
        assertEquals("list1", mListWC.getName());

        Thread.sleep(1000);
        updatedList.setLastChanged(new Date(System.currentTimeMillis()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mListWC);
        assertEquals("changedlist", mListWC.getName());
        assertEquals(mCat, mListWC.getCategory());
        assertTrue(preUpdate.getTime() + " is not before " + mListWC.getUpdated().getTime(),
                preUpdate.before(mListWC.getUpdated()));
    }

    @Test
    public void testDeleteList() throws Exception {
        String url = "/groups/%d/lists/%s";
        Date preDelete = mListWC.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAList.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mListWC.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<ShoppingList> savedListQuery = mManager.createQuery("select sl from " +
                "ShoppingList sl where sl.group = :group and sl.UUID = :uuid", ShoppingList.class);
        savedListQuery.setParameter("group", mGroup);
        savedListQuery.setParameter("uuid", mListWC.getUUID());
        List<ShoppingList> savedLists = savedListQuery.getResultList();
        assertEquals(0, savedLists.size());
        TypedQuery<DeletedObject> savedDeletedListQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid", DeletedObject.class);
        savedDeletedListQuery.setParameter("group", mGroup);
        savedDeletedListQuery.setParameter("uuid", mListWC.getUUID());
        List<DeletedObject> savedDeletedLists = savedDeletedListQuery.getResultList();
        assertEquals(1, savedDeletedLists.size());
        assertTrue(preDelete.before(savedDeletedLists.get(0).getTime()));

    }
}
