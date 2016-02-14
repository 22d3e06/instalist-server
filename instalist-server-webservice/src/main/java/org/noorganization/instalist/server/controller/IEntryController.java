package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.ListEntry;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing lists entries.
 * Created by damihe on 09.02.16.
 */
public interface IEntryController {
    /**
     * Creates a list.
     * @param _groupId The id of the group that should contain the entry.
     * @param _entryUUID The uuid of the entry identifying it in the group.
     * @param _productUUID The product of the entry.
     * @param _listUUID The list of the entry.
     * @param _amount The new amount of the entry.
     * @param _priority The priority of the entty.
     * @param _struck Whether the entry should be struck through.
     * @param _lastChanged A change date.
     * @throws ConflictException If already a list with same uuid exists or the list was already
     * deleted.
     * @throws BadRequestException If either linked product or list was not found.
     */
    void add(int _groupId, UUID _entryUUID, UUID _productUUID, UUID _listUUID,
             float _amount, int _priority, boolean _struck, Instant _lastChanged)
            throws ConflictException, BadRequestException;

    /**
     * Updates an entry.
     * @param _groupId The id of the group containing the entry.
     * @param _entryUUID The uuid of the entry identifying it in the group.
     * @param _productUUID The product of the entry. May be null for no change.
     * @param _listUUID The list of the entry. May be null for no change.
     * @param _amount The new amount of the entry. May be null for no change.
     * @param _priority The priority of the entty. May be null for no change.
     * @param _struck Whether the entry should be struck through. May be null for no change.
     * @param _lastChanged A change date.
     * @throws ConflictException If a change was made before.
     * @throws GoneException If list was deleted before.
     * @throws NotFoundException If list was not found.
     * @throws BadRequestException If either linked product or list was not found.
     */
    void update(int _groupId, UUID _entryUUID, UUID _productUUID, UUID _listUUID, Float _amount,
                Integer _priority, Boolean _struck, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException;

    /**
     * Deletes a list and all entries.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list identifying it in the group.
     * @throws GoneException If list was deleted before.
     * @throws NotFoundException If list was not found.
     */
    void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException;

    /**
     * Finds a list entry.
     * @param _group The group containing the entry.
     * @param _uuid The entry's existing UUID.
     * @return Either the found ListEntry or null, if not found.
     */
    ListEntry getEntryByGroupAndUUID(DeviceGroup _group, UUID _uuid);

    /**
     * Finds a deleted entry.
     * @param _group The group containing the deleted ListEntry.
     * @param _uuid The entry's old UUID.
     * @return Either the found ListEntry as DeletedObject or null, if not found.
     */
    DeletedObject getDeletedEntryByGroupAndUUID(DeviceGroup _group, UUID _uuid);
}
