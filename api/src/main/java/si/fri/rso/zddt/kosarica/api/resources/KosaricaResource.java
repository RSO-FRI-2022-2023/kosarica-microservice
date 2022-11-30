package si.fri.rso.zddt.kosarica.api.resources;

import com.kumuluz.ee.logs.cdi.Log;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import si.fri.rso.zddt.common.models.Izdelek;
import si.fri.rso.zddt.common.models.Kosarica;
import si.fri.rso.zddt.kosarica.services.dao.KosaricaDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log4j2
@Path("kosarica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KosaricaResource {

    @Inject
    private KosaricaDAO kosaricaDAO;

    @Operation(description = "Vrni seznam košaric.", summary = "Seznam košaric")
    @APIResponse(responseCode = "200",
            description = "Seznam košaric.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Kosarica.class))
    )
    @GET
    @Log
    public Response getKosarice() {
        return Response
                .status(Response.Status.OK)
                .entity(kosaricaDAO.getAll())
                .build();
    }

    @Operation(description = "Vrni košarico glede na ID.", summary = "Košarica")
    @APIResponse(responseCode = "200",
            description = "Košarica glede na ID.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Kosarica.class))
    )
    @APIResponse(responseCode = "404",
            description = "Košarica bi bila najdena.")
    @GET
    @Path("{id}")
    public Response getKosarico(@PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaDAO.getById(id);
        if (kosarica != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(kosaricaDAO.getById(id))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Dodaj izdelek v košarico", summary = "Izdelek v košarico")
    @APIResponse(responseCode = "201",
            description = "Dodaj izdelek v košarico",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Kosarica.class))
    )
    @POST
    @Path("{id}/dodajIzdelek")
    public Response addIzdelek(@PathParam("id") Integer id, Izdelek izdelek) {
        return Response
                .status(Response.Status.OK)
                .entity(kosaricaDAO.addIzdelek(id, izdelek))
                .build();
    }

    @Operation(description = "Izbriši košarico", summary = "Izbriši košarico")
    @APIResponse(responseCode = "204",
            description = "Brisanje košarice",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Kosarica.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri brisanju košarice.")
    @DELETE
    @Path("{id}")
    public Response deleteKosarica(@PathParam("id") Integer id) {
        boolean success = kosaricaDAO.delete(id);
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

    @Operation(description = "Izbriši košarico", summary = "Izbriši košarico")
    @APIResponse(responseCode = "204",
            description = "Brisanje košarice",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Kosarica.class))
    )
    @APIResponse(responseCode = "500",
            description = "Napaka pri brisanju košarice.")
    @DELETE
    @Path("{id}/izbrisiIzdelek/{izdelekId}")
    public Response deleteIzdelek(@PathParam("id") Integer id, @PathParam("izdelekId") Integer izdelekId) {
        boolean success = kosaricaDAO.deleteIzdelek(id, izdelekId);
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
}
