package es.uah.movieswebfront.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Country {
    private Integer id;
    private String countryName;

    public Country(Integer id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }   

}
