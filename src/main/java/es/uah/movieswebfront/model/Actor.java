package es.uah.movieswebfront.model;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Actor {
    private Integer id;
    private String name;
    private String birthDate;
    private Country birthCountry;

    // This override is to ensure that the equals method compares the objects by their id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return id != null && id.equals(actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // public Actor(Integer id, String name, String birthDate, Country birthCountry) {
    //     this.id = id;
    //     this.name = name;
    //     this.birthDate = birthDate;
    //     this.birthCountry = birthCountry;
    // }
}
