package es.uah.movieswebfront.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;


@Service
public class RatesServiceImpl implements IRatesService {

    @Autowired
    RestTemplate template;

    @Autowired
    IUsuariosService usuariosService;

    @Autowired
    IMovieService moviesService;

    String url = "http://localhost:8091/rate";

    @Override
    public Rate buscarRatePorMovieId(Integer idMovie) {
        Rate[] rates = template.getForObject(url + "/movie/" + idMovie, Rate[].class);
        Double rateAverage = 0.0;
        if (rates != null && rates.length > 0) {
            for (Rate r : rates) {
                rateAverage += r.getValue();
                System.out.println("Rate: " + r.getValue());
            }
            rateAverage = rateAverage / rates.length;
            rates[0].setRateAverage(rateAverage); // Establece el promedio en el primer objeto Rate
        }

        return rates != null && rates.length > 0 ? rates[0] : null;
    }

    // @Override
    // public Page<Rate> buscarTodas(Pageable pageable) {
    //     Rate[] rates = template.getForObject(url, Rate[].class);
    //     List<Rate> ratesList = Arrays.asList(rates);

    //     int pageSize = pageable.getPageSize();
    //     int currentPage = pageable.getPageNumber();
    //     int startItem = currentPage * pageSize;
    //     List<Rate> list;

    //     if(ratesList.size() < startItem) {
    //         list = Collections.emptyList();
    //     } else {
    //         int toIndex = Math.min(startItem + pageSize, ratesList.size());
    //         list = ratesList.subList(startItem, toIndex);
    //     }
    //     Page<Rate> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ratesList.size());
    //     return page;
    // }

    // @Override
    // public Page<Rate> buscarRatesPorIdMovie(Integer idMovie, Pageable pageable) {
    //     Rate[] rates = template.getForObject(url+"/movie/"+idMovie, Rate[].class);
    //     List<Rate> ratesList = Arrays.asList(rates);

    //     int pageSize = pageable.getPageSize();
    //     int currentPage = pageable.getPageNumber();
    //     int startItem = currentPage * pageSize;
    //     List<Rate>list;

    //     if(ratesList.size() <startItem) {
    //         list = Collections.emptyList();
    //     } else {
    //         int toIndex = Math.min(startItem + pageSize, ratesList.size());
    //         list = ratesList.subList(startItem, toIndex);
    //     }
    //     Page<Rate> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ratesList.size());
    //     return page;
    // }

    @Override
    public String guardarRate(int rating, Integer idMovie, Integer idUser) {
        Rate rate = new Rate();
        Usuario usuario = usuariosService.buscarUsuarioPorId(idUser);
        Movie movie = moviesService.getMovieById(idMovie);
    
        rate.setValue((double) rating);
        rate.setIdMovie(movie.getId());
        rate.setUsuario(usuario);
        rate.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        template.postForObject(url, rate, Rate.class);
        
        return "Rate guardado";
    }

    @Override
    public void eliminarRate(Integer idRate) {
        template.delete(url+ "/" +  idRate);
    }

}
