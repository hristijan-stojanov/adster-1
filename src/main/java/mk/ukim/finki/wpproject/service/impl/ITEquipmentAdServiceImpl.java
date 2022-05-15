package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.enums.*;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.ITEquipmentAdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.*;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.ITEquipmentAdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ITEquipmentAdServiceImpl implements ITEquipmentAdService {

    private final AdService adService;
    private final AdRepository adRepository;
    private final ITEquipmentAdRepository itEquipmentAdRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public ITEquipmentAdServiceImpl(AdService adService, AdRepository adRepository, ITEquipmentAdRepository itEquipmentAdRepository,
                                    CategoryRepository categoryRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
        this.itEquipmentAdRepository = itEquipmentAdRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<ITEquipmentAd> findAll() {
        return this.itEquipmentAdRepository.findAll();
    }

    @Override
    public Optional<ITEquipmentAd> findById(Long id) {
        return Optional.of(this.itEquipmentAdRepository.findById(id).orElseThrow(() -> new ITEquipmentAdNotFoundException(id)));
    }

    @Override
    public Optional<ITEquipmentAd> save(ITEquipmentAd houseAd) {
        return Optional.of(this.itEquipmentAdRepository.save(houseAd));
    }

    @Override
    public Optional<ITEquipmentAd> save(String title, String description, boolean isExchangePossible,
                                        boolean isDeliveryPossible, Double price, String cityName,
                                        AdType type, Condition condition, Long categoryId, Long userId,
                                        ITBrand brand, ProcessorBrand processor, String processorModel,
                                        TypeMemory typeMemory, int memorySize, int ramMemorySize) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));


        ITEquipmentAd itEquipmentAd = new ITEquipmentAd(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, user, brand,
                processor, processorModel, typeMemory, memorySize, ramMemorySize);

        return this.save(itEquipmentAd);
    }

    @Override
    public Optional<ITEquipmentAd> edit(Long itEquipmentAdId, String title, String description,
                                        boolean isExchangePossible, boolean isDeliveryPossible,
                                        Double price, String cityName, AdType type, Condition condition,
                                        Long categoryId, ITBrand brand, ProcessorBrand processor,
                                        String processorModel, TypeMemory typeMemory, int memorySize,
                                        int ramMemorySize) {

        ITEquipmentAd itEquipmentAd = this.itEquipmentAdRepository.findById(itEquipmentAdId).orElseThrow(() -> new ITEquipmentAdNotFoundException(itEquipmentAdId));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        City city = this.cityRepository.findById(cityName).orElseThrow(() -> new CityNotFoundException(cityName));

        AdSetterClass.adEditing(itEquipmentAd, title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category);

        itEquipmentAd.setBrand(brand);
        itEquipmentAd.setProcessor(processor);
        itEquipmentAd.setProcessorModel(processorModel);
        itEquipmentAd.setTypeMemory(typeMemory);
        itEquipmentAd.setMemorySize(memorySize);
        itEquipmentAd.setRamMemorySize(ramMemorySize);

        return this.save(itEquipmentAd);
    }

    @Override
    public void deleteById(Long adId) {
//        ITEquipmentAd itEquipmentAd = this.itEquipmentAdRepository.findById(adId).orElseThrow(() -> new ITEquipmentAdNotFoundException(adId));
//        this.itEquipmentAdRepository.delete(itEquipmentAd);

        this.itEquipmentAdRepository.deleteById(adId);
        // zaso ne se brise na taj nacin
    }

    @Override
    public List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, ITBrand itBrand,
                               String model, ProcessorBrand processorBrand, String processorModel, TypeMemory typeMemory,
                               Integer memorySizeFrom, Integer memorySizeTo, Integer ramMemorySizeFrom, Integer ramMemorySizeTo) {

        List<Ad> filteredList = adRepository.findAll();
        filteredList.retainAll(adService.filterList(type, title, cityId, categoryId, priceFrom, priceTo));


        if (itBrand != null && !itBrand.toString().isEmpty()) {
            filteredList.retainAll(itEquipmentAdRepository.findAllByBrand(itBrand));
        }


        if (processorBrand != null && !processorBrand.toString().isEmpty()) {
            filteredList.retainAll(itEquipmentAdRepository.findAllByProcessor(processorBrand));
        }


        if (processorModel != null && !processorModel.isEmpty()) {
            filteredList.retainAll(itEquipmentAdRepository.findAllByProcessorModelContains(processorModel));
        }


        if (typeMemory != null && !typeMemory.toString().isEmpty()) {
            filteredList.retainAll(itEquipmentAdRepository.findAllByTypeMemory(typeMemory));
        }


        if (memorySizeFrom != null && memorySizeTo != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByMemorySizeGreaterThanEqualAndMemorySizeLessThanEqual(memorySizeFrom, memorySizeTo));
        else if (memorySizeTo != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByMemorySizeLessThanEqual(memorySizeTo));
        else if (memorySizeFrom != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByMemorySizeGreaterThanEqual(memorySizeFrom));


        if (ramMemorySizeFrom != null && ramMemorySizeTo != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByRamMemorySizeGreaterThanEqualAndRamMemorySizeLessThanEqual(ramMemorySizeFrom, ramMemorySizeTo));
        else if (ramMemorySizeTo != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByRamMemorySizeLessThanEqual(ramMemorySizeTo));
        else if (ramMemorySizeFrom != null)
            filteredList.retainAll(itEquipmentAdRepository.findAllByRamMemorySizeGreaterThanEqual(ramMemorySizeFrom));


        return filteredList;
    }
}
