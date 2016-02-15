package org.noorganization.instalist.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.*;
import org.noorganization.instalist.server.support.DatabaseHelper;

/**
 * Created by damihe on 23.01.16.
 */
public class Application extends ResourceConfig {

    public Application() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance();
        dbHelper.initialize("org.noorganization.instalist.server");

        register(CategoriesResource.class);
        register(GroupsResource.class);
        register(EntryResource.class);
        register(ProductResource.class);
        register(RecipeResource.class);
        register(ListResource.class);
        register(UnitResource.class);

        register(AuthenticationFilter.class);
    }
}
