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
import java.util.Date;
import java.util.List;
import java.util.UUID;

class TagController implements ITagController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _tagUUID, String _name, Instant _lastChanged)
            throws ConflictException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        Tag toCheck = getTagByGroupAndUUID(group, _tagUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = getDeletedTagByGroupAndUUID(group, _tagUUID);
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

    public Tag getTagByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<Tag> recipeQuery = mManager.createQuery("select t from Tag t where " +
                "t.UUID = :uuid and t.group = :group", Tag.class);
        recipeQuery.setParameter("uuid", _uuid);
        recipeQuery.setParameter("group", _group);
        recipeQuery.setMaxResults(1);
        List<Tag> recipes = recipeQuery.getResultList();
        if (recipes.size() == 0)
            return null;
        return recipes.get(0);
    }

    public DeletedObject getDeletedTagByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delTagQuery = mManager.createQuery("select do from " +
                        "DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                        "do.type = :type order by do.updated desc",
                DeletedObject.class);
        delTagQuery.setParameter("group", _group);
        delTagQuery.setParameter("uuid", _uuid);
        delTagQuery.setParameter("type", DeletedObject.Type.TAG);
        delTagQuery.setMaxResults(1);
        List<DeletedObject> delTagResult = delTagQuery.getResultList();
        if (delTagResult.size() == 0)
            return null;
        return delTagResult.get(0);
    }

    TagController(EntityManager _manager) {
        mManager = _manager;
    }

    private Tag getTag(DeviceGroup _group, UUID _uuid, EntityTransaction _tx) throws
            GoneException, NotFoundException {
        Tag rtn = getTagByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (getDeletedTagByGroupAndUUID(_group, _uuid) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }
}
