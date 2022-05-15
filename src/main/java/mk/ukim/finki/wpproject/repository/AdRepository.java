package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAllByType (AdType type);

    List<Ad> findByTitleContainsIgnoreCase(String title);
//    @Query(value = "select *, 0 as clazz_ from ads a where a.title ilike :title", nativeQuery = true)
//    List<Ad> findAllByTitleLikeQuery(@Param("title") String title);

    List<Ad> findAllByCity(City city);

    List<Ad> findAllByCategory(Category category);

    List<Ad> findAllByPriceIsBetween(Double priceFrom, Double priceTo);

}