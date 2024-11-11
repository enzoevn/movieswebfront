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
    private String country;
    private String director;
    private String genre;
    private String synopsis;
    private String coverImage;
    private List<Actor> actors;

    public Movie(Integer id, String title, Integer year, Integer duration, String country, String director, String genre, String synopsis, String coverImage, List<Actor> actors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.synopsis = synopsis;
        this.coverImage = coverImage;
        this.actors = actors;
    }
}
