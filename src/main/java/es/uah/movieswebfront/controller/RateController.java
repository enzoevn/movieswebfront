package es.uah.movieswebfront.controller;


import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IRatesService;
import es.uah.movieswebfront.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rates")
public class RateController {
    @Autowired
    IRatesService ratesService;

    @Autowired
    IUsuariosService usuariosService;

}
