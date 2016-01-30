package org.noorganization.instalist.server.support;

import org.glassfish.jersey.internal.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 * This helper was made for minimizing overhead for authenticating devices against tokens and http-
 * basic-auth. It also preserves the logins in memory, so that they can be easily invalidated by
 * redeploying the application.
 * Created by damihe on 30.01.16.
 */
public class AuthHelper {
    private static AuthHelper sInstance;

    private SecureRandom mRandom;
    private HashMap<String, AuthInfo> mClients;

    /**
     * Searches a group by authenticated client.
     * @param _db A database-connection to use for the search.
     * @param _token The token identifying the client.
     * @return Either a prositive group id or a negative number, if no group was found for the
     * token.
     */
    public int getGroupIdByToken(Connection _db, String _token) {
        return -1;
    }

    /**
     * Searches the device-id for the given token.
     * @param _token The token identifying the client.
     * @return Either a positive device id or a negative number, if no authentication was found.
     */
    public int getDeviceIdByToken(String _token) {
        return -1;
    }

    /**
     * Generates a token if authentication data is correct.
     * @param _db A database connection for checking authentication data.
     * @param _authHeader The String from the HTTP-header. Looks like
     *                    "Basic 76fhAdskjDSFsfgjsdDE9Rd=="
     * @return Either a token-string or null if authentication data was incorrect.
     */
    public String getTokenByHttpAuth(Connection _db, String _authHeader) {
        if (!_authHeader.matches("Basic\\s+[^\\s]+"))
            return null;
        String authInfo = _authHeader.replaceFirst("Basic\\s+", "");
        authInfo = Base64.decodeAsString(authInfo);

        int colonPos = authInfo.indexOf(":");
        if (colonPos < 1)
            return null;
        int deviceId;
        try {
            deviceId = Integer.parseInt(authInfo.substring(0, colonPos));
        } catch (NumberFormatException e) {
            return null;
        }
        String secret = authInfo.substring(colonPos + 1);
        if (secret.length() == 0)
            return null;

        PreparedStatement deviceInfo = null;
        try {
            deviceInfo = _db.prepareStatement("SELECT autorizedtogroup, secret FROM " +
                    "devices WHERE id = ?");
            deviceInfo.setInt(1, deviceId);
            ResultSet deviceResult = deviceInfo.executeQuery();
            if (deviceResult.next()) {
                String savedSecret = deviceResult.getString("secret");
                boolean isAuthorized = deviceResult.getBoolean("autorizedtogroup");
                deviceResult.close();

                if (BCrypt.checkpw(secret, savedSecret)){
                    for (String alreadyGeneratedToken : mClients.keySet()) {
                        if (mClients.get(alreadyGeneratedToken).deviceId == deviceId) {
                            mClients.remove(alreadyGeneratedToken);
                            break;
                        }
                    }

                    String token = generateToken();
                    AuthInfo authenticated = new AuthInfo();
                    authenticated.deviceId = deviceId;
                    authenticated.authorized = isAuthorized;
                    authenticated.authenticated = new Date();
                    mClients.put(token, authenticated);
                    return token;
                } else
                    return null;
            } else {
                deviceResult.close();
                return null;
            }
        } catch (SQLException _e) {
            _e.printStackTrace();
        } finally {
            if (deviceInfo != null) {
                try {
                    deviceInfo.close();
                } catch (SQLException _e) {}
            }
        }
        return null;
    }

    public static AuthHelper getInstance() {
        if (sInstance == null)
            sInstance = new AuthHelper();
        return sInstance;
    }

    private AuthHelper() {
        mRandom = new SecureRandom();
        mClients = new HashMap<String, AuthInfo>();
    }

    private String generateToken() {
        String foundToken = random32();
        while (mClients.containsKey(foundToken))
            foundToken = random32();
        return  foundToken;
    }

    private String random32() {
        byte randomData[] = new byte[16];
        mRandom.nextBytes(randomData);
        StringBuilder rtn = new StringBuilder(32);
        for (byte currentByte : randomData)
            rtn.append(String.format("%02x", currentByte & 0xff));
        return rtn.toString();
    }

    private static class AuthInfo {
        public int     deviceId;
        public boolean authorized;
        public Date    authenticated;
    }
}
