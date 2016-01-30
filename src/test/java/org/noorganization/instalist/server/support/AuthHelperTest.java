package org.noorganization.instalist.server.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.AppConfiguration;
import org.noorganization.instalist.server.support.AuthHelper;
import org.noorganization.instalist.server.support.DatabaseHelper;

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
    private int mDevice;

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
                    "('dev', ?, TRUE, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparationStmt2.setInt(1, mDevicegroup);
            preparationStmt2.setString(2, sEncryptedSecret);
            preparationStmt2.executeUpdate();
            ResultSet deviceRS = preparationStmt2.getGeneratedKeys();
            if (deviceRS.next())
                mDevice = deviceRS.getInt(1);
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
                Base64.encodeAsString(mDevice + ":")));
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic " +
                Base64.encodeAsString(mDevice + ":wrongpassword")));
        assertNull(mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                "-1:" + sSecret)));

        String token = mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                mDevice + ":" + sSecret));
        assertNotNull(token);

        String token2 = mInstance.getTokenByHttpAuth(mConnection, "Basic " + Base64.encodeAsString(
                mDevice + ":" + sSecret));
        assertNotEquals(token, token2);
    }
}
