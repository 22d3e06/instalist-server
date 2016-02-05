package org.noorganization.instalist.server.support;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.CommonData;

import javax.ws.rs.core.Application;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AuthHelperTest extends JerseyTest {

    private AuthHelper mInstance;
    private int mDevicegroup;
    private int mDevice1;
    private int mDevice2;

    private CommonData mData;

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthHelper.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mData = new CommonData();
        
//        PreparedStatement preparationStmt1 = mData.mDb.prepareStatement(
//                "INSERT INTO devicegroups (readableid) VALUES ('123456')",
//                Statement.RETURN_GENERATED_KEYS);
//        preparationStmt1.executeUpdate();
//        ResultSet devicegroupRS = preparationStmt1.getGeneratedKeys();
//        if(devicegroupRS.next()) {
//            mDevicegroup = devicegroupRS.getInt(1);
//            PreparedStatement preparationStmt2 = mData.mDb.prepareStatement("INSERT INTO devices " +
//                    "(name, devicegroup_id, autorizedtogroup, secret) VALUES " +
//                    "('dev1', ?, TRUE, ?), ('dev2', ?, TRUE, ?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            preparationStmt2.setInt(1, mDevicegroup);
//            preparationStmt2.setString(2, mData.mEncryptedSecret);
//            preparationStmt2.setInt(3, mDevicegroup);
//            preparationStmt2.setString(4, mData.mEncryptedSecret);
//            preparationStmt2.executeUpdate();
//            ResultSet deviceRS = preparationStmt2.getGeneratedKeys();
//            if (deviceRS.next()) {
//                mDevice1 = deviceRS.getInt(1);
//                deviceRS.next();
//                mDevice2 = deviceRS.getInt(1);
//            }
//            deviceRS.close();
//            preparationStmt2.close();
//        }
//        devicegroupRS.close();
//        preparationStmt1.close();

        mInstance = AuthHelper.getInstance();
    }

    @After
    public void tearDown() throws Exception {
//        PreparedStatement deletionStmt = mData.mDb.prepareStatement("DELETE FROM devicegroups " +
//                "WHERE id = ?");
//        deletionStmt.setInt(1, mDevicegroup);
//        deletionStmt.executeUpdate();
//        deletionStmt.close();
        super.tearDown();
    }

    @Test
    public void testGetTokenByHttpAuth() throws Exception {
//        assertNull(mInstance.getTokenByHttpAuth(mData.mDb, "Basic"));
//        assertNull(mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice1 + ":")));
//        assertNull(mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice1 + ":wrongpassword")));
//        assertNull(mInstance.getTokenByHttpAuth(mData.mDb, "Basic " + Base64.encodeAsString(
//                "-1:" + mData.mSecret)));
//
//        String token = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " + Base64.encodeAsString(
//                mDevice1 + ":" + mData.mSecret));
//        assertNotNull(token);
//        assertTrue(mInstance.mClients.containsKey(token));
//
//        String token2 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " + Base64.encodeAsString(
//                mDevice1 + ":" + mData.mSecret));
//        assertNotEquals(token, token2);
//        assertFalse(mInstance.mClients.containsKey(token));
//        assertTrue(mInstance.mClients.containsKey(token2));
//
//        String tokenDev2 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice2 + ":" + mData.mSecret));
//        assertNotNull(tokenDev2);
//        assertTrue(mInstance.mClients.containsKey(token2));
//        assertTrue(mInstance.mClients.containsKey(tokenDev2));
//        assertNotEquals(token2, tokenDev2);
    }

    /**
     * This test requires that testGetTokenByHttpAuth was successful.
     */
    @Test
    public void testGetDeviceIdByToken() {
        assertTrue(mInstance.getDeviceIdByToken(null) < 0);
        assertTrue(mInstance.getDeviceIdByToken("invalidToken") < 0);

//        String validToken1 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice1 + ":" + mData.mSecret));
//        String validToken2 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice2 + ":" + mData.mSecret));
//        assertEquals(mDevice1, mInstance.getDeviceIdByToken(validToken1));
//        assertEquals(mDevice2, mInstance.getDeviceIdByToken(validToken2));
    }

    /**
     * This test requires that testGetTokenByHttpAuth and testGetDeviceIdByToken was successful.
     */
    @Test
    public void testGetGroupIdByToken() {
//        String validToken1 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice1 + ":" + mData.mSecret));
//        String validToken2 = mInstance.getTokenByHttpAuth(mData.mDb, "Basic " +
//                Base64.encodeAsString(mDevice2 + ":" + mData.mSecret));
//
//        assertTrue(mInstance.getGroupIdByToken("blablahuedude") < 0);
//
//        assertEquals(mDevicegroup, mInstance.getGroupIdByToken(validToken1));
//        assertEquals(mDevicegroup, mInstance.getGroupIdByToken(validToken2));
    }
}
