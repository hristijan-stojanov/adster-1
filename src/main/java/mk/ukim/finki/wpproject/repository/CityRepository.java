package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
}
