package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ActorDTO;
import facades.ActorFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

@Path("actor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorResource {

    EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    ActorFacade FACADE = ActorFacade.getActorFacade(EMF);
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    public Response getAll() {
        List<ActorDTO> actorDTOS = FACADE.getAll();
        return Response.ok(GSON.toJson(actorDTOS)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(
        @PathParam("id") long id
    ) {
        ActorDTO actorDTO = FACADE.getById(id);
        return Response.ok(GSON.toJson(actorDTO)).build();
    }
}
