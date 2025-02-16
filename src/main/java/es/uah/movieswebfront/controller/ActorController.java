package es.uah.movieswebfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.uah.movieswebfront.model.Actor;
import es.uah.movieswebfront.model.Country;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IActorService;
import es.uah.movieswebfront.service.ICountryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private IActorService actorService;

    @Autowired
    private ICountryService countryService;

    @GetMapping("")
    public String getAllActors(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 actors per page
        Page<Actor> actorPage = actorService.getAllActors(pageable);
        PageRender<Actor> pageRender = new PageRender<>("/actors", actorPage);
        model.addAttribute("actors", actorPage.getContent());
        model.addAttribute("page", pageRender);
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

    @PostMapping("/delete/{id}")
    public String deleteActor(@PathVariable Integer id) {
        actorService.deleteActor(id);
        return "redirect:/actors";
    }

    @GetMapping("/edit/{id}")
    public String updateActor(Model model, @PathVariable Integer id) {
        Actor actor = actorService.getActorById(id);
        Country country = actor.getBirthCountry();
        model.addAttribute("actor", actor);
        model.addAttribute("country", country);
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("formTitle", "Edit Actor");
        model.addAttribute("formAction", "/actors/update");
        model.addAttribute("buttonText", "Update Actor");
        return "forms/actor-form";
    }

    @PostMapping("/update")
    public String updateActor(@RequestParam Integer id, @RequestParam String name, @RequestParam String birthDate, @RequestParam("birthCountry") Integer countryId) {
        Actor actor = new Actor();
        actor.setId(id);
        actor.setName(name);
        actor.setBirthDate(birthDate);
        actor.setBirthCountry(countryService.getCountryById(countryId));
        actorService.updateActor(actor);
        return "redirect:/actors";
    }


}
