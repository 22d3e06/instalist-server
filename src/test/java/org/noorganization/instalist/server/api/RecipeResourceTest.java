/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.RecipeInfo;
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

public class RecipeResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    Recipe mRecipe;
    Recipe mNARecipe;
    DeletedObject mDeletedRecipe;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(RecipeResource.class);
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
        mRecipe = new Recipe().withGroup(mGroup).withName("recipe1").withUUID(UUID.randomUUID()).
                withUpdated(creation);

        mDeletedRecipe = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.RECIPE).withUpdated(creation);
        mNAGroup = new DeviceGroup();
        mNARecipe = new Recipe().withGroup(mNAGroup).withName("recipe2").withUUID(
                UUID.randomUUID());

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mRecipe);
        mManager.persist(mDeletedRecipe);
        mManager.persist(mNAGroup);
        mManager.persist(mNARecipe);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mRecipe);
        mManager.refresh(mDeletedRecipe);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNARecipe);
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
    public void testGetRecipes() throws Exception {
        String url = "/groups/%d/recipes";

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
        RecipeInfo[] allRecipeInfo = okResponse1.readEntity(RecipeInfo[].class);
        assertEquals(2, allRecipeInfo.length);
        for(RecipeInfo current: allRecipeInfo) {
            if (mRecipe.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("recipe1", current.getName());
                assertEquals(mRecipe.getUpdated(), current.getLastChanged().toInstant());
                assertFalse(current.getDeleted());
            } else if (mDeletedRecipe.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getName());
                assertEquals(mDeletedRecipe.getUpdated(), current.getLastChanged().toInstant());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected recipe.");
        }

        Thread.sleep(200);
        mManager.getTransaction().begin();
        mRecipe.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(new Date(System.
                        currentTimeMillis() - 100), true)).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        RecipeInfo[] oneRecipeInfo = okResponse2.readEntity(RecipeInfo[].class);
        assertEquals(1, oneRecipeInfo.length);
        assertEquals(mRecipe.getUUID(), UUID.fromString(oneRecipeInfo[0].getUUID()));
        assertEquals("recipe1", oneRecipeInfo[0].getName());
        assertFalse(oneRecipeInfo[0].getDeleted());
    }

    @Test
    public void testGetRecipe() throws Exception {
        String url = "/groups/%d/recipes/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        RecipeInfo returnedRecipeInfo = okResponse1.readEntity(RecipeInfo.class);
        assertNotNull(returnedRecipeInfo);
        assertEquals(mRecipe.getUUID(), UUID.fromString(returnedRecipeInfo.getUUID()));
        assertEquals("recipe1", returnedRecipeInfo.getName());
        assertEquals(mRecipe.getUpdated(), returnedRecipeInfo.getLastChanged().toInstant());
        assertFalse(returnedRecipeInfo.getDeleted());
    }

    @Test
    public void testPostRecipe() throws Exception {
        String url = "/groups/%d/recipes";
        RecipeInfo newRecipe = new RecipeInfo().withUUID(mRecipe.getUUID()).withName("recipe3");
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newRecipe));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newRecipe));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newRecipe));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newRecipe));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mRecipe);
        assertEquals("recipe1", mRecipe.getName());

        newRecipe.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newRecipe));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<Recipe> savedRecipeQuery = mManager.createQuery("select r from " +
                "Recipe r where r.group = :group and r.UUID = :uuid", Recipe.class);
        savedRecipeQuery.setParameter("group", mGroup);
        savedRecipeQuery.setParameter("uuid", UUID.fromString(newRecipe.getUUID()));
        List<Recipe> savedRecipes = savedRecipeQuery.getResultList();
        assertEquals(1, savedRecipes.size());
        assertEquals("recipe3", savedRecipes.get(0).getName());
        assertTrue(preInsert.isBefore(savedRecipes.get(0).getUpdated()));
    }

    @Test
    public void testPutRecipe() throws Exception {
        String url = "/groups/%d/recipes/%s";
        Instant preUpdate = mRecipe.getUpdated();
        RecipeInfo updatedList = new RecipeInfo().withDeleted(false).withName("changedrecipe");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().put(Entity.json(updatedList));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedList));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mRecipe);
        assertEquals("recipe1", mRecipe.getName());

        updatedList.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mRecipe);
        assertEquals("recipe1", mRecipe.getName());

        updatedList.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mRecipe);
        assertEquals("changedrecipe", mRecipe.getName());
        assertTrue(preUpdate + " is not before " + mRecipe.getUpdated(),
                preUpdate.isBefore(mRecipe.getUpdated()));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        String url = "/groups/%d/recipes/%s";
        Instant preDelete = mRecipe.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNARecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mRecipe.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<Recipe> savedRecipeQuery = mManager.createQuery("select r from Recipe r where " +
                "r.group = :group and r.UUID = :uuid", Recipe.class);
        savedRecipeQuery.setParameter("group", mGroup);
        savedRecipeQuery.setParameter("uuid", mRecipe.getUUID());
        List<Recipe> savedRecipes = savedRecipeQuery.getResultList();
        assertEquals(0, savedRecipes.size());
        TypedQuery<DeletedObject> savedDeletedUnitQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid and do.type = :type",
                DeletedObject.class);
        savedDeletedUnitQuery.setParameter("group", mGroup);
        savedDeletedUnitQuery.setParameter("uuid", mRecipe.getUUID());
        savedDeletedUnitQuery.setParameter("type", DeletedObject.Type.RECIPE);
        List<DeletedObject> savedDeletedRecipes = savedDeletedUnitQuery.getResultList();
        assertEquals(1, savedDeletedRecipes.size());
        assertTrue(preDelete.isBefore(savedDeletedRecipes.get(0).getUpdated()));
    }
}
