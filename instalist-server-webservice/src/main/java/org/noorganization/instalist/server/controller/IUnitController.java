package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Unit;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * This controller is written for making all write operations on Units. Additional it should make
 * finding Units easier.
 */
public interface IUnitController {

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

    /**
     * Finds a Unit.
     * @param _group The group containing the Unit.
     * @param _uuid The Unit's existing UUID.
     * @return Either the found Unit or null, if not found.
     */
    Unit getUnitByGroupAndUUID(DeviceGroup _group, UUID _uuid);

    /**
     * Finds a deleted Unit.
     * @param _group The group containing the Unit.
     * @param _uuid The Unit's old UUID.
     * @return Either the found Unit as DeletedObject or null, if not found.
     */
    DeletedObject getDeletedUnitByGroupAndUUID(DeviceGroup _group, UUID _uuid);
}
