package org.noorganization.instalist.server.support;

import java.sql.Connection;

/**
 * This helper was made for minimizing overhead for authenticating devices against tokens and http-
 * basic-auth. It also preserves the logins in memory, so that they can be easily invalidated by
 * redeploying the application.
 * Created by damihe on 30.01.16.
 */
public class AuthHelper {
    private static AuthHelper sInstance;

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
        return null;
    }

    public static AuthHelper getInstance() {
        if (sInstance == null)
            sInstance = new AuthHelper();
        return sInstance;
    }

    private AuthHelper() {
    }
}
