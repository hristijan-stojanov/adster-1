package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import java.util.*;

public interface RealEstateAdService {

    List<RealEstateAd> findAll ();

    Optional<RealEstateAd> findById(Long id);

    Optional<RealEstateAd> save (RealEstateAd houseAd);

    Optional<RealEstateAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                                int quadrature);

    Optional<RealEstateAd> edit(Long realEstateAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                           Double price, String cityName, AdType type, Condition condition, Long categoryId,
                           int quadrature);

    void deleteById(Long adId);

}
