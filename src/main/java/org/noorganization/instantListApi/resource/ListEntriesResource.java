
package org.noorganization.instantListApi.resource;

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
import org.noorganization.instantListApi.model.ListEntry;


/**
 * Collection of available listEntries.
 * 
 */
@Path("listEntries")
public interface ListEntriesResource {


    /**
     * Get a list of listEntries.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    ListEntriesResource.GetListEntriesResponse getListEntries(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new listEntry.
     * 
     * 
     * @param entity
     *      e.g. {
     *       "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
     *       "name" : "test_List1",
     *       "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241"
     *     }
     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    ListEntriesResource.PostListEntriesResponse postListEntries(ListEntry entity)
        throws Exception
    ;

    /**
     * Returns the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @GET
    @Path("{listEntryId}")
    @Produces({
        "application/json"
    })
    ListEntriesResource.GetListEntriesByListEntryIdResponse getListEntriesByListEntryId(
        @PathParam("listEntryId")
        String listEntryId)
        throws Exception
    ;

    /**
     * Updates the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     * @param entity
     *      e.g. {
     *       "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
     *       "name" : "test_List1",
     *       "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241"
     *     }
     *     
     */
    @PUT
    @Path("{listEntryId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    ListEntriesResource.PutListEntriesByListEntryIdResponse putListEntriesByListEntryId(
        @PathParam("listEntryId")
        String listEntryId, ListEntry entity)
        throws Exception
    ;

    /**
     * Deletes the listEntry.
     * 
     * 
     * @param listEntryId
     *     
     */
    @DELETE
    @Path("{listEntryId}")
    @Produces({
        "application/json"
    })
    ListEntriesResource.DeleteListEntriesByListEntryIdResponse deleteListEntriesByListEntryId(
        @PathParam("listEntryId")
        String listEntryId)
        throws Exception
    ;

    public class DeleteListEntriesByListEntryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private DeleteListEntriesByListEntryIdResponse(Response delegate) {
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
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Successfull processed by server, no content."
         *     },
         *   "status" : 204,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Successfull processed by server, no content."
         *         },
         *       "status" : 204,
         *       "success" : true
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Various changes, look into the location header fields"
         *     },
         *   "status" : 300,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Various changes, look into the location header fields"
         *         },
         *       "status" : 300,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "You have no rights to do this!"
         *     },
         *   "status" : 403,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "You have no rights to do this!"
         *         },
         *       "status" : 403,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This method is not supported!"
         *     },
         *   "status" : 405,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This method is not supported!"
         *         },
         *       "status" : 405,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The request timed out, try again!"
         *     },
         *   "status" : 408,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The request timed out, try again!"
         *         },
         *       "status" : 408,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "There is a conflict with data!"
         *     },
         *   "status" : 409,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "There is a conflict with data!"
         *         },
         *       "status" : 409,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The resource is gone!"
         *     },
         *   "status" : 410,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The resource is gone!"
         *         },
         *       "status" : 410,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The sent entity was too large!"
         *     },
         *   "status" : 413,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The sent entity was too large!"
         *         },
         *       "status" : 413,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This resource is currently locked."
         *     },
         *   "status" : 423,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This resource is currently locked."
         *         },
         *       "status" : 423,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Too many requests. Calm down!"
         *     },
         *   "status" : 429,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Too many requests. Calm down!"
         *         },
         *       "status" : 429,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Server error!"
         *     },
         *   "status" : 500,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Server error!"
         *         },
         *       "status" : 500,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.DeleteListEntriesByListEntryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.DeleteListEntriesByListEntryIdResponse(responseBuilder.build());
        }

    }

    public class GetListEntriesByListEntryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetListEntriesByListEntryIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Successfull processed by server, no content."
         *     },
         *   "status" : 204,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Successfull processed by server, no content."
         *         },
         *       "status" : 204,
         *       "success" : true
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Various changes, look into the location header fields"
         *     },
         *   "status" : 300,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Various changes, look into the location header fields"
         *         },
         *       "status" : 300,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "You have no rights to do this!"
         *     },
         *   "status" : 403,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "You have no rights to do this!"
         *         },
         *       "status" : 403,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This method is not supported!"
         *     },
         *   "status" : 405,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This method is not supported!"
         *         },
         *       "status" : 405,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The request timed out, try again!"
         *     },
         *   "status" : 408,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The request timed out, try again!"
         *         },
         *       "status" : 408,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "There is a conflict with data!"
         *     },
         *   "status" : 409,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "There is a conflict with data!"
         *         },
         *       "status" : 409,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The resource is gone!"
         *     },
         *   "status" : 410,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The resource is gone!"
         *         },
         *       "status" : 410,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The sent entity was too large!"
         *     },
         *   "status" : 413,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The sent entity was too large!"
         *         },
         *       "status" : 413,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This resource is currently locked."
         *     },
         *   "status" : 423,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This resource is currently locked."
         *         },
         *       "status" : 423,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Too many requests. Calm down!"
         *     },
         *   "status" : 429,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Too many requests. Calm down!"
         *         },
         *       "status" : 429,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Server error!"
         *     },
         *   "status" : 500,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Server error!"
         *         },
         *       "status" : 500,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesByListEntryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesByListEntryIdResponse(responseBuilder.build());
        }

    }

    public class GetListEntriesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetListEntriesResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.GetListEntriesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Successfull processed by server, no content."
         *     },
         *   "status" : 204,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Successfull processed by server, no content."
         *         },
         *       "status" : 204,
         *       "success" : true
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Various changes, look into the location header fields"
         *     },
         *   "status" : 300,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Various changes, look into the location header fields"
         *         },
         *       "status" : 300,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "You have no rights to do this!"
         *     },
         *   "status" : 403,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "You have no rights to do this!"
         *         },
         *       "status" : 403,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This method is not supported!"
         *     },
         *   "status" : 405,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This method is not supported!"
         *         },
         *       "status" : 405,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The request timed out, try again!"
         *     },
         *   "status" : 408,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The request timed out, try again!"
         *         },
         *       "status" : 408,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "There is a conflict with data!"
         *     },
         *   "status" : 409,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "There is a conflict with data!"
         *         },
         *       "status" : 409,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The resource is gone!"
         *     },
         *   "status" : 410,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The resource is gone!"
         *         },
         *       "status" : 410,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The sent entity was too large!"
         *     },
         *   "status" : 413,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The sent entity was too large!"
         *         },
         *       "status" : 413,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This resource is currently locked."
         *     },
         *   "status" : 423,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This resource is currently locked."
         *         },
         *       "status" : 423,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Too many requests. Calm down!"
         *     },
         *   "status" : 429,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Too many requests. Calm down!"
         *         },
         *       "status" : 429,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Server error!"
         *     },
         *   "status" : 500,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Server error!"
         *         },
         *       "status" : 500,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.GetListEntriesResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.GetListEntriesResponse(responseBuilder.build());
        }

    }

    public class PostListEntriesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PostListEntriesResponse(Response delegate) {
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
        public static ListEntriesResource.PostListEntriesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.PostListEntriesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.PostListEntriesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
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
        public static ListEntriesResource.PostListEntriesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Successfull processed by server, no content."
         *     },
         *   "status" : 204,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Successfull processed by server, no content."
         *         },
         *       "status" : 204,
         *       "success" : true
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Various changes, look into the location header fields"
         *     },
         *   "status" : 300,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Various changes, look into the location header fields"
         *         },
         *       "status" : 300,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "You have no rights to do this!"
         *     },
         *   "status" : 403,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "You have no rights to do this!"
         *         },
         *       "status" : 403,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This method is not supported!"
         *     },
         *   "status" : 405,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This method is not supported!"
         *         },
         *       "status" : 405,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The request timed out, try again!"
         *     },
         *   "status" : 408,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The request timed out, try again!"
         *         },
         *       "status" : 408,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "There is a conflict with data!"
         *     },
         *   "status" : 409,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "There is a conflict with data!"
         *         },
         *       "status" : 409,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The resource is gone!"
         *     },
         *   "status" : 410,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The resource is gone!"
         *         },
         *       "status" : 410,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The sent entity was too large!"
         *     },
         *   "status" : 413,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The sent entity was too large!"
         *         },
         *       "status" : 413,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This resource is currently locked."
         *     },
         *   "status" : 423,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This resource is currently locked."
         *         },
         *       "status" : 423,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Too many requests. Calm down!"
         *     },
         *   "status" : 429,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Too many requests. Calm down!"
         *         },
         *       "status" : 429,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Server error!"
         *     },
         *   "status" : 500,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Server error!"
         *         },
         *       "status" : 500,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PostListEntriesResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PostListEntriesResponse(responseBuilder.build());
        }

    }

    public class PutListEntriesByListEntryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PutListEntriesByListEntryIdResponse(Response delegate) {
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
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
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
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Successfull processed by server, no content."
         *     },
         *   "status" : 204,
         *   "success" : true
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Successfull processed by server, no content."
         *         },
         *       "status" : 204,
         *       "success" : true
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Various changes, look into the location header fields"
         *     },
         *   "status" : 300,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Various changes, look into the location header fields"
         *         },
         *       "status" : 300,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "You have no rights to do this!"
         *     },
         *   "status" : 403,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "You have no rights to do this!"
         *         },
         *       "status" : 403,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This method is not supported!"
         *     },
         *   "status" : 405,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This method is not supported!"
         *         },
         *       "status" : 405,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The request timed out, try again!"
         *     },
         *   "status" : 408,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The request timed out, try again!"
         *         },
         *       "status" : 408,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "There is a conflict with data!"
         *     },
         *   "status" : 409,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "There is a conflict with data!"
         *         },
         *       "status" : 409,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The resource is gone!"
         *     },
         *   "status" : 410,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The resource is gone!"
         *         },
         *       "status" : 410,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "The sent entity was too large!"
         *     },
         *   "status" : 413,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "The sent entity was too large!"
         *         },
         *       "status" : 413,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "This resource is currently locked."
         *     },
         *   "status" : 423,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "This resource is currently locked."
         *         },
         *       "status" : 423,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Too many requests. Calm down!"
         *     },
         *   "status" : 429,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Too many requests. Calm down!"
         *         },
         *       "status" : 429,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         *   "data" :
         *     {
         *       "msg" : "Server error!"
         *     },
         *   "status" : 500,
         *   "success" : false
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data" :
         *         {
         *           "msg" : "Server error!"
         *         },
         *       "status" : 500,
         *       "success" : false
         *     }
         *     
         */
        public static ListEntriesResource.PutListEntriesByListEntryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListEntriesResource.PutListEntriesByListEntryIdResponse(responseBuilder.build());
        }

    }

}
