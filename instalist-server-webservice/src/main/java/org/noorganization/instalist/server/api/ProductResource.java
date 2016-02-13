
package org.noorganization.instalist.server.api;

import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.noorganization.instalist.comm.message.ProductInfo;
import org.noorganization.instalist.server.TokenSecured;

@Path("/groups/{groupid}/products")
public class ProductResource {


    /**
     * Get a list of products.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getProducts(@PathParam("groupid") int _groupId,
                                @QueryParam("changedSince") String _changedSince) throws Exception {
        return null;
    }

    /**
     * Returns the product.
     * 
     * 
     * @param productId
     *     
     */
    @GET
    @TokenSecured
    @Path("{productuuid}")
    @Produces({ "application/json" })
    public Response getProduct(@PathParam("groupid") int _groupId,
                               @PathParam("productuuid") String _productUUID) throws Exception {
        return null;
    }

    /**
     * Updates the product.
     * 
     * 
     * @param productId
     *     
     * @param entity
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
        return null;
    }

    /**
     * Creates the product.
     * 
     * 
     * @param productId
     *     
     * @param entity
     *      e.g. examples/product.example
     */
    @POST
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postProduct(@PathParam("groupid") int _groupId,
                                ProductInfo entity) throws Exception {
        return null;
    }

    /**
     * Deletes the product.
     * 
     * 
     * @param productId
     *     
     */
    @DELETE
    @TokenSecured
    @Path("{productuuid}")
    @Produces({ "application/json" })
    public Response deleteProductById(@PathParam("groupid") int _groupId,
                                      @PathParam("productuuid") String _productUUID)
            throws Exception {
        return null;
    }

}
