
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
import org.noorganization.instantListApi.model.Category;


/**
 * Collection of available categories.
 * 
 */
@Path("categories")
public interface CategoriesResource {


    /**
     * Get a list of categories.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    CategoriesResource.GetCategoriesResponse getCategories(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new category.
     * 
     * 
     * @param entity
     *      e.g. {
     *       "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
     *       "name" : "test_Category"
     *     }
     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    CategoriesResource.PostCategoriesResponse postCategories(Category entity)
        throws Exception
    ;

    /**
     * Returns the category.
     * 
     * 
     * @param categoryId
     *     
     */
    @GET
    @Path("{categoryId}")
    @Produces({
        "application/json"
    })
    CategoriesResource.GetCategoriesByCategoryIdResponse getCategoriesByCategoryId(
        @PathParam("categoryId")
        String categoryId)
        throws Exception
    ;

    /**
     * Updates the category.
     * 
     * 
     * @param categoryId
     *     
     * @param entity
     *      e.g. {
     *       "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
     *       "name" : "test_Category"
     *     }
     *     
     */
    @PUT
    @Path("{categoryId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    CategoriesResource.PutCategoriesByCategoryIdResponse putCategoriesByCategoryId(
        @PathParam("categoryId")
        String categoryId, Category entity)
        throws Exception
    ;

    /**
     * Deletes the category.
     * 
     * 
     * @param categoryId
     *     
     */
    @DELETE
    @Path("{categoryId}")
    @Produces({
        "application/json"
    })
    CategoriesResource.DeleteCategoriesByCategoryIdResponse deleteCategoriesByCategoryId(
        @PathParam("categoryId")
        String categoryId)
        throws Exception
    ;

    public class DeleteCategoriesByCategoryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private DeleteCategoriesByCategoryIdResponse(Response delegate) {
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.DeleteCategoriesByCategoryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.DeleteCategoriesByCategoryIdResponse(responseBuilder.build());
        }

    }

    public class GetCategoriesByCategoryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetCategoriesByCategoryIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "status" : 200,
         * "success" : true,
         * "data" :
         *     {
         *       "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *       "name" : "test_Category1",
         *       "lastChanged": 2016-01-19T11:54:07+01:00
         *     }
         * }
         * 
         * 
         * @param entity
         *     {
         *     "status" : 200,
         *     "success" : true,
         *     "data" :
         *         {
         *           "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *           "name" : "test_Category1",
         *           "lastChanged": 2016-01-19T11:54:07+01:00
         *         }
         *     }
         *     
         */
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesByCategoryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesByCategoryIdResponse(responseBuilder.build());
        }

    }

    public class GetCategoriesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetCategoriesResponse(Response delegate) {
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
         *       "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *       "name" : "test_Category1",
         *       "lastChanged": 2016-01-19T11:54:07+01:00
         *     },
         *     {
         *       "id" : "340fa987-72e7-4939-b2da-138ffc2ae5e0",
         *       "name" : "test_Category2",
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
         *           "id" : "a7b3d2b4-5f5a-461f-a80d-04e48af0b241",
         *           "name" : "test_Category1",
         *           "lastChanged": 2016-01-19T11:54:07+01:00
         *         },
         *         {
         *           "id" : "340fa987-72e7-4939-b2da-138ffc2ae5e0",
         *           "name" : "test_Category2",
         *           "lastChanged": 2016-01-19T11:54:07+01:00
         *         }
         *       ]
         *     }
         *     
         *     
         */
        public static CategoriesResource.GetCategoriesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.GetCategoriesResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.GetCategoriesResponse(responseBuilder.build());
        }

    }

    public class PostCategoriesResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PostCategoriesResponse(Response delegate) {
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
        public static CategoriesResource.PostCategoriesResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
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
        public static CategoriesResource.PostCategoriesResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PostCategoriesResponse(responseBuilder.build());
        }

    }

    public class PutCategoriesByCategoryIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PutCategoriesByCategoryIdResponse(Response delegate) {
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
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
        public static CategoriesResource.PutCategoriesByCategoryIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new CategoriesResource.PutCategoriesByCategoryIdResponse(responseBuilder.build());
        }

    }

}
