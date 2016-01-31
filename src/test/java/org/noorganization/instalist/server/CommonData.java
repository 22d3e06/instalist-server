package org.noorganization.instalist.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.noorganization.instalist.server.message.AppConfiguration;
import org.noorganization.instalist.server.support.DatabaseHelper;

import java.io.IOException;
import java.sql.Connection;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonData {

    public Connection mDb;

    public static final String sSecret = "justATest";
    public static final String sEncryptedSecret =
            "$2a$10$qzC73lPz3oz3mDvAbesbDeOhrjU1zFov8.fjrDZiyhPHkIl3XdGZW";

    public ObjectMapper mJsonMapper;

    public CommonData() throws IOException {
        mJsonMapper = new ObjectMapper();
        AppConfiguration config = mJsonMapper.readValue(getClass().getClassLoader().
                getResourceAsStream("database/config.json"), AppConfiguration.class);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance();
        dbHelper.initialize(config);

        mDb = dbHelper.getConnection();
    }
}
