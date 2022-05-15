package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
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
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.HouseAdService;
import mk.ukim.finki.wpproject.service.RealEstateAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseAdServiceImpl implements HouseAdService {

    private final RealEstateAdService realEstateAdService;
    private final AdRepository adRepository;
    private final HouseAdRepository houseAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public HouseAdServiceImpl(RealEstateAdService realEstateAdService, AdRepository adRepository, HouseAdRepository houseAdRepository,
                              CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.realEstateAdService = realEstateAdService;
        this.adRepository = adRepository;
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

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, Integer quadratureFrom, Integer quadratureTo,
                               Integer yearMadeFrom, Integer yearMadeTo, Integer yardAreaFrom, Integer yardAreaTo, Integer numRoomsFrom, Integer numRoomsTo,
                               Integer numFloorsFrom, Integer numFloorsTo, Boolean hasBasement, Heating heating) {


        List<Ad> filteredList = adRepository.findAll();
        filteredList.retainAll(realEstateAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, quadratureFrom, quadratureTo));

        if (yearMadeFrom != null && yearMadeTo != null)
            filteredList.retainAll(houseAdRepository.findAllByYearMadeGreaterThanAndYearMadeLessThan(yearMadeFrom, yearMadeTo));
        else if (yearMadeTo != null)
            filteredList.retainAll(houseAdRepository.findAllByYearMadeLessThan(yearMadeTo));
        else if (yearMadeFrom != null)
            filteredList.retainAll(houseAdRepository.findAllByYearMadeGreaterThan(yearMadeFrom));


        if (yardAreaFrom != null && yardAreaTo != null)
            filteredList.retainAll(houseAdRepository.findAllByYardAreaGreaterThanAndYardAreaLessThan(yardAreaFrom, yardAreaTo));
        else if (yardAreaTo != null)
            filteredList.retainAll(houseAdRepository.findAllByYardAreaLessThan(yardAreaTo));
        else if (yardAreaFrom != null)
            filteredList.retainAll(houseAdRepository.findAllByYardAreaGreaterThan(yardAreaFrom));


        if (numRoomsFrom != null && numRoomsTo != null)
            filteredList.retainAll(houseAdRepository.findAllByNumRoomsGreaterThanAndNumRoomsLessThan(numRoomsFrom, numRoomsTo));
        else if (numRoomsTo != null)
            filteredList.retainAll(houseAdRepository.findAllByNumRoomsLessThan(numRoomsTo));
        else if (numRoomsFrom != null)
            filteredList.retainAll(houseAdRepository.findAllByNumRoomsGreaterThan(numRoomsFrom));


        if (numFloorsFrom != null && numFloorsTo != null)
            filteredList.retainAll(houseAdRepository.findAllByNumFloorsGreaterThanAndNumFloorsLessThan(numFloorsFrom, numFloorsTo));
        else if (numFloorsTo != null)
            filteredList.retainAll(houseAdRepository.findAllByNumFloorsLessThanEqual(numFloorsTo));
        else if (numFloorsFrom != null)
            filteredList.retainAll(houseAdRepository.findAllByNumFloorsGreaterThanEqual(numFloorsFrom));


        if (hasBasement != null && !hasBasement.toString().isEmpty()) {
            filteredList.retainAll(houseAdRepository.findAllByHasBasement(hasBasement));
        }


        if (heating != null && !heating.toString().isEmpty()) {
            filteredList.retainAll(houseAdRepository.findAllByHeating(heating));
        }

        return filteredList;
    }
}
