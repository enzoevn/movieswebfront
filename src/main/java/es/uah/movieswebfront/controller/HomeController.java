package es.uah.movieswebfront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.service.IMovieService;

@Controller
public class HomeController
{
    @Autowired
    private IMovieService movieService;
    
    @GetMapping("/")
    public String home(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "home"; // Ensure this matches the name of your home.html template
    }
}
