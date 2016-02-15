
package org.noorganization.instalist.server.api;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.noorganization.instalist.comm.message.ProductInfo;
import org.noorganization.instalist.server.CommonEntity;
import org.noorganization.instalist.server.TokenSecured;
import org.noorganization.instalist.server.controller.IProductController;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.comm.message.Error;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.model.Product;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

@Path("/groups/{groupid}/products")
public class ProductResource {


    /**
     * Get a list of products.
     * @param _groupId The id of the group containing the products.
     * @param _changedSince Optional. Limits the request to the elements that changed since the
     *                      given date. ISO 8601 time e.g. "2016-01-19T11:54:07+0100".
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getProducts(@PathParam("groupid") int _groupId,
                                @QueryParam("changedsince") String _changedSince) throws Exception {
        List<Product> foundProducts;
        List<DeletedObject> foundDeleted;
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

            TypedQuery<Product> foundProductsQuery = manager.createQuery("select p from " +
                            "Product p where p.group = :group and p.updated > :updated",
                    Product.class);
            foundProductsQuery.setParameter("group", group);
            foundProductsQuery.setParameter("updated", changedSince);
            foundProducts = foundProductsQuery.getResultList();

            TypedQuery<DeletedObject> foundDeletedListsQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.time > :updated and" +
                    " do.type = :type",
                            DeletedObject.class);
            foundDeletedListsQuery.setParameter("group", group);
            foundDeletedListsQuery.setParameter("updated", Date.from(changedSince));
            foundDeletedListsQuery.setParameter("type", DeletedObject.Type.PRODUCT);
            foundDeleted = foundDeletedListsQuery.getResultList();
        } else {
            TypedQuery<Product> foundProductsQuery = manager.createQuery("select p from " +
                    "Product p where p.group = :group", Product.class);
            foundProductsQuery.setParameter("group", group);
            foundProducts = foundProductsQuery.getResultList();

            TypedQuery<DeletedObject> foundDeletedProductsQuery = manager.createQuery("select do " +
                    "from DeletedObject do where do.group = :group and do.type = :type",
                            DeletedObject.class);
            foundDeletedProductsQuery.setParameter("group", group);
            foundDeletedProductsQuery.setParameter("type", DeletedObject.Type.PRODUCT);
            foundDeleted = foundDeletedProductsQuery.getResultList();
        }
        manager.close();

        ArrayList<ProductInfo> rtn = new ArrayList<ProductInfo>(foundProducts.size() +
                foundDeleted.size());
        for (Product current : foundProducts) {
            ProductInfo toAdd = new ProductInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setName(current.getName());
            toAdd.setDefaultAmount(current.getDefaultAmount());
            toAdd.setStepAmount(current.getStepAmount());
            if (current.getUnit() != null)
                toAdd.setUnitUUID(current.getUnit().getUUID());
            toAdd.setLastChanged(Date.from(current.getUpdated()));
            toAdd.setDeleted(false);
            rtn.add(toAdd);
        }
        for (DeletedObject current : foundDeleted) {
            ProductInfo toAdd = new ProductInfo();
            toAdd.setUUID(current.getUUID());
            toAdd.setLastChanged(current.getTime());
            toAdd.setDeleted(true);
            rtn.add(toAdd);
        }

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Finds a single product.
     * @param _groupId The id of the group containing the searched product.
     * @param _productUUID The uuid of the needed product.
     */
    @GET
    @TokenSecured
    @Path("{productuuid}")
    @Produces({ "application/json" })
    public Response getProduct(@PathParam("groupid") int _groupId,
                               @PathParam("productuuid") String _productUUID) throws Exception {
        UUID toFind;
        try {
            toFind = UUID.fromString(_productUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        DeviceGroup group = manager.find(DeviceGroup.class, _groupId);
        IProductController productController = ControllerFactory.getProductController(manager);

        Product foundProduct = productController.getProductByGroupAndUUID(group, toFind);
        if (foundProduct == null) {
            if (productController.getDeletedProductByGroupAndUUID(group, toFind) != null) {
                manager.close();
                return ResponseFactory.generateGone(new Error().withMessage("The requested " +
                        "product has been deleted."));
            }
            manager.close();
            return ResponseFactory.generateNotFound(new Error().withMessage("The requested " +
                    "product was not found."));
        }
        manager.close();

        ProductInfo rtn = new ProductInfo().withDeleted(false);
        rtn.setUUID(toFind);
        rtn.setName(foundProduct.getName());
        rtn.setDefaultAmount(foundProduct.getDefaultAmount());
        rtn.setStepAmount(foundProduct.getStepAmount());
        if (foundProduct.getUnit() != null)
            rtn.setUnitUUID(foundProduct.getUnit().getUUID());
        rtn.setLastChanged(Date.from(foundProduct.getUpdated()));

        return ResponseFactory.generateOK(rtn);
    }

    /**
     * Updates the product.
     * @param _groupId The group containing the product to update.
     * @param _productUUID The uuid of the product to update.
     * @param _entity The data for changing the product.
     *      e.g. examples/product.example
     */
    @PUT
    @TokenSecured
    @Path("{productuuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putProduct(@PathParam("groupid") int _groupId,
                               @PathParam("productuuid") String _productUUID,
                               ProductInfo _entity) throws Exception {
        if ((_entity.getUUID() != null && !_entity.getUUID().equals(_productUUID)) ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getDefaultAmount() != null && _entity.getDefaultAmount() < 0.001f) ||
                (_entity.getStepAmount() != null && _entity.getStepAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toUpdate;
        UUID unitUUID = null;
        boolean removeUnit = (_entity.getRemoveUnit() != null ? _entity.getRemoveUnit() : false);
        try {
            toUpdate = UUID.fromString(_productUUID);
            if (_entity.getUnitUUID() != null && !removeUnit)
                unitUUID = UUID.fromString(_entity.getUnitUUID());
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
        IProductController productController = ControllerFactory.getProductController(manager);
        try {
            productController.update(_groupId, toUpdate, _entity.getName(),
                    _entity.getDefaultAmount(), _entity.getStepAmount(), unitUUID, removeUnit,
                    updated);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The product was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The product has been " +
                    "deleted."));
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved product."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "unit was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

    /**
     * Creates a product.
     * @param _groupId The id of the group that should contain the new product.
     * @param _entity Data for creating the group.
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postProduct(@PathParam("groupid") int _groupId,
                                ProductInfo _entity) throws Exception {
        if (_entity.getUUID() == null ||
                (_entity.getName() != null && _entity.getName().length() == 0) ||
                (_entity.getDeleted() != null && _entity.getDeleted()) ||
                (_entity.getDefaultAmount() != null && _entity.getDefaultAmount() < 0.001f) ||
                (_entity.getStepAmount() != null && _entity.getStepAmount() < 0.001f))
            return ResponseFactory.generateBadRequest(CommonEntity.sInvalidData);

        UUID toCreate;
        UUID unitUUID = null;
        boolean removeUnit = (_entity.getRemoveUnit() != null ? _entity.getRemoveUnit() : false);
        try {
            toCreate = UUID.fromString(_entity.getUUID());
            if (_entity.getUnitUUID() != null && !removeUnit)
                unitUUID = UUID.fromString(_entity.getUnitUUID());
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
        float defaultAmount = (_entity.getDefaultAmount() != null ? _entity.getDefaultAmount() :
                1f);
        float stepAmount = (_entity.getStepAmount() != null ? _entity.getStepAmount() : 1f);

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IProductController productController = ControllerFactory.getProductController(manager);
        try {
            productController.add(_groupId, toCreate, _entity.getName(), defaultAmount,
                    stepAmount, unitUUID, created);
        } catch (ConflictException _e) {
            return ResponseFactory.generateConflict(new Error().withMessage("The sent data would " +
                    "conflict with saved product."));
        } catch (BadRequestException _e) {
            return ResponseFactory.generateBadRequest(new Error().withMessage("The referenced " +
                    "unit was not found."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateCreated(null);
    }

    /**
     * Deletes the product.
     * @param _groupId The id of the group which contains the product.
     * @param _productUUID The uuid of the still existing product.
     *     
     */
    @DELETE
    @TokenSecured
    @Path("{productuuid}")
    @Produces({ "application/json" })
    public Response deleteProduct(@PathParam("groupid") int _groupId,
                                  @PathParam("productuuid") String _productUUID)
            throws Exception {
        UUID toDelete;
        try {
            toDelete = UUID.fromString(_productUUID);
        } catch (IllegalArgumentException _e) {
            return ResponseFactory.generateBadRequest(CommonEntity.INVALID_UUID);
        }

        EntityManager manager = DatabaseHelper.getInstance().getManager();
        IProductController productController = ControllerFactory.getProductController(manager);
        try {
            productController.delete(_groupId, toDelete);
        } catch (NotFoundException _e) {
            return ResponseFactory.generateNotFound(new Error().withMessage("The product was not " +
                    "found."));
        } catch (GoneException _e) {
            return ResponseFactory.generateGone(new Error().withMessage("The product has been " +
                    "deleted."));
        } finally {
            manager.close();
        }

        return ResponseFactory.generateOK(null);
    }

}
