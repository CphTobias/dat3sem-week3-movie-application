package dtos;

import entities.Movie;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieDTO {

    private long id;
    private int year;
    private String title;
    private List<ActorDTO> actors;

    public static List<MovieDTO> getFromList(List<Movie> movies) {
        return movies.stream().
            map(m -> new MovieDTO(m)).
            collect(Collectors.toList());
    }

    public MovieDTO(long id, int year, String title, List<ActorDTO> actors) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.actors = actors;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.actors = ActorDTO.getFromList(movie.getActors());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDTO)) {
            return false;
        }
        MovieDTO movieDTO = (MovieDTO) o;
        return getId() == movieDTO.getId() && Objects.equals(getActors(), movieDTO.getActors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getActors());
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

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }
}
