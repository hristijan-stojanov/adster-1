package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.repository.CityRepository;
import mk.ukim.finki.wpproject.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAll() {
        return this.cityRepository.findAll();
    }

    @Override
    public City create(String name) {

        City city = new City(name);

        return this.cityRepository.save(city);
    }

    @Override
    public City edit(String keyName, String nameToChangeTo) {
        City city = this.cityRepository.findById(keyName).orElseThrow(() -> new CityNotFoundException(keyName));

        city.setName(nameToChangeTo);

        return this.cityRepository.save(city);
    }


    @Override
    public void deleteById(String name) {
        City city = this.cityRepository.findById(name).orElseThrow(() -> new CityNotFoundException(name));

        this.cityRepository.delete(city);
    }
}
