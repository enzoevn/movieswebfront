package es.uah.movieswebfront.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Movie {
    private Integer id;
    private String title;
    private Integer year;
    private Integer duration;
    private Country country;
    private String director;
    private String genre;
    private String synopsis;
    private String coverImage;
    private List<Actor> actors;
    private Double rateAverage;
}
