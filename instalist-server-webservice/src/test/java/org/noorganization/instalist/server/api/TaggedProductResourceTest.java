package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.TaggedProductInfo;
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
import java.util.UUID;

import static org.junit.Assert.*;

public class TaggedProductResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    TaggedProduct mTaggedProduct;
    TaggedProduct mNATaggedProduct;
    Product mProduct;
    Tag mTag;
    Product mNAProduct;
    Tag mNATag;
    DeletedObject mDeletedIngredient;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(TaggedProductResource.class);
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
        mTag = new Tag().withGroup(mGroup).withName("tag1").withUUID(UUID.randomUUID()).
                withUpdated(creation);
        mTaggedProduct = new TaggedProduct().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withUpdated(creation).withProduct(mProduct).withTag(mTag);
        mDeletedIngredient = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.TAGGEDPRODUCT).withUpdated(creation);
        mNAGroup = new DeviceGroup();
        mNATag = new Tag().withName("tag2").withUUID(UUID.randomUUID()).withGroup(mNAGroup);
        mNAProduct = new Product().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withName("product2");
        mNATaggedProduct = new TaggedProduct().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withProduct(mNAProduct).withTag(mNATag);

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mProduct);
        mManager.persist(mTag);
        mManager.persist(mTaggedProduct);
        mManager.persist(mDeletedIngredient);
        mManager.persist(mNAGroup);
        mManager.persist(mNAProduct);
        mManager.persist(mNATag);
        mManager.persist(mNATaggedProduct);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mProduct);
        mManager.refresh(mTag);
        mManager.refresh(mTaggedProduct);
        mManager.refresh(mDeletedIngredient);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAProduct);
        mManager.refresh(mNATag);
        mManager.refresh(mNATaggedProduct);
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
    public void testGetTaggedProducts() throws Exception {
        String url = "/groups/%d/taggedproducts";

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
        TaggedProductInfo[] allTaggedProductInfo = okResponse1.
                readEntity(TaggedProductInfo[].class);
        assertEquals(2, allTaggedProductInfo.length);
        for (TaggedProductInfo current: allTaggedProductInfo) {
            if (mTaggedProduct.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals(mTaggedProduct.getUpdated(), current.getLastChanged().toInstant());
                assertEquals(mTag.getUUID(), UUID.fromString(current.getTagUUID()));
                assertEquals(mProduct.getUUID(), UUID.fromString(current.getProductUUID()));
                assertFalse(current.getDeleted());
            } else if (mDeletedIngredient.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getTagUUID());
                assertNull(current.getProductUUID());
                assertEquals(mDeletedIngredient.getUpdated(), current.getLastChanged().toInstant());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected TaggedProduct.");
        }

        mManager.getTransaction().begin();
        mTaggedProduct.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(Date.from(preUpdate), true)).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        TaggedProductInfo[] oneTaggedProductInfo = okResponse2.
                readEntity(TaggedProductInfo[].class);
        assertEquals(1, oneTaggedProductInfo.length);
        assertEquals(mTaggedProduct.getUUID(), UUID.fromString(oneTaggedProductInfo[0].getUUID()));
        assertFalse(oneTaggedProductInfo[0].getDeleted());
    }

    @Test
    public void testGetTaggedProduct() throws Exception {
        String url = "/groups/%d/taggedproducts/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        TaggedProductInfo returnedTPInfo = okResponse1.readEntity(TaggedProductInfo.class);
        assertNotNull(returnedTPInfo);
        assertEquals(mTaggedProduct.getUpdated(), returnedTPInfo.getLastChanged().toInstant());
        assertEquals(mTag.getUUID(), UUID.fromString(returnedTPInfo.getTagUUID()));
        assertEquals(mProduct.getUUID(), UUID.fromString(returnedTPInfo.getProductUUID()));
        assertFalse(returnedTPInfo.getDeleted());
    }

    @Test
    public void testPostTaggedProduct() throws Exception {
        String url = "/groups/%d/taggedproducts";
        TaggedProductInfo newTP = new TaggedProductInfo().withUUID(mTaggedProduct.getUUID()).
                withProductUUID(mProduct.getUUID()).withTagUUID(mNATag.getUUID());
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newTP));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newTP));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTP));
        assertEquals(401, wrongGroupResponse.getStatus());

        newTP.setUUID(UUID.randomUUID());
        Response wrongRefResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTP));
        assertEquals(400, wrongRefResponse.getStatus());

        newTP.setTagUUID(mTag.getUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTP));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<TaggedProduct> savedTaggedProductQuery = mManager.createQuery("select tp from " +
                "TaggedProduct tp where tp.group = :group and tp.UUID = :uuid",
                TaggedProduct.class);
        savedTaggedProductQuery.setParameter("group", mGroup);
        savedTaggedProductQuery.setParameter("uuid", UUID.fromString(newTP.getUUID()));
        List<TaggedProduct> savedIngredients = savedTaggedProductQuery.getResultList();
        assertEquals(1, savedIngredients.size());
        assertTrue(preInsert.isBefore(savedIngredients.get(0).getUpdated()));
    }

    @Test
    public void testPutTaggedProduct() throws Exception {
        String url = "/groups/%d/taggedproducts/%s";
        Instant preUpdate = mTaggedProduct.getUpdated();
        TaggedProductInfo updatedTP = new TaggedProductInfo().withTagUUID(mNATag.getUUID());

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().put(Entity.json(updatedTP));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedTP));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(410, goneResponse.getStatus());

        Response wrongRefResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(400, wrongRefResponse.getStatus());
        mManager.refresh(mTaggedProduct);
        assertEquals(mTag.getUUID(), mTaggedProduct.getTag().getUUID());

        updatedTP.setTagUUID(mTag.getUUID());
        updatedTP.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mTaggedProduct);
        assertEquals(mTag.getUUID(), mTaggedProduct.getTag().getUUID());


        updatedTP.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedTP));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mTaggedProduct);
        assertTrue(preUpdate + " is not before " + mTaggedProduct.getUpdated(),
                preUpdate.isBefore(mTaggedProduct.getUpdated()));
    }

    @Test
    public void testDeleteTaggedProducts() throws Exception {
        String url = "/groups/%d/taggedproducts/%s";
        Instant preDelete = mTaggedProduct.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mTaggedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<TaggedProduct> savedTaggedProductQuery = mManager.createQuery("select tp from " +
                "TaggedProduct tp where tp.group = :group and tp.UUID = :uuid",
                TaggedProduct.class);
        savedTaggedProductQuery.setParameter("group", mGroup);
        savedTaggedProductQuery.setParameter("uuid", mTaggedProduct.getUUID());
        List<TaggedProduct> savedTaggedProducts = savedTaggedProductQuery.getResultList();
        assertEquals(0, savedTaggedProducts.size());
        TypedQuery<DeletedObject> savedDeletedTPQuery = mManager.createQuery("select do " +
                "from DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                "do.type = :type", DeletedObject.class);
        savedDeletedTPQuery.setParameter("group", mGroup);
        savedDeletedTPQuery.setParameter("uuid", mTaggedProduct.getUUID());
        savedDeletedTPQuery.setParameter("type", DeletedObject.Type.TAGGEDPRODUCT);
        List<DeletedObject> savedDeletedTaggedProducts = savedDeletedTPQuery.getResultList();
        assertEquals(1, savedDeletedTaggedProducts.size());
        assertTrue(preDelete.isBefore(savedDeletedTaggedProducts.get(0).getUpdated()));
    }
}
