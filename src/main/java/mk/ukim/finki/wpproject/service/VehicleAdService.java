package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;

import java.util.*;

public interface VehicleAdService {

    List<VehicleAd> findAll();

    Optional<VehicleAd> findById(Long id);

    Optional<VehicleAd> save(VehicleAd vehicleAd);

    Optional<VehicleAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                             CarBrand brand, int yearMade, Color color, double milesTraveled, Fuel fuel, int enginePower,
                             Gearbox gearbox, Registration registration);

    Optional<VehicleAd> edit(Long vehicleAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityName, AdType type, Condition condition, Long categoryId,
                             CarBrand brand, int yearMade, Color color, double milesTraveled, Fuel fuel, int enginePower,
                             Gearbox gearbox, Registration registration);

    void deleteById(Long adId);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, CarBrand carBrand, Integer yearMadeFrom,
                        Integer yearMadeTo, Integer enginePowerFrom, Integer enginePowerTo, Double milesTraveledFrom, Double milesTraveledTo,
                        Fuel fuel, Color color, Gearbox gearbox, Registration registration);

}
