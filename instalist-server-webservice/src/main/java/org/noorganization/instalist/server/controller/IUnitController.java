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
import org.noorganization.instalist.server.model.Unit;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

/**
 * This controller is written for making all write operations on Units. Additional it should make
 * finding Units easier.
 */
public interface IUnitController extends IFinder<Unit> {

    /**
     * Create a new Unit.
     * @param _groupId The id of the group that should contains the new unit.
     * @param _newUUID Unit's new UUID.
     * @param _name Unit's new name.
     * @param _created Unit's creation date.
     * @throws ConflictException If a Unit with this UUID already exists or was deleted after
     * {@code _created}.
     */
    void add(int _groupId, UUID _newUUID, String _name, Instant _created) throws ConflictException;

    /**
     * Updates a unit.
     * @param _groupId The id of the group that should contains the new unit.
     * @param _uuid Existing Unit's UUID.
     * @param _name Unit's new name.
     * @param _updated Update time of Unit for determining conflicts.
     * @throws ConflictException If the Unit was modified after {@code _updated}
     * @throws NotFoundException If the Unit was not found.
     * @throws GoneException If the Unit was not found because it has been deleted before.
     */
    void update(int _groupId, UUID _uuid, String _name, Instant _updated) throws ConflictException,
            NotFoundException, GoneException;

    /**
     * Deletes a unit and unlinks it from all
     * {@link org.noorganization.instalist.server.model.Product}'s.
     * @param _groupId The id of the group that should contains the new unit.
     * @param _uuid Existing Unit's UUID.
     * @throws NotFoundException If the Unit was not found.
     * @throws GoneException If the Unit was not found because it has been deleted before.
     */
    void delete(int _groupId, UUID _uuid) throws NotFoundException, GoneException;
}
