package org.noorganization.instalist.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.*;
import org.noorganization.instalist.server.model.Tag;
import org.noorganization.instalist.server.model.TaggedProduct;
import org.noorganization.instalist.server.support.DatabaseHelper;

/**
 * The Application defines all available resources and prepares the database connection.
 */
public class Application extends ResourceConfig {

    public Application() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance();
        dbHelper.initialize("org.noorganization.instalist.server");

        register(CategoriesResource.class);
        register(EntryResource.class);
        register(GroupsResource.class);
        register(IngredientResource.class);
        register(ListResource.class);
        register(ProductResource.class);
        register(RecipeResource.class);
        register(Tag.class);
        register(TaggedProduct.class);
        register(UnitResource.class);

        register(AuthenticationFilter.class);
    }
}
