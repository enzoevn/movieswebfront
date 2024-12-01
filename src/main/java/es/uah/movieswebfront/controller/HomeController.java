package es.uah.movieswebfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IMovieService;

@Controller
public class HomeController
{
    @Autowired
    private IMovieService movieService;
    
    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Movie> moviePage = movieService.getAllMovies(PageRequest.of(page, 3)); // 3 movies per page
        PageRender<Movie> pageRender = new PageRender<>("/", moviePage);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("page", pageRender);
        return "home"; // Ensure this matches the name of your home.html template
    }
}
