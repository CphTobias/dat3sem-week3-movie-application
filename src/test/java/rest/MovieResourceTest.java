package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Actor;
import entities.Movie;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie movie1, movie2;
    Actor actor1;
    Actor actor2;
    Actor actor3;
    List<Actor> actorList;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        actor1 = new Actor("Bob1", 18);
        actor2 = new Actor("Bob2", 21);
        actor3 = new Actor("Bob3", 30);
        actorList = new ArrayList<>();

        try {
            em.getTransaction().begin();

            //create actors for list
            em.persist(actor1);
            em.persist(actor2);
            em.persist(actor3);
            actorList.add(actor1);
            actorList.add(actor2);
            actorList.add(actor3);

            //create movies with our created actorlist
            movie1 = new Movie(2001, "First", actorList, 333333);
            movie2 = new Movie(2003, "Second", actorList, 444444);
            em.persist(movie1);
            em.persist(movie2);
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
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie").then().statusCode(200);
    }

    @Test
    public void testGetAllMovies() throws Exception {
        given()
            .when()
            .get("/movie/")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("size()", is(2))
            .body("title", hasItems(movie1.getTitle(), movie2.getTitle()))
            .body("actors[0].name", hasItems(actor1.getName(), actor2.getName(), actor3.getName()));
    }

    @Test
    public void testGetByTitle() throws Exception {
        given()
            .pathParam("title", movie2.getTitle())
            .when()
            .get("/movie/title/{title}")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("", hasEntry("title", movie2.getTitle()));
    }

    @Test
    public void testGetById() throws Exception {
        given()
            .pathParam("id", movie1.getId())
            .when()
            .get("/movie/{id}")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("", hasEntry("title", movie1.getTitle()));
    }

    @Test
    public void testGetActorByName() throws Exception {
        given()
            .pathParam("name", "Bob1")
            .when()
            .get("/movie/actor/{name}")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("size()", is(2))
            .body("actors.name[0]", hasItem("Bob1"));
    }

    @Test
    public void testGetCount() throws Exception {
        given()
            .when()
            .get("/movie/count")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("count", is(2));
    }

    @Test
    public void testAddMovie() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestBody = gson.toJson(new Movie(2910, "Test", actorList, 13321));
        given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody)
            .when()
            .post("/movie/")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .log().body()
            .body("", hasEntry("title", "Test"));
    }
}
