package org.noorganization.instalist.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.CategoriesResource;
import org.noorganization.instalist.server.api.GroupsResource;
import org.noorganization.instalist.server.api.ListResource;
import org.noorganization.instalist.server.api.UnitResource;
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

        register(AuthenticationFilter.class);
    }
}
