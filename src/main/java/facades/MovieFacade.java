package facades;

import dtos.MovieDTO;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public MovieDTO addMovie(Movie movie){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(movie);
    }

    public MovieDTO getByTitle(String title){
        EntityManager em = getEntityManager();
        return new MovieDTO(em.createNamedQuery("Movie.getByTitle", Movie.class)
            .setParameter("title", title)
            .getSingleResult());
    }

    public MovieDTO getById(long id){
        EntityManager em = getEntityManager();
        return new MovieDTO(em.find(Movie.class, id));
    }

    public List<MovieDTO> getAll(){
        EntityManager em = getEntityManager();
        List<Movie> movies = em.createNamedQuery("Movie.getAll", Movie.class).getResultList();
        return MovieDTO.getFromList(movies);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade fe = getMovieFacade(emf);
        fe.getAll().forEach(dto->System.out.println(dto));
    }

}
