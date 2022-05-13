package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface ApartmentAdRepository extends JpaRepository<ApartmentAd, Long> {


    List<ApartmentAd> findAllByHasBasement (Boolean hasBasment);

    List<ApartmentAd> findAllByHasElevator (Boolean hasElevator);

    List<ApartmentAd> findAllByHasParkingSpot (Boolean hasParkingSpot);

    List<ApartmentAd> findAllByHeating (Heating heating);

    List<ApartmentAd> findAllByFloorGreaterThanAndFloorLessThan (Integer floorFrom, Integer floorTo);

    List<ApartmentAd> findAllByFloorGreaterThan (Integer floor);

    List<ApartmentAd> findAllByFloorLessThan (Integer floor);

    List<ApartmentAd> findAllByYearMadeGreaterThan(Integer yearMade);

    List<ApartmentAd> findAllByYearMadeLessThan (Integer yearMade);

    List<ApartmentAd> findAllByYearMadeGreaterThanAndYearMadeLessThan(Integer yearFrom, Integer yearTo);

    List<ApartmentAd> findAllByNumRoomsGreaterThanEqualAndNumRoomsLessThanEqual (Integer numRoomsGreater, Integer numRoomsLess);

    List<ApartmentAd> findAllByNumRoomsLessThan(Integer numRooms);

    List<ApartmentAd> findAllByNumRoomsGreaterThan (Integer numRooms);

}
