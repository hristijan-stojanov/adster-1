package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.repository.AdRepository;
import mk.ukim.finki.wpproject.repository.CategoryRepository;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.AdService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public AdServiceImpl(AdRepository adRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Ad> findAll() {
        return this.adRepository.findAll();
    }

    @Override
    public Optional<Ad> findById(Long id) {
        return this.adRepository.findById(id);
    }

//    @Override
//    public Optional<Ad> findByName(String name) {
//        return this.adRepository.findByName;
//    }

    @Override
    public Optional<Ad> edit(Long id, String title, boolean isExchangePossible, Long price, String city, Long categoryId) {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id)); //might throw exception when not found
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(id));
        ad.setTitle(title);
        ad.setExchangePossible(isExchangePossible);
        ad.setPrice(price);
        ad.setCity(city);
        ad.setCategory(category);

        return Optional.of(this.adRepository.save(ad));
    }

    @Override
    public Optional<Ad> save(String title, boolean isExchangePossible, Long price, String city, LocalDateTime time, Long categoryId, String advertisedByUserUsername) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        User user = this.userRepository.findById(advertisedByUserUsername).orElseThrow(() -> new UserNotFoundException(advertisedByUserUsername));
        Ad ad = new Ad(title, isExchangePossible, price, city, time, category, user);

        return Optional.of(this.adRepository.save(ad));
    }

    @Override
    public void deleteById(Long id) {
        Ad ad = this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        this.adRepository.delete(ad);
    }
}
