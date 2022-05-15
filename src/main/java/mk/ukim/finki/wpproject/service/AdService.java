package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.*;

public interface AdService {

    List<Ad> findAll();

    Optional<Ad> findById(Long id);

    // Optional<Ad> findByName(String name);
    // Optional<Ad> findByTitle (String title);

    Optional<Ad> save(Ad ad);


    Optional<Ad> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                      Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId);


    Optional<Ad> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                      Double price, String cityId, AdType type, Condition condition, Long categoryId);

    void deleteById(Long id);

    public Page<Ad> findPaginated(Pageable pageable, List<Ad> filteredAds);

    public String redirectAdBasedOnCategory(Long id);

    List<Ad> additionalFilter(Double priceFrom, Double priceTo);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo);
}
