package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.generic.IFinder;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.model.Recipe;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing recipes.
 */
public interface IIngredientController extends IFinder<Ingredient> {
    /**
     * Creates a ingredient.
     * @param _groupId The id of the group that should contain the ingredient.
     * @param _ingredientUUID The uuid of the ingredient identifying it in the group.
     * @param _recipeUUID The uuid of the recipe with contains the ingredient.
     * @param _productUUID The uuid of the product with is contained by the recipe.
     * @param _amount The amount of {@code _productUUID}.
     * @param _lastChanged A change date.
     * @throws ConflictException If already a ingredient with same uuid exists or it was already
     * deleted after {@code _lastChanged}.
     * @throws BadRequestException If either {@code _recipeUUID} or {@code _productUUID} could
     * not be resolved.
     */
    void add(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
             float _amount, Instant _lastChanged) throws ConflictException, BadRequestException;

    /**
     * Updates a ingredient.
     * @param _groupId The id of the group that contains the ingredient.
     * @param _ingredientUUID The uuid of the ingredient identifying it in the group.
     * @param _recipeUUID The uuid of the recipe with contains the ingredient.
     * @param _productUUID The uuid of the product with is contained by the recipe.
     * @param _amount The amount of {@code _productUUID}.
     * @param _lastChanged A change date.
     * @throws ConflictException If a change was made before.
     * @throws GoneException If recipe was deleted before.
     * @throws NotFoundException If recipe was not found.
     * @throws BadRequestException If either {@code _recipeUUID} or {@code _productUUID} could
     * not be resolved.
     */
    void update(int _groupId, UUID _ingredientUUID, UUID _recipeUUID, UUID _productUUID,
                Float _amount, Instant _lastChanged) throws ConflictException, GoneException,
            NotFoundException, BadRequestException;

    /**
     * Deletes a recipe.
     * @param _groupId The id of the group containing the ingredient.
     * @param _ingredientUUID The uuid of the ingredient identifying it in the group.
     * @throws GoneException If ingredient was deleted before.
     * @throws NotFoundException If ingredient was not found.
     */
    void delete(int _groupId, UUID _ingredientUUID) throws GoneException, NotFoundException;
}
