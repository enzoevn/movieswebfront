package es.uah.movieswebfront.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Secured("ROLE_ADMIN") //Solo accesible a usuarios con el rol de administrador
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "admin"; // Nombre del template (admin.html)
    }
}
