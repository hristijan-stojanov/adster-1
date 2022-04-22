package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesAdRepository extends JpaRepository<ClothesAd, Long> {
}
