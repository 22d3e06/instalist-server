package org.noorganization.instalist.server.support.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * Since JAX-RS does not specify this type of Exception, we had to do it ourself. This type of
 * exception gets thrown if a client tries to access or modify a deleted object.
 * Created by Michael Wodniok on 2016-02-08.
 */
public class GoneException extends ClientErrorException {
    public GoneException() {
        super(Response.Status.GONE);
    }

    public GoneException(Response response) {
        super(response);
    }

    public GoneException(String message, Response response) {
        super(message, response);
    }

    public GoneException(String message, int status, Throwable cause) {
        super(message, status, cause);
    }

    public GoneException(Response response, Throwable cause) {
        super(response, cause);
    }

    public GoneException(String message, Response response, Throwable cause) {
        super(message, response, cause);
    }
}
