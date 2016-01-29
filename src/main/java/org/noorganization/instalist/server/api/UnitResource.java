
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
import org.noorganization.instalist.server.model.Unit;


/**
 * Collection of available units.
 * 
 */
@Path("units")
public interface UnitResource {


    /**
     * Get a list of units.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({ "application/json" })
    Response getUnits(@QueryParam("changedSince") Date changedSince) throws Exception;

    /**
     * Returns the unit.
     * 
     * 
     * @param unitId
     *     
     */
    @GET
    @Path("{unitId}")
    @Produces({ "application/json" })
    Response getUnitById(@PathParam("unitId") String unitId) throws Exception;

    /**
     * Updates the unit.
     * 
     * 
     * @param unitId
     *     
     * @param entity
     *      e.g. examples/unit.example
     */
    @PUT
    @Path("{unitId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response putUnitById(@PathParam("unitId") String unitId, Unit entity) throws Exception;

    /**
     * Creates the unit.
     * 
     * 
     * @param unitId
     *     
     * @param entity
     *      e.g. examples/unit.example
     */
    @POST
    @Path("{unitId}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    Response postUnitById(@PathParam("unitId") String unitId, Unit entity) throws Exception;

    /**
     * Deletes the unit.
     * 
     * 
     * @param unitId
     *     
     */
    @DELETE
    @Path("{unitId}")
    @Produces({ "application/json" })
    Response deleteUnitById(@PathParam("unitId") String unitId) throws Exception;

}
