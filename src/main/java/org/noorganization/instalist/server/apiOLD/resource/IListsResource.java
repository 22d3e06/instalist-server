
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
import org.noorganization.instalist.server.apiOLD.model.ShoppingList;


/**
 * Collection of available lists.
 * 
 */
@Path("lists")
public interface IListsResource {


    /**
     * Get a list of lists.
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
    IListsResource.GetListsResponse getLists(
        @QueryParam("changedSince")
        Date changedSince,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Add a new list.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
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
    IListsResource.PostListsResponse postLists(
        @QueryParam("accessToken")
        String accessToken, ShoppingList entity)
        throws Exception
    ;

    /**
     * Returns the list.
     * 
     * 
     * @param listId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @GET
    @Path("{listId}")
    @Produces({
        "application/json"
    })
    IListsResource.GetListsByListIdResponse getListsByListId(
        @PathParam("listId")
        String listId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Updates the list.
     * 
     * 
     * @param listId
     *     
     * @param accessToken
     *     An access token is required for secured routes
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
    IListsResource.PutListsByListIdResponse putListsByListId(
        @PathParam("listId")
        String listId,
        @QueryParam("accessToken")
        String accessToken, ShoppingList entity)
        throws Exception
    ;

    /**
     * Deletes the list.
     * 
     * 
     * @param listId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @DELETE
    @Path("{listId}")
    @Produces({
        "application/json"
    })
    IListsResource.DeleteListsByListIdResponse deleteListsByListId(
        @PathParam("listId")
        String listId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    public class DeleteListsByListIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
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
        public static IListsResource.DeleteListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.DeleteListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.DeleteListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.DeleteListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.DeleteListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.DeleteListsByListIdResponse(responseBuilder.build());
        }

    }

    public class GetListsByListIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
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
        public static IListsResource.GetListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.GetListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.GetListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.GetListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsByListIdResponse(responseBuilder.build());
        }

    }

    public class GetListsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
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
        public static IListsResource.GetListsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsResponse(responseBuilder.build());
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
        public static IListsResource.GetListsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsResponse(responseBuilder.build());
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
        public static IListsResource.GetListsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsResponse(responseBuilder.build());
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
        public static IListsResource.GetListsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.GetListsResponse(responseBuilder.build());
        }

    }

    public class PostListsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
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
        public static IListsResource.PostListsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PostListsResponse(responseBuilder.build());
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
        public static IListsResource.PostListsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PostListsResponse(responseBuilder.build());
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
        public static IListsResource.PostListsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PostListsResponse(responseBuilder.build());
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
        public static IListsResource.PostListsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PostListsResponse(responseBuilder.build());
        }

    }

    public class PutListsByListIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
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
        public static IListsResource.PutListsByListIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.PutListsByListIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.PutListsByListIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PutListsByListIdResponse(responseBuilder.build());
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
        public static IListsResource.PutListsByListIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IListsResource.PutListsByListIdResponse(responseBuilder.build());
        }

    }

}
