package es.uah.movieswebfront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uah.movieswebfront.model.Actor;
import es.uah.movieswebfront.model.Country;
import es.uah.movieswebfront.service.IActorService;
import es.uah.movieswebfront.service.ICountryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private IActorService actorService;

    @Autowired
    private ICountryService countryService;

    @GetMapping("")
    public String getAllActors(Model model) {
        List<Actor> actors = actorService.getAllActors();
        model.addAttribute("actors", actors);
        return "actors";
    }
    
    @GetMapping("/create")
    public String createActor(Model model) {
        Actor actor = new Actor();
        Country country = new Country();
        model.addAttribute("actor", actor);
        model.addAttribute("country", country);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("formTitle", "Create Actor");
        model.addAttribute("formAction", "/actors/save");
        model.addAttribute("buttonText", "Create Actor");
        return "forms/actor-form";
    }

    @PostMapping("/save")
    public String saveActor(@RequestParam String name, @RequestParam String birthDate, @RequestParam("birthCountry") Integer countryId) {
        Actor actor = new Actor();
        System.out.println("Country ID: " + countryId);
        actor.setName(name);
        actor.setBirthDate(birthDate);
        actor.setBirthCountry(countryService.getCountryById(countryId));
        actorService.saveActor(actor);
        return "redirect:/actors";
    }

    @GetMapping("/delete")
    public String deleteActor(Integer id) {
        actorService.deleteActor(id);
        return "redirect:/actors";
    }

}
