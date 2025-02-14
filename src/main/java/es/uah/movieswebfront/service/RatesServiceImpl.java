package es.uah.movieswebfront.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.movieswebfront.model.Movie;
import es.uah.movieswebfront.model.Rate;
import es.uah.movieswebfront.model.Usuario;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class RatesServiceImpl implements IRatesService {

    @Autowired
    RestTemplate template;

    @Autowired
    IUsuariosService usuariosService;

    @Autowired
    IMovieService moviesService;

    String url = "http://localhost:8090/api/usuarios/rates"; //Cambia si es necesario

    @Override
    public Page<Rate> buscarTodas(Pageable pageable) {
        Rate[] rates = template.getForObject(url, Rate[].class);
        List<Rate> ratesList = Arrays.asList(rates);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Rate> list;

        if(ratesList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ratesList.size());
            list = ratesList.subList(startItem, toIndex);
        }
        Page<Rate> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ratesList.size());
        return page;
    }

    @Override
    public Page<Rate> buscarRatesPorIdMovie(Integer idMovie, Pageable pageable) {
        Rate[] rates = template.getForObject(url+"/movie/"+idMovie, Rate[].class);
        List<Rate> ratesList = Arrays.asList(rates);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Rate>list;

        if(ratesList.size() <startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ratesList.size());
            list = ratesList.subList(startItem, toIndex);
        }
        Page<Rate> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ratesList.size());
        return page;
    }

    @Override
    public Rate buscarRatePorId(Integer idRate) {
        Rate rate = template.getForObject(url+"/"+idRate, Rate.class);
        return rate;
    }

    @Override
    public String guardarRate(Rate rate) {
        Rate rateAux = template.postForObject(url, rate, Rate.class);
        return rateAux != null ? "Rate guardada con éxito" : "Error al guardar la rate";        
    }

    @Override
    public void eliminarRate(Integer idRate) {
        template.delete(url+ "/" +  idRate);
    }

}
