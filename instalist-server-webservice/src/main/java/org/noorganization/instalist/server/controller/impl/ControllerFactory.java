package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.controller.IGroupController;

import javax.persistence.EntityManager;

/**
 * This Factory points to always up-to-date controllers (for decoupling).
 * Created by damihe on 05.02.16.
 */
public class ControllerFactory {

    public static IAuthController getAuthController() {
        return new AuthController();
    }

    public static ICategoryController getCategoryController(EntityManager _manager) {
        return new CategoryController(_manager);
    }

    public static IGroupController getGroupController(EntityManager _manager) {
        return new GroupController(_manager);
    }

    private ControllerFactory(){
    }
}
