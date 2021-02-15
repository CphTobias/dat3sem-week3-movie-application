package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import entities.Movie;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;
import facades.MovieFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final MovieFacade FACADE =  MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    public Response getAllMovies() {
        List<MovieDTO> movieDTOS = FACADE.getAll();
        return Response.ok(GSON.toJson(movieDTOS)).build();
    }

    @GET
    @Path("/title/{title}")
    public Response getByTitle(
        @PathParam("title") String title
    ) {
        MovieDTO movieDTO = FACADE.getByTitle(title);
        return Response.ok(GSON.toJson(movieDTO)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(
        @PathParam("id") long id
    ) {
        MovieDTO movieDTO = FACADE.getById(id);
        return Response.ok(GSON.toJson(movieDTO)).build();
    }

    @POST
    public Response addMovie(Movie movie) {
        MovieDTO movieDTO = FACADE.addMovie(movie);
        return Response.ok(GSON.toJson(movieDTO)).build();
    }

    @GET
    @Path("/actor/{actorName}")
    public Response getByActorName(
        @PathParam("actorName") String actorName
    ) {
        List<MovieDTO> movieDTOS = FACADE.getByActorName(actorName);
        return Response.ok(GSON.toJson(movieDTOS)).build();
    }

}
