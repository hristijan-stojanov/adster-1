package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface HouseAdRepository extends JpaRepository<HouseAd, Long> {

    List<HouseAd> findAllByHeating(Heating heating);

    List<HouseAd> findAllByHasBasement (Boolean hasBasment);

    List<HouseAd> findAllByNumFloorsGreaterThanEqual(Integer floors);

    List<HouseAd> findAllByNumFloorsLessThanEqual(Integer floors);

    List<HouseAd> findAllByNumFloorsGreaterThanAndNumFloorsLessThan(Integer floorsLess, Integer floorsGreater);

    List<HouseAd> findAllByNumRoomsGreaterThan(Integer numRooms);

    List<HouseAd> findAllByNumRoomsLessThan(Integer numRooms);

    List<HouseAd> findAllByNumRoomsGreaterThanAndNumRoomsLessThan(Integer numRoomsGreater, Integer numRoomsLess);

    List<HouseAd> findAllByYardAreaLessThan(Integer yardArea);

    List<HouseAd> findAllByYardAreaGreaterThan (Integer yardArea);

    List<HouseAd> findAllByYardAreaGreaterThanAndYardAreaLessThan(Integer yardAreaGreater , Integer yardAreaLess);

    List<HouseAd> findAllByYearMadeLessThan(Integer yearMade);

    List<HouseAd> findAllByYearMadeGreaterThan(Integer yearMade);

    List<HouseAd> findAllByYearMadeGreaterThanAndYearMadeLessThan(Integer yearGreater, Integer yearLess);

}
