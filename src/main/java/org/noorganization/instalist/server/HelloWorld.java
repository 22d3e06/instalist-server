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
    }

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/json")
    public AJSON getClichedMessage() {
        // Return some cliched textual content
        AJSON rtn = new AJSON();
        rtn.what = "Hello World";
        rtn.the = "Is there someone?";
        rtn.fuck = "World is strange.";
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
