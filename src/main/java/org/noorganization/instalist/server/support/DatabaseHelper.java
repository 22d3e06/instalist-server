package org.noorganization.instalist.server.support;

import org.mariadb.jdbc.Driver;
import org.noorganization.instalist.server.model.AppConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 *
 * Created by damihe on 30.01.16.
 */
public class DatabaseHelper {
    private static DatabaseHelper   sInstance;
    private        AppConfiguration mConfig;

    /**
     * Retrieves a database-connection. The DatabaseHelper must get initialized once before this call works.
     * @return Either the connection or null, if connecting failed.
     */
    public Connection getConnection() {
        if (mConfig == null)
            throw new IllegalStateException("DatabaseHelper was not initialized properly.");
        try {
            Connection rtn = DriverManager.getConnection(mConfig.mDatabaseUrl);
            return rtn;
        } catch (SQLException e) {
            System.err.println("Could not connect to Database. Trace:");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Initializes the DatabaseHelper, including checking and upgrading of the internal structures.
     * @param _config The configuration-information about how connecting to database and which version is the current.
     */
    public void initialize(AppConfiguration _config) {
        if (_config.mDatabaseVersion < 1 || _config.mDatabaseUrl == null) {
            System.err.println("Application configuration is incomplete. DatabaseHelper needs a database-version and " +
                    "-connection URI");
            return;
        }

        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            System.err.println("Registering MariaDB-Driver failed. This may be harmless if it gets autoloaded by the " +
                    "container. Exception trace:");
            e.printStackTrace();
        }

        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(_config.mDatabaseUrl);
            int dbVersion = getDatabaseVersion(dbConnection);
            if (dbVersion == 0) {
                createDatabaseStructures(dbConnection);
            } else if (dbVersion < _config.mDatabaseVersion) {
                upgradeDatabaseStructures(dbConnection);
            } else if (dbVersion > _config.mDatabaseVersion) {
                System.err.println("The Database is too new for this Application. The Application will not work.");
                return;
            }
        } catch (SQLException _e) {
            System.err.println("Connection to database was not possible. Exception trace:");
            _e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (Exception e) {
                    // do nothing here. If closing fails there is something really broken.
                }
            }
        }

        mConfig = _config;
    }

    public static DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper();
        }
        return sInstance;
    }

    private DatabaseHelper() {
    }

    private void createDatabaseStructures(Connection _db) throws SQLException {
        Statement creationStmt = _db.createStatement();

        BufferedReader sqlFileReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().
                getResourceAsStream("database/creation.sql")));
        try {
            String sqlLine;
            StringBuilder sqlStmtBuilder = new StringBuilder();
            while ((sqlLine = sqlFileReader.readLine()) != null) {
                if (sqlLine.isEmpty() || sqlLine.startsWith("-- "))
                    continue;
                if (sqlLine.contains(";")) {
                    sqlStmtBuilder.append(sqlLine.subSequence(0, sqlLine.indexOf(";")));
                    creationStmt.addBatch(sqlStmtBuilder.toString());
                    sqlStmtBuilder.setLength(0);
                } else
                    sqlStmtBuilder.append(sqlLine);
            }
            creationStmt.executeBatch();
        } catch (IOException e) {
            System.err.println("Exception caught when reading adding the initial SQL to database.");
            e.printStackTrace();
        } finally {
            creationStmt.close();
        }
    }

    private void upgradeDatabaseStructures(Connection _db) {
        // reserved for future use.
    }

    private int getDatabaseVersion(Connection _db) throws SQLException {
        int rtn = 0;
        Statement dbVersionCheckStmt = _db.createStatement();
        try {
            ResultSet dbVersionResult = dbVersionCheckStmt.executeQuery(
                    "SELECT value_int FROM application_preferences WHERE preference = 'dbversion'");
            if (dbVersionResult.next()) {
                rtn = dbVersionResult.getInt("value_int");
            }
            dbVersionResult.close();
        } catch (SQLSyntaxErrorException e) {
            rtn = 0;
        } finally {
            dbVersionCheckStmt.close();
        }
        return rtn;
    }
}
