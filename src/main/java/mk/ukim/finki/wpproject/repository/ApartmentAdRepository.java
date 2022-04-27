package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface ApartmentAdRepository extends JpaRepository<ApartmentAd, Long> {

    List<ApartmentAd> findAllByQuadratureGreaterThanEqual(Integer quadrature);

    List<ApartmentAd> findAllByQuadratureLessThanEqual(Integer quadrature);

    List<ApartmentAd> findAllByNumFloorsGreaterThanEqual(Integer floors);

    List<ApartmentAd> findAllByNumFloorsLessThanEqual(Integer floors);

    List<ApartmentAd> findAllByHasElevator(Boolean hasElevator);

    List<ApartmentAd> findAllByHasParkingSpot(Boolean hasParkingSpot);

    List<ApartmentAd> findAllByHeating(Heating heating);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndQuadratureGreaterThanEqual(Integer quadratureLess, Integer quadratureGreater);

    List<ApartmentAd> findAllByNumFloorsLessThanEqualAndNumFloorsGreaterThanEqual(Integer floorsLess, Integer floorsGreater);

    List<ApartmentAd> findAllByYearMadeGreaterThan(Integer yearMade);

    List<ApartmentAd> findAllByYearMadeLessThan(Integer yearMade);

    List<ApartmentAd> findAllByYearMadeGreaterThanAndYearMadeLessThan(Integer yearGreater, Integer yearLess);

}
