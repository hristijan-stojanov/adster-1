package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.adImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ImageDbRepository extends JpaRepository<adImage, Long> {
}
