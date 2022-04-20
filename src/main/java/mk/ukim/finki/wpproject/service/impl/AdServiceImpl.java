package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.AdRepository;
import mk.ukim.finki.wpproject.repository.CategoryRepository;
import mk.ukim.finki.wpproject.repository.CityRepository;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.AdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public AdServiceImpl(AdRepository adRepository, CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Ad> findAll() {
        return this.adRepository.findAll();
    }

    @Override
    public Optional<Ad> findById(Long id) {
        return this.adRepository.findById(id);
    }

//    @Override
//    public Optional<Ad> findByName(String name) {
//        return this.adRepository.findByName;
//    }
    // treba da se naprave "findByTitle"

    @Override
    public Optional<Ad> save(Ad ad) {
        return Optional.of(adRepository.save(ad));
    }

    @Override
    public Optional<Ad> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityId, AdType type, Condition condition, Long categoryId, Long userId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        Ad ad = new Ad(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, user);

        return Optional.of(this.adRepository.save(ad));
    }

    @Override
    public Optional<Ad> edit(Long adId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                             Double price, String cityId, AdType type, Condition condition, Long categoryId) {

        Ad ad = this.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        AdSetterClass.adEditing(ad, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        return this.save(ad);
    }

    @Override
    public void deleteById(Long id) {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        this.adRepository.delete(ad);
    }

}
