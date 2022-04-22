package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.City;

import java.util.*;

public interface CityService {

    List<City> findAll();

    City create (String name);

    City edit (String keyName, String nameToChangeTo);

    void deleteById(String name);

}
