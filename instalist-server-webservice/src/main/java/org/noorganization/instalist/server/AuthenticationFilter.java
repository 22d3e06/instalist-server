/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server;

import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.support.CommonEntity;
import org.noorganization.instalist.server.support.ResponseFactory;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
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
                    generateNotAuthorized(CommonEntity.NOT_AUTHORIZED));

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
                    generateNotAuthorized(CommonEntity.NOT_AUTHORIZED));
    }
}
