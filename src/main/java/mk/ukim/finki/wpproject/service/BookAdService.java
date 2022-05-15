package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.BookAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Genre;
import mk.ukim.finki.wpproject.model.enums.Heating;

import java.util.List;
import java.util.Optional;

public interface BookAdService {

    List<BookAd> findAll();

    Optional<BookAd> findById(Long id);

    Optional<BookAd> save(BookAd bookAd);

    Optional<BookAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                          Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId,
                          String author, int yearMade, int numPages, Genre genre);

    Optional<BookAd> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                          Double price, String cityId, AdType type, Condition condition, Long categoryId,
                          String author, int yearMade, int numPages, Genre genre);

    void deleteById(Long id);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, String author, Genre genre);
}
