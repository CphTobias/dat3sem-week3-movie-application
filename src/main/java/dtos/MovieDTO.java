package dtos;

import entities.Actor;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.OneToMany;

public class MovieDTO {

    private long id;
    private int year;
    private String title;
    private List<Actor> actors;

    public static List<MovieDTO> getFromList(List<Movie> movies) {
        return movies.stream().
            map(m -> new MovieDTO(m)).
            collect(Collectors.toList());
    }

    public MovieDTO(long id, int year, String title, List<Actor> actors) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.actors = actors;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.actors = movie.getActors();
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
            "id=" + id +
            ", year=" + year +
            ", title='" + title + '\'' +
            ", actors=" + actors +
            '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
