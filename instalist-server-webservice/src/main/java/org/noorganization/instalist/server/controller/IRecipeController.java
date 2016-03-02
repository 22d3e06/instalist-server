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

package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.generic.IFinder;
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
public interface IRecipeController extends IFinder<Recipe> {
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
}
