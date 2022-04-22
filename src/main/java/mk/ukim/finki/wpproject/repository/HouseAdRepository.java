package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.realEstates.HouseAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseAdRepository extends JpaRepository<HouseAd, Long> {
}
