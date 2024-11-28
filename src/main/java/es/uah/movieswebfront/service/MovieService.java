package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService implements IMovieService {

    private final String baseUrl = "http://localhost:8001/movies";

    @Override
    public List<Movie> getAllMovies() {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public Movie getMovieById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl + "/" + id, Movie.class);
    }

    @Override
    public void saveMovie(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(baseUrl, movie, Movie.class);
    }

    @Override
    public void deleteMovie(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(baseUrl + "/" + id);
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/title?title=" + title, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public List<Movie> getMoviesByYear(Integer year) {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/year?year=" + year, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/genre/" + genre, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public List<Movie> getMoviesByActor(String actor) {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/actor?actor=" + actor, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public List<Movie> searchMovies(String query) {
        RestTemplate restTemplate = new RestTemplate();
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/title/" + query, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }
}
