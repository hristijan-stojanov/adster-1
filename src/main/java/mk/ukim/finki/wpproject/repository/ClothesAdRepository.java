package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.ClothesAd;
import mk.ukim.finki.wpproject.model.enums.Color;
import mk.ukim.finki.wpproject.model.enums.Size;
import mk.ukim.finki.wpproject.model.enums.TypeClothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ClothesAdRepository extends JpaRepository<ClothesAd, Long> {

    List<ClothesAd> findAllByTypeClothing(TypeClothing typeClothing);

    List<ClothesAd> findAllByNumSize (Integer size);

    List<ClothesAd> findAllBySize (Size size);

    List<ClothesAd> findAllByColor(Color color);

}
