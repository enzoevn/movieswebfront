package es.uah.movieswebfront.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.uah.movieswebfront.model.Rate;

public interface IRatesService {

    List<Rate> buscarRatePorMovieId(Integer idMovie);
    String guardarRate(int rating, Integer idMovie, Integer idUser, String comments);
    void eliminarRate(Integer idRate);
    List<Rate> obtenerTodosRates();
    List<Rate> buscarRatesPorUsuarios(String query, String searchType);
    Page<Rate> buscarTodos(Pageable pageable);

}
