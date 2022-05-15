package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.*;

import java.util.List;
import java.util.Optional;

public interface ClothesAdService {

    List<ClothesAd> findAll();


    Optional<ClothesAd> findById(Long id);


    Optional<ClothesAd> save(ClothesAd clothesAd);


    Optional<ClothesAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId,
                             TypeClothing typeClothing, int numSize, Size size, Color color);


    Optional<ClothesAd> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityId, AdType type, Condition condition, Long CategoryId,
                             TypeClothing typeClothing, int numSize, Size size, Color color);


    void deleteById(Long id);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, TypeClothing typeClothing, Size size, Color color);

}
