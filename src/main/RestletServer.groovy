#!/usr/bin/env groovy -classpath org.restlet.jar:com.noelios.restlet.jar

import org.restlet.*
import org.restlet.data.*

// RequestHandler will handle all of our requests
class RequestHandler extends Restlet{
    // handle() is called by the framework whenever there's a HTTP request
    def void handle(Request request, Response response){
        /* The only method that's allowed is GET, so check it -- if it's GET,
           return a representation, and the default status will be returned,
           which is 200 OK. If not, set the response status to an error. */

        if (request.method == Method.GET){
            /* handle() returns void, for reasons that will become clear
               later. The way to output content is modify the passed-in response
               by setting the Entity of the response. Restlet refers to the
               Representation as an "entity"; I'm actually not 100% sure why. */
            response.setEntity("Hello, world!", MediaType.TEXT_PLAIN)
        }
        else{
            // The request method is not GET, so set an error response status
            response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED)
                response.setAllowedMethods([Method.GET] as Set)
        }
    }
}

/* Create a new HTTP Server on port 3000, pass it a new instance of RequestHandler,
   to which it will pass all incoming Requests, and start it. */
new Server(Protocol.HTTP, 3000, new RequestHandler()).start()

