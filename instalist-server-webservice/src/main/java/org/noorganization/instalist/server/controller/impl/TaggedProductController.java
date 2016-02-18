package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.ITagController;
import org.noorganization.instalist.server.controller.ITaggedProductController;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaggedProductController implements ITaggedProductController {

    private EntityManager mManager;

    public void add(int _groupId, UUID _tpUUID, UUID _tagUUID, UUID _productUUID,
                    Instant _lastChanged)
            throws ConflictException, BadRequestException {
        EntityTransaction tx = mManager.getTransaction();
        tx.begin();
        DeviceGroup group = mManager.find(DeviceGroup.class, _groupId);

        TaggedProduct toCheck = getTaggedProductByGroupAndUUID(group, _tpUUID);
        if (toCheck != null) {
            tx.rollback();
            throw new ConflictException();
        }
        DeletedObject previousDeleted = getDeletedTaggedProductByGroupAndUUID(group, _tpUUID);
        if (previousDeleted != null && _lastChanged.isBefore(previousDeleted.getUpdated())) {
            tx.rollback();
            throw new ConflictException();
        }
        ITagController tagController = ControllerFactory.getTagController(mManager);
        Tag newTag = tagController.getTagByGroupAndUUID(group, _tagUUID);
        IProductController productController = ControllerFactory.getProductController(mManager);
        Product newProduct = productController.getProductByGroupAndUUID(group, _productUUID);
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
            newTag = tagController.getTagByGroupAndUUID(group, _tagUUID);
            if (newTag == null) {
                tx.rollback();
                throw new BadRequestException();
            }
        }
        Product newProduct = null;
        if (_productUUID != null) {
            IProductController productController = ControllerFactory.getProductController(mManager);
            newProduct = productController.getProductByGroupAndUUID(group, _productUUID);
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

    public TaggedProduct getTaggedProductByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<TaggedProduct> taggedProductQuery = mManager.createQuery("select tp from " +
                "TaggedProduct tp where tp.UUID = :uuid and tp.group = :group",
                TaggedProduct.class);
        taggedProductQuery.setParameter("uuid", _uuid);
        taggedProductQuery.setParameter("group", _group);
        List<TaggedProduct> taggedProductResult = taggedProductQuery.getResultList();
        if (taggedProductResult.size() == 0)
            return null;
        return taggedProductResult.get(0);
    }

    public DeletedObject getDeletedTaggedProductByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delTaggedProductQuery = mManager.createQuery("select do from " +
                        "DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                        "do.type = :type order by do.updated desc",
                DeletedObject.class);
        delTaggedProductQuery.setParameter("group", _group);
        delTaggedProductQuery.setParameter("uuid", _uuid);
        delTaggedProductQuery.setParameter("type", DeletedObject.Type.TAGGEDPRODUCT);
        delTaggedProductQuery.setMaxResults(1);
        List<DeletedObject> delTaggedProductResult = delTaggedProductQuery.getResultList();
        if (delTaggedProductResult.size() == 0)
            return null;
        return delTaggedProductResult.get(0);
    }

    TaggedProductController(EntityManager _manager) {
        mManager = _manager;
    }

    private TaggedProduct getTP(DeviceGroup _group, UUID _ingredientUUID,
                                EntityTransaction _tx)
            throws GoneException, NotFoundException {
        TaggedProduct rtn = getTaggedProductByGroupAndUUID(_group, _ingredientUUID);
        if (rtn == null) {
            if (getDeletedTaggedProductByGroupAndUUID(_group, _ingredientUUID) == null) {
                _tx.rollback();
                throw new NotFoundException();
            }
            _tx.rollback();
            throw new GoneException();
        }
        return rtn;
    }
}
