package dtos;

import entities.Actor;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;

public class ActorDTO {

    private Long id;
    private String name;
    private int age;

    public static List<ActorDTO> getFromList(List<Actor> actors) {
        return actors.stream()
            .map(a -> new ActorDTO(a))
            .collect(Collectors.toList());
    }

    public ActorDTO(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        this.age = actor.getAge();
    }

    @Override
    public String toString() {
        return "ActorDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
