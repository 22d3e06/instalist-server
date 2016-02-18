package org.noorganization.instalist.server.controller;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.generic.IFinder;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Recipe;
import org.noorganization.instalist.server.model.Tag;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import java.time.Instant;
import java.util.UUID;

/**
 * A controller for changing tags.
 */
public interface ITagController extends IFinder<Tag> {
    /**
     * Creates a tag.
     * @param _groupId The id of the group that should contain the tag.
     * @param _tagUUID The uuid of the tag identifying it in the group.
     * @param _name The name of the tag.
     * @param _lastChanged A change date.
     * @throws ConflictException If already a tag with same uuid exists or the recipe was already
     * deleted after {@code _lastChanged}.
     */
    void add(int _groupId, UUID _tagUUID, String _name, Instant _lastChanged)
            throws ConflictException;

    /**
     * Updates a tag.
     * @param _groupId The id of the group containing the tag.
     * @param _tagUUID The uuid of the tag identifying it in the group.
     * @param _name The name of the tag. May be null for no change.
     * @param _lastChanged A change date.
     * @throws ConflictException If a change was made before.
     * @throws GoneException If tag was deleted before.
     * @throws NotFoundException If tag was not found.
     */
    void update(int _groupId, UUID _tagUUID, String _name, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException;

    /**
     * Deletes a tag.
     * @param _groupId The id of the group containing the tag.
     * @param _tagUUID The uuid of the tag identifying it in the group.
     * @throws GoneException If tag was deleted before.
     * @throws NotFoundException If tag was not found.
     */
    void delete(int _groupId, UUID _tagUUID) throws GoneException, NotFoundException;
}
