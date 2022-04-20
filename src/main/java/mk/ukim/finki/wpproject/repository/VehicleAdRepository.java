package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.VehicleAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAdRepository extends JpaRepository<VehicleAd, Long> {
}
