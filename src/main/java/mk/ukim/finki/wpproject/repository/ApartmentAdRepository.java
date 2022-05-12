package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.ApartmentAd;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface ApartmentAdRepository extends JpaRepository<ApartmentAd, Long> {

    //5

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsFrom, Integer floorsTo, Integer yearMade);

    //--------------------------------------------------------------------------//

    //4

    //No yearMade
    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsFrom, Integer floorsTo);

    //No numFloorsTo
    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsFrom, Integer yearMade);

    //No numFloorsFrom
    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsTo, Integer yearMade);

    //No quadratureTo
    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer floorsFrom, Integer floorsTo, Integer yearMade);

    //No quadratureFrom
    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureTo, Integer floorsFrom, Integer floorsTo, Integer yearMade);

    //-----------------------------------------------------------------------//

    //3

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsGreaterThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsFrom);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndNumFloorsLessThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer floorsTo);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqual(Integer quadratureFrom, Integer floorsFrom, Integer floorsTo);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndNumFloorsLessThanEqual(Integer quadratureTo, Integer floorsFrom, Integer floorsTo);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer quadratureTo, Integer yearMade);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsGreaterThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer floorsFrom, Integer yearMade);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsGreaterThanEqualAndYearMadeGreaterThanEqual(Integer quadratureTo, Integer floorsFrom, Integer yearMade);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer floorsTo, Integer yearMade);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureTo, Integer floorsTo, Integer yearMade);

    List<ApartmentAd> findAllByNumFloorsGreaterThanEqualAndNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer floorsFrom, Integer floorsTo, Integer yearMade);


    //-------------------------------------------------------------------------//

    //2

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqual(Integer quadratureFrom, Integer quadratureTo);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsGreaterThanEqual(Integer quadratureFrom, Integer numFloorsFrom);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndNumFloorsLessThanEqual(Integer quadratureFrom, Integer numFloorsTo);

    List<ApartmentAd> findAllByQuadratureGreaterThanEqualAndYearMadeGreaterThanEqual(Integer quadratureFrom, Integer yearMade);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsGreaterThanEqual(Integer quadratureTo, Integer numFloorsFrom);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndNumFloorsLessThanEqual(Integer quadratureTo, Integer numFloorsTo);

    List<ApartmentAd> findAllByQuadratureLessThanEqualAndYearMadeGreaterThanEqual(Integer quadratureTo, Integer yearFrom);

    List<ApartmentAd> findAllByNumFloorsGreaterThanEqualAndNumFloorsLessThanEqual(Integer floorsFrom, Integer floorsTo);

    List<ApartmentAd> findAllByNumFloorsGreaterThanEqualAndYearMadeGreaterThanEqual(Integer floorsFrom, Integer yearMade);

    List<ApartmentAd> findAllByNumFloorsLessThanEqualAndYearMadeGreaterThanEqual(Integer numFloorsTo, Integer yearMade);

    //--------------------------------------------------------------------------//

    //1

    List<ApartmentAd> findAllByQuadratureGreaterThanEqual(Integer quadrature);

    List<ApartmentAd> findAllByQuadratureLessThanEqual(Integer quadrature);

    List<ApartmentAd> findAllByNumFloorsGreaterThanEqual(Integer floors);

    List<ApartmentAd> findAllByNumFloorsLessThanEqual(Integer floors);

    List<ApartmentAd> findAllByYearMadeGreaterThan(Integer yearMade);

}
