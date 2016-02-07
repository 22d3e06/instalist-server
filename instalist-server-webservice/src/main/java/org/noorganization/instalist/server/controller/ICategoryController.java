package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;
import org.noorganization.instalist.server.support.exceptions.NotFoundException;

import java.util.UUID;

/**
 * Controller for managing categories. This is a controller for managing real contents.
 * Created by damihe on 07.02.16.
 */
public interface ICategoryController {

    /**
     * Adds a new category to the group.
     * @param _groupId The id of the group to add the category
     * @param _uuid The uuid of the category.
     * @param _name The name of the category.
     * @return The created Category.
     * @throws ConflictException If there is already a category with same uuid for the group.
     */
    Category add(int _groupId, UUID _uuid, String _name) throws ConflictException;

    /**
     * Updates a category.
     * @param _id The id of the category to update.
     * @param _name The new name of the category.
     * @throws GoneException If category was deleted before.
     * @throws NotFoundException If category was not found.
     */
    void update(int _id, String _name) throws GoneException, NotFoundException;

    /**
     * Deletes a category.
     * @param _id The id of the category to delete.
     * @throws GoneException If already deleted before.
     * @throws NotFoundException If category was not found.
     */
    void delete(int _id) throws GoneException, NotFoundException;
}
