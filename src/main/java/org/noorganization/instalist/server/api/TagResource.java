
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
import org.noorganization.instalist.server.message.Tag;


/**
 * Collection of available tags.
 * 
 */
@Path("tags")
public interface TagResource {


    /**
     * Get a list of tags.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getTags(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the tag.
     * 
     * 
     * @param tagId
     *     
     */
    @GET
    @Path("{tagId}")
    @Produces({ "application/json" })
    Response getTagById(@PathParam("tagId") String tagId) throws Exception;

    /**
     * Updates the tag.
     * 
     * 
     * @param tagId
     *     
     * @param entity
     *      e.g. examples/tag.example
     */
    @PUT
    @Path("{tagId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putTagById(@PathParam("tagId") String tagId, Tag entity) throws Exception;

    /**
     * Creates the tag.
     * 
     * 
     * @param tagId
     *     
     * @param entity
     *      e.g. examples/tag.example
     */
    @POST
    @Path("{tagId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postTagById(@PathParam("tagId") String tagId, Tag entity) throws Exception;

    /**
     * Deletes the tag.
     * 
     * 
     * @param tagId
     *     
     */
    @DELETE
    @Path("{tagId}")
    @Produces({ "application/json" })
    Response deleteTagById(@PathParam("tagId") String tagId) throws Exception;

}
