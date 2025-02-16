package es.uah.movieswebfront.service;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public String guardarRate(int rating, Integer idMovie, Integer idUser) {
        Rate rate = new Rate();
        Usuario usuario = usuariosService.buscarUsuarioPorId(idUser);
    
        rate.setValue((double) rating);
        rate.setIdMovie(idMovie);
        rate.setUsuario(usuario);
        rate.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        template.postForObject(url, rate, Rate.class);
        
        return "Rate guardado";
    }

    @Override
    public void eliminarRate(Integer idRate) {
        template.delete(url+ "/" +  idRate);
    }
    
    @Override
    public List<Rate> obtenerTodosRates() {
        Rate[] rates = template.getForObject(url, Rate[].class);
        List<Rate> rateList = List.of(rates);
    
        // Para cada rate, asigna el usuario completo si existe su ID
        for (Rate rate : rateList) {
            if (rate.getUsuario() != null && rate.getUsuario().getIdUsuario() != null) {
                Usuario fullUser = usuariosService.buscarUsuarioPorId(rate.getUsuario().getIdUsuario());
                rate.setUsuario(fullUser);
            }
        }
        return rateList;
    }
}
