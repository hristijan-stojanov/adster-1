package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
