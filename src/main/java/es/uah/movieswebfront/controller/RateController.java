package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IMovieService;
import es.uah.movieswebfront.service.IRatesService;
import es.uah.movieswebfront.service.IUsuariosService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/rates")
public class RateController {
    @Autowired
    IRatesService ratesService;

    @Autowired
    IUsuariosService usuarioService;

    @Autowired
    IMovieService moviesService;

    @GetMapping("/listado")
    public String listRates(@RequestParam(defaultValue="0") int page, Model model, Principal principal) {
        List<Rate> rates = ratesService.obtenerTodosRates();
        Usuario user = usuarioService.buscarUsuarioPorCorreo(principal.getName());
        List<Movie> movies = moviesService.getAllMovies();
        
        // Asignar nombre de la película y nombre del usuario a cada rate
        for (Rate rate : rates) {
            try {
                Movie movie = moviesService.getMovieById(rate.getIdMovie());
                if (movie != null) {
                    rate.setMovieTitle(movie.getTitle());
                } else {
                    rate.setMovieTitle("Unknown Movie");
                }
            } catch (HttpClientErrorException e) {
                rate.setMovieTitle("Unknown Movie");
            }

            try {
                Usuario rateUser = usuarioService.buscarUsuarioPorId(rate.getIdUsuario());
                if (rateUser != null) {
                    rate.setUsername(rateUser.getNombre());
                } else {
                    rate.setUsername("Unknown User");
                }
            } catch (HttpClientErrorException e) {
                rate.setUsername("Unknown User");
            }
        }
        
        int pageSize = 5; // 5 rates per page
        int start = page * pageSize;
        int end = Math.min(start + pageSize, rates.size());
        List<Rate> paginatedRates = rates.subList(start, end);
        Page<Rate> ratePage = new PageImpl<>(paginatedRates, PageRequest.of(page, pageSize), rates.size());
        PageRender<Rate> pageRender = new PageRender<>("/rates/listado", ratePage);
        model.addAttribute("rates", ratePage.getContent());
        model.addAttribute("page", pageRender);
        model.addAttribute("user", user);
        model.addAttribute("movies", movies);
        return "rates";
    }

    @PostMapping("/delete/{idRate}")
    public String deleteRate(@PathVariable Integer idRate) {
        ratesService.eliminarRate(idRate);
        return "redirect:/rates/listado";
    }

    @GetMapping("/search")
    public String buscarRatesPorUsuarios(@RequestParam("query") String query, String searchType, @RequestParam(defaultValue = "0") int page, Model model) {
        List<Rate> rates = ratesService.buscarRatesPorUsuarios(query, searchType);
        System.out.println("Rates: " + rates);
        
        // se iteran los rates para asignar el nombre de usuario y el título de la película
        for (Rate rate : rates) {
            try {
                Usuario rateUser = usuarioService.buscarUsuarioPorId(rate.getIdUsuario());
                if (rateUser != null) {
                    rate.setUsername(rateUser.getNombre());
                } else {
                    rate.setUsername("Unknown User");
                }
            } catch (HttpClientErrorException e) {
                rate.setUsername("Unknown User");
            }
    
            try {
                Movie movie = moviesService.getMovieById(rate.getIdMovie());
                if (movie != null) {
                    rate.setMovieTitle(movie.getTitle());
                } else {
                    rate.setMovieTitle("Unknown Movie");
                }
            } catch (HttpClientErrorException e) {
                rate.setMovieTitle("Unknown Movie");
            }
        }
        
        int pageSize = 5; // 5 per page
        int start = page * pageSize;
        int end = Math.min((start + pageSize), rates.size());
        List<Rate> paginatedUsuario = rates.subList(start, end);
        Page<Rate> ratePage = new PageImpl<>(paginatedUsuario, PageRequest.of(page, pageSize), rates.size());
        PageRender<Rate> pageRender = new PageRender<>("/rates/query=" + query + "&searchType=" + searchType, ratePage);
        model.addAttribute("rates", ratePage.getContent());
        model.addAttribute("listadoRates", ratePage.getContent());
        model.addAttribute("page", pageRender);
        return "rates";
    }

}
