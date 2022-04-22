package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;

import java.util.List;
import java.util.Optional;

public interface ApartmentAdService {
    List<ApartmentAd> findAll();

    Optional<ApartmentAd> findById(Long id);

    Optional<ApartmentAd> save(ApartmentAd apartmentAd);


    Optional<ApartmentAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                               Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId,
                               int quadrature, int yearMade, int numRooms, int numFloors, int floor,
                               boolean hasBasement, boolean hasElevator, boolean hasParkingSpot, Heating heating);


    Optional<ApartmentAd> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                               Double price, String cityId, AdType type, Condition condition, Long categoryId,
                               int quadrature, int yearMade, int numRooms, int numFloors, int floor,
                               boolean hasBasement, boolean hasElevator, boolean hasParkingSpot, Heating heating);

    void deleteById(Long id);

    // save create update delete findAll
}
