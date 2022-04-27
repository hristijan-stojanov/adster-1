package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RealEstateAdRepository extends JpaRepository<RealEstateAd, Long> {

    List<RealEstateAd> findAllByQuadratureLessThanEqual(Integer quadrature);

    List<RealEstateAd> findAllByQuadratureGreaterThanEqual(Integer quadrature);

    List<RealEstateAd> findAllByQuadratureGreaterThanEqualAndQuadratureLessThanEqual(Integer quadratureGreater, Integer quadratureLess);

}
