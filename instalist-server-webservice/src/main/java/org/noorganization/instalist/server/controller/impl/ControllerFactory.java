package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.IGroupController;

import javax.persistence.EntityManager;

/**
 * Created by damihe on 05.02.16.
 */
public class ControllerFactory {

    public static IAuthController getAuthController(EntityManager _manager) {
        return new AuthController(_manager);
    }

    public static IGroupController getGroupController(EntityManager _manager) {
        return new GroupController(_manager);
    }

    private ControllerFactory(){
    }
}
