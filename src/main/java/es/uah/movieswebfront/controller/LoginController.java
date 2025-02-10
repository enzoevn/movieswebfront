package es.uah.movieswebfront.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model,
            Principal principal) {

        if (principal != null) {
            return "redirect:/movies";
        }

        if (error != null) {
            model.addAttribute("msg",
                    "Error al iniciar sesión: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
        }

        return "login";
    }

    // @PostMapping("/login")
    // public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
    //     System.out.println("Username: " + username);
    //     System.out.println("Password: " + password);
    //     // Aquí puedes agregar la lógica para autenticar al usuario usando el servicio de usuarios
    //     // Por ejemplo, puedes llamar a un servicio que verifique las credenciales del usuario
    //     // y redirigir al usuario a la página correspondiente si las credenciales son correctas

    //     // Ejemplo de lógica de autenticación (esto debe ser reemplazado con tu lógica real)
    //     if ("admin@example.com".equals(username) && "password".equals(password)) {
    //         return "redirect:/movies";
    //     } else {
    //         model.addAttribute("msg", "Error al iniciar sesión: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
    //         return "login";
    //     }
    // }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
