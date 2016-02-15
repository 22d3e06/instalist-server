package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Recipe;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing recipes.
 */
public interface IRecipeController {
    /**
     * Creates a recipe.
     * @param _groupId The id of the group that should contain the recipe.
     * @param _recipeUUID The uuid of the recipe identifying it in the group.
     * @param _name The product of the recipe.
     * @param _lastChanged A change date.
     * @throws ConflictException If already a recipe with same uuid exists or the recipe was already
     * deleted after {@code _lastChanged}.
     */
    void add(int _groupId, UUID _recipeUUID, String _name, Instant _lastChanged)
            throws ConflictException;

    /**
     * Updates a recipe.
     * @param _groupId The id of the group containing the entry.
     * @param _recipeUUID The uuid of the recipe identifying it in the group.
     * @param _name The name of the recipe. May be null for no change.
     * @param _lastChanged A change date.
     * @throws ConflictException If a change was made before.
     * @throws GoneException If recipe was deleted before.
     * @throws NotFoundException If recipe was not found.
     */
    void update(int _groupId, UUID _recipeUUID, String _name, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException;

    /**
     * Deletes a recipe.
     * @param _groupId The id of the group containing the recipe.
     * @param _recipeUUID The uuid of the recipe identifying it in the group.
     * @throws GoneException If recipe was deleted before.
     * @throws NotFoundException If recipe was not found.
     */
    void delete(int _groupId, UUID _recipeUUID) throws GoneException, NotFoundException;

    /**
     * Finds a recipe.
     * @param _group The group containing the recipe.
     * @param _uuid The recipe's existing UUID.
     * @return Either the found Recipe or null, if not found.
     */
    Recipe getRecipeByGroupAndUUID(DeviceGroup _group, UUID _uuid);

    /**
     * Finds a deleted recipe.
     * @param _group The group containing the deleted recipe.
     * @param _uuid The recipe's old UUID.
     * @return Either the found recipe as DeletedObject or null, if not found.
     */
    DeletedObject getDeletedRecipeByGroupAndUUID(DeviceGroup _group, UUID _uuid);
}
