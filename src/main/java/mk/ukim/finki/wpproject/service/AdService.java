package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;

import java.time.LocalDateTime;
import java.util.*;

public interface AdService {

    List<Ad> findAll ();

    Optional<Ad> findById(Long id);

   // Optional<Ad> findByName(String name);

    Optional<Ad> edit (Long id, String title, boolean isExchangePossible, Long price, String city, Long categoryId);

    Optional<Ad> save (String title, boolean isExchangePossible, Long price, String city, LocalDateTime time, Long categoryId, String advertisedByUserUsername);

    void deleteById (Long id);


}
