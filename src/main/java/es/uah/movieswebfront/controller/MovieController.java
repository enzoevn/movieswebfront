package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Actor;
import es.uah.movieswebfront.model.Country;
import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.service.IActorService;
import es.uah.movieswebfront.service.ICountryService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private ICountryService countryService;

    @Autowired
    private IActorService actorService;

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

    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, String searchType, Model model) {
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
        // Update the page with the new image
        return "redirect:/movies/details/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(Model model, @PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);
        List<Actor> selectedActors = movie.getActors();
        Country country = movie.getCountry();

        // if (selectedActors == null) {
        //     selectedActors = new ArrayList<>();
        // }

        // if (country == null) {
        //     country = new Country();
        // }

        model.addAttribute("movie", movie);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("actors", actorService.getAllActors());
        model.addAttribute("movieActor", selectedActors);
        model.addAttribute("country", country);
        model.addAttribute("formTitle", "Edit Movie");
        model.addAttribute("formAction", "/movies/update");
        model.addAttribute("buttonText", "Update Movie");
        return "edit-movie";
    }

    @GetMapping("/create")
    public String createMovie(Model model) {
        Movie movie = new Movie();
        List<Actor> selectedActors = new ArrayList<>();
        Country country = new Country();

        model.addAttribute("movie", movie);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("actors", actorService.getAllActors());
        model.addAttribute("movieActor", selectedActors);
        model.addAttribute("country", country);
        model.addAttribute("formTitle", "Create Movie");
        model.addAttribute("formAction", "/movies/save");
        model.addAttribute("buttonText", "Create Movie");
        return "edit-movie";
    }

    @PostMapping("/update")
    public String updateMovie(@RequestParam("id") Integer id, @RequestParam("title") String title,
            @RequestParam("genre") String genre, @RequestParam("year") Integer year,
            @RequestParam("country") Integer countryId, @RequestParam("actors") List<Integer> actors,
            @RequestParam("director") String director, @RequestParam("duration") Integer duration,
            @RequestParam("synopsis") String synopsis, Model model) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setCountry(countryService.getCountryById(countryId));
        movie.setDirector(director);
        movie.setDuration(duration);
        movie.setSynopsis(synopsis);
        movie.setCoverImage(movieService.getMovieById(id).getCoverImage());
        List<Actor> selectedActors = new ArrayList<>();
        for (Integer actorId : actors) {
            selectedActors.add(actorService.getActorById(actorId));
        }
        movie.setActors(selectedActors);
        movieService.updateMovie(movie);
        return "redirect:/movies/details/" + id;
    }

    @PostMapping("/save")
    public String saveMovie(@RequestParam("title") String title,
            @RequestParam("genre") String genre, @RequestParam("year") Integer year,
            @RequestParam("country") Integer countryId, @RequestParam("actors") List<Integer> actors,
            @RequestParam("director") String director, @RequestParam("duration") Integer duration,
            @RequestParam("synopsis") String synopsis, Model model) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setCountry(countryService.getCountryById(countryId));
        movie.setDirector(director);
        movie.setDuration(duration);
        movie.setSynopsis(synopsis);
        List<Actor> selectedActors = new ArrayList<>();
        for (Integer actorId : actors) {
            selectedActors.add(actorService.getActorById(actorId));
        }
        movie.setActors(selectedActors);
        movieService.createMovie(movie);
        return "redirect:/movies";
    }

}
