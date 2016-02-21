package org.noorganization.instalist.server.controller.generic;

import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * This interface is a extraction of getter functions for finding entities.
 * Created by damihe on 18.02.16.
 */
public interface IFinder<T> {

    EntityManager getManager();

    Class<T> getManagedType();

    /**
     * Finds an object by uuid and group.
     * @param _group The group, that contains the object.
     * @param _uuid The uuid identifying the object in the group.
     * @return Either the object or null if not found.
     */
    default T findByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getManagedType());
        Root<T> root = cq.from(getManagedType());
        cq.where(cb.and(cb.equal(root.get("UUID"), _uuid), cb.equal(root.get("group"), _group)));
        TypedQuery<T> objectQuery = getManager().createQuery(cq);
        objectQuery.setMaxResults(1);
        List<T> objectResult = objectQuery.getResultList();
        if (objectResult.size() == 0)
            return null;
        return objectResult.get(0);
    }

    /**
     * Finds a deleted object by uuid and group.
     * @param _group The group that contained the object.
     * @param _uuid The uuid that identified the object in the group.
     * @return Either the deleted object or null if it was not deleted.
     */
    default DeletedObject findDeletedByGroupAndUUID(DeviceGroup _group, UUID _uuid) {
        TypedQuery<DeletedObject> delObjectQuery = getManager().createQuery("select do from " +
                "DeletedObject do where do.UUID = :uuid and do.group = :group and " +
                "do.type = :type order by do.updated desc", DeletedObject.class);
        delObjectQuery.setMaxResults(1);
        delObjectQuery.setParameter("uuid", _uuid);
        delObjectQuery.setParameter("group", _group);
        delObjectQuery.setParameter("type", DeletedObject.mapType(getManagedType()));
        List<DeletedObject> delObjects = delObjectQuery.getResultList();
        if (delObjects.size() == 0)
            return null;
        return delObjects.get(0);
    }

    /**
     * Find a item or throw an exception.
     * @param _group The group, that contains the object to find.
     * @param _uuid The uuid identifying the object in the group.
     * @return The found object. Never null.
     * @throws NotFoundException if item was not found and not deleted.
     * @throws GoneException if item was deleted.
     */
    default T findOrThrow(DeviceGroup _group, UUID _uuid) throws NotFoundException, GoneException {
        T rtn = findByGroupAndUUID(_group, _uuid);
        if (rtn == null) {
            if (findDeletedByGroupAndUUID(_group, _uuid) == null)
                throw new NotFoundException();
            else
                throw new GoneException();
        }
        return rtn;
    }
}
