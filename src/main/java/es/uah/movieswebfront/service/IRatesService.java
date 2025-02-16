package es.uah.movieswebfront.service;

import java.util.List;

import es.uah.movieswebfront.model.Rate;

public interface IRatesService {

    Rate buscarRatePorMovieId(Integer idMovie);
    String guardarRate(int rating, Integer idMovie, Integer idUser);
    void eliminarRate(Integer idRate);
    List<Rate> obtenerTodosRates();    

}
