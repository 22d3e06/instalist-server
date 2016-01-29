
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
import org.noorganization.instalist.server.apiOLD.model.Ingredient;
import org.noorganization.instalist.server.apiOLD.model.Recipe;


/**
 * Collection of available recipes.
 * 
 */
@Path("recipes")
public interface IRecipesResource {


    /**
     * Get a list of recipes.
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
    IRecipesResource.GetRecipesResponse getRecipes(
        @QueryParam("changedSince")
        Date changedSince,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Add a new recipe.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param entity
     *      e.g. {

     *       "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

     *       "name" : "test_Recipe"

     *     }

     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IRecipesResource.PostRecipesResponse postRecipes(
        @QueryParam("accessToken")
        String accessToken, Recipe entity)
        throws Exception
    ;

    /**
     * Returns the recipe.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param recipeId
     *     
     */
    @GET
    @Path("{recipeId}")
    @Produces({
        "application/json"
    })
    IRecipesResource.GetRecipesByRecipeIdResponse getRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

    /**
     * Updates the recipe.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param recipeId
     *     
     * @param entity
     *      e.g. {

     *       "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

     *       "name" : "test_Recipe"

     *     }

     *     
     */
    @PUT
    @Path("{recipeId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IRecipesResource.PutRecipesByRecipeIdResponse putRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId,
        @QueryParam("accessToken")
        String accessToken, Recipe entity)
        throws Exception
    ;

    /**
     * Deletes the recipe.
     * 
     * 
     * @param accessToken
     *     An access token is required for secured routes
     * @param recipeId
     *     
     */
    @DELETE
    @Path("{recipeId}")
    @Produces({
        "application/json"
    })
    IRecipesResource.DeleteRecipesByRecipeIdResponse deleteRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId,
        @QueryParam("accessToken")
        String accessToken)
        throws Exception
    ;

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
    @Path("ingredients")
    @Produces({
        "application/json"
    })
    IRecipesResource.GetRecipesIngredientsResponse getRecipesIngredients(
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
    @Path("ingredients")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IRecipesResource.PostRecipesIngredientsResponse postRecipesIngredients(
        @QueryParam("accessToken")
        String accessToken, Ingredient entity)
        throws Exception
    ;

    public class DeleteRecipesByRecipeIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private DeleteRecipesByRecipeIdResponse(Response delegate) {
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
        public static IRecipesResource.DeleteRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.DeleteRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.DeleteRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.DeleteRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

    public class GetRecipesByRecipeIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private GetRecipesByRecipeIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {

         * "status" : 200,

         * "success" : true,

         * "data" :

         *   {

         *     "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "name" : "test_Recipe",

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

         *         "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "name" : "test_Recipe",

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *     }

         *     
         */
        public static IRecipesResource.GetRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

    public class GetRecipesIngredientsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private GetRecipesIngredientsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {

         * "status" : 200,

         * "success" : true,

         * "count" : 1,

         * "data" :

         *   [

         *   {

         *     "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *     "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *     "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "amount" : 0.5,

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

         *     "count" : 1,

         *     "data" :

         *       [

         *       {

         *         "id" : "8b4343ba-273e-417c-b89d-b7abab780cb2",

         *         "productId" : "87c03314-8733-4e91-b356-0f1d37dd4eb8",

         *         "recipeId" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "amount" : 0.5,

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *       ]

         *     }

         *     
         *     
         */
        public static IRecipesResource.GetRecipesIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
        }

    }

    public class GetRecipesResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private GetRecipesResponse(Response delegate) {
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

         *     "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *     "name" : "test_Recipe",

         *     "lastChanged": 2016-01-19T11:54:07+01:00

         *   },

         *   {

         *     "id" : "c937bad6-7121-441b-bf3c-c6c0971bb9fe",

         *     "name" : "test_Recipe2",

         *     "lastChanged": 2016-01-19T11:54:07+01:00

         *   }

         * 

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

         *         "id" : "ce3c12e8-747a-4745-abd7-411e2a21d52c",

         *         "name" : "test_Recipe",

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       },

         *       {

         *         "id" : "c937bad6-7121-441b-bf3c-c6c0971bb9fe",

         *         "name" : "test_Recipe2",

         *         "lastChanged": 2016-01-19T11:54:07+01:00

         *       }

         *     

         *       ]

         *     }

         *     
         *     
         */
        public static IRecipesResource.GetRecipesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.GetRecipesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.GetRecipesResponse(responseBuilder.build());
        }

    }

    public class PostRecipesIngredientsResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private PostRecipesIngredientsResponse(Response delegate) {
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
        public static IRecipesResource.PostRecipesIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
        }

    }

    public class PostRecipesResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private PostRecipesResponse(Response delegate) {
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
        public static IRecipesResource.PostRecipesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static IRecipesResource.PostRecipesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PostRecipesResponse(responseBuilder.build());
        }

    }

    public class PutRecipesByRecipeIdResponse
        extends org.noorganization.instalist.server.apiOLD.resource.support.ResponseWrapper
    {


        private PutRecipesByRecipeIdResponse(Response delegate) {
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
        public static IRecipesResource.PutRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.PutRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.PutRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static IRecipesResource.PutRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IRecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

}
