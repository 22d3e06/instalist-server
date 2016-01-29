
package org.noorganization.instalist.server.apiOLD.resource;

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
import javax.ws.rs.core.StreamingOutput;
import org.noorganization.instalist.server.apiOLD.model.Unit;


/**
 * Collection of available units.
 * 
 */
@Path("units")
public interface IUnitsResource {


    /**
     * Get a list of units.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     * @param accessToken
     *     An access token is required for secured routes
     */
    @GET
    @Produces({
        "application/json"
    })
    IUnitsResource.GetUnitsResponse getUnits(
        @QueryParam("changedSince")
        Date changedSince,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Add a new unit.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param entity
     *      e.g. {

     *       "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

     *       "name" : "test_Unit1"

     *     }

     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IUnitsResource.PostUnitsResponse postUnits(
        @QueryParam("accessToken")
        String accessToken, Unit entity)
        throws Exception
    ;

    /**
     * Returns the unit.
     * 
     * 
     * @param unitId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @GET
    @Path("{unitId}")
    @Produces({
        "application/json"
    })
    IUnitsResource.GetUnitsByUnitIdResponse getUnitsByUnitId(
        @PathParam("unitId")
        String unitId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Updates the unit.
     * 
     * 
     * @param unitId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     * @param entity
     *      e.g. {

     *       "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

     *       "name" : "test_Unit1"

     *     }

     *     
     */
    @PUT
    @Path("{unitId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IUnitsResource.PutUnitsByUnitIdResponse putUnitsByUnitId(
        @PathParam("unitId")
        String unitId,
        @QueryParam("accessToken")
        String accessToken, Unit entity)
        throws Exception
    ;

    /**
     * Deletes the unit.
     * 
     * 
     * @param unitId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @DELETE
    @Path("{unitId}")
    @Produces({
        "application/json"
    })
    IUnitsResource.DeleteUnitsByUnitIdResponse deleteUnitsByUnitId(
        @PathParam("unitId")
        String unitId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    public class DeleteUnitsByUnitIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private DeleteUnitsByUnitIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Deletion without error"
         *     },
         *   "status" : 200,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Deletion without error"
         *         },
         *       "status" : 200,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.DeleteUnitsByUnitIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.DeleteUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Update with error"
         *     },
         *   "status" : 400,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Update with error"
         *         },
         *       "status" : 400,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.DeleteUnitsByUnitIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.DeleteUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The element has been created.",
         *       "relativePath": "/recipes/someuuid"
         *     },
         *   "status" : 201,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The element has been created.",
         *           "relativePath": "/recipes/someuuid"
         *         },
         *       "status" : 201,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.DeleteUnitsByUnitIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.DeleteUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Accepted by server. But processed later."
         *     },
         *   "status" : 202,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Accepted by server. But processed later."
         *         },
         *       "status" : 202,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.DeleteUnitsByUnitIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.DeleteUnitsByUnitIdResponse(responseBuilder.build());
        }

    }

    public class GetUnitsByUnitIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private GetUnitsByUnitIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {

         * "status" : 200,

         * "success" : true,

         * "data" :

         *   {

         *     "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

         *     "name" : "test_Unit1",

         *     "lastChanged": 2016-01-19T11:54:07+01:00

         *   }

         * }

         * 
         * 
         * @param entity
         *     {

         *     "status" : 200,

         *     "success" : true,

         *     "data" :

         *       {

         *         "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

         *         "name" : "test_Unit1",

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *     }

         *     
         */
        public static IUnitsResource.GetUnitsByUnitIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Get with error."
         *     },
         *   "status" : 400,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Get with error."
         *         },
         *       "status" : 400,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsByUnitIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The element has been created.",
         *       "relativePath": "/recipes/someuuid"
         *     },
         *   "status" : 201,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The element has been created.",
         *           "relativePath": "/recipes/someuuid"
         *         },
         *       "status" : 201,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsByUnitIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Accepted by server. But processed later."
         *     },
         *   "status" : 202,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Accepted by server. But processed later."
         *         },
         *       "status" : 202,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsByUnitIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsByUnitIdResponse(responseBuilder.build());
        }

    }

    public class GetUnitsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private GetUnitsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {

         * "status" : 200,

         * "success" : true,

         * "count" : 2,

         * "data" :

         *   [

         *     {

         *       "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

         *       "name" : "test_Unit1",

         *       "lastChanged": 2016-01-19T11:54:07+01:00

         *     },

         *     {

         *       "id" : "7359a706-3303-497e-b6bb-11f34e73e7c4",

         *       "name" : "test_Unit2",

         *       "lastChanged": 2016-01-19T11:54:07+01:00

         *     }

         *   ]

         * }

         * 
         * 
         * 
         * @param entity
         *     {

         *     "status" : 200,

         *     "success" : true,

         *     "count" : 2,

         *     "data" :

         *       [

         *         {

         *           "id" : "079d3bc8-ee0c-47de-a9c5-bb673e980892",

         *           "name" : "test_Unit1",

         *           "lastChanged": 2016-01-19T11:54:07+01:00

         *         },

         *         {

         *           "id" : "7359a706-3303-497e-b6bb-11f34e73e7c4",

         *           "name" : "test_Unit2",

         *           "lastChanged": 2016-01-19T11:54:07+01:00

         *         }

         *       ]

         *     }

         *     
         *     
         */
        public static IUnitsResource.GetUnitsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Get with error"
         *     },
         *   "status" : 400,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Get with error"
         *         },
         *       "status" : 400,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The element has been created.",
         *       "relativePath": "/recipes/someuuid"
         *     },
         *   "status" : 201,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The element has been created.",
         *           "relativePath": "/recipes/someuuid"
         *         },
         *       "status" : 201,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Accepted by server. But processed later."
         *     },
         *   "status" : 202,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Accepted by server. But processed later."
         *         },
         *       "status" : 202,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.GetUnitsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.GetUnitsResponse(responseBuilder.build());
        }

    }

    public class PostUnitsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private PostUnitsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Creation without error"
         *     },
         *   "status" : 200,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Creation without error"
         *         },
         *       "status" : 200,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.PostUnitsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PostUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Creation with error"
         *     },
         *   "status" : 400,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Creation with error"
         *         },
         *       "status" : 400,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.PostUnitsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PostUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The element has been created.",
         *       "relativePath": "/recipes/someuuid"
         *     },
         *   "status" : 201,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The element has been created.",
         *           "relativePath": "/recipes/someuuid"
         *         },
         *       "status" : 201,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.PostUnitsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PostUnitsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Accepted by server. But processed later."
         *     },
         *   "status" : 202,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Accepted by server. But processed later."
         *         },
         *       "status" : 202,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.PostUnitsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PostUnitsResponse(responseBuilder.build());
        }

    }

    public class PutUnitsByUnitIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private PutUnitsByUnitIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Update without error"
         *     },
         *   "status" : 200,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Update without error"
         *         },
         *       "status" : 200,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.PutUnitsByUnitIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PutUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "data" :
         *   {
         *     "msg" : "Update with error"
         *   },
         * "status" : 400,
         * "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *     "data" :
         *       {
         *         "msg" : "Update with error"
         *       },
         *     "status" : 400,
         *     "success" : false
         *     }
         *     
         */
        public static IUnitsResource.PutUnitsByUnitIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PutUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The element has been created.",
         *       "relativePath": "/recipes/someuuid"
         *     },
         *   "status" : 201,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The element has been created.",
         *           "relativePath": "/recipes/someuuid"
         *         },
         *       "status" : 201,
         *       "success" : true
         *     }
         *     
         */
        public static IUnitsResource.PutUnitsByUnitIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PutUnitsByUnitIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Accepted by server. But processed later."
         *     },
         *   "status" : 202,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Accepted by server. But processed later."
         *         },
         *       "status" : 202,
         *       "success" : false
         *     }
         *     
         */
        public static IUnitsResource.PutUnitsByUnitIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IUnitsResource.PutUnitsByUnitIdResponse(responseBuilder.build());
        }

    }

}
