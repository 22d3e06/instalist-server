package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.controller.generic.IFinder;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Product;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

/**
 * This controller is written for making all write operations on products. Additional it should make
 * finding them easier.
 */
public interface IProductController extends IFinder<Product> {

    /**
     * Create a new product.
     * @param _groupId The id of the group that should contains the new product.
     * @param _newUUID Product's new UUID.
     * @param _name Product's new name.
     * @param _defaultAmount The new Products default amount.
     * @param _stepAmount The new Products step amount.
     * @param _unitUUID A optional UUID of a linked existing unit. May be null.
     * @param _created Product's creation date.
     * @throws ConflictException If a Unit with this UUID already exists or was deleted after
     * {@code _created}.
     * @throws BadRequestException If the {@code _unitUUID} was not null and does not exist.
     */
    void add(int _groupId, UUID _newUUID, String _name, float _defaultAmount, float _stepAmount,
             UUID _unitUUID, Instant _created) throws ConflictException, BadRequestException;

    /**
     * Updates a product.
     * @param _groupId The id of the group that contains the existing product.
     * @param _uuid Existing product's UUID.
     * @param _name Product's new name. May be null for no change.
     * @param _defaultAmount Product's new default amount. May be null for no change.
     * @param _stepAmount Product's new step amount. May be null for no change.
     * @param _unitUUID Product's new unit-uuid. May be null for no change, except {@code
     * _removeUnit} is set to true.
     * @param _removeUnit Whether to remove the saved unit (if the product was linked to a unit).
     *                    If set to true, {@code _unitUUID} will be ignored and the unit will be
     *                    set to null.
     * @param _updated Update time of product for determining conflicts.
     * @throws ConflictException If the product was modified after {@code _updated}
     * @throws NotFoundException If the product was not found.
     * @throws GoneException If the product was not found because it has been deleted before.
     * @throws BadRequestException If the new linked Unit does not exist.
     */
    void update(int _groupId, UUID _uuid, String _name, Float _defaultAmount, Float _stepAmount,
                UUID _unitUUID, boolean _removeUnit, Instant _updated) throws ConflictException,
            NotFoundException, GoneException, BadRequestException;

    /**
     * Deletes a Product and removes all linked instances of
     * {@link org.noorganization.instalist.server.model.ListEntry}.
     * @param _groupId The id of the group that contains the old product.
     * @param _uuid Still existing product's UUID.
     * @throws NotFoundException If the product was not found.
     * @throws GoneException If the product was not found because it has been deleted before.
     */
    void delete(int _groupId, UUID _uuid) throws NotFoundException, GoneException;
}
