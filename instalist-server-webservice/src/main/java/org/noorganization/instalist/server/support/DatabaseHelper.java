package org.noorganization.instalist.server.support;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * This Helper holds an EntityManagerFactory and allows to generate new EntityManager's
 * Created by damihe on 30.01.16.
 */
public class DatabaseHelper {
    private static DatabaseHelper sInstance;
    private EntityManagerFactory  mFactory;

    /**
     * Retrieves a database-connection. The DatabaseHelper must get initialized once before this
     * call works.
     * @return Either the connection or null, if connecting failed.
     * @throws IllegalStateException If initialize was not called properly before.
     */
    public EntityManager getManager() {
        if (mFactory == null)
            throw new IllegalStateException("DatabaseHelper was not initialized properly.");
        EntityManager rtn = mFactory.createEntityManager();
        rtn.setFlushMode(FlushModeType.COMMIT);
        return rtn;
    }

    /**
     * Initializes the DatabaseHelper.
     * @param _jpaInstance The JPA-Persistence-Instance to use as Database-Connection.
     */
    public void initialize(String _jpaInstance) {
        if (mFactory != null)
            mFactory.close();
        mFactory = Persistence.createEntityManagerFactory(_jpaInstance);
    }

    public static DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper();
        }
        return sInstance;
    }

    private DatabaseHelper() {
    }
}
