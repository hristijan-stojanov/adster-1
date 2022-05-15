package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Genre;

import java.util.*;

public interface RealEstateAdService {

    List<RealEstateAd> findAll();

    Optional<RealEstateAd> findById(Long id);

    Optional<RealEstateAd> save(RealEstateAd houseAd);

    Optional<RealEstateAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                                int quadrature);

    Optional<RealEstateAd> edit(Long realEstateAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                Double price, String cityName, AdType type, Condition condition, Long categoryId,
                                int quadrature);

    void deleteById(Long adId);

    List<Ad> filterList(AdType type,String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, Integer quadratureFrom, Integer quadratureTo);

}
