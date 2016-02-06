package org.noorganization.instalist.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.message.AppConfiguration;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonData {

    public String mSecret;
    public String mEncryptedSecret;

    public CommonData() throws IOException {
        mSecret = "justATest";
        mEncryptedSecret = BCrypt.hashpw(mSecret, BCrypt.gensalt(10));

        DatabaseHelper.getInstance().initialize("org.noorganization.instalist.server.test");
    }

    public void flushEntityManager(EntityManager _manager) {
        _manager.getTransaction().begin();
        _manager.flush();
        _manager.getTransaction().commit();
    }
}
