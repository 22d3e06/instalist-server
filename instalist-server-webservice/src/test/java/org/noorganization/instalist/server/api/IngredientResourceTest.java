package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.IngredientInfo;
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

public class IngredientResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    Ingredient mIngredient;
    Ingredient mNAIngredient;
    Product mProduct;
    Recipe mRecipe;
    Product mNAProduct;
    Recipe mNARecipe;
    DeletedObject mDeletedIngredient;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(IngredientResource.class);
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
        mRecipe = new Recipe().withGroup(mGroup).withName("recipe1").withUUID(UUID.randomUUID()).
                withUpdated(creation);
        mIngredient = new Ingredient().withGroup(mGroup).withUUID(UUID.randomUUID()).withAmount(1f).
                withUpdated(creation).withProduct(mProduct).withRecipe(mRecipe);
        mDeletedIngredient = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.INGREDIENT).withUpdated(creation);
        mNAGroup = new DeviceGroup();
        mNARecipe = new Recipe().withName("recipe2").withUUID(UUID.randomUUID()).
                withGroup(mNAGroup);
        mNAProduct = new Product().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withName("product2");
        mNAIngredient = new Ingredient().withGroup(mNAGroup).withUUID(UUID.randomUUID()).
                withProduct(mNAProduct).withRecipe(mNARecipe);

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mProduct);
        mManager.persist(mRecipe);
        mManager.persist(mIngredient);
        mManager.persist(mDeletedIngredient);
        mManager.persist(mNAGroup);
        mManager.persist(mNAProduct);
        mManager.persist(mNARecipe);
        mManager.persist(mNAIngredient);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mProduct);
        mManager.refresh(mRecipe);
        mManager.refresh(mIngredient);
        mManager.refresh(mDeletedIngredient);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAProduct);
        mManager.refresh(mNARecipe);
        mManager.refresh(mNAIngredient);
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
    public void testGetIngredients() throws Exception {
        String url = "/groups/%d/ingredients";

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
        IngredientInfo[] allIngredientInfo = okResponse1.readEntity(IngredientInfo[].class);
        assertEquals(2, allIngredientInfo.length);
        for(IngredientInfo current: allIngredientInfo) {
            if (mIngredient.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals(mIngredient.getUpdated(), current.getLastChanged().toInstant());
                assertEquals(mRecipe.getUUID(), UUID.fromString(current.getRecipeUUID()));
                assertEquals(mProduct.getUUID(), UUID.fromString(current.getProductUUID()));
                assertEquals(1f, current.getAmount(), 0.001f);
                assertFalse(current.getDeleted());
            } else if (mDeletedIngredient.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getRecipeUUID());
                assertNull(current.getProductUUID());
                assertNull(current.getAmount());
                assertEquals(mDeletedIngredient.getUpdated(), current.getLastChanged().toInstant());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected Entry.");
        }

        mManager.getTransaction().begin();
        mIngredient.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(Date.from(preUpdate), true)).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        IngredientInfo[] oneIngredientInfo = okResponse2.readEntity(IngredientInfo[].class);
        assertEquals(1, oneIngredientInfo.length);
        assertEquals(mIngredient.getUUID(), UUID.fromString(oneIngredientInfo[0].getUUID()));
        assertFalse(oneIngredientInfo[0].getDeleted());
    }

    @Test
    public void testGetIngredient() throws Exception {
        String url = "/groups/%d/ingredients/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        IngredientInfo returnedIngredientInfo = okResponse1.readEntity(IngredientInfo.class);
        assertNotNull(returnedIngredientInfo);
        assertEquals(mIngredient.getUpdated(), returnedIngredientInfo.getLastChanged().toInstant());
        assertEquals(mRecipe.getUUID(), UUID.fromString(returnedIngredientInfo.getRecipeUUID()));
        assertEquals(mProduct.getUUID(), UUID.fromString(returnedIngredientInfo.getProductUUID()));
        assertEquals(1f, returnedIngredientInfo.getAmount(), 0.001f);
        assertFalse(returnedIngredientInfo.getDeleted());
    }

    @Test
    public void testPostIngredient() throws Exception {
        String url = "/groups/%d/ingredients";
        IngredientInfo newIngred = new IngredientInfo().withUUID(mIngredient.getUUID()).
                withProductUUID(mProduct.getUUID()).withRecipeUUID(mRecipe.getUUID()).withAmount(3f);
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newIngred));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newIngred));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newIngred));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newIngred));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mIngredient);
        assertEquals(1f, mIngredient.getAmount(), 0.001f);

        newIngred.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newIngred));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<Ingredient> savedIngredientQuery = mManager.createQuery("select i from " +
                "Ingredient i where i.group = :group and i.UUID = :uuid", Ingredient.class);
        savedIngredientQuery.setParameter("group", mGroup);
        savedIngredientQuery.setParameter("uuid", UUID.fromString(newIngred.getUUID()));
        List<Ingredient> savedIngredients = savedIngredientQuery.getResultList();
        assertEquals(1, savedIngredients.size());
        assertEquals(3f, savedIngredients.get(0).getAmount(), 0.001f);
        assertTrue(preInsert.isBefore(savedIngredients.get(0).getUpdated()));
    }

    @Test
    public void testPutIngredient() throws Exception {
        String url = "/groups/%d/ingredients/%s";
        Instant preUpdate = mIngredient.getUpdated();
        IngredientInfo updatedIngred = new IngredientInfo().withAmount(3f);

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().put(Entity.json(updatedIngred));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedIngred));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedIngred));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedIngred));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedIngred));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mIngredient);
        assertEquals(1f, mIngredient.getAmount(), 0.001f);

        updatedIngred.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedIngred));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mIngredient);
        assertEquals(1f, mIngredient.getAmount(), 0.001f);

        updatedIngred.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedIngred));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mIngredient);
        assertEquals(3f, mIngredient.getAmount(), 0.001f);
        assertTrue(preUpdate + " is not before " + mIngredient.getUpdated(),
                preUpdate.isBefore(mIngredient.getUpdated()));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        String url = "/groups/%d/ingredients/%s";
        Instant preDelete = mIngredient.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mIngredient.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<Ingredient> savedIngredientQuery = mManager.createQuery("select i from " +
                "Ingredient i where i.group = :group and i.UUID = :uuid", Ingredient.class);
        savedIngredientQuery.setParameter("group", mGroup);
        savedIngredientQuery.setParameter("uuid", mIngredient.getUUID());
        List<Ingredient> savedIngredients = savedIngredientQuery.getResultList();
        assertEquals(0, savedIngredients.size());
        TypedQuery<DeletedObject> savedDeletedEntryQuery = mManager.createQuery("select do " +
                "from DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                "do.type = :type", DeletedObject.class);
        savedDeletedEntryQuery.setParameter("group", mGroup);
        savedDeletedEntryQuery.setParameter("uuid", mIngredient.getUUID());
        savedDeletedEntryQuery.setParameter("type", DeletedObject.Type.INGREDIENT);
        List<DeletedObject> savedDeletedEntries = savedDeletedEntryQuery.getResultList();
        assertEquals(1, savedDeletedEntries.size());
        assertTrue(preDelete.isBefore(savedDeletedEntries.get(0).getUpdated()));
    }
}
