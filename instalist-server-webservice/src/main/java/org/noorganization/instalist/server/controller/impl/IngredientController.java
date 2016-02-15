package org.noorganization.instalist.server.controller.impl;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

public class IngredientController implements IIngredientController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
                    float _amount, Instant _lastChanged)
            throws ConflictException, BadRequestException {

    }

    public void update(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
                       float _amount, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException {

    }

    public void delete(int _groupId, UUID _ingredientUUID) throws GoneException, NotFoundException {

    }

    public Ingredient getIngredientByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        return null;
    }

    public DeletedObject getDeletedIngredientByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        return null;
    }

    IngredientController(EntityManager _manager) {
        mManager = _manager;
    }
}
