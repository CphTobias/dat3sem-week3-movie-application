package facades;

import dtos.ActorDTO;
import dtos.MovieDTO;
import entities.Actor;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class ActorFacade {

    private static ActorFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ActorFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ActorFacade getActorFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ActorFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ActorDTO addActor(Actor actor){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ActorDTO(actor);
    }

    public ActorDTO getById(long id){
        EntityManager em = getEntityManager();
        return new ActorDTO(em.find(Actor.class, id));
    }

    public List<ActorDTO> getAll(){
        EntityManager em = getEntityManager();
        List<Actor> actors = em.createNamedQuery("Actor.getAll", Actor.class).getResultList();
        return ActorDTO.getFromList(actors);
    }

    public List<Actor> getAllActors(){
        EntityManager em = getEntityManager();
        List<Actor> actors = em.createQuery("SELECT a FROM Actor a", Actor.class).getResultList();
        return actors;
    }
}
