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
import org.noorganization.instalist.comm.message.TagInfo;
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

public class TagResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    Tag mTag;
    Tag mNATag;
    DeletedObject mDeletedTag;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(TagResource.class);
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
        mTag = new Tag().withGroup(mGroup).withName("tag1").withUUID(UUID.randomUUID()).
                withUpdated(creation);

        mDeletedTag = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.TAG).withUpdated(creation);
        mNAGroup = new DeviceGroup();
        mNATag = new Tag().withGroup(mNAGroup).withName("tag2").withUUID(UUID.randomUUID());

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mTag);
        mManager.persist(mDeletedTag);
        mManager.persist(mNAGroup);
        mManager.persist(mNATag);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mTag);
        mManager.refresh(mDeletedTag);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNATag);
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
    public void testGetTags() throws Exception {
        String url = "/groups/%d/tags";

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
        TagInfo[] allTagInfo = okResponse1.readEntity(TagInfo[].class);
        assertEquals(2, allTagInfo.length);
        for(TagInfo current: allTagInfo) {
            if (mTag.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("tag1", current.getName());
                assertEquals(mTag.getUpdated(), current.getLastChanged().toInstant());
                assertFalse(current.getDeleted());
            } else if (mDeletedTag.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getName());
                assertEquals(mDeletedTag.getUpdated(), current.getLastChanged().toInstant());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected tag.");
        }

        Thread.sleep(200);
        mManager.getTransaction().begin();
        mTag.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(new Date(System.
                        currentTimeMillis() - 100), true)).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        TagInfo[] oneTagInfo = okResponse2.readEntity(TagInfo[].class);
        assertEquals(1, oneTagInfo.length);
        assertEquals(mTag.getUUID(), UUID.fromString(oneTagInfo[0].getUUID()));
        assertEquals("tag1", oneTagInfo[0].getName());
        assertFalse(oneTagInfo[0].getDeleted());
    }

    @Test
    public void testGetTag() throws Exception {
        String url = "/groups/%d/tags/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        TagInfo returnedTagInfo = okResponse1.readEntity(TagInfo.class);
        assertNotNull(returnedTagInfo);
        assertEquals(mTag.getUUID(), UUID.fromString(returnedTagInfo.getUUID()));
        assertEquals("tag1", returnedTagInfo.getName());
        assertEquals(mTag.getUpdated(), returnedTagInfo.getLastChanged().toInstant());
        assertFalse(returnedTagInfo.getDeleted());
    }

    @Test
    public void testPostTag() throws Exception {
        String url = "/groups/%d/tags";
        TagInfo newTag = new TagInfo().withUUID(mTag.getUUID()).withName("tag3");
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newTag));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newTag));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTag));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTag));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mTag);
        assertEquals("tag1", mTag.getName());

        newTag.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newTag));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<Tag> savedTagQuery = mManager.createQuery("select t from Tag t where " +
                "t.group = :group and t.UUID = :uuid", Tag.class);
        savedTagQuery.setParameter("group", mGroup);
        savedTagQuery.setParameter("uuid", UUID.fromString(newTag.getUUID()));
        List<Tag> savedTags = savedTagQuery.getResultList();
        assertEquals(1, savedTags.size());
        assertEquals("tag3", savedTags.get(0).getName());
        assertTrue(preInsert.isBefore(savedTags.get(0).getUpdated()));
    }

    @Test
    public void testPutTag() throws Exception {
        String url = "/groups/%d/tags/%s";
        Instant preUpdate = mTag.getUpdated();
        TagInfo updatedList = new TagInfo().withDeleted(false).withName("changedtag");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().put(Entity.json(updatedList));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedList));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mTag);
        assertEquals("tag1", mTag.getName());

        updatedList.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mTag);
        assertEquals("tag1", mTag.getName());

        updatedList.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedList));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mTag);
        assertEquals("changedtag", mTag.getName());
        assertTrue(preUpdate + " is not before " + mTag.getUpdated(),
                preUpdate.isBefore(mTag.getUpdated()));
    }

    @Test
    public void testDeleteTag() throws Exception {
        String url = "/groups/%d/tags/%s";
        Instant preDelete = mTag.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNATag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mTag.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<Tag> savedTagQuery = mManager.createQuery("select t from Tag t where " +
                "t.group = :group and t.UUID = :uuid", Tag.class);
        savedTagQuery.setParameter("group", mGroup);
        savedTagQuery.setParameter("uuid", mTag.getUUID());
        List<Tag> savedTag = savedTagQuery.getResultList();
        assertEquals(0, savedTag.size());
        TypedQuery<DeletedObject> savedDeletedTagQuery = mManager.createQuery("select do from " +
                "DeletedObject do where do.group = :group and do.UUID = :uuid and do.type = :type",
                DeletedObject.class);
        savedDeletedTagQuery.setParameter("group", mGroup);
        savedDeletedTagQuery.setParameter("uuid", mTag.getUUID());
        savedDeletedTagQuery.setParameter("type", DeletedObject.Type.TAG);
        List<DeletedObject> savedDeletedTags = savedDeletedTagQuery.getResultList();
        assertEquals(1, savedDeletedTags.size());
        assertTrue(preDelete.isBefore(savedDeletedTags.get(0).getUpdated()));
    }
}
