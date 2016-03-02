/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.*;
import org.noorganization.instalist.server.model.Tag;
import org.noorganization.instalist.server.model.TaggedProduct;
import org.noorganization.instalist.server.support.DatabaseHelper;

/**
 * The Application defines all available resources and prepares the database connection.
 *
 * This Application is like a main-method.
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
