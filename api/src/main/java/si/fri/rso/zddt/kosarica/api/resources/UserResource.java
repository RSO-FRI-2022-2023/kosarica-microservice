package si.fri.rso.zddt.kosarica.api.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import si.fri.rso.zddt.common.models.Uporabnik;
import si.fri.rso.zddt.kosarica.services.dao.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE")
@Log
public class UserResource {

    @Inject
    private UserDAO userDAO;

    @Operation(description = "Vrni seznam uporabnikov.", summary = "Seznam uporabnikov")
    @APIResponse(responseCode = "200",
            description = "Seznam uporabnikov.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Uporabnik.class,
                            readOnly = true,
                            description = "the users",
                            required = true,
                            name = "Users"))
    )
    @GET
    public Response getAllUsers() {
        return Response
                .status(Response.Status.OK)
                .entity(userDAO.getAllUsers())
                .build();
    }

    @Operation(description = "Vrni uporabnika glede na ID.", summary = "Vrni uporabnika")
    @APIResponse(responseCode = "200",
            description = "Uporabnik",
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class))
    )
    @APIResponse(responseCode = "404",
            description = "Uporabnik bi bil najden.")
    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Integer userId) {
        Uporabnik uporabnik = userDAO.getUserById(userId);
        if (uporabnik != null) {
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
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(success)
                    .build();
        }
    }

    @Operation(description = "Dodaj uporabnika.", summary = "Dodajanje uporabnika")
    @APIResponse(responseCode = "201",
            description = "Dodajanje uporabnika",
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri dodajanju uporabnika.")
    @POST
    public Response addUser(@RequestBody(
            description = "User to create",
            required = true,
            content = @Content(
                    example = "{\"firstname\"=\"David\", \"lastname\"=\"Trafela\"}",
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Uporabnik.class)))
                            Uporabnik uporabnik) {
        log.info("User {}", uporabnik);

        Uporabnik uporabnik1 = userDAO.addUser(uporabnik);
        if (uporabnik1 != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(uporabnik1)
                    .build();
        } else {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(false)
                    .build();
        }
    }

    @Operation(description = "Posodobi uporabnika.", summary = "Posodobi uporabnika")
    @APIResponse(responseCode = "200",
            description = "Posodobi uporabnika",
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri posodabljanju uporabnika.")
    @PUT
    public Response updateUser(Uporabnik uporabnik) {
        Uporabnik uporabnik1 = userDAO.updateUser(uporabnik);
        if (uporabnik1 != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(uporabnik1)
                    .build();
        } else {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(false)
                    .build();
        }
    }
}
