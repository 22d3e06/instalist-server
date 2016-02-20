package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.generic.IFinder;
import org.noorganization.instalist.server.model.ListEntry;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing lists entries.
 *
 * Created by damihe on 2016-02-02.
 */
public interface IEntryController extends IFinder<ListEntry> {
    /**
     * Creates a list entry.
     * @param _groupId The id of the group that should contain the entry.
     * @param _entryUUID The uuid of the entry identifying it in the group.
     * @param _productUUID The product of the entry.
     * @param _listUUID The list of the entry.
     * @param _amount The new amount of the entry.
     * @param _priority The priority of the entty.
     * @param _struck Whether the entry should be struck through.
     * @param _lastChanged A change date.
     * @throws ConflictException If already an entry with same uuid exists or the list was already
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
     * @throws GoneException If list entry was deleted before.
     * @throws NotFoundException If entry was not found.
     * @throws BadRequestException If either linked product or list was not found.
     */
    void update(int _groupId, UUID _entryUUID, UUID _productUUID, UUID _listUUID, Float _amount,
                Integer _priority, Boolean _struck, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException;

    /**
     * Deletes an entry.
     * @param _groupId The id of the group containing the entry.
     * @param _entryUUID The uuid of the entry identifying it in the group.
     * @throws GoneException If list entry was deleted before.
     * @throws NotFoundException If entry was not found.
     */
    void delete(int _groupId, UUID _entryUUID) throws GoneException, NotFoundException;
}
