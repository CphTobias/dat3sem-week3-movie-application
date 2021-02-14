package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({
    @NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie"),
    @NamedQuery(name = "Movie.getAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.getByTitle", query = "SELECT m FROM Movie m WHERE m.title LIKE :title")
})
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;

    @Column(unique = true)
    private String title;

    private double budget;

    @OneToMany
    private List<Actor> actors;

    public Movie(int year, String title, List<Actor> actors, double budget) {
        this.year = year;
        this.title = title;
        this.actors = actors;
        this.budget = budget;
    }

    public Movie() {

    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", year=" + year +
            ", title='" + title + '\'' +
            ", actors=" + actors +
            ", budget=" + budget +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
