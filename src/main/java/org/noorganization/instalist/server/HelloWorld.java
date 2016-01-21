package org.noorganization.instalist.server;
/*import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;*/

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by damihe on 17.01.16.
 */
// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({ "what", "the", "fuck" })
    public class AJSON {

        @JsonProperty("what")
        String what;

        @JsonProperty("the")
        String the;

        @JsonProperty("fuck")
        String fuck;

        public String getWhat() {
            return what;
        }

        public void setWhat(String _what) {
            what = _what;
        }

        public String getThe() {
            return the;
        }

        public void setThe(String _the) {
            the = _the;
        }

        public String getFuck() {
            return fuck;
        }

        public void setFuck(String _fuck) {
            fuck = _fuck;
        }

        public AJSON() {}
    }

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    public AJSON getClichedMessage() {
        // Return some cliched textual content
        AJSON rtn = new AJSON();
        rtn.setWhat("Hello World");
        rtn.setThe("Is there someone?");
        rtn.setFuck("World is strange.");
        return rtn;
    }

    /*public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/helloworld");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }*/
}
