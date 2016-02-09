package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public static IListController getListController(EntityManager _manager) {
        return new ListController(_manager);
    }

    public static IEntryController getEntryController(EntityManager _manager) {
        return new EntryController(_manager);
    }

    private ControllerFactory(){
    }
}
