package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    @Autowired
    private IMovieService movieService;

    private List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movies", movies);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String getMovieById(Model model, @PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movie", movie);
        model.addAttribute("movies", movies);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movie_details";
    }

    @GetMapping("/movies/genre/{genre}")
    public String searchMoviesByGenre(Model model, @PathVariable String genre) {
        List<Movie> moviesByGenre = movieService.getMoviesByGenre(genre);
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movies", moviesByGenre);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movies";
    }

    @GetMapping("/movies/search")
    public String searchMovies(@RequestParam("query") String query, Model model) {
        List<Movie> movies = movieService.searchMovies(query);
        model.addAttribute("movies", movies);
        return "index"; // Ensure this matches the name of your HTML template
    }
}
