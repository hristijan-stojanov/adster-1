package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.ApartmentAdService;
import mk.ukim.finki.wpproject.service.RealEstateAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentAdServiceImpl implements ApartmentAdService {

    private final AdService adService;
    private final RealEstateAdService realEstateAdService;
    private final AdRepository adRepository;
    private final ApartmentAdRepository apartmentAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public ApartmentAdServiceImpl(AdService adService, RealEstateAdService realEstateAdService, AdRepository adRepository, ApartmentAdRepository apartmentAdRepository, CategoryRepository categoryRepository,
                                  UserRepository userRepository, CityRepository cityRepository) {
        this.adService = adService;
        this.realEstateAdService = realEstateAdService;
        this.adRepository = adRepository;
        this.apartmentAdRepository = apartmentAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<ApartmentAd> findAll() {
        return this.apartmentAdRepository.findAll();
    }

    @Override
    public Optional<ApartmentAd> findById(Long id) {
        return this.apartmentAdRepository.findById(id);
    }

    @Override
    public Optional<ApartmentAd> save(ApartmentAd apartmentAd) {
        return Optional.of(apartmentAdRepository.save(apartmentAd));
    }


    @Override
    public Optional<ApartmentAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                      Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId,
                                      int quadrature, int yearMade, int numRooms, int numFloors, int floor,
                                      boolean hasBasement, boolean hasElevator, boolean hasParkingSpot, Heating heating) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        ApartmentAd apartmentAd = new ApartmentAd(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, user,
                quadrature, yearMade, numRooms, numFloors, floor,
                hasBasement, hasElevator, hasParkingSpot, heating);


        return Optional.of(this.apartmentAdRepository.save(apartmentAd));
    }

    @Override
    public Optional<ApartmentAd> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                      Double price, String cityId, AdType type, Condition condition, Long categoryId,
                                      int quadrature, int yearMade, int numRooms, int numFloors, int floor,
                                      boolean hasBasement, boolean hasElevator, boolean hasParkingSpot, Heating heating) {

        ApartmentAd apartmentAd = this.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        AdSetterClass.adEditing(apartmentAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        apartmentAd.setQuadrature(quadrature);
        apartmentAd.setYearMade(yearMade);
        apartmentAd.setNumRooms(numRooms);
        apartmentAd.setNumFloors(numFloors);
        apartmentAd.setFloor(floor);
        apartmentAd.setHasBasement(hasBasement);
        apartmentAd.setHasElevator(hasElevator);
        apartmentAd.setHasParkingSpot(hasParkingSpot);
        apartmentAd.setHeating(heating);

        return this.save(apartmentAd);
    }

    @Override
    public void deleteById(Long id) {
        ApartmentAd apartmentAd = this.apartmentAdRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        this.apartmentAdRepository.delete(apartmentAd);
    }

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, Integer quadratureFrom, Integer quadratureTo,
                               Integer yearMadeFrom, Integer yearMadeTo, Integer numRoomsFrom, Integer numRoomsTo, Integer floorFrom, Integer floorTo,
                               Boolean hasBasement, Boolean hasElevator, Boolean hasParkingSpot, Heating heating) {

        List<Ad> filteredList = adRepository.findAll();
        filteredList.retainAll(realEstateAdService.filterList(type, title, cityId, categoryId, priceFrom, priceTo, quadratureFrom, quadratureTo));

//        filteredList.retainAll(adService.filterList(title, cityId, categoryId, priceFrom, priceTo));

        if (yearMadeFrom != null && yearMadeTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByYearMadeGreaterThanAndYearMadeLessThan(yearMadeFrom, yearMadeTo));
        else if (yearMadeTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByYearMadeLessThan(yearMadeTo));
        else if (yearMadeFrom != null)
            filteredList.retainAll(apartmentAdRepository.findAllByYearMadeGreaterThan(yearMadeFrom));

        if (numRoomsFrom != null && numRoomsTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByNumRoomsGreaterThanEqualAndNumRoomsLessThanEqual(numRoomsFrom, numRoomsTo));
        else if (numRoomsTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByNumRoomsLessThan(numRoomsTo));
        else if (numRoomsFrom != null)
            filteredList.retainAll(apartmentAdRepository.findAllByNumRoomsGreaterThan(numRoomsFrom));

        if (floorFrom != null && floorTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByFloorGreaterThanAndFloorLessThan(floorFrom, floorTo));
        else if (floorTo != null)
            filteredList.retainAll(apartmentAdRepository.findAllByFloorLessThan(floorTo));
        else if (floorFrom != null)
            filteredList.retainAll(apartmentAdRepository.findAllByFloorGreaterThan(floorFrom));

        if (hasBasement != null && !hasBasement.toString().isEmpty()) {
            filteredList.retainAll(apartmentAdRepository.findAllByHasBasement(hasBasement));
        }

        if (hasElevator != null && !hasElevator.toString().isEmpty()) {
            filteredList.retainAll(apartmentAdRepository.findAllByHasElevator(hasElevator));
        }

        if (hasParkingSpot != null && !hasParkingSpot.toString().isEmpty()) {
            filteredList.retainAll(apartmentAdRepository.findAllByHasParkingSpot(hasParkingSpot));
        }

        if (heating != null && !heating.toString().isEmpty()) {
            filteredList.retainAll(apartmentAdRepository.findAllByHeating(heating));
        }

        return filteredList;
    }
}
