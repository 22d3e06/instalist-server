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

import org.noorganization.instalist.server.controller.generic.IFinder;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

/**
 * Controller for managing categories. This is a controller for managing real contents.
 * Created by damihe on 07.02.16.
 */
public interface ICategoryController extends IFinder<Category> {

    /**
     * Adds a new category to the group.
     * @param _groupId The id of the group to add the category
     * @param _uuid The uuid of the category.
     * @param _name The name of the category.
     * @param _added The time of addition.
     * @return The created Category.
     * @throws ConflictException If there is already a category with same uuid for the group.
     */
    Category add(int _groupId, UUID _uuid, String _name, Instant _added) throws ConflictException;

    /**
     * Updates a category.
     * @param _id The id of the group.
     * @param _categoryUUID The uuid of the category to update.
     * @param _name The new name of the category.
     * @param _change The time of change.
     * @throws GoneException If category was deleted before.
     * @throws NotFoundException If category was not found.
     * @throws ConflictException If a change made after this occurred before.
     */
    Category update(int _id, UUID _categoryUUID, String _name, Instant _change)
            throws GoneException, NotFoundException, ConflictException;

    /**
     * Deletes a category.
     * @param _groupId The id of the category to delete.
     * @throws GoneException If already deleted before.
     * @throws NotFoundException If category was not found.
     */
    DeletedObject delete(int _groupId, UUID _categoryUUID) throws ConflictException, GoneException,
            NotFoundException;

}
