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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rates")
public class RateController {
    @Autowired
    IRatesService ratesService;

    @Autowired
    IUsuariosService usuariosService;

    @GetMapping("/listado")
    public String listadoRates(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Rate> listado = ratesService.buscarTodas(pageable);
        PageRender<Rate> pageRender = new PageRender<Rate>("/crates/listado", listado);
        model.addAttribute("titulo", "Listado de todas las rates");
        model.addAttribute("listadoRates", listado);
        model.addAttribute("page", pageRender);
        return "usuarios/listRate"; //Asegúrate de que este archivo existe.
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Rate rate = new Rate();
        model.addAttribute("titulo", "Nueva rate");
        model.addAttribute("rate", rate);
        return "usuarios/formRate"; //Asegúrate de que este archivo existe.
    }

    @PostMapping("/guardar/")
    public String guardarRate(Model model, Rate rate, RedirectAttributes attributes) {
        ratesService.guardarRate(rate);
        attributes.addFlashAttribute("msg", "Los datos de la rate fueron guardados!");
        return "redirect:/rates/listado";
    }

    // @GetMapping("/rate/{idMovie}")
    // public String rate(@PathVariable("idMovie") Integer idMovie, Model model, Principal principal) {
    //     Usuario usuario = usuariosService.buscarUsuarioPorCorreo(principal.getName());
    //     Movie movie = new Movie();
    //     movie.setId(idMovie);
    //     Rate rate = new Rate();
    //     rate.setUsuario(usuario);
    //     rate.setIdMovie(movie.getId());
    //     model.addAttribute("titulo", "Nueva rate");
    //     model.addAttribute("rate", rate);
    //     return "usuarios/formRate";
    // }

    @GetMapping("/editar/{id}")
    public String editarRate(Model model, @PathVariable("id") Integer id) {
        Rate rate = ratesService.buscarRatePorId(id);
        model.addAttribute("titulo", "Editar rate");
        model.addAttribute("rate", rate);
        return "usuarios/formRate"; //Asegúrate de que este archivo existe.
    }

    @GetMapping("/borrar/{id}")
    public String eliminarRate(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Rate rate = ratesService.buscarRatePorId(id);
        if (rate != null) {
            ratesService.eliminarRate(id);
            attributes.addFlashAttribute("msg", "Los datos de la rate fueron borrados!");
        } else {
            attributes.addFlashAttribute("msg", "Rate no encontrada!");
        }

        return "redirect:/crates/listado";
    }

}
