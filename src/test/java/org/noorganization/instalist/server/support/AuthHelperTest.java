package org.noorganization.instalist.server.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.AppConfiguration;

import javax.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AuthHelperTest extends JerseyTest {

    private AuthHelper mInstance;
    private Connection mConnection;
    private int mDevicegroup;
    private int mDevice1;
    private int mDevice2;

    private static final String sSecret = "justATest";
    private static final String sEncryptedSecret =
            "$2a$10$qzC73lPz3oz3mDvAbesbDeOhrjU1zFov8.fjrDZiyhPHkIl3XdGZW";

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthHelper.class);
    }

    @Before
    public void setUp() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        AppConfiguration config = jsonMapper.readValue(getClass().getClassLoader().
                getResourceAsStream("database/config.json"), AppConfiguration.class);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance();
        dbHelper.initialize(config);

        mConnection = dbHelper.getConnection();
        PreparedStatement preparationStmt1 = mConnection.prepareStatement(
                "INSERT INTO devicegroups (readableid) VALUES ('123456')",
                Statement.RETURN_GENERATED_KEYS);
        preparationStmt1.executeUpdate();
        ResultSet devicegroupRS = preparationStmt1.getGeneratedKeys();
        if(devicegroupRS.next()) {
            mDevicegroup = devicegroupRS.getInt(1);
            PreparedStatement preparationStmt2 = mConnection.prepareStatement("INSERT INTO devices " +
                    "(name, devicegroup_id, autorizedtogroup, secret) VALUES " +
                    "('dev1', ?, TRUE, ?), ('dev2', ?, TRUE, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparationStmt2.setInt(1, mDevicegroup);
            preparationStmt2.setString(2, sEncryptedSecret);
            preparationStmt2.setInt(3, mDevicegroup);
            preparationStmt2.setString(4, sEncryptedSecret);
            preparationStmt2.executeUpdate();
            ResultSet deviceRS = preparationStmt2.getGeneratedKeys();
            if (deviceRS.next()) {
                mDevice1 = deviceRS.getInt(1);
                deviceRS.next();
                mDevice2 = deviceRS.getInt(1);
            }
            deviceRS.close();
            preparationStmt2.close();
        }
        devicegroupRS.close();
        preparationStmt1.close();

        mInstance = AuthHelper.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        PreparedStatement deletionStmt = mConnection.prepareStatement("DELETE FROM devicegroups " +
                "WHERE id = ?");
        deletionStmt.setInt(1, mDevicegroup);
        deletionStmt.executeUpdate();
        deletionStmt.close();
    }

    @Test
    public void testGetTokenByHttpAuth() throws Exception {
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic"));
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice1 + ":")));
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice1 + ":wrongpassword")));
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                "-1:" + sSecret)));

        String token = mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                mDevice1 + ":" + sSecret));
        assertNotNull(token);
        assertTrue(mInstance.mClients.containsKey(token));

        String token2 = mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                mDevice1 + ":" + sSecret));
        assertNotEquals(token, token2);
        assertFalse(mInstance.mClients.containsKey(token));
        assertTrue(mInstance.mClients.containsKey(token2));

        String tokenDev2 = mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice2 + ":" + sSecret));
        assertNotNull(tokenDev2);
        assertTrue(mInstance.mClients.containsKey(token2));
        assertTrue(mInstance.mClients.containsKey(tokenDev2));
        assertNotEquals(token2, tokenDev2);
    }

    /**
     * This test requires that testGetTokenByHttpAuth was successful.
     */
    @Test
    public void testGetDeviceIdByToken() {
        assertTrue(mInstance.getDeviceIdByToken(null) < 0);
        assertTrue(mInstance.getDeviceIdByToken("invalidToken") < 0);

        String validToken1 = mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice1 + ":" + sSecret));
        String validToken2 = mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice2 + ":" + sSecret));
        assertEquals(mDevice1, mInstance.getDeviceIdByToken(validToken1));
        assertEquals(mDevice2, mInstance.getDeviceIdByToken(validToken2));
    }

    /**
     * This test requires that testGetTokenByHttpAuth and testGetDeviceIdByToken was successful.
     */
    @Test
    public void testGetGroupIdByToken() {
        String validToken1 = mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice1 + ":" + sSecret));
        String validToken2 = mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice2 + ":" + sSecret));

        assertTrue(mInstance.getGroupIdByToken(mConnection, "blablahuedude") < 0);

        assertEquals(mDevicegroup, mInstance.getGroupIdByToken(mConnection, validToken1));
        assertEquals(mDevicegroup, mInstance.getGroupIdByToken(mConnection, validToken2));
    }
}
