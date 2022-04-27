package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface HouseAdRepository extends JpaRepository<HouseAd, Long> {

    List<HouseAd> findAllByQuadratureGreaterThanEqual(Integer quadrature);

    List<HouseAd> findAllByQuadratureLessThanEqual(Integer quadrature);

    List<HouseAd> findAllByHeating(Heating heating);

    List<HouseAd> findAllByNumFloorsGreaterThanEqual(Integer floors);

    List<HouseAd> findAllByNumFloorsLessThanEqual(Integer floors);

    List<HouseAd> findAllByNumFloorsLessThanEqualAndNumFloorsGreaterThanEqual(Integer floorsLess, Integer floorsGreater);

    List<HouseAd> findAllByNumRoomsGreaterThan(Integer numRooms);

    List<HouseAd> findAllByNumRoomsLessThan(Integer numRooms);

    List<HouseAd> findAllByNumRoomsGreaterThanAndNumRoomsLessThan(Integer numRoomsGreater, Integer numRoomsLess);

    List<HouseAd> findAllByYearMadeLessThan(Integer yearMade);

    List<HouseAd> findAllByYearMadeGreaterThanAndYearMadeLessThan(Integer yearGreater, Integer yearLess);

}
