
package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.comm.message.TagInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.ITagController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Tag;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/groups/{groupid}/tags")
public class TagResource {


    /**
     * Get a list of tags.
     * @param _groupId The id of the group containing the tags.
     * @param _changedSince Limits the request to elements that changed since the given date. ISO
     *                      8601 time e.g. 2016-01-19T11:54:07+0100. Optional.
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getTags(@PathParam("groupid") int _groupId,
                            @QueryParam("changedsince") String _changedSince) throws Exception {
        Instant changedSince = null;
        try {
            if (_changedSince != null)
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).
                    toInstant();
        } catch (ParseException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        List<Tag> tags;
        List<DeletedObject> deletedTags;
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (changedSince != null) {
            TypedQuery<Tag> tagQuery = manager.createQuery("select t from Tag t where " +
                    "t.group = :group and t.updated > :updated", Tag.class);
            tagQuery.setParameter("group", group);
            tagQuery.setParameter("updated", changedSince);
            tags = tagQuery.getResultList();

            TypedQuery<DeletedObject> deletedRecipesQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.updated > :updated and " +
                    "do.type = :type", DeletedObject.class);
            deletedRecipesQuery.setParameter("group", group);
            deletedRecipesQuery.setParameter("updated", changedSince);
            deletedRecipesQuery.setParameter("type", DeletedObject.Type.TAG);
            deletedTags = deletedRecipesQuery.getResultList();
        } else {
            tags = new ArrayList<Tag>(group.getTags());

            TypedQuery<DeletedObject> deletedRecipesQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedRecipesQuery.setParameter("group", group);
            deletedRecipesQuery.setParameter("type", DeletedObject.Type.TAG);
            deletedTags = deletedRecipesQuery.getResultList();
        }
        manager.close();

        ArrayList<TagInfo> rtn = new ArrayList<TagInfo>(tags.size() + deletedTags.size());
        for (Tag current: tags) {
            TagInfo toAdd = new TagInfo().withDeleted(false);
            toAdd.setUUID(current.getUUID());
            toAdd.setName(current.getName());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }
        for (DeletedObject current: deletedTags) {
            TagInfo toAdd = new TagInfo().withDeleted(true);
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Get a single tag.
     * @param _groupId The id of the group containing the tag.
     * @param _tagUUID The uuid of the requested tag.
     */
    @GET
    @TokenSecured
    @Path("{taguuid}")
    @Produces({ "application/json" })
    public Response getTag(@PathParam("groupid") int _groupId,
                           @PathParam("taguuid") String _tagUUID) throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_tagUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ITagController tagController = ControllerFactory.getTagController(manager);
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        Tag current = tagController.findByGroupAndUUID(group, toFind);
        if (current == null) {
            if (tagController.findDeletedByGroupAndUUID(group, toFind) == null) {
                manager.close();
                return ResponseFactory.generateNotFound(new Error().withMessage("Tag was not " +
                        "found."));
            }
            manager.close();
            return ResponseFactory.generateGone(new Error().withMessage("Tag was deleted " +
                    "before."));
        }
        manager.close();

        TagInfo rtn = new TagInfo().withDeleted(false);
        rtn.setUUID(current.getUUID());
        rtn.setName(current.getName());
        rtn.setLastChanged(Date.from(current.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates the tag.
     * @param _groupId The id of the group containing the tag to change.
     * @param _tagUUID The uuid of the tag identifying it in the group.
     * @param _entity Data to change.
     */
    @PUT
    @TokenSecured
    @Path("{taguuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putTag(@PathParam("groupid") int _groupId,
                           @PathParam("taguuid") String _tagUUID,
                           TagInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_tagUUID)) ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toUpdate;
        try {
            toUpdate = UUID.fromString(_tagUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant updated;
        if (_entity.getLastChanged() != null) {
            updated = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(updated))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            updated = Instant.now();

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ITagController tagController = ControllerFactory.getTagController(manager);
        try {
            tagController.update(_groupId, toUpdate, _entity.getName(), updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The tag was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The tag has been " +
                    "deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved tag."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the tag.
     * @param _groupId The id of the group that should contain the new tag.
     * @param _entity Data needed for creation.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postTag(@PathParam("groupid") int _groupId,
                            TagInfo _entity) throws Exception {
        if (_entity.getUUID() == null ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toCreate;
        try {
            toCreate = UUID.fromString(_entity.getUUID());
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }
        Instant created;
        if (_entity.getLastChanged() != null) {
            created = _entity.getLastChanged().toInstant();
            if (Instant.now().isBefore(created))
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
        } else
            created = Instant.now();

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ITagController tagController = ControllerFactory.getTagController(manager);
        try {
            tagController.add(_groupId, toCreate, _entity.getName(), created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved tag."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the tag and linked tagged products (but not the products themselves).
     * @param _groupId The id of the group still containing the tag.
     * @param _tagUUID the uuid of the tag to delete.
     */
    @DELETE
    @TokenSecured
    @Path("{taguuid}")
    @Produces({ "application/json" })
    public Response deleteTag(@PathParam("groupid") int _groupId,
                              @PathParam("taguuid") String _tagUUID) throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_tagUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ITagController tagController = ControllerFactory.getTagController(manager);
        try {
            tagController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The tag was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The tag has been " +
                    "deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
