package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.ICategoryController;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;
import org.noorganization.instalist.server.support.exceptions.NotFoundException;

import javax.persistence.EntityManager;
import java.util.UUID;

class CategoryController implements ICategoryController {

    private EntityManager mManager;

    public Category add(int _groupId, UUID _uuid, String _name) throws ConflictException {
        return null;
    }

    public void update(int _id, String _name) throws GoneException, NotFoundException {

    }

    public void delete(int _id) throws GoneException, NotFoundException {

    }

    CategoryController(EntityManager _manager) {
        mManager = _manager;
    }
}
