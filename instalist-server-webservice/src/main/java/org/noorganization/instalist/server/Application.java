package org.noorganization.instalist.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.CategoriesResource;
import org.noorganization.instalist.server.api.UserResource;
import org.noorganization.instalist.server.message.AppConfiguration;
import org.noorganization.instalist.server.support.DatabaseHelper;

import java.io.IOException;

/**
 * Created by damihe on 23.01.16.
 */
public class Application extends ResourceConfig {

    public Application() {
        register(UserResource.class);
        register(CategoriesResource.class);

        ObjectMapper jsonMapper = new ObjectMapper();

        try {
            AppConfiguration config = jsonMapper.readValue(getClassLoader().getResourceAsStream("database/config.json"), AppConfiguration.class);
            DatabaseHelper dbHelper = DatabaseHelper.getInstance();
            dbHelper.initialize(config);
        } catch (IOException e) {
            System.err.println("Database config-file could not be read. Exception-trace:");
            e.printStackTrace();
        }
    }
}
