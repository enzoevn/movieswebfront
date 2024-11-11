package es.uah.movieswebfront.model;

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
    private String birthCountry;

    public Actor(Integer id, String name, String birthDate, String birthCountry) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.birthCountry = birthCountry;
    }
}
