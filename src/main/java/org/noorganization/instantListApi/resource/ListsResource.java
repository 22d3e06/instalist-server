
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
import org.noorganization.instantListApi.model.ShoppingList;


/**
 * Collection of available lists.
 * 
 */
@Path("lists")
public interface ListsResource {


    /**
     * Get a list of lists.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    ListsResource.GetListsResponse getLists(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new list.
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
    ListsResource.PostListsResponse postLists(ShoppingList entity)
        throws Exception
    ;

    /**
     * Returns the list.
     * 
     * 
     * @param listId
     *     
     */
    @GET
    @Path("{listId}")
    @Produces({
        "application/json"
    })
    ListsResource.GetListsByListIdResponse getListsByListId(
        @PathParam("listId")
        String listId)
        throws Exception
    ;

    /**
     * Updates the list.
     * 
     * 
     * @param listId
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
    @Path("{listId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    ListsResource.PutListsByListIdResponse putListsByListId(
        @PathParam("listId")
        String listId, ShoppingList entity)
        throws Exception
    ;

    /**
     * Deletes the list.
     * 
     * 
     * @param listId
     *     
     */
    @DELETE
    @Path("{listId}")
    @Produces({
        "application/json"
    })
    ListsResource.DeleteListsByListIdResponse deleteListsByListId(
        @PathParam("listId")
        String listId)
        throws Exception
    ;

    public class DeleteListsByListIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private DeleteListsByListIdResponse(Response delegate) {
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
        public static ListsResource.DeleteListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.DeleteListsByListIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.DeleteListsByListIdResponse(responseBuilder.build());
        }

    }

    public class GetListsByListIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetListsByListIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "status" : 200,
         * "success" : true,
         * "data" :
         *   {
         *     "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
         *     "name" : "test_List1",
         *     "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
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
         *         "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
         *         "name" : "test_List1",
         *         "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *         "lastChanged": 2016-01-19T11:54:07+01:00
         *       }
         *     }
         *     
         */
        public static ListsResource.GetListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.GetListsByListIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsByListIdResponse(responseBuilder.build());
        }

    }

    public class GetListsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetListsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "status" : 200,
         * "success" : true,
         * "count" : 2,
         * "data" :
         *   [
         *   {
         *     "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
         *     "name" : "test_List1",
         *     "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *     "lastChanged": 2016-01-19T11:54:07+01:00
         *   },
         *   {
         *     "id" : "7669efef-264c-4801-8132-221451b95831",
         *     "name" : "test_List2",
         *     "categoryId" : "340fa987-72e7-4939-b2da-138ffc2ae5e0",
         *     "lastChanged": 2016-01-19T11:54:07+01:00
         *   }
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
         *       {
         *         "id" : "1634a3d5-e240-4039-b265-653d20b679e0",
         *         "name" : "test_List1",
         *         "categoryId" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *         "lastChanged": 2016-01-19T11:54:07+01:00
         *       },
         *       {
         *         "id" : "7669efef-264c-4801-8132-221451b95831",
         *         "name" : "test_List2",
         *         "categoryId" : "340fa987-72e7-4939-b2da-138ffc2ae5e0",
         *         "lastChanged": 2016-01-19T11:54:07+01:00
         *       }
         *       ]
         *     }
         *     
         *     
         */
        public static ListsResource.GetListsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
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
        public static ListsResource.GetListsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.GetListsResponse(responseBuilder.build());
        }

    }

    public class PostListsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PostListsResponse(Response delegate) {
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
        public static ListsResource.PostListsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
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
        public static ListsResource.PostListsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PostListsResponse(responseBuilder.build());
        }

    }

    public class PutListsByListIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PutListsByListIdResponse(Response delegate) {
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
        public static ListsResource.PutListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static ListsResource.PutListsByListIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new ListsResource.PutListsByListIdResponse(responseBuilder.build());
        }

    }

}
