package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;

import java.util.*;

public interface HouseAdService {

    List<HouseAd> findAll();

    Optional<HouseAd> findById(Long id);

    Optional<HouseAd> save(HouseAd houseAd);

    Optional<HouseAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                           Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                           int quadrature, int yearMade, int yardArea, int numRooms, int numFloors, boolean hasBasement,
                           Heating heating);

    Optional<HouseAd> edit(Long houseAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                           Double price, String cityName, AdType type, Condition condition, Long categoryId,
                           int quadrature, int yearMade, int yardArea, int numRooms, int numFloors, boolean hasBasement,
                           Heating heating);

    void deleteById(Long adId);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, Integer quadratureFrom, Integer quadratureTo,
                        Integer yearMadeFrom, Integer yearMadeTo, Integer yardAreaFrom, Integer yardAreaTo, Integer numRoomsFrom, Integer numRoomsTo,
                        Integer numFloorsFrom, Integer numFloorsTo, Boolean hasBasement, Heating heating);

}
