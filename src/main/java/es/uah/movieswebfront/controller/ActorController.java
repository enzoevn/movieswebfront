package es.uah.movieswebfront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uah.movieswebfront.model.Actor;
import es.uah.movieswebfront.service.IActorService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private IActorService actorService;

    @GetMapping("")
    public String getAllActors(Model model) {
        List<Actor> actors = actorService.getAllActors();
        model.addAttribute("actors", actors);
        return "actors";
    }    

}
