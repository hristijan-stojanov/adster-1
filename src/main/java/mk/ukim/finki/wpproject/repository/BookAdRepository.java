package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.ads.BookAd;
import mk.ukim.finki.wpproject.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookAdRepository extends JpaRepository<BookAd, Long> {

    List<BookAd> findAllByAuthorContainsIgnoreCase(String name);

    List<BookAd> findAllByYearMadeGreaterThan(Integer yearMade);

    List<BookAd> findAllByYearMadeLessThan(Integer yearMade);

    List<BookAd> findAllByYearMadeGreaterThanAndYearMadeLessThan(Integer yearMadeGreater, Integer yearMadeLess);

    List<BookAd> findAllByNumPagesGreaterThan(Integer numPages);

    List<BookAd> findAllByNumPagesLessThan(Integer numPages);

    List<BookAd> findAllByNumPagesGreaterThanAndNumPagesLessThan(Integer numPagesGreater, Integer numPagesLess);

    List<BookAd> findAllByGenre(Genre genre);

}
