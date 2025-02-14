package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatesService {

    Page<Rate> buscarTodas(Pageable pageable);

    Page<Rate> buscarRatesPorIdMovie(Integer idMovie, Pageable pageable);

    Rate buscarRatePorId(Integer idRate);

    String guardarRate(Rate rate);

    void eliminarRate(Integer idRate);

}
