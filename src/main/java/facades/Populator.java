/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.ActorDTO;
import dtos.MovieDTO;
import entities.Actor;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        ActorFacade actorFacade = ActorFacade.getActorFacade(emf);
        MovieFacade fe = MovieFacade.getMovieFacade(emf);

        ActorDTO actorDTO = actorFacade.addActor(new Actor("Jon", 24));
        System.out.println(actorDTO);
        ActorDTO actorDTO1 = actorFacade.addActor(new Actor("Tobias", 24));
        System.out.println(actorDTO1);
        ActorDTO actorDTO2 = actorFacade.addActor(new Actor("Amalie", 24));
        System.out.println(actorDTO2);

        List<Actor> actors = actorFacade.getAllActors();

        double budget = 21313221;
        MovieDTO movieDTO = fe.addMovie(new Movie(1998, "Insane1", actors, budget));
        System.out.println(movieDTO);
        fe.addMovie(new Movie(1998, "Insane2", actors, budget));
        fe.addMovie(new Movie(1998, "Insane3", actors, budget));
        fe.addMovie(new Movie(1998, "Insane4", actors, budget));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
