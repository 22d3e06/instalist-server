
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
import org.noorganization.instalist.server.message.Product;


/**
 * Collection of available products.
 * 
 */
@Path("products")
public interface ProductResource {


    /**
     * Get a list of products.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getProducts(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the product.
     * 
     * 
     * @param productId
     *     
     */
    @GET
    @Path("{productId}")
    @Produces({ "application/json" })
    Response getProductById(@PathParam("productId") String productId) throws Exception;

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
    @Path("{productId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putProductById(@PathParam("productId") String productId, Product entity)
            throws Exception;

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
    @Path("{productId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postProductById(@PathParam("productId") String productId, Product entity)
            throws Exception;

    /**
     * Deletes the product.
     * 
     * 
     * @param productId
     *     
     */
    @DELETE
    @Path("{productId}")
    @Produces({ "application/json" })
    Response deleteProductById(@PathParam("productId") String productId) throws Exception;

}
