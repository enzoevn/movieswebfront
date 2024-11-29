package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    private List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping
    public String getMovies(Model model) {
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movies", movies);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movies";
    }

    @GetMapping("/details/{id}")
    public String getMovieById(Model model, @PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movie", movie);
        model.addAttribute("movies", movies);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movie_details";
    }

    @GetMapping("/genre/{genre}")
    public String searchMoviesByGenre(Model model, @PathVariable String genre) {
        List<Movie> moviesByGenre = movieService.getMoviesByGenre(genre);
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movies", moviesByGenre);
        model.addAttribute("uniqueGenres", uniqueGenres);
        return "movies";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, @RequestParam("searchType") String searchType, Model model) {
        if (query.equals('#'))
            return "redirect:/home";
        List<Movie> movies = movieService.searchMovies(query, searchType);
        model.addAttribute("movies", movies);
        return "movies";
    }

    @PostMapping("/uploadImage/{id}")
    public String uploadImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image, Model model) {
        try {
            movieService.uploadImage(id, image);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            List<Movie> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            return "movies"; // Return the same view
        }
        // wait for the image to be uploaded
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/movies/details/" + id;   
    }

    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
        
}
