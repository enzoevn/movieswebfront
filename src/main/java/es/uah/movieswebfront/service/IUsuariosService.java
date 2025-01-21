package es.uah.movieswebfront.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.uah.movieswebfront.model.Usuario;

public interface IUsuariosService {

    Page<Usuario> buscarTodos(Pageable pageable);

    Usuario buscarUsuarioPorId(Integer idUsuario);

    Usuario buscarUsuarioPorNombre(String nombre);

    Usuario buscarUsuarioPorCorreo(String correo);

    Usuario login(String correo, String clave);

    void guardarUsuario(Usuario usuario);

    void eliminarUsuario(Integer idUsuario);

}
