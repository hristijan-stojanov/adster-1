package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.BookAd;
import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.exceptions.*;
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.AdService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final ApartmentAdRepository apartmentAdRepository;
    private final BookAdRepository bookAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public AdServiceImpl(AdRepository adRepository, ApartmentAdRepository apartmentAdRepository, BookAdRepository bookAdRepository, CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.adRepository = adRepository;
        this.apartmentAdRepository = apartmentAdRepository;
        this.bookAdRepository = bookAdRepository;
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
        this.adRepository.deleteById(id);
    }


    public Page<Ad> findPaginated(Pageable pageable, List<Ad> filteredAds) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ad> adsSubList;
        List<Ad> ads = filteredAds;

        if (ads.size() < startItem) {
            adsSubList = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, ads.size());
            adsSubList = ads.subList(startItem, toIndex);
        }

        return new PageImpl<Ad>(adsSubList, PageRequest.of(currentPage, pageSize), ads.size());
    }

    @Override
    public String redirectAdBasedOnCategory(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        if (category.getName().equals("Apartment")) {
            return "ApartmentAd";
        } else if (category.getName().equals("House")) {
            return "HouseAd";
        } else if (category.getName().equals("Real Estate")) {
            return "RealEstateAd";
        } else if (category.getName().equals("Book")) {
            return "BookAd";
        } else if (category.getName().equals("Clothes")) {
            return "ClothesAd";
        } else if (category.getName().equals("IT Equipment")) {
            return "ITEquipmentAd";
        } else if (category.getName().equals("Vehicle")) {
            return "VehicleAd";
        } else {
            return "OtherAd";
        }
    }

    @Override
    public List<Ad> additionalFilter(Double priceFrom, Double priceTo) {
        if (priceFrom != null && priceTo != null)
            return adRepository.findAllByPriceIsBetween(priceFrom, priceTo);
        else if (priceFrom != null)
            return adRepository.findAllByPriceIsBetween(priceFrom, Double.MAX_VALUE);
        else if (priceTo != null)
            return adRepository.findAllByPriceIsBetween(0.0, priceTo);
        return adRepository.findAll();
    }

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo) {
        List<Ad> filteredList = adRepository.findAll();

        if (type != null && !type.toString().isEmpty()){
            filteredList.retainAll(this.adRepository.findAllByType(type));
        }

        if (title != null && !title.isEmpty()) {
            filteredList.retainAll(this.adRepository.findByTitleContainsIgnoreCase(title));
        }
        if (cityId != null && !cityId.isEmpty()) {
            City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
            filteredList.retainAll(this.adRepository.findAllByCity(city));
        }
        if (categoryId != null && !categoryId.toString().isEmpty()) {
            Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
            filteredList.retainAll(this.adRepository.findAllByCategory(category));
        }

        if (priceFrom != null && priceTo != null)
            filteredList.retainAll(adRepository.findAllByPriceIsBetween(priceFrom, priceTo));
        else if (priceFrom != null)
            filteredList.retainAll(adRepository.findAllByPriceIsBetween(priceFrom, Double.MAX_VALUE));
        else if (priceTo != null)
            filteredList.retainAll(adRepository.findAllByPriceIsBetween(0.0, priceTo));

        return filteredList;
    }
}
