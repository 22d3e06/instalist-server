package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.EntryInfo;
import org.noorganization.instalist.comm.message.ProductInfo;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class EntryResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    ListEntry mEntry;
    ListEntry mNAEntry;
    Product mProduct;
    ShoppingList mList;
    Product mNAProduct;
    ShoppingList mNAList;
    DeletedObject mDeletedEntry;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(EntryResource.class);
        rc.register(AuthenticationFilter.class);
        return rc;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        CommonData data = new CommonData();
        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();

        Instant creation = Instant.now();
        mGroup = new DeviceGroup();
        mProduct = new Product().withGroup(mGroup).withName("product1").withUUID(UUID.randomUUID()).
                withUpdated(creation);
        mList = new ShoppingList().withGroup(mGroup).withName("list1").withUUID(UUID.randomUUID()).
                withUpdated(creation);
        mEntry = new ListEntry().withGroup(mGroup).withUUID(UUID.randomUUID()).withAmount(1f).
                withPriority(2).withUpdated(creation).withProduct(mProduct).withList(mList);
        mDeletedEntry = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.LISTENTRY).withTime(Date.from(creation));
        mNAGroup = new DeviceGroup();
        mNAList = new ShoppingList().withName("list2").withUUID(UUID.randomUUID()).
                withGroup(mNAGroup);
        mNAProduct = new Product().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withName("product2");
        mNAEntry = new ListEntry().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withProduct(mNAProduct).withList(mList);

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mProduct);
        mManager.persist(mList);
        mManager.persist(mEntry);
        mManager.persist(mDeletedEntry);
        mManager.persist(mNAGroup);
        mManager.persist(mNAProduct);
        mManager.persist(mNAList);
        mManager.persist(mNAEntry);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mProduct);
        mManager.refresh(mList);
        mManager.refresh(mEntry);
        mManager.refresh(mDeletedEntry);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAProduct);
        mManager.refresh(mNAList);
        mManager.refresh(mNAEntry);
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
    public void testGetEntries() throws Exception {
        String url = "/groups/%d/listentries";

        Instant preUpdate = Instant.now();

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
        EntryInfo[] allEntryInfo = okResponse1.readEntity(EntryInfo[].class);
        assertEquals(3, allEntryInfo.length);
        for(EntryInfo current: allEntryInfo) {
            if (mEntry.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals(mEntry.getUpdated(), current.getLastChanged().toInstant());
                assertEquals(mList.getUUID(), UUID.fromString(current.getListUUID()));
                assertEquals(mProduct.getUUID(), UUID.fromString(current.getProductUUID()));
                assertEquals(1f, current.getAmount(), 0.001f);
                assertEquals(2, (int) current.getPriority());
                assertFalse(current.getStruck());
                assertFalse(current.getDeleted());
            } else if (mDeletedEntry.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getListUUID());
                assertNull(current.getProductUUID());
                assertNull(current.getAmount());
                assertNull(current.getPriority());
                assertNull(current.getStruck());
                assertEquals(mDeletedEntry.getTime(), current.getLastChanged());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected Entry.");
        }

        mManager.getTransaction().begin();
        mEntry.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(Date.from(preUpdate), true)).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        EntryInfo[] oneEntryInfo = okResponse2.readEntity(EntryInfo[].class);
        assertEquals(1, oneEntryInfo.length);
        assertEquals(mEntry.getUUID(), UUID.fromString(oneEntryInfo[0].getUUID()));
        assertFalse(oneEntryInfo[0].getDeleted());
    }

    @Test
    public void testGetEntry() throws Exception {
        String url = "/groups/%d/listentries/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        EntryInfo returnedEntryInfo = okResponse1.readEntity(EntryInfo.class);
        assertNotNull(returnedEntryInfo);
        assertEquals(mEntry.getUpdated(), returnedEntryInfo.getLastChanged().toInstant());
        assertEquals(mList.getUUID(), UUID.fromString(returnedEntryInfo.getListUUID()));
        assertEquals(mProduct.getUUID(), UUID.fromString(returnedEntryInfo.getProductUUID()));
        assertEquals(1f, returnedEntryInfo.getAmount(), 0.001f);
        assertEquals(2, (int) returnedEntryInfo.getPriority());
        assertFalse(returnedEntryInfo.getStruck());
        assertFalse(returnedEntryInfo.getDeleted());
    }

    @Test
    public void testPostEntry() throws Exception {
        String url = "/groups/%d/listentries";
        EntryInfo newProduct = new EntryInfo().withUUID(mEntry.getUUID()).
                withProductUUID(mProduct.getUUID()).withListUUID(mList.getUUID()).withAmount(3f);
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newProduct));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newProduct));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mEntry);
        assertEquals(1f, mEntry.getAmount(), 0.001f);

        newProduct.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<ListEntry> savedEntriesQuery = mManager.createQuery("select le from " +
                "ListEntry le where le.group = :group and le.UUID = :uuid", ListEntry.class);
        savedEntriesQuery.setParameter("group", mGroup);
        savedEntriesQuery.setParameter("uuid", UUID.fromString(newProduct.getUUID()));
        List<ListEntry> savedEntries = savedEntriesQuery.getResultList();
        assertEquals(1, savedEntries.size());
        assertEquals(3f, savedEntries.get(0).getAmount(), 0.001f);
        assertTrue(preInsert.isBefore(savedEntries.get(0).getUpdated()));
    }

    @Test
    public void testPutEntry() throws Exception {
        String url = "/groups/%d/listentries/%s";
        Instant preUpdate = mEntry.getUpdated();
        EntryInfo updatedEntry = new EntryInfo().withAmount(3f);

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().put(Entity.json(updatedEntry));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedEntry));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedEntry));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedEntry));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedEntry));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mEntry);
        assertEquals(1f, mEntry.getAmount(), 0.001f);

        updatedEntry.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedEntry));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mEntry);
        assertEquals(1f, mEntry.getAmount(), 0.001f);

        updatedEntry.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedEntry));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mEntry);
        assertEquals(3f, mEntry.getAmount(), 0.001f);
        assertTrue(preUpdate + " is not before " + mEntry.getUpdated(),
                preUpdate.isBefore(mEntry.getUpdated()));
    }

    @Test
    public void testDeleteEntry() throws Exception {
        String url = "/groups/%d/listentries/%s";
        Instant preDelete = mEntry.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mEntry.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<ListEntry> savedEntryQuery = mManager.createQuery("select le from ListEntry le "+
                "where le.group = :group and le.UUID = :uuid", ListEntry.class);
        savedEntryQuery.setParameter("group", mGroup);
        savedEntryQuery.setParameter("uuid", mEntry.getUUID());
        List<ListEntry> savedEntries = savedEntryQuery.getResultList();
        assertEquals(0, savedEntries.size());
        TypedQuery<DeletedObject> savedDeletedEntryQuery = mManager.createQuery("select do " +
                "from DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                "do.type = :type", DeletedObject.class);
        savedDeletedEntryQuery.setParameter("group", mGroup);
        savedDeletedEntryQuery.setParameter("uuid", mEntry.getUUID());
        savedDeletedEntryQuery.setParameter("type", DeletedObject.Type.LISTENTRY);
        List<DeletedObject> savedDeletedEntries = savedDeletedEntryQuery.getResultList();
        assertEquals(1, savedDeletedEntries.size());
        assertTrue(preDelete.isBefore(savedDeletedEntries.get(0).getTime().toInstant()));
    }
}
