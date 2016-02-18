package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.ITagController;
import org.noorganization.instalist.server.controller.ITaggedProductController;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

class TagController implements ITagController {

    private EntityManager mManager;

    @Override
    public void add(int _groupId, UUID _tagUUID, String _name, Instant _lastChanged)
            throws ConflictException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Tag toCheck = findByGroupAndUUID(group, _tagUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = findDeletedByGroupAndUUID(group, _tagUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }

        Tag toCreate = new Tag().withGroup(group);
        toCreate.setUUID(_tagUUID);
        toCreate.setName(_name);
        toCreate.setUpdated(_lastChanged);
        mManager.persist(toCreate);

        tx.commit();
    }

    @Override
    public void update(int _groupId, UUID _recipeUUID, String _name, Instant _lastChanged)
            throws ConflictException, GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Tag toUpdate = getTag(group, _recipeUUID, tx);
        if (toUpdate.getUpdated().isAfter(_lastChanged)) {
            tx.rollback();
            throw new ConflictException();
        }

        if (_name != null)
            toUpdate.setName(_name);
        toUpdate.setUpdated(_lastChanged);

        tx.commit();
    }

    @Override
    public void delete(int _groupId, UUID _recipeUUID) throws GoneException, NotFoundException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Tag toDelete = getTag(group, _recipeUUID, tx);

        ITaggedProductController taggedProductController = ControllerFactory.
                getTaggedProductController(mManager);
        for (TaggedProduct taggedProduct: toDelete.getTaggedProducts()) {
            try {
                taggedProductController.delete(_groupId, taggedProduct.getUUID());
            } catch (Exception _e) {}
        }
        DeletedObject oldRecipe = new DeletedObject().withGroup(group);
        oldRecipe.setUUID(_recipeUUID);
        oldRecipe.setType(DeletedObject.Type.TAG);
        mManager.persist(oldRecipe);
        mManager.remove(toDelete);

        tx.commit();
    }

    TagController(EntityManager _manager) {
        mManager = _manager;
    }

    private Tag getTag(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        Tag rtn = findByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (findDeletedByGroupAndUUID(_group, _uuid) == null) {
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
    public Class<Tag> getManagedType() {
        return Tag.class;
    }
}
