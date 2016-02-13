package org.noorganization.instalist.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.*;
import org.noorganization.instalist.server.message.AppConfiguration;
import org.noorganization.instalist.server.support.DatabaseHelper;

import java.io.IOException;

/**
 * Created by damihe on 23.01.16.
 */
public class Application extends ResourceConfig {

    public Application() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance();
        dbHelper.initialize("org.noorganization.instalist.server");

        register(GroupsResource.class);
        register(CategoriesResource.class);
        register(ListResource.class);
        register(UnitResource.class);
        register(ProductResource.class);

        register(AuthenticationFilter.class);
    }
}
