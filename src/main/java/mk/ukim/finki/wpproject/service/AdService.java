package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import java.util.*;

public interface AdService {

    List<Ad> findAll();

    Optional<Ad> findById(Long id);

   // Optional<Ad> findByName(String name);

    Optional<Ad> save(Ad ad);

    Optional<Ad> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                      Double price, City city, AdType type, Condition condition, Long categoryId, Long userId);

    Optional<Ad> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                      Double price, City city, AdType type, Condition condition, Long categoryId);

    void deleteById(Long adId);

}
