package si.fri.rso.zddt.kosarica.api.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import si.fri.rso.zddt.kosarica.models.User;
import si.fri.rso.zddt.kosarica.services.dao.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserDAO userDAO;

    @Operation(description = "Vrni seznam uporabnikov.", summary = "Seznam uporabnikov")
    @APIResponse(responseCode = "200",
            description = "Seznam uporabnikov.",
            content = @Content(
                    schema = @Schema(implementation = User.class))
    )
    @GET
    public Response getAllUsers() {
        return Response.status(Response.Status.OK).entity(userDAO.getAllUsers()).build();
    }

    @Operation(description = "Vrni uporabnika glede na ID.", summary = "Vrni uporabnika")
    @APIResponse(responseCode = "200",
            description = "Uporabnik",
            content = @Content(
                    schema = @Schema(implementation = User.class))
    )
    @APIResponse(responseCode = "404",
            description = "Uporabnik bi bil najden.")
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Izbriši uporabnika.", summary = "Brisanje uporabnika")
    @APIResponse(responseCode = "204",
            description = "Brisanje uporabnika"
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri brisanju uporabnika.")
    @DELETE
    @Path("{id}")
    public Response deleteUserById(@PathParam("id") Integer userId) {
        boolean success = userDAO.deleteUser(userId);
        if (success) {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(success)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Dodaj uporabnika.", summary = "Dodajanje uporabnika")
    @APIResponse(responseCode = "201",
            description = "Dodajanje uporabnika",
            content = @Content(
                    schema = @Schema(implementation = User.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri dodajanju uporabnika.")
    @POST
    public Response addUser(@RequestBody(
            description = "User to create",
            required = true,
            content = @Content(
                    schema = @Schema(type = SchemaType.OBJECT, implementation = User.class)))
                            User user) {
        User user1 = userDAO.addUser(user);
        if (user1 != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(user1)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Posodobi uporabnika.", summary = "Posodobi uporabnika")
    @APIResponse(responseCode = "200",
            description = "Posodobi uporabnika",
            content = @Content(
                    schema = @Schema(implementation = User.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri posodabljanju uporabnika.")
    @PUT
    public Response updateUser(User user) {
        User user1 = userDAO.updateUser(user);
        if (user1 != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(user1)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
