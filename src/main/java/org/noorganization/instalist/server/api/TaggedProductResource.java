
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
import org.noorganization.instalist.server.model.TaggedProduct;


/**
 * Collection of available taggedProducts.
 * 
 */
@Path("taggedProducts")
public interface TaggedProductResource {


    /**
     * Get a list of taggedProducts.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getTaggedProducts(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the taggedProduct.
     * 
     * 
     * @param taggedProductId
     *     
     */
    @GET
    @Path("{taggedProductId}")
    @Produces({ "application/json" })
    Response getTaggedProductById(@PathParam("taggedProductId") String taggedProductId)
            throws Exception;

    /**
     * Updates the taggedProduct.
     * 
     * 
     * @param entity
     *      e.g. examples/taggedProduct.example
     * @param taggedProductId
     *     
     */
    @PUT
    @Path("{taggedProductId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putTaggedProductById(@PathParam("taggedProductId") String taggedProductId,
                                                TaggedProduct entity) throws Exception;
    ;

    /**
     * Creates the taggedProduct.
     * 
     * 
     * @param entity
     *      e.g. examples/taggedProduct.example
     * @param taggedProductId
     *     
     */
    @POST
    @Path("{taggedProductId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postTaggedProductById(@PathParam("taggedProductId") String taggedProductId,
                                    TaggedProduct entity) throws Exception;

    /**
     * Deletes the taggedProduct.
     * 
     * 
     * @param taggedProductId
     *     
     */
    @DELETE
    @Path("{taggedProductId}")
    @Produces({ "application/json" })
    Response deleteTaggedProductById(@PathParam("taggedProductId") String taggedProductId)
            throws Exception;

}
