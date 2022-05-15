package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User register (String username, String password, String repeatPassword, String name, String surname, String email, String phoneNumber, Role role);

    User register (String username, String email);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<Ad> findAllSavedAdsByUser(Long userId);

    List<Ad> findAllAdvertisedAdsByUser(Long userId);

    Optional<User> edit(Long id, String username, String name, String surname, String email, String phoneNumber);

    Optional<User> save(User user);

    User getUserFromType(Object userType);

}
