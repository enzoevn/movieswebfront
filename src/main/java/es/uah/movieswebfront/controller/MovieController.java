package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Actor;
import es.uah.movieswebfront.model.Country;
import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IActorService;
import es.uah.movieswebfront.service.ICountryService;
import es.uah.movieswebfront.service.IMovieService;
import es.uah.movieswebfront.service.IRatesService;
import es.uah.movieswebfront.service.IUsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.security.Principal;
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

    @Autowired
    private IRatesService rateService;

    @Autowired
    private IUsuariosService usuarioService;

    private List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
    @GetMapping("/listado")
    public String getMovies(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 movies per page
        Page<Movie> moviePage = movieService.getAllMovies(pageable);
        PageRender<Movie> pageRender = new PageRender<>("/movies", moviePage);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("page", pageRender);
        return "movies";
    }

    @GetMapping("/details/{id}")
    public String getMovieById(Model model, @PathVariable Integer id, Principal principal) {
        Movie movie = movieService.getMovieById(id);
        // Buscar usuario por email en lugar de parsear el id
        Usuario user = usuarioService.buscarUsuarioPorCorreo(principal.getName());
        System.out.println("Usuario: " + user.getIdUsuario());
        Rate rate = rateService.buscarRatePorMovieId(id);
        List<Movie> movies = getAllMovies();
        Set<String> uniqueGenres = movies.stream().map(Movie::getGenre).collect(Collectors.toSet());
        model.addAttribute("movie", movie);
        model.addAttribute("movies", movies);
        model.addAttribute("uniqueGenres", uniqueGenres);
        model.addAttribute("rate", rate);
        model.addAttribute("user", user);
        return "movie_details";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, String searchType, @RequestParam(defaultValue = "0") int page, Model model) {
        List<Movie> movies = movieService.searchMovies(query, searchType);
        int pageSize = 5; // 5 movies per page
        int start = page * pageSize;
        int end = Math.min((start + pageSize), movies.size());
        List<Movie> paginatedMovies = movies.subList(start, end);
        Page<Movie> moviePage = new PageImpl<>(paginatedMovies, PageRequest.of(page, pageSize), movies.size());
        PageRender<Movie> pageRender = new PageRender<>("/movies/search?query=" + query + "&searchType=" + searchType, moviePage);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("page", pageRender);
        return "movies";
    }

    @PostMapping("/uploadImage/{id}")
    public String uploadImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image, Model model) {
        try {
            movieService.uploadImage(id, image);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            Page<Movie> movies = movieService.getAllMovies(PageRequest.of(0, 5));
            PageRender<Movie> pageRender = new PageRender<>("/movies", movies);
            model.addAttribute("movies", movies);
            model.addAttribute("page", pageRender);
            return "movies";
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
        model.addAttribute("movie", movie);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("actors", actorService.getAllActors());
        model.addAttribute("movieActor", selectedActors);
        model.addAttribute("country", country);
        model.addAttribute("formTitle", "Edit Movie");
        model.addAttribute("formAction", "/movies/update");
        model.addAttribute("buttonText", "Update Movie");
        return "forms/movie-form";
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
        return "forms/movie-form";
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

    @Secured("ROLE_ADMIN")
    @GetMapping("/rates")
    public String movieRatesPage(Model model) {
        // List<MovieRate> movieRates = movieRateService.getAllMovieRates();
        // model.addAttribute("movieRates", movieRates);
        model.addAttribute("titulo", "Gestión de Calificaciones de Películas");
        return "movie_rates"; // Nombre del template
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/rates/reset/{movieId}")
    public String resetMovieRate(@PathVariable Long movieId) {
        // movieRateService.resetMovieRate(movieId);
        return "redirect:/movies/rates";
    }

    @PostMapping("/rate")
    public String rateMovie(@RequestParam("rating") int rating,
                            @RequestParam("movieId") int movieId,
                            @RequestParam("userId") int userId) {
        System.out.println("Rating: " + rating);
        System.out.println("Movie ID: " + movieId);
        System.out.println("User ID: " + userId);
        
        rateService.guardarRate(rating, movieId, userId);
        
        return "redirect:/movies/details/" + movieId;
    }    

    @GetMapping
    public String getMoviesList(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 movies per page
        Page<Movie> moviePage = movieService.getAllMovies(pageable);
        PageRender<Movie> pageRender = new PageRender<>("/movies/listado", moviePage);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("page", pageRender);
        return "movies-list";
    }

}
