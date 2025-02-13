package es.uah.movieswebfront.service;

import java.util.List;

import es.uah.movieswebfront.model.Rol;


public interface IRolesService {

    List<Rol> buscarTodos();

    Rol buscarRolPorId(Integer idRol);

    void guardarRol(Rol rol);

    void eliminarRol(Integer idRol);

}
