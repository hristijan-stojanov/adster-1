package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.HouseAdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.CategoryRepository;
import mk.ukim.finki.wpproject.repository.CityRepository;
import mk.ukim.finki.wpproject.repository.HouseAdRepository;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.HouseAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseAdServiceImpl implements HouseAdService {

    private final HouseAdRepository houseAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public HouseAdServiceImpl(HouseAdRepository houseAdRepository, CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.houseAdRepository = houseAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }


    @Override
    public List<HouseAd> findAll() {
        return this.houseAdRepository.findAll();
    }

    @Override
    public Optional<HouseAd> findById(Long id) {
        return this.houseAdRepository.findById(id);
    }

    @Override
    public Optional<HouseAd> save(HouseAd houseAd) {
        return Optional.of(this.houseAdRepository.save(houseAd));
    }

    @Override
    public Optional<HouseAd> save(String title, String description, boolean isExchangePossible,
                                  boolean isDeliveryPossible, Double price, String cityName, AdType type,
                                  Condition condition, Long categoryId, Long userId, int quadrature,
                                  int yearMade, int yardArea, int numRooms, int numFloors,
                                  boolean hasBasement, Heating heating) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        HouseAd houseAd = new HouseAd(title, description, isExchangePossible, isDeliveryPossible, price,
                                        city, type, condition, category, user, quadrature, yearMade, yardArea,
                                        numRooms, numFloors, hasBasement, heating);

        return this.save(houseAd);
    }

    @Override
    public Optional<HouseAd> edit(Long houseAdId, String title, String description, boolean isExchangePossible,
                                  boolean isDeliveryPossible, Double price, String cityName, AdType type,
                                  Condition condition, Long categoryId, int quadrature,
                                  int yearMade, int yardArea, int numRooms, int numFloors,
                                  boolean hasBasement, Heating heating) {

        HouseAd houseAd = this.houseAdRepository.findById(houseAdId).orElseThrow(() -> new HouseAdNotFoundException(houseAdId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));

        AdSetterClass.adEditing(houseAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        houseAd.setQuadrature(quadrature);
        houseAd.setYearMade(yearMade);
        houseAd.setYardArea(yardArea);
        houseAd.setNumRooms(numRooms);
        houseAd.setNumFloors(numFloors);
        houseAd.setHasBasement(hasBasement);
        houseAd.setHeating(heating);

        return this.save(houseAd);
    }


    @Override
    public void deleteById(Long adId) {
        HouseAd houseAd = this.houseAdRepository.findById(adId).orElseThrow(() -> new HouseAdNotFoundException(adId));
        this.houseAdRepository.delete(houseAd);
    }
}
