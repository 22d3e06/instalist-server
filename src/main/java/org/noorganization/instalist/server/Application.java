package org.noorganization.instalist.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.noorganization.instalist.server.api.UserResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by damihe on 23.01.16.
 */
public class Application extends ResourceConfig {
    public Application() {
        register(UserResource.class);
        register(HelloWorld.class);
        //        UserResource.class,
        //        HelloWorld.class);
    }
}
