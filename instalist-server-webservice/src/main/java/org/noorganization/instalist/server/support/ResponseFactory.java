package org.noorganization.instalist.server.support;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by damihe on 29.01.16.
 */
public class ResponseFactory {

    /**
     * Generates a resopnse with HTTP-Code OK.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateOK(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Created. This Response should be only used in POST-
     * Requests.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateCreated(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.CREATED);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Accepted. This Response should be only used in POST, PUT
     * or DELETE-requests for indicating a change will be executed. The result may be visible later.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateAccepted(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.ACCEPTED);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Bad Request. May be used for indicating there was an
     * error in the request-format. Please use more specific codes if possible.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateBadRequest(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Not Authorized. May be used for indicating that
     * authorization is missing.
     * @param _entity Optional json-entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateNotAuthorized(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED);
        if (_entity != null) {
            builder.type(MediaType.APPLICATION_JSON_TYPE);
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Not Authorized. May be used for indicating that
     * authorization is missing. This variant also send an Auth-Basic requirement.
     * @param _entity Optional json-entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateNotAuthorizedWAuth(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED);
        //builder.location(UriBuilder.fromMethod(UserResource.class, "getUserToken").build());
        builder.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=InstalistServer");
        if (_entity != null) {
            builder.type(MediaType.APPLICATION_JSON_TYPE);
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Not Found. Use this if the requested object was not found
     * and wasn't deleted (then use {@link #generateGone(Object)}).
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateNotFound(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Conflict. Use this if the requested change is in conflict
     * with the saved state on server.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateConflict(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Gone. May be used when a deleted object is requested.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateGone(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.GONE);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }

    /**
     * Generates a resopnse with HTTP-Code Internal Server Error. May be used when a strange error
     * happens. Only use in unexpected situations.
     * @param _entity Optional entity (body). If null, no body will be added to the response.
     * @return The generated Response.
     */
    public static Response generateServerError(Object _entity) {
        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        if (_entity != null) {
            builder.entity(_entity);
        }
        return builder.build();
    }
}
