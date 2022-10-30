package si.fri.rso.zddt.kosarica.api.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import si.fri.rso.zddt.kosarica.models.User;
import si.fri.rso.zddt.kosarica.services.dao.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {

    @Inject
    private UserDAO userDAO;

    @Operation(description = "Vrne seznam cen.", summary = "Seznam cen")
    @APIResponse(responseCode = "200",
            description = "Seznam cen.",
            content = @Content(
                    schema = @Schema(implementation = User.class))
    )
    @APIResponse(responseCode = "404", description = "Cena not found")
    @GET
    public Response getAllUsers() {
        return Response.status(Response.Status.OK).entity(userDAO.getAllUsers()).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Integer userId) {
        User user = userDAO.getUserById(userId);
        if (user != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(userDAO)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
