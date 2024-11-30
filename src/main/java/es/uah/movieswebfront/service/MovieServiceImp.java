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
public class MovieServiceImp implements IMovieService {

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
    public void deleteMovie(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(baseUrl + "/" + id);
    }

    @Override
    public List<Movie> searchMovies(String query, String searchType) {
        RestTemplate restTemplate = new RestTemplate();
        if (query.equals("") || query.equals("#")) {
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
        // wait for the image to be uploaded
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMovie(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(baseUrl + "/" + movie.getId(), movie);
    }

    @Override
    public void createMovie(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(baseUrl, movie, Movie.class);
    }

    @Override
    public String getDirector(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(baseUrl + "/" + id, Movie.class);
        assert movie != null;
        String director = movie.getDirector();
        return director;
    }
}
