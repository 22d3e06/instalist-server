
package org.noorganization.instalist.server.api;

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

import org.noorganization.instalist.comm.message.UnitInfo;
import org.noorganization.instalist.server.TokenSecured;


/**
 * Collection of available units.
 * 
 */
@Path("/groups/{groupid}/units")
public class UnitResource {


    /**
     * Get a list of units.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @TokenSecured
    @Produces({ "application/json" })
    public Response getUnits(@PathParam("groupid") int _groupId,
                             @QueryParam("changedsince") String changedSince) throws Exception {
        return null;
    }

    /**
     * Returns the unit.
     * 
     * 
     * @param unitId
     *     
     */
    @GET
    @TokenSecured
    @Path("{unituuid}")
    @Produces({ "application/json" })
    public Response getUnitById(@PathParam("groupid") int _groupId,
                                @PathParam("unituuid") String _unitUUID) throws Exception {
        return null;
    }

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
    @TokenSecured
    @Path("{unituuid}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response putUnitById(@PathParam("groupid") int _groupId,
                                @PathParam("unituuid") String _unitUUID,
                                UnitInfo _entity) throws Exception {
        return null;
    }

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
    @TokenSecured
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postUnit(@PathParam("groupid") int _groupId, UnitInfo _entity) throws
            Exception {
        return null;
    }

    /**
     * Deletes the unit.
     * 
     * 
     * @param unitId
     *     
     */
    @DELETE
    @TokenSecured
    @Path("{unituuid}")
    @Produces({ "application/json" })
    public Response deleteUnit(@PathParam("groupid") int _groupId,
                               @PathParam("unituuid") String _unitUUID) throws Exception {
        return null;
    }

}
