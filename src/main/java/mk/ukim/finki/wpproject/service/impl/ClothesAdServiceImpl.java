package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.CategoryRepository;
import mk.ukim.finki.wpproject.repository.CityRepository;
import mk.ukim.finki.wpproject.repository.ClothesAdRepository;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.ClothesAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClothesAdServiceImpl implements ClothesAdService {

    private final ClothesAdRepository clothesAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public ClothesAdServiceImpl(ClothesAdRepository clothesAdRepository, CategoryRepository categoryRepository,
                                UserRepository userRepository, CityRepository cityRepository) {
        this.clothesAdRepository = clothesAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<ClothesAd> findAll() {
        return this.clothesAdRepository.findAll();
    }

    @Override
    public Optional<ClothesAd> findById(Long id) {
        return this.clothesAdRepository.findById(id);
    }

    @Override
    public Optional<ClothesAd> save(ClothesAd clothesAd) {
        return Optional.of(clothesAdRepository.save(clothesAd));
    }

    @Override
    public Optional<ClothesAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                    Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId,
                                    TypeClothing typeClothing, int numSize, Size size, Color color) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        ClothesAd clothesAd = new ClothesAd(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, user, typeClothing, numSize, size, color);

        return Optional.of(clothesAdRepository.save(clothesAd));

    }

    @Override
    public Optional<ClothesAd> edit(Long adId, String title, String description, boolean isExchangePossible,
                                    boolean isDeliveryPossible, Double price, String cityId, AdType type, Condition condition,
                                    Long categoryId, TypeClothing typeClothing, int numSize, Size size, Color color) {


        ClothesAd clothesAd = this.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        AdSetterClass.adEditing(clothesAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type,
                condition, category);

        clothesAd.setTypeClothing(typeClothing);
        clothesAd.setNumSize(numSize);
        clothesAd.setSize(size);
        clothesAd.setColor(color);

        return this.save(clothesAd);
    }

    @Override
    public void deleteById(Long id) {
        ClothesAd clothesAd = this.clothesAdRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        this.clothesAdRepository.delete(clothesAd);
    }
}
