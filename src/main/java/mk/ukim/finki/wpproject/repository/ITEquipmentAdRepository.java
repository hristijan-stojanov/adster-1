package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import mk.ukim.finki.wpproject.model.enums.ITBrand;
import mk.ukim.finki.wpproject.model.enums.ProcessorBrand;
import mk.ukim.finki.wpproject.model.enums.TypeMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ITEquipmentAdRepository extends JpaRepository<ITEquipmentAd, Long> {

    List<ITEquipmentAd> findAllByBrand(ITBrand itBrand);

    List<ITEquipmentAd> findAllByProcessor(ProcessorBrand processor);

    List<ITEquipmentAd> findAllByProcessorModelContains(String processorModel);

    List<ITEquipmentAd> findAllByTypeMemory(TypeMemory typeMemory);

    List<ITEquipmentAd> findAllByMemorySizeGreaterThanEqual(Integer memorySize);

    List<ITEquipmentAd> findAllByMemorySizeLessThanEqual(Integer memorySize);

    List<ITEquipmentAd> findAllByMemorySizeGreaterThanEqualAndMemorySizeLessThanEqual(Integer memorySizeGreater, Integer memorySizeLess);

    List<ITEquipmentAd> findAllByRamMemorySizeGreaterThanEqual(Integer ramMemorySize);

    List<ITEquipmentAd> findAllByRamMemorySizeLessThanEqual(Integer ramMemorySize);

    List<ITEquipmentAd> findAllByRamMemorySizeGreaterThanEqualAndRamMemorySizeLessThanEqual(Integer ramMemorySizeGreater, Integer ramMemorySizeLess);

}
