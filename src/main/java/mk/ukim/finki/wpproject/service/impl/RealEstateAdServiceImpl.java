package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.RealEstateAdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.RealEstateAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RealEstateAdServiceImpl implements RealEstateAdService {

    private final AdService adService;
    private final AdRepository adRepository;
    private final RealEstateAdRepository realEstateAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public RealEstateAdServiceImpl(AdService adService, AdRepository adRepository, RealEstateAdRepository realEstateAdRepository,
                                   CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
        this.realEstateAdRepository = realEstateAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<RealEstateAd> findAll() {
        return this.realEstateAdRepository.findAll();
    }

    @Override
    public Optional<RealEstateAd> findById(Long id) {
        RealEstateAd realEstateAd = this.realEstateAdRepository.findById(id).orElseThrow(() -> new RealEstateAdNotFoundException(id));
        return Optional.of(realEstateAd);
    }

    @Override
    public Optional<RealEstateAd> save(RealEstateAd realEstateAd) {
        return Optional.of(this.realEstateAdRepository.save(realEstateAd));
    }

    @Override
    public Optional<RealEstateAd> save(String title, String description, boolean isExchangePossible,
                                       boolean isDeliveryPossible, Double price, String cityName,
                                       AdType type, Condition condition, Long categoryId, Long userId,
                                       int quadrature) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        RealEstateAd realEstateAd = new RealEstateAd(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, user, quadrature);

        return this.save(realEstateAd);
    }

    @Override
    public Optional<RealEstateAd> edit(Long realEstateAdId, String title, String description,
                                       boolean isExchangePossible, boolean isDeliveryPossible,
                                       Double price, String cityName, AdType type, Condition condition,
                                       Long categoryId, int quadrature) {

        RealEstateAd realEstateAd = this.realEstateAdRepository.findById(realEstateAdId).orElseThrow(() -> new RealEstateAdNotFoundException(realEstateAdId));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));

        AdSetterClass.adEditing(realEstateAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        realEstateAd.setQuadrature(quadrature);

        return this.save(realEstateAd);
    }

    @Override
    public void deleteById(Long adId) {
        RealEstateAd realEstateAd = this.realEstateAdRepository.findById(adId).orElseThrow(() -> new RealEstateAdNotFoundException(adId));
        this.realEstateAdRepository.delete(realEstateAd);
    }

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, Integer quadratureFrom, Integer quadratureTo) {
        List<Ad> filteredList = adRepository.findAll();

//        if (title != null && !title.isEmpty()){
//            filteredList.retainAll(this.adRepository.findByTitleContainsIgnoreCase(title));
//        }
//        if (cityId != null && !cityId.isEmpty()){
//            City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
//            filteredList.retainAll(this.adRepository.findAllByCity(city));
//        }
//        if (categoryId != null && !categoryId.toString().isEmpty()){
//            Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
//            filteredList.retainAll(this.adRepository.findAllByCategory(category));
//        }

        filteredList.retainAll(adService.filterList(type, title, cityId, categoryId, priceFrom, priceTo));

        if (quadratureFrom != null && quadratureTo != null)
            filteredList.retainAll(realEstateAdRepository.findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqual(quadratureFrom, quadratureTo));
        else if (quadratureTo != null)
            filteredList.retainAll(realEstateAdRepository.findAllByQuadratureLessThanEqual(quadratureTo));
        else if (quadratureFrom != null)
            filteredList.retainAll(realEstateAdRepository.findAllByQuadratureGreaterThanEqual(quadratureFrom));

        return filteredList;
    }
}
