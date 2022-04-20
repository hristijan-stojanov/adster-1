package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.ITEquipmentAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITEquipmentAdRepository extends JpaRepository<ITEquipmentAd, Long> {
}
