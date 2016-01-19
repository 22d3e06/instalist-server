
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
import org.noorganization.instantListApi.model.TaggedProduct;


/**
 * Collection of available taggedProducts.
 * 
 */
@Path("taggedProducts")
public interface TaggedProductsResource {


    /**
     * Get a list of taggedProducts.
     * 
     * 
     * @param changedSince
     *     Requests only the elements that changed since the given date. ISO 8601 time e.g. 2016-01-19T11:54:07+01:00
     */
    @GET
    @Produces({
        "application/json"
    })
    TaggedProductsResource.GetTaggedProductsResponse getTaggedProducts(
        @QueryParam("changedSince")
        Date changedSince)
        throws Exception
    ;

    /**
     * Add a new taggedProduct.
     * 
     * 
     * @param entity
     *      e.g. {
     *       "id" : "e29c2231-b351-4907-ba42-4dd59f03ad76",
     *       "tagId" : "da91ab7d-886e-4183-8b90-2340ef5b806e",
     *       "productId" :  "87c03314-8733-4e91-b356-0f1d37dd4eb8"
     *     }
     *     
     */
    @POST
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    TaggedProductsResource.PostTaggedProductsResponse postTaggedProducts(TaggedProduct entity)
        throws Exception
    ;

    /**
     * Returns the taggedProduct.
     * 
     * 
     * @param taggedProductId
     *     
     */
    @GET
    @Path("{taggedProductId}")
    @Produces({
        "application/json"
    })
    TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse getTaggedProductsByTaggedProductId(
        @PathParam("taggedProductId")
        String taggedProductId)
        throws Exception
    ;

    /**
     * Updates the taggedProduct.
     * 
     * 
     * @param entity
     *      e.g. {
     *       "id" : "e29c2231-b351-4907-ba42-4dd59f03ad76",
     *       "tagId" : "da91ab7d-886e-4183-8b90-2340ef5b806e",
     *       "productId" :  "87c03314-8733-4e91-b356-0f1d37dd4eb8"
     *     }
     *     
     * @param taggedProductId
     *     
     */
    @PUT
    @Path("{taggedProductId}")
    @Consumes("application/json")
    @Produces({
        "application/json"
    })
    TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse putTaggedProductsByTaggedProductId(
        @PathParam("taggedProductId")
        String taggedProductId, TaggedProduct entity)
        throws Exception
    ;

    /**
     * Deletes the taggedProduct.
     * 
     * 
     * @param taggedProductId
     *     
     */
    @DELETE
    @Path("{taggedProductId}")
    @Produces({
        "application/json"
    })
    TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse deleteTaggedProductsByTaggedProductId(
        @PathParam("taggedProductId")
        String taggedProductId)
        throws Exception
    ;

    public class DeleteTaggedProductsByTaggedProductIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private DeleteTaggedProductsByTaggedProductIdResponse(Response delegate) {
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.DeleteTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
        }

    }

    public class GetTaggedProductsByTaggedProductIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetTaggedProductsByTaggedProductIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
        }

    }

    public class GetTaggedProductsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private GetTaggedProductsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         *     
         */
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.GetTaggedProductsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.GetTaggedProductsResponse(responseBuilder.build());
        }

    }

    public class PostTaggedProductsResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PostTaggedProductsResponse(Response delegate) {
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PostTaggedProductsResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PostTaggedProductsResponse(responseBuilder.build());
        }

    }

    public class PutTaggedProductsByTaggedProductIdResponse
        extends org.noorganization.instantListApi.resource.support.ResponseWrapper
    {


        private PutTaggedProductsByTaggedProductIdResponse(Response delegate) {
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonCreated(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonAccepted(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(202).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonNoContent(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(204).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonMultipleChoices(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(300).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonForbidden(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonMethodNotAllowed(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(405).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonRequestTimeout(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(408).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonConflict(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(409).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonGone(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(410).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonRequestTooLong(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(413).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonLocked(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(423).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJson_429(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(429).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
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
        public static TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse withJsonInternalServerError(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new TaggedProductsResource.PutTaggedProductsByTaggedProductIdResponse(responseBuilder.build());
        }

    }

}
