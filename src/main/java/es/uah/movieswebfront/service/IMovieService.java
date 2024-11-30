package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Movie;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IMovieService {

    List<Movie> getAllMovies();
    Movie getMovieById(Integer id);
    void deleteMovie(Integer id);
    void updateMovie(Movie movie);
    List<Movie> searchMovies(String query, String searchType);
    void uploadImage(Integer id, MultipartFile image);
    String getDirector(Integer id);

}
