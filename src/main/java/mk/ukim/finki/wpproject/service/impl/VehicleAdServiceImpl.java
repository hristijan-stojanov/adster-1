package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.VehicleNotFoundException;
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.VehicleAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleAdServiceImpl implements VehicleAdService {

    private final AdService adService;
    private final AdRepository adRepository;
    private final VehicleAdRepository vehicleAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public VehicleAdServiceImpl(AdService adService, AdRepository adRepository, VehicleAdRepository vehicleAdRepository,
                                CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
        this.vehicleAdRepository = vehicleAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<VehicleAd> findAll() {
        return this.vehicleAdRepository.findAll();
    }

    @Override
    public Optional<VehicleAd> findById(Long id) {
        VehicleAd vehicleAd = this.vehicleAdRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        return Optional.of(vehicleAd);
    }

    @Override
    public Optional<VehicleAd> save(VehicleAd vehicleAd) {
        return Optional.of(this.vehicleAdRepository.save(vehicleAd));
    }

    @Override
    public Optional<VehicleAd> save(String title, String description, boolean isExchangePossible,
                                    boolean isDeliveryPossible, Double price, String cityName, AdType type,
                                    Condition condition, Long categoryId, Long userId, CarBrand brand,
                                    int yearMade, Color color, double milesTraveled, Fuel fuel,
                                    int enginePower, Gearbox gearbox, Registration registration) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));


        VehicleAd vehicleAd = new VehicleAd(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, user, brand, yearMade,
                color, milesTraveled, fuel, enginePower, gearbox, registration);

        return this.save(vehicleAd);
    }

    @Override
    public Optional<VehicleAd> edit(Long vehicleAdId, String title, String description,
                                    boolean isExchangePossible, boolean isDeliveryPossible, Double price,
                                    String cityName, AdType type, Condition condition, Long categoryId,
                                    CarBrand brand, int yearMade, Color color, double milesTraveled,
                                    Fuel fuel, int enginePower, Gearbox gearbox, Registration registration) {

        VehicleAd vehicleAd = this.vehicleAdRepository.findById(vehicleAdId).orElseThrow(() -> new VehicleNotFoundException(vehicleAdId));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));

        AdSetterClass.adEditing(vehicleAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        vehicleAd.setBrand(brand);
        vehicleAd.setYearMade(yearMade);
        vehicleAd.setColor(color);
        vehicleAd.setMilesTraveled(milesTraveled);
        vehicleAd.setFuel(fuel);
        vehicleAd.setEnginePower(enginePower);
        vehicleAd.setGearbox(gearbox);
        vehicleAd.setRegistration(registration);

        return this.save(vehicleAd);
    }

    @Override
    public void deleteById(Long adId) {
        VehicleAd vehicleAd = this.vehicleAdRepository.findById(adId).orElseThrow(() -> new VehicleNotFoundException(adId));
        this.vehicleAdRepository.delete(vehicleAd);
    }

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, CarBrand carBrand,
                               Integer yearMadeFrom, Integer yearMadeTo, Integer enginePowerFrom, Integer enginePowerTo,
                               Double milesTraveledFrom, Double milesTraveledTo, Fuel fuel, Color color, Gearbox gearbox, Registration registration) {

        List<Ad> filteredList = adRepository.findAll();
        filteredList.retainAll(adService.filterList(type, title, cityId, categoryId, priceFrom, priceTo));

        if (carBrand != null && !carBrand.toString().isEmpty()) {
            filteredList.retainAll(vehicleAdRepository.findAllByBrand(carBrand));
        }

        if (yearMadeFrom != null && yearMadeTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByYearMadeGreaterThanEqualAndYearMadeLessThanEqual(yearMadeFrom, yearMadeTo));
        else if (yearMadeTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByYearMadeLessThanEqual(yearMadeTo));
        else if (yearMadeFrom != null)
            filteredList.retainAll(vehicleAdRepository.findAllByYearMadeGreaterThanEqual(yearMadeFrom));


        if (enginePowerFrom != null && enginePowerTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByEnginePowerGreaterThanAndEnginePowerLessThanEqual(enginePowerFrom, enginePowerTo));
        else if (enginePowerTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByEnginePowerLessThan(enginePowerTo));
        else if (enginePowerFrom != null)
            filteredList.retainAll(vehicleAdRepository.findAllByEnginePowerGreaterThan(enginePowerFrom));


        if (milesTraveledFrom != null && milesTraveledTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByMilesTraveledGreaterThanEqualAndMilesTraveledLessThanEqual(milesTraveledFrom, milesTraveledTo));
        else if (milesTraveledTo != null)
            filteredList.retainAll(vehicleAdRepository.findAllByMilesTraveledLessThanEqual(milesTraveledTo));
        else if (milesTraveledFrom != null)
            filteredList.retainAll(vehicleAdRepository.findAllByMilesTraveledGreaterThanEqual(milesTraveledFrom));


        if (fuel != null && !fuel.toString().isEmpty()) {
            filteredList.retainAll(vehicleAdRepository.findAllByFuel(fuel));
        }


        if (color != null && !color.toString().isEmpty()) {
            filteredList.retainAll(vehicleAdRepository.findAllByColor(color));
        }


        if (gearbox != null && !gearbox.toString().isEmpty()) {
            filteredList.retainAll(vehicleAdRepository.findAllByGearbox(gearbox));
        }


        if (registration != null && !registration.toString().isEmpty()) {
            filteredList.retainAll(vehicleAdRepository.findAllByRegistration(registration));
        }

        return filteredList;
    }
}
