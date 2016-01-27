package org.noorganization.instalist.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instantListApi.resource.impl.CategoriesResource;

/**
 * Created by damihe on 23.01.16.
 */
public class Application extends ResourceConfig {
    public Application() {
        registerClasses(CategoriesResource.class, HelloWorld.class);
    }
}
