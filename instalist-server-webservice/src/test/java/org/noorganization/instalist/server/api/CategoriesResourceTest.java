package org.noorganization.instalist.server.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.AuthenticationFilter;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.comm.message.CategoryInfo;
import org.noorganization.instalist.comm.support.DateHelper;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
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

public class CategoriesResourceTest extends JerseyTest {

    private CommonData    mData;
    private EntityManager mManager;
    private DeviceGroup   mGroup;
    private DeviceGroup   mNotAccessibleGroup;
    private String        mToken;
    private Category      mCategory;
    private Category      mNotAccessibleCategory;
    private DeletedObject mDeletedCategory;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rtn = new ResourceConfig(CategoriesResource.class);
        rtn.register(AuthenticationFilter.class);
        return rtn;
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();

        mData = new CommonData();

        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();
        mGroup = new DeviceGroup();
        Date now = new Date(System.currentTimeMillis());
        Device aDev = new Device().withAuthorized(true).withGroup(mGroup).
                withSecret(mData.mEncryptedSecret).withName("testDev");
        mCategory = new Category().withGroup(mGroup).withName("cat1").withUUID(UUID.randomUUID()).
                withUpdated(now);
        mDeletedCategory = new DeletedObject().withGroup(mGroup).
                withType(DeletedObject.Type.CATEGORY).withUUID(UUID.randomUUID()).withTime(now);
        mNotAccessibleGroup = new DeviceGroup().withUpdated(now);
        mNotAccessibleCategory = new Category().withGroup(mNotAccessibleGroup).withName("cat2").
                withUUID(UUID.randomUUID()).withUpdated(now);

        mManager.persist(mGroup);
        mManager.persist(aDev);
        mManager.persist(mCategory);
        mManager.persist(mDeletedCategory);
        mManager.persist(mNotAccessibleGroup);
        mManager.persist(mNotAccessibleCategory);
        mManager.flush();
        mManager.getTransaction().commit();
        mManager.refresh(mGroup);
        mManager.refresh(aDev);
        mManager.refresh(mCategory);
        mManager.refresh(mDeletedCategory);
        mManager.refresh(mNotAccessibleGroup);
        mManager.refresh(mNotAccessibleCategory);

        mToken = ControllerFactory.getAuthController().getTokenByHttpAuth(mManager, aDev.getId(),
                mData .mSecret);
        assertNotNull(mToken);
    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void testGetCategories() throws Exception {
        final String url = "/groups/%d/categories";
        Response wrongTokenResponse = target(String.format(url, mGroup.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token wrongToken").get();
        assertEquals(401, wrongTokenResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNotAccessibleGroup.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongTimeResponse = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", "So 7. Feb 18:26:52 CET 2016").
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(400, wrongTimeResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse.getStatus());
        CategoryInfo[] allCategories = okResponse.readEntity(CategoryInfo[].class);
        assertEquals(2, allCategories.length);
        Date shortTimeAgo = new Date(System.currentTimeMillis() - 10000);
        for (int i = 0; i < 2; i++) {
            assertNotNull(allCategories[i].getLastChanged());
            if (mDeletedCategory.getUUID().equals(UUID.fromString(allCategories[i].getUUID()))) {
                assertNull(allCategories[i].getName());
                assertTrue(allCategories[i].getDeleted());
            } else if (mCategory.getUUID().equals(UUID.fromString(allCategories[i].getUUID()))) {
                assertEquals("cat1", allCategories[i].getName());
                assertFalse(allCategories[i].getDeleted());
            } else {
                fail("Got wrong category.");
            }
            Date changeDate = DateHelper.parseDate(allCategories[i].getLastChanged());
            assertNotNull(changeDate);
            assertTrue(shortTimeAgo.before(changeDate));
        }

        Response okResponseEmpty = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", DateHelper.writeDate(new Date(
                        System.currentTimeMillis() + 10000))).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponseEmpty.getStatus());
        CategoryInfo[] noCategories = okResponseEmpty.readEntity(CategoryInfo[].class);
        assertEquals(0, noCategories.length);
    }

    @Test
    public void testPutCategory() throws Exception {
        final String url = "/groups/%d/categories/%s";
        Response wrongTokenResponse = target(String.format(url, mGroup.getId(), mCategory.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token wrongToken").
                put(Entity.json(new CategoryInfo().withName("dev111")));
        assertEquals(401, wrongTokenResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNotAccessibleGroup.getId(),
                mCategory.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(new CategoryInfo().withName("dev111")));;
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongCatResponse = target(String.format(url, mGroup.getId(),
                mNotAccessibleCategory.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(new CategoryInfo().withName("dev111")));
        assertEquals(404, wrongCatResponse.getStatus());

        Response invalidCatResponse = target(String.format(url, mGroup.getId(), mCategory.
                getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(new CategoryInfo().withUUID(mNotAccessibleCategory.getUUID().toString()).
                        withName("dev111")));
        assertEquals(400, invalidCatResponse.getStatus());
        mManager.refresh(mNotAccessibleCategory);
        mManager.refresh(mCategory);
        assertEquals("cat1", mCategory.getName());
        assertEquals("cat2", mNotAccessibleCategory.getName());

        Date beforeChange = new Date(System.currentTimeMillis());
        Thread.sleep(1000);
        Response validCatResponse = target(String.format(url, mGroup.getId(), mCategory.getUUID().
                toString())).request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(new CategoryInfo().withName("dev111")));
        assertEquals(200, validCatResponse.getStatus());
        mManager.refresh(mCategory);
        assertEquals("dev111", mCategory.getName());
        assertTrue(beforeChange.before(mCategory.getUpdated()));

        Response conflictCatResponse = target(String.format(url, mGroup.getId(), mCategory.
                getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(new CategoryInfo().withName("cat1").
                        withLastChanged(beforeChange)));
        assertEquals(409, conflictCatResponse.getStatus());
        mManager.refresh(mCategory);
        assertEquals("dev111", mCategory.getName());
        assertTrue(beforeChange.before(mCategory.getUpdated()));
    }

    @Test
    public void testPostCategory() throws Exception {
        final String url = "/groups/%d/categories";

        UUID uuid = UUID.randomUUID();
        Response wrongTokenResponse = target(String.format(url, mGroup.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token wrongToken").
                post(Entity.json(new CategoryInfo().withUUID(uuid).withName("cat3")));
        assertEquals(401, wrongTokenResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNotAccessibleGroup.getId())).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                post(Entity.json(new CategoryInfo().withUUID(uuid).withName("cat3")));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response invalidCatResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                post(Entity.json(new CategoryInfo().withUUID(uuid)));
        assertEquals(400, invalidCatResponse.getStatus());

        Response conflictCatResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                post(Entity.json(new CategoryInfo().withUUID(mCategory.getUUID()).
                        withName("cat3")));
        assertEquals(409, conflictCatResponse.getStatus());

        mManager.refresh(mCategory);
        assertEquals("cat1", mCategory.getName());

        Response validCatResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                post(Entity.json(new CategoryInfo().withUUID(uuid).withName("cat3")));
        assertEquals(201, validCatResponse.getStatus());
        TypedQuery<Category> savedCatQuery = mManager.createQuery("select c from Category c where " +
                "c.UUID = :uuid and c.group = :groupid", Category.class);
        savedCatQuery.setParameter("uuid", uuid);
        savedCatQuery.setParameter("groupid", mGroup);
        List<Category> savedCats = savedCatQuery.getResultList();
        assertEquals(1, savedCats.size());
        assertEquals("cat3", savedCats.get(0).getName());
    }

    @Test
    public void testDeleteCategoryById() throws Exception {
        final String url = "/groups/%d/categories/%s";
        Response wrongTokenResponse = target(String.format(url, mGroup.getId(),
                mCategory.getUUID())).request().header(HttpHeaders.AUTHORIZATION, "X-Token " +
                "wrongToken").delete();
        assertEquals(401, wrongTokenResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNotAccessibleGroup.getId(),
                mCategory.getUUID())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongCatResponse = target(String.format(url, mGroup.getId(),
                mNotAccessibleCategory.getUUID())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongCatResponse.getStatus());
        mManager.clear();
        assertNotNull(mManager.find(Category.class, mNotAccessibleCategory.getId()));

        Response goneCatResponse = target(String.format(url, mGroup.getId(),
                mDeletedCategory.getUUID())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(410, goneCatResponse.getStatus());

        Response validCatResponse = target(String.format(url, mGroup.getId(), mCategory.getUUID()))
                .request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, validCatResponse.getStatus());
        mManager.clear();
        assertNull(mManager.find(Category.class, mCategory.getId()));
        TypedQuery<DeletedObject> deletedCat1Query = mManager.createQuery("select do from " +
                "DeletedObject do where do.UUID = :uuid and do.group = :groupid and " +
                "do.type = :type", DeletedObject.class);
        deletedCat1Query.setParameter("uuid", mCategory.getUUID());
        deletedCat1Query.setParameter("groupid", mGroup);
        deletedCat1Query.setParameter("type", DeletedObject.Type.CATEGORY);
        List<DeletedObject> deletedCat1 = deletedCat1Query.getResultList();
        assertEquals(1, deletedCat1.size());
    }
}