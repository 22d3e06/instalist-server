
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
import org.noorganization.instantListApi.model.Ingredient;


/**
 * Collection of available ingredients.
 * 
 */
@Path("ingredients")
public interface IIngredientsResource {


    /**
     * Get a list of ingredients.
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
    IIngredientsResource.GetIngredientsResponse getIngredients(
        @QueryParam("changedSince")
        Date changedSince,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Add a new ingredient.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param entity
     *      e.g. {

     *       "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

     *       "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

     *       "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

     *       "amount" : 0.5

     *     }

     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IIngredientsResource.PostIngredientsResponse postIngredients(
        @QueryParam("accessToken")
        String accessToken, Ingredient entity)
        throws Exception
    ;

    /**
     * Returns the ingredient.
     * 
     * 
     * @param ingredientId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @GET
    @Path("{ingredientId}")
    @Produces({
        "application/json"
    })
    IIngredientsResource.GetIngredientsByIngredientIdResponse getIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Updates the ingredient.
     * 
     * 
     * @param ingredientId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     * @param entity
     *      e.g. {

     *       "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

     *       "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

     *       "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

     *       "amount" : 0.5

     *     }

     *     
     */
    @PUT
    @Path("{ingredientId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IIngredientsResource.PutIngredientsByIngredientIdResponse putIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId,
        @QueryParam("accessToken")
        String accessToken, Ingredient entity)
        throws Exception
    ;

    /**
     * Deletes the ingredient.
     * 
     * 
     * @param ingredientId
     *     
     * @param accessToken
     *     An access token is required for secured routes
     */
    @DELETE
    @Path("{ingredientId}")
    @Produces({
        "application/json"
    })
    IIngredientsResource.DeleteIngredientsByIngredientIdResponse deleteIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    public class DeleteIngredientsByIngredientIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private DeleteIngredientsByIngredientIdResponse(Response delegate) {
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
        public static IIngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
        }

    }

    public class GetIngredientsByIngredientIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetIngredientsByIngredientIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {

         * "status" : 200,

         * "success" : true,

         * "data" :

         *   {

         *     "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *     "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *     "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "amount" : 0.5,

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

         *         "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *         "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *         "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "amount" : 0.5,

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *     }

         *     
         */
        public static IIngredientsResource.GetIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
        }

    }

    public class GetIngredientsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetIngredientsResponse(Response delegate) {
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

         *     "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *     "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *     "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "amount" : 0.5,

         *     "lastChanged": 2016-01-19T11:54:07+01:00

         *   },

         *   {

         *     "id" : "f86f1c7c-0e49-4af2-82df-1f50600590ff",

         *     "productId" : "de14221d-6958-4cf1-b099-7c6a67b2125d",

         *     "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "amount" : 2.5,

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

         *         "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *         "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *         "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "amount" : 0.5,

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       },

         *       {

         *         "id" : "f86f1c7c-0e49-4af2-82df-1f50600590ff",

         *         "productId" : "de14221d-6958-4cf1-b099-7c6a67b2125d",

         *         "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "amount" : 2.5,

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *       ]

         *     }

         *     
         *     
         */
        public static IIngredientsResource.GetIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.GetIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.GetIngredientsResponse(responseBuilder.build());
        }

    }

    public class PostIngredientsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PostIngredientsResponse(Response delegate) {
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
        public static IIngredientsResource.PostIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.PostIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.PostIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IIngredientsResource.PostIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PostIngredientsResponse(responseBuilder.build());
        }

    }

    public class PutIngredientsByIngredientIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PutIngredientsByIngredientIdResponse(Response delegate) {
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
        public static IIngredientsResource.PutIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.PutIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.PutIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IIngredientsResource.PutIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IIngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
        }

    }

}
