package hackathon.microstream.service.rest.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class RootResource {

    /**
     * Redirects to the openapi-ui endpoint
     * @param uriInfo
     * @return
     */
    @GET
    @Operation(hidden = true)
    public Response get(@Context UriInfo uriInfo) {
        return Response
                .ok("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <meta http-equiv=\"Refresh\" content=\"0; url=openapi-ui/index.html\" />\n" +
                        "  </head>\n" +
                        "</html>")
                .build();
    }
}
