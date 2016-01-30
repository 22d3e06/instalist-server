
package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.noorganization.instalist.server.support.ResponseFactory;
import org.noorganization.instalist.server.model.DeviceRegistration;
import org.noorganization.instalist.server.model.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
public class UserResource {

    public static class MockingObject {

        @JsonIgnore
        private Map<String, Object> mAttributes;

        @JsonAnyGetter
        public Map<String, Object> get() {
            return mAttributes;
        }

        public Object get(String _key) {
            return mAttributes.get(_key);
        }

        @JsonAnySetter
        public void set(String _key, Object _value) {
            mAttributes.put(_key, _value);
        }

        public MockingObject() {
            mAttributes = new HashMap<String, Object>();
        }
    }

    /**
     * Get the auth token.
     * Needs basic authentication with server-side-generated id as user and client-sided secret as
     * client. For Encoding-method view RFC 2601
     */
    @GET
    @Path("token")
    @Produces({ "application/json" })
    public Response getUserToken(@Context HttpHeaders _headers) throws Exception {
        String authHeader = _headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            Error message = new Error();
            message.setMessage("Authentication needed.");
            return ResponseFactory.generateNotAuthorizedWAuth(message);
        } else {

        }
        System.out.println("Authorization header: " + authHeader);
        MockingObject answerObject = new MockingObject();
        answerObject.set("auth", _headers.getHeaderString(HttpHeaders.AUTHORIZATION));
        return ResponseFactory.generateOK(answerObject);
    }

    /**
     * The action to connect a new device with a group.
     * 
     * @param _registration A JSON-Object containing all needed information for registering.
     *      e.g. {
     *       "groupid" : "AB7Zbm",
     *       "secret" : "dkjhfsdcbuiufien=--ihrienncdjXXCndjjFJJED"
     *     }
     *     
     */
    @POST
    @Path("register_device")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response postUserRegisterDevice(DeviceRegistration _registration) throws Exception {
        return null;
    }

    /**
     * The action to get a temporary access key to a group.
     * 
     */
    @GET
    @Path("group/access_key")
    @Produces({ "application/json" })
    public Response getUserGroupAccessKey(@QueryParam("token") String _token) throws Exception {
        return null;
    }

    /**
     * The action to create a new group of devices sharing lists etc.
     * 
     */
    @POST
    @Path("register_group")
    @Produces({ "application/json" })
    public Response postUserRegisterGroup() throws Exception {
        return null;
    }

}
