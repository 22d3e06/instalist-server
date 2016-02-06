package org.noorganization.instalist.server.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.message.Category;
import org.noorganization.instalist.server.support.DateHelper;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.Date;

import static org.junit.Assert.*;

public class CategoriesResourceTest extends JerseyTest {

    static final String sUrl = "/categories";

    private CommonData mData;
    private int        mGroup;
    private String     mToken;
    private String     mCategoryUUID;
    private String     mDeletedCategoryUUID;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(CategoriesResource.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();

        mData = new CommonData();

//        PreparedStatement groupStmt = mData.mDb.prepareStatement("INSERT INTO devicegroups " +
//                "(readableid) VALUES (NULL)", Statement.RETURN_GENERATED_KEYS);
//        groupStmt.executeUpdate();
//        ResultSet groupRS = groupStmt.getGeneratedKeys();
//        groupRS.first();
//        mGroup = groupRS.getInt(1);
//        groupRS.close();
//        groupStmt.close();
//
//        PreparedStatement deviceStmt = mData.mDb.prepareStatement("INSERT INTO devices (name, " +
//                "autorizedtogroup, secret, devicegroup_id) VALUES ('dev1', TRUE, ?, ?)",
//                Statement.RETURN_GENERATED_KEYS);
//        deviceStmt.setString(1, mData.mEncryptedSecret);
//        deviceStmt.setInt(2, mGroup);
//        deviceStmt.executeUpdate();
//        ResultSet deviceRS = deviceStmt.getGeneratedKeys();
//        deviceRS.first();
//        int deviceId = deviceRS.getInt(1);
//        deviceRS.close();
//        deviceStmt.close();
//
//        mToken = AuthController.getInstance().getTokenByHttpAuth(mData.mDb, "Basic " + Base64.
//                encodeAsString(deviceId + ":" + mData.mSecret));
//        assertNotNull(mToken);
//
//        mCategoryUUID = UUID.randomUUID().toString();
//        PreparedStatement category1Stmt = mData.mDb.prepareStatement("INSERT INTO categories " +
//                "(uuid, name, devicegroup_id) VALUES (?, 'cat1', ?)");
//        category1Stmt.setString(1, mCategoryUUID);
//        category1Stmt.setInt(2, mGroup);
//        assertEquals(1, category1Stmt.executeUpdate());
//        category1Stmt.close();
//
//        mDeletedCategoryUUID = UUID.randomUUID().toString();
//        PreparedStatement category2Stmt = mData.mDb.prepareStatement("INSERT INTO deletion_log " +
//                "(uuid, type, devicegroup_id) VALUES (?, 'category', ?)");
//        category2Stmt.setString(1, mDeletedCategoryUUID);
//        category2Stmt.setInt(2, mGroup);
//        assertEquals(1, category2Stmt.executeUpdate());
//        category2Stmt.close();
    }

    @After
    public void tearDown() throws Exception {
//        PreparedStatement groupCleanUpStmt = mData.mDb.prepareStatement("DELETE FROM devicegroups " +
//                "WHERE id = ?");
//        groupCleanUpStmt.setInt(1, mGroup);
//        groupCleanUpStmt.executeUpdate();
//        groupCleanUpStmt.close();
//        mData.mDb.close();
        super.tearDown();
    }

    @Test
    public void testGetCategories() throws Exception {
        Response wrongTokenResponse = target(sUrl).queryParam("token", "invalidtoken").request()
                .get();
        assertEquals(401, wrongTokenResponse.getStatus());

        Response wrongTimeResponse = target(sUrl).queryParam("token", mToken).
                queryParam("changedsince", "Tue Feb  2 15:59:41 CET 2016").request().get();
        assertEquals(400, wrongTimeResponse.getStatus());

        Response okResponse = target(sUrl).queryParam("token", mToken).request().get();
        assertEquals(200, okResponse.getStatus());
        Category[] allCategories = okResponse.readEntity(Category[].class);
        assertEquals(2, allCategories.length);
        for (int i = 0; i < 2; i++) {
            assertNotNull(allCategories[i].getLastChanged());
            if (mDeletedCategoryUUID.equals(allCategories[i].getUUID())) {
                assertNull(allCategories[i].getName());
                assertTrue(allCategories[i].getDeleted());
            } else if (mCategoryUUID.equals(allCategories[i].getUUID())) {
                assertEquals("cat1", allCategories[i].getName());
                assertFalse(allCategories[i].getDeleted());
            } else {
                fail("Got wrong category.");
            }
        }

        Response okResponseEmpty = target(sUrl).queryParam("token", mToken).
                queryParam("changedsince", DateHelper.writeDate(new Date(
                        System.currentTimeMillis() + 10000))).request().get();
        assertEquals(200, okResponseEmpty.getStatus());
        Category[] noCategories = okResponseEmpty.readEntity(Category[].class);
        assertEquals(0, noCategories.length);

        Response okResponseSpecific = target(sUrl).queryParam("token", mToken).
                queryParam("uuid", mCategoryUUID).request().get();
        assertEquals(200, okResponseSpecific.getStatus());
        Category[] oneCategory = okResponseSpecific.readEntity(Category[].class);
        assertEquals(1, oneCategory.length);
        assertEquals(mCategoryUUID, oneCategory[0].getUUID());
        assertEquals("cat1", oneCategory[0].getName());
        assertFalse(oneCategory[0].getDeleted());
        assertTrue(new Date(System.currentTimeMillis() - 20000).before(DateHelper.parseDate(
                oneCategory[0].getLastChanged())));
    }

    @Ignore("Not implemented yet.")
    @Test
    public void testPutCategoryById() throws Exception {

    }

    @Ignore("Not implemented yet.")
    @Test
    public void testPostCategoryById() throws Exception {

    }

    @Ignore("Not implemented yet.")
    @Test
    public void testDeleteCategoryById() throws Exception {

    }
}