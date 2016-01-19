
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
public interface IngredientsResource {


    /**
     * Get a list of ingredients.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    IngredientsResource.GetIngredientsResponse getIngredients(
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
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IngredientsResource.PostIngredientsResponse postIngredients(Ingredient entity)
        throws Exception
    ;

    /**
     * Returns the ingredient.
     * 
     * 
     * @param ingredientId
     *     
     */
    @GET
    @Path("{ingredientId}")
    @Produces({
        "application/json"
    })
    IngredientsResource.GetIngredientsByIngredientIdResponse getIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId)
        throws Exception
    ;

    /**
     * Updates the ingredient.
     * 
     * 
     * @param ingredientId
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
    @PUT
    @Path("{ingredientId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    IngredientsResource.PutIngredientsByIngredientIdResponse putIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId, Ingredient entity)
        throws Exception
    ;

    /**
     * Deletes the ingredient.
     * 
     * 
     * @param ingredientId
     *     
     */
    @DELETE
    @Path("{ingredientId}")
    @Produces({
        "application/json"
    })
    IngredientsResource.DeleteIngredientsByIngredientIdResponse deleteIngredientsByIngredientId(
        @PathParam("ingredientId")
        String ingredientId)
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.DeleteIngredientsByIngredientIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.DeleteIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsByIngredientIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.GetIngredientsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.GetIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PostIngredientsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PostIngredientsResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
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
        public static IngredientsResource.PutIngredientsByIngredientIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new IngredientsResource.PutIngredientsByIngredientIdResponse(responseBuilder.build());
        }

    }

}
