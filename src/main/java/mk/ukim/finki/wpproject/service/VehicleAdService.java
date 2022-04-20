package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;

import java.util.*;
public interface VehicleAdService {

    List<VehicleAd> findAll ();

    Optional<VehicleAd> findById(Long id);

    Optional<VehicleAd> save (VehicleAd vehicleAd);

    Optional<VehicleAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                             CarBrand brand, int yearMade, Color color, double milesTraveled, Fuel fuel, int enginePower,
                             Gearbox gearbox, Registration registration);

    Optional<VehicleAd> edit(Long vehicleAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityName, AdType type, Condition condition, Long categoryId,
                             CarBrand brand, int yearMade, Color color, double milesTraveled, Fuel fuel, int enginePower,
                             Gearbox gearbox, Registration registration);

    void deleteById(Long adId);
}
