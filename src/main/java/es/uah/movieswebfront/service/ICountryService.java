package es.uah.movieswebfront.service;

import java.util.List;

import es.uah.movieswebfront.model.Country;

public interface ICountryService {

    List<Country> getAllCountries();
    Country getCountryById(Integer id);

}
