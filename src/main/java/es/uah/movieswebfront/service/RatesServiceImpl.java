package es.uah.movieswebfront.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Rate> buscarRatePorMovieId(Integer idMovie) {
        Rate[] rates = template.getForObject(url + "/movie/" + idMovie, Rate[].class);
        Double rateAverage = 0.0;
        if (rates != null && rates.length > 0) {
            for (Rate r : rates) {
                rateAverage += r.getValue();
                System.out.println("Rate: " + r.getValue());
            }
            rateAverage = rateAverage / rates.length;
            for (Rate r : rates) {
                r.setRateAverage(rateAverage); // Establece el promedio en cada objeto Rate
            }
        }

        return rates != null ? Arrays.asList(rates) : new ArrayList<>();
    }

    @Override
    public String guardarRate(int rating, Integer idMovie, Integer idUser, String comments) {
        Rate rate = new Rate();
        Usuario usuario = usuariosService.buscarUsuarioPorId(idUser);
    
        rate.setValue((double) rating);
        rate.setIdMovie(idMovie);
        rate.setUsuario(usuario);
        rate.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        rate.setComments(comments);

        System.out.println("Rate: " + rate);
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

    @Override
    public Page<Rate> buscarTodos(Pageable pageable) {
        Rate[] rates = template.getForObject(url, Rate[].class);
        List<Rate> ratesList = Arrays.asList(rates);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Rate> list;

        if (ratesList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ratesList.size());
            list = ratesList.subList(startItem, toIndex);
        }

        Page<Rate> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ratesList.size());
        return page;
    }

    @Override
    public List<Rate> buscarRatesPorUsuarios(String query, String searchType) {
        RestTemplate restTemplate = new RestTemplate();
        if (query.equals("") || query.equals("#")) {
            return buscarTodos(PageRequest.of(0, 5)).getContent();
        }
        Usuario usuario = usuariosService.buscarUsuarioPorNombre(query);
        if (usuario == null) {
            return Collections.emptyList();
        }
        String userId = usuariosService.buscarUsuarioPorNombre(query).getIdUsuario().toString();
        String urlQuery = url + "/" + searchType + "/" + userId;
        System.out.println(urlQuery);
        Rate[] rates = restTemplate.getForObject(urlQuery, Rate[].class);
        System.out.println(Arrays.toString(rates));
        assert rates != null;
        return Arrays.asList(rates);
    }
}
