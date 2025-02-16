package es.uah.movieswebfront.service;

import es.uah.movieswebfront.model.Rate;

public interface IRatesService {

    // Page<Rate> buscarTodas(Pageable pageable);
    // Page<Rate> buscarRatesPorIdMovie(Integer idMovie, Pageable pageable);
    Rate buscarRatePorMovieId(Integer idMovie);
    String guardarRate(int rating, Integer idMovie, Integer idUser);
    void eliminarRate(Integer idRate);

}
