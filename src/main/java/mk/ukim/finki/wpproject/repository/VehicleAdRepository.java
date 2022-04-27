package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import mk.ukim.finki.wpproject.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface VehicleAdRepository extends JpaRepository<VehicleAd, Long> {

    List<VehicleAd> findAllByBrand(CarBrand brand);

    List<VehicleAd> findAllByYearMadeGreaterThanEqual(Integer year);

    List<VehicleAd> findAllByYearMadeLessThanEqual (Integer year);

    List<VehicleAd> findAllByYearMadeGreaterThanEqualAndYearMadeLessThanEqual(Integer yearGreater, Integer yearLess);

    List<VehicleAd> findAllByEnginePowerGreaterThan(Integer enginePower);

    List<VehicleAd> findAllByEnginePowerLessThan(Integer enginePower);

    List<VehicleAd> findAllByEnginePowerGreaterThanAndEnginePowerLessThanEqual(Integer enginePowerGreater, Integer enginePowerLess);

    List<VehicleAd> findAllByMilesTraveledGreaterThanEqual(Double milesTravelled);

    List<VehicleAd> findAllByMilesTraveledLessThanEqual(Double milesTravelled);

    List<VehicleAd> findAllByMilesTraveledGreaterThanEqualAndMilesTraveledLessThanEqual(Double milesTravelledGreater, Double milesTravelledLess);

    List<VehicleAd> findAllByFuel(Fuel fuel);

    List<VehicleAd> findAllByColor(Color color);

    List<VehicleAd> findAllByGearbox (Gearbox gearbox);

    List<VehicleAd> findAllByRegistration (Registration registration);



}
