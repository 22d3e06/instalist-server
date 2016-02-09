package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import java.util.Date;
import java.util.UUID;

/**
 * A controller for changing lists entries.
 * Created by damihe on 09.02.16.
 */
public interface IEntryController {
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
    void add(int _groupId, UUID _listUUID, String _name, UUID _category, Date _lastChanged)
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
    void update(int _groupId, UUID _listUUID, String _name, UUID _category, Date _lastChanged)
            throws ConflictException, GoneException, NotFoundException;

    /**
     * Deletes a list and all entries.
     * @param _groupId The id of the group containing the list.
     * @param _listUUID The uuid of the list identifying it in the group.
     * @throws GoneException If list was deleted before.
     * @throws NotFoundException If list was not found.
     */
    void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException;
}
