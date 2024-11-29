package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public List<Movie> searchMovies(String query, String searchType) {
        RestTemplate restTemplate = new RestTemplate();
        if (query.equals("")) {
            return getAllMovies();
        }
        Movie[] movies = restTemplate.getForObject(baseUrl + "/search/" + searchType + "/" + query, Movie[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    @Override
    public void uploadImage(Integer id, MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                //Check if is a valid image
                String contentType = image.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Invalid image file");
                }
                else {
                    System.out.println("Image file is valid");
                    // Save the image to the static/images folder with the name {id}.jpg
                    Path path = Paths.get("src/main/resources/static/images/" + id + ".jpg");
                    Files.write(path, image.getBytes());
                }     
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new IllegalArgumentException("Image file is empty");
        }
        // Update the movie's image URL in the database
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(baseUrl + "/" + id, Movie.class);
        assert movie != null;
        movie.setCoverImage("/images/" + id + ".jpg");
        // Log the movie object for debugging
        System.out.println("Updating movie: " + movie);

        restTemplate.put(baseUrl + "/" + id, movie);
    }
}
