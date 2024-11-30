package es.uah.movieswebfront.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.movieswebfront.model.Country;

@Service
public class CountryServiceImp implements ICountryService {

    private String baseUrl = "http://localhost:8001/countries";

    @Override
    public List<Country> getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        Country[] countries = restTemplate.getForObject(baseUrl, Country[].class);
        assert countries != null;
        return List.of(countries);        
    }

    @Override
    public Country getCountryById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseUrl + "/" + id, Country.class);
    }
}
