
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
import org.noorganization.instantListApi.model.Recipe;


/**
 * Collection of available recipes.
 * 
 */
@Path("recipes")
public interface RecipesResource {


    /**
     * Get a list of recipes.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    RecipesResource.GetRecipesResponse getRecipes(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new recipe.
     * 
     * 
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
    RecipesResource.PostRecipesResponse postRecipes(Recipe entity)
        throws Exception
    ;

    /**
     * Returns the recipe.
     * 
     * 
     * @param recipeId
     *     
     */
    @GET
    @Path("{recipeId}")
    @Produces({
        "application/json"
    })
    RecipesResource.GetRecipesByRecipeIdResponse getRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId)
        throws Exception
    ;

    /**
     * Updates the recipe.
     * 
     * 
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
    RecipesResource.PutRecipesByRecipeIdResponse putRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId, Recipe entity)
        throws Exception
    ;

    /**
     * Deletes the recipe.
     * 
     * 
     * @param recipeId
     *     
     */
    @DELETE
    @Path("{recipeId}")
    @Produces({
        "application/json"
    })
    RecipesResource.DeleteRecipesByRecipeIdResponse deleteRecipesByRecipeId(
        @PathParam("recipeId")
        String recipeId)
        throws Exception
    ;

    /**
     * Get a list of ingredients.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Path("ingredients")
    @Produces({
        "application/json"
    })
    RecipesResource.GetRecipesIngredientsResponse getRecipesIngredients(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new ingredient.
     * 
     * 
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
    RecipesResource.PostRecipesIngredientsResponse postRecipesIngredients(Ingredient entity)
        throws Exception
    ;

    public class DeleteRecipesByRecipeIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.DeleteRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.DeleteRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.DeleteRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.DeleteRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.DeleteRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

    public class GetRecipesByRecipeIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.GetRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

    public class GetRecipesIngredientsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.GetRecipesIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesIngredientsResponse(responseBuilder.build());
        }

    }

    public class GetRecipesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.GetRecipesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.GetRecipesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.GetRecipesResponse(responseBuilder.build());
        }

    }

    public class PostRecipesIngredientsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.PostRecipesIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesIngredientsResponse(responseBuilder.build());
        }

    }

    public class PostRecipesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.PostRecipesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesResponse(responseBuilder.build());
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
        public static RecipesResource.PostRecipesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PostRecipesResponse(responseBuilder.build());
        }

    }

    public class PutRecipesByRecipeIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
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
        public static RecipesResource.PutRecipesByRecipeIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.PutRecipesByRecipeIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.PutRecipesByRecipeIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
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
        public static RecipesResource.PutRecipesByRecipeIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new RecipesResource.PutRecipesByRecipeIdResponse(responseBuilder.build());
        }

    }

}
