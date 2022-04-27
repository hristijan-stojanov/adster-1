package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAllByTitleContainsAndCityAndCategory(String title, City city, Category category);

    List<Ad> findAllByTitleContainsAndCity(String title, City city);

    List<Ad> findAllByTitleContainsAndCategory(String title, Category category);

    List<Ad> findAllByCityAndCategory(City city, Category category);

    List<Ad> findAllByTitleContains(String title);

    List<Ad> findAllByCity(City city);

    List<Ad> findAllByCategory(Category category);


}