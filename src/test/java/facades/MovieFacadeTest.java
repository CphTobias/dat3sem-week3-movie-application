package facades;

import dtos.ActorDTO;
import dtos.MovieDTO;
import entities.Actor;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    Movie movie1;
    Movie movie2;
    Movie movie3;
    List<Actor> actorList;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Actor actor1 = new Actor("Bob1", 18);
        Actor actor2 = new Actor("Bob2", 21);
        Actor actor3 = new Actor("Bob3", 30);
        actorList = new ArrayList<>();
        try {
            em.getTransaction().begin();
            em.persist(actor1);
            em.persist(actor2);
            em.persist(actor3);
            actorList.add(actor1);
            actorList.add(actor2);
            actorList.add(actor3);
            movie1 = new Movie(2001, "First movie", actorList, 333333);
            movie2 = new Movie(2003, "Second movie", actorList, 444444);
            movie3 = new Movie(2002, "Third movie", actorList, 555555);
            em.persist(movie1);
            em.persist(movie2);
            em.persist(movie3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createNamedQuery("Actor.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getByTitle() {
        List<MovieDTO> movieDTOS = facade.getByTitle("Second movie");
        movieDTOS.forEach(movie -> assertTrue(movie.getTitle().contains("Second movie")));
    }

    @Test
    void getById() {
        MovieDTO movieDTO = facade.getById(movie1.getId());
        assertEquals(movie1.getId(), movieDTO.getId());
    }

    @Test
    void addMovie() {
        Movie movie = new Movie(1998, "Add me", actorList, 900);
        MovieDTO movieDTO = facade.addMovie(movie);
        assertEquals(movie.getTitle(), movieDTO.getTitle());
        assertEquals(movie.getYear(), movieDTO.getYear());
    }

    @Test
    void getAll() {
        List<MovieDTO> movieDTOS = facade.getAll();
        assertEquals(3, movieDTOS.toArray().length);
    }

    @Test
    void getByActorName() {
        List<MovieDTO> movieDTOS = facade.getByActorName("Bob1");
        movieDTOS.forEach(movie -> {
            boolean foundActor = movie.getActors().stream()
                .anyMatch(actor -> actor.getName().equals("Bob1"));
            assertTrue(foundActor);
        });
    }
}
