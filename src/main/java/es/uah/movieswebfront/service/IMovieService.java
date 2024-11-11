package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getAllMovies();
    Movie getMovieById(Integer id);
    void saveMovie(Movie movie);
    void deleteMovie(Integer id);
    List<Movie> getMoviesByTitle(String title);
    List<Movie> getMoviesByYear(Integer year);
    List<Movie> getMoviesByGenre(String genre);
    List<Movie> getMoviesByActor(String actor);
}
