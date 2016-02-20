package org.noorganization.instalist.server;

import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.support.DatabaseHelper;
import org.noorganization.instalist.server.support.ResponseFactory;

import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Authentication as filter. Inspired by
 * <a href="http://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based">Stackoverflow</a>.
 * Thanks to the special IAuthControlle, there is no db-connection needed, with should improve
 * performance for refusing clients with wrong authorization to group.
 * This filter checks, if the device (which is authenticated by token) has authorization to group
 * sent by url. It does not check if it has access to other url parts, since this check would be
 * different for every case and would make the filter big and slow.
 * Created by Michael Wodniok on 2016-02-05.
 */
@TokenSecured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("X-Token "))
            throw new NotAuthorizedException(ResponseFactory.
                    generateNotAuthorized(CommonEntity.sNotAuthorized));

        MultivaluedMap<String, String> parameters = requestContext.getUriInfo().getPathParameters();
        int groupId;
        try {
            groupId = Integer.parseInt(parameters.getFirst("groupid"));
        } catch (NumberFormatException _e) {
            throw new IOException("\"groupid\" was not found in path.");
        }

        String token = authorizationHeader.substring("X-Token ".length()).trim();

        Device authenticatedDev = ControllerFactory.getAuthController().
                getDeviceByToken(token);
        if (authenticatedDev == null || authenticatedDev.getGroup().getId() != groupId ||
                !authenticatedDev.getAuthorized())
            throw new NotAuthorizedException(ResponseFactory.
                    generateNotAuthorized(CommonEntity.sNotAuthorized));
    }
}
