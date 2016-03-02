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
import org.noorganization.instalist.server.model.ShoppingList;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing lists.
 * Created by damihe on 09.02.16.
 */
public interface IListController extends IFinder<ShoppingList> {
    /**
     * Creates a list.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list identifying it in the group.
     * @param _name The name of the list.
     * @param _category The (optional) category of the list. May be null.
     * @param _lastChanged A change date.
     * @throws ConflictException If already a list with same uuid exists or the list was already
     * deleted.
     */
    void add(int _groupId, UUID _listUUID, String _name, UUID _category, Instant _lastChanged)
            throws ConflictException;

    /**
     * Updates a list.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list identifying it in the group.
     * @param _name The name of the list.
     * @param _category The (optional) category of the list. May be null.
     * @param _lastChanged A change date.
     * @throws ConflictException If a change was made before.
     * @throws GoneException If list was deleted before.
     * @throws NotFoundException If list was not found.
     */
    void update(int _groupId, UUID _listUUID, String _name, UUID _category,
                boolean _removeCategory, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException;

    /**
     * Deletes a list and all entries.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list identifying it in the group.
     * @throws GoneException If list was deleted before.
     * @throws NotFoundException If list was not found.
     */
    void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException;
}
