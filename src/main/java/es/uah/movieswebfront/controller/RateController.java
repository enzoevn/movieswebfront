package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.service.IRatesService;
import es.uah.movieswebfront.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rates")
public class RateController {
    @Autowired
    IRatesService ratesService;

    @Autowired
    IUsuariosService usuariosService;

}
