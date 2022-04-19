package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.RealEstateAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateAdRepository extends JpaRepository<RealEstateAd, Long> {
}
