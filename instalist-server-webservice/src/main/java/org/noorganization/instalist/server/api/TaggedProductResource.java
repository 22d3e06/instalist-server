
package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.comm.message.IngredientInfo;
import org.noorganization.instalist.comm.message.TaggedProductInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IIngredientController;
import org.noorganization.instalist.server.controller.ITaggedProductController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.model.TaggedProduct;
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


@Path("/groups/{groupid}/taggedproducts")
public class TaggedProductResource {

    /**
     * Get a list of tagged products.
     * @param _groupId The id of the group containing various tagged products.
     * @param _changedSince Limits the result to elements that changed since the given date. ISO
     *                      8601 time e.g. 2016-01-19T11:54:07+0100. Optional.
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getTaggedProducts(@PathParam("groupid") int _groupId,
                                      @QueryParam("changedsince") String _changedSince)
            throws Exception {
        List<TaggedProduct> taggedProducts;
        List<DeletedObject> deletedTaggedProducts;
        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);

        if (_changedSince != null) {
            Instant changedSince;
            try {
                changedSince = ISO8601Utils.parse(_changedSince, new ParsePosition(0)).
                        toInstant();
            } catch (ParseException _e) {
                manager.close();
                return ResponseFactory.generateBadRequest(CommonEntity.INVALID_DATE);
            }

            TypedQuery<TaggedProduct> taggedProductQuery = manager.createQuery("select tp from " +
                    "TaggedProduct tp where tp.group = :group and tp.updated > :updated",
                    TaggedProduct.class);
            taggedProductQuery.setParameter("group", group);
            taggedProductQuery.setParameter("updated", changedSince);
            taggedProducts = taggedProductQuery.getResultList();

            TypedQuery<DeletedObject> deletedTaggedProductQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.time > :updated and " +
                    "do.type = :type", DeletedObject.class);
            deletedTaggedProductQuery.setParameter("group", group);
            deletedTaggedProductQuery.setParameter("updated", Date.from(changedSince));
            deletedTaggedProductQuery.setParameter("type", DeletedObject.Type.TAGGEDPRODUCT);
            deletedTaggedProducts = deletedTaggedProductQuery.getResultList();
        } else {
            taggedProducts = new ArrayList<TaggedProduct>(group.getTaggedProducts());

            TypedQuery<DeletedObject> deletedIngredientsQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.type = :type",
                    DeletedObject.class);
            deletedIngredientsQuery.setParameter("group", group);
            deletedIngredientsQuery.setParameter("type", DeletedObject.Type.TAGGEDPRODUCT);
            deletedTaggedProducts = deletedIngredientsQuery.getResultList();
        }
        manager.close();

        ArrayList<TaggedProductInfo> rtn = new ArrayList<TaggedProductInfo>(taggedProducts.size() +
                deletedTaggedProducts.size());
        for (TaggedProduct current : taggedProducts) {
            TaggedProductInfo toAdd = new TaggedProductInfo().withDeleted(false);
            toAdd.setUUID(current.getUUID());
            toAdd.setProductUUID(current.getProduct().getUUID());
            toAdd.setTagUUID(current.getTag().getUUID());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            rtn.add(toAdd);
        }
        for (DeletedObject current : deletedTaggedProducts) {
            TaggedProductInfo toAdd = new TaggedProductInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(current.getTime());
            toAdd.setDeleted(true);
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Returns a single ingredient.
     * @param _groupId The id of the group containing the tagged product.
     * @param _taggedProductUUID The uuid of the tagged product itself.
     */
    @GET
    @TokenSecured
    @Path("{tpuuid}")
    @Produces({ "application/json" })
    public Response getTaggedProduct(@PathParam("groupid") int _groupId,
                                     @PathParam("tpuuid") String _taggedProductUUID)
            throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_taggedProductUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        ITaggedProductController taggedProductController = ControllerFactory.
                getTaggedProductController(manager);

        TaggedProduct foundTaggedProduct = taggedProductController.
                getTaggedProductByGroupAndUUID(group, toFind);
        if (foundTaggedProduct == null) {
            if (taggedProductController.getDeletedTaggedProductByGroupAndUUID(group, toFind) !=
                    null) {
                manager.close();
                return ResponseFactory.generateGone(new Error().withMessage("The requested " +
                        "tagged product has been deleted."));
            }
            manager.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                    "tagged product was not found."));
        }
        manager.close();

        TaggedProductInfo rtn = new TaggedProductInfo().withDeleted(false);
        rtn.setUUID(foundTaggedProduct.getUUID());
        rtn.setProductUUID(foundTaggedProduct.getProduct().getUUID());
        rtn.setTagUUID(foundTaggedProduct.getTag().getUUID());
        rtn.setLastChanged(Date.from(foundTaggedProduct.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates an tagged product. This REST-Endpoint is currently not really useful as
     * TaggedProduct only contain links between Products and Tags and no additional information. It
     * was created as reservation for future use.
     * @param _groupId The id of the group containing the tagged product.
     * @param _taggedProductUUID The uuid of the tagged product itself.
     * @param _entity Data for updating the tagged product.
     */
    @PUT
    @TokenSecured
    @Path("{tpuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putTaggedProduct(@PathParam("groupid") int _groupId,
                                     @PathParam("tpuuid") String _taggedProductUUID,
                                     TaggedProductInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_taggedProductUUID)) ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toUpdate;
        UUID productUUID = null;
        UUID tagUUID = null;
        try {
            toUpdate = UUID.fromString(_taggedProductUUID);
            if (_entity.getProductUUID() != null)
                productUUID = UUID.fromString(_entity.getProductUUID());
            if (_entity.getTagUUID() != null)
                tagUUID = UUID.fromString(_entity.getTagUUID());
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
        ITaggedProductController taggedProductController = ControllerFactory.
                getTaggedProductController(manager);
        try {
            taggedProductController.update(_groupId, toUpdate, tagUUID, productUUID, updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The tagged product " +
                    "was not found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The tagged product has " +
                    "been deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved tagged product."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "product or tag was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates the tagged product.
     * @param _groupId The id of the group that will contain the tagged product.
     * @param _entity Data for the created tagged product.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postTaggedProduct(@PathParam("groupid") int _groupId,
                                      TaggedProductInfo _entity) throws Exception {
        if (_entity.getUUID() == null || _entity.getTagUUID() == null ||
                _entity.getProductUUID() == null ||
                (_entity.getDeleted() != null && _entity.getDeleted()))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toCreate;
        UUID productUUID;
        UUID tagUUID;
        try {
            toCreate = UUID.fromString(_entity.getUUID());
            productUUID = UUID.fromString(_entity.getProductUUID());
            tagUUID = UUID.fromString(_entity.getTagUUID());
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
        ITaggedProductController taggedProductController = ControllerFactory.
                getTaggedProductController(manager);
        try {
            taggedProductController.add(_groupId, toCreate, tagUUID, productUUID, created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved tagged product."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "recipe or tag was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the tagged product.
     * @param _groupId The id of the group still containing the tagged product.
     * @param _taggedProductUUID The uuid of the tagged product itself.
     */
    @DELETE
    @TokenSecured
    @Path("{tpuuid}")
    @Produces({ "application/json" })
    public Response deleteTaggedProduct(@PathParam("groupid") int _groupId,
                                        @PathParam("tpuuid") String _taggedProductUUID)
            throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_taggedProductUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        ITaggedProductController taggedProductController = ControllerFactory.
                getTaggedProductController(manager);
        try {
            taggedProductController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The tagged product " +
                    "was not found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The tagged product has " +
                    "been deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
