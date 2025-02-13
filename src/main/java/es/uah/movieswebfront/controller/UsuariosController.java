package es.uah.movieswebfront.controller;

import es.uah.movieswebfront.model.Rol;
import es.uah.movieswebfront.model.Usuario;
import es.uah.movieswebfront.paginator.PageRender;
import es.uah.movieswebfront.service.IRolesService;
import es.uah.movieswebfront.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    IUsuariosService usuariosService;

    @Autowired
    IRolesService rolesService;

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Usuario usuario = usuariosService.buscarUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Detalle del usuario: " + usuario.getNombre());
        return "usuarios/verUsuario";
    }

    @GetMapping("/listado")
    public String listadoUsuarios(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Usuario> listado = usuariosService.buscarTodos(pageable);
        PageRender<Usuario> pageRender = new PageRender<Usuario>("/cusuarios/listado", listado);
        model.addAttribute("titulo", "Listado de todos los usuarios");
        model.addAttribute("listadoUsuarios", listado);
        model.addAttribute("page", pageRender);
        return "usuarios/listUsuario";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        List<Rol> roles = rolesService.buscarTodos();
        model.addAttribute("titulo", "Nuevo usuario");
        model.addAttribute("allRoles", roles);
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "usuarios/formUsuario";
    }

    @PostMapping("/guardar/")
    public String guardarUsuario(Model model, Usuario usuario, RedirectAttributes attributes) {
        //si existe un usuario con el mismo correo no lo guardamos
        if (usuariosService.buscarUsuarioPorCorreo(usuario.getCorreo())!=null) {
            attributes.addFlashAttribute("msga", "Error al guardar, ya existe el correo!");
            return "redirect:/cusuarios/listado";
        }
        List<Rol> roles = rolesService.buscarTodos();
        model.addAttribute("allRoles", roles);
        usuariosService.guardarUsuario(usuario);
        model.addAttribute("titulo", "Nuevo usuario");
        attributes.addFlashAttribute("msg", "Los datos del usuario fueron guardados!");
        return "redirect:/cusuarios/listado";
    }

    @PostMapping("/registrar")
    public String registro(Model model, Usuario usuario, RedirectAttributes attributes) {
        // new user

        System.out.println("Usuario: " + usuario);
        //si existe un usuario con el mismo correo no lo guardamos
        if (usuariosService.buscarUsuarioPorCorreo(usuario.getCorreo())!=null) {
            attributes.addFlashAttribute("msga", "Error al guardar, ya existe el correo!");
            return "redirect:/login";
        }
        Usuario user = new Usuario();
        model.addAttribute("username", user.getNombre());
        model.addAttribute("password", user.getClave());
        model.addAttribute("email", user.getCorreo());
        model.addAttribute("enabled", true);
        model.addAttribute("authorities", Arrays.asList("ROLE_USER"));
        model.addAttribute("formAction", "/usuarios/guardar");

        usuariosService.guardarUsuario(usuario);

        attributes.addFlashAttribute("msg", "Los datos del registro fueron guardados!");
        return "redirect:/login";
    }

    @GetMapping("/registrar")
    public String nuevoRegistro(Model model) {
        model.addAttribute("formTitle", "Register");
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "register";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(Model model, @PathVariable("id") Integer id) {
        Usuario usuario = usuariosService.buscarUsuarioPorId(id);
        model.addAttribute("titulo", "Editar usuario");
        model.addAttribute("usuario", usuario);
        List<Rol> roles = rolesService.buscarTodos();
        model.addAttribute("allRoles", roles);
        return "usuarios/formUsuario";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarUsuario(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Usuario usuario = usuariosService.buscarUsuarioPorId(id);
        if (usuario != null) {
            usuariosService.eliminarUsuario(id);
            attributes.addFlashAttribute("msg", "Los datos del usuario fueron borrados!");
        } else {
            attributes.addFlashAttribute("msg", "Usuario no encontrado!");
        }

        return "redirect:/usuarios/listado";
    }

}
