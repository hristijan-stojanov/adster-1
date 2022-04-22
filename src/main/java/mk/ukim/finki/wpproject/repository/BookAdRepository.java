package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.BookAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAdRepository extends JpaRepository<BookAd, Long> {
}
