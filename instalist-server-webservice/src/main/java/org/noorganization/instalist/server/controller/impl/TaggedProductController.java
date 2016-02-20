package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.ITagController;
import org.noorganization.instalist.server.controller.ITaggedProductController;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.UUID;

class TaggedProductController implements ITaggedProductController {

    private EntityManager mManager;

    @Override
    public void add(int _groupId, UUID _tpUUID, UUID _tagUUID, UUID _productUUID,
                    Instant _lastChanged)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        TaggedProduct toCheck = findByGroupAndUUID(group, _tpUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = findDeletedByGroupAndUUID(group, _tpUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }
        ITagController tagController = ControllerFactory.getTagController(mManager);
        Tag newTag = tagController.findByGroupAndUUID(group, _tagUUID);
        IProductController productController = ControllerFactory.getProductController(mManager);
        Product newProduct = productController.findByGroupAndUUID(group, _productUUID);
        if (newProduct == null || newTag == null) {
            tx.rollback();
            throw new BadRequestException();
        }

        TaggedProduct toCreate = new TaggedProduct().withGroup(group);
        toCreate.setUUID(_tpUUID);
        toCreate.setTag(newTag);
        toCreate.setProduct(newProduct);
        toCreate.setUpdated(_lastChanged);
        mManager.persist(toCreate);

        tx.commit();
    }

    @Override
    public void update(int _groupId, UUID _tpUUID, UUID _tagUUID, UUID _productUUID,
                       Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        TaggedProduct toUpdate = getTP(group, _tpUUID, tx);
        if (toUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }
        Tag newTag = null;
        if (_tagUUID != null) {
            ITagController tagController = ControllerFactory.getTagController(mManager);
            newTag = tagController.findByGroupAndUUID(group, _tagUUID);
            if (newTag == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }
        Product newProduct = null;
        if (_productUUID != null) {
            IProductController productController = ControllerFactory.getProductController(mManager);
            newProduct = productController.findByGroupAndUUID(group, _productUUID);
            if (newProduct == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }

        if (newTag != null)
            toUpdate.setTag(newTag);
        if (newProduct != null)
            toUpdate.setProduct(newProduct);
        toUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    @Override
    public void delete(int _groupId, UUID _tpUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        TaggedProduct toDelete = getTP(group, _tpUUID, tx);
        DeletedObject oldProduct = new DeletedObject().withGroup(group);
        oldProduct.setUUID(_tpUUID);
        oldProduct.setType(DeletedObject.Type.TAGGEDPRODUCT);
        mManager.persist(oldProduct);
        mManager.remove(toDelete);

        tx.commit();
    }

    TaggedProductController(EntityManager _manager) {
        mManager = _manager;
    }

    private TaggedProduct getTP(DeviceGroup _group, UUID _ingredientUUID,
                                EntityTransaction _tx)
            throws GoneException, NotFoundException {
        TaggedProduct rtn = findByGroupAndUUID(_group, _ingredientUUID);
        if (rtn == null) {
            if (findDeletedByGroupAndUUID(_group, _ingredientUUID) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }

    @Override
    public EntityManager getManager() {
        return mManager;
    }

    @Override
    public Class<TaggedProduct> getManagedType() {
        return TaggedProduct.class;
    }
}
