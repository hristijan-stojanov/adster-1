package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.enums.*;

import java.util.*;

public interface ITEquipmentAdService {

    List<ITEquipmentAd> findAll();

    Optional<ITEquipmentAd> findById(Long id);

    Optional<ITEquipmentAd> save(ITEquipmentAd houseAd);

    Optional<ITEquipmentAd> save(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                 Double price, String cityName, AdType type, Condition condition, Long categoryId, Long userId,
                                 ITBrand brand, ProcessorBrand processor, String processorModel, TypeMemory typeMemory,
                                 int memorySize, int ramMemorySize);

    Optional<ITEquipmentAd> edit(Long houseAdId, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                                 Double price, String cityName, AdType type, Condition condition, Long categoryId,
                                 ITBrand brand, ProcessorBrand processor, String processorModel, TypeMemory typeMemory,
                                 int memorySize, int ramMemorySize);

    void deleteById(Long adId);

    List<Ad> filterList(AdType type, String title, String cityId, Long categoryId, Double priceFrom, Double priceTo, ITBrand itBrand,
                        String model, ProcessorBrand processorBrand, String processorModel, TypeMemory typeMemory, Integer memorySizeFrom, Integer memorySizeTo,
                        Integer ramMemorySizeFrom, Integer ramMemorySizeTo);

}
