package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.enums.AdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
