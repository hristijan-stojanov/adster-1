package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User register (String username, String password, String repeatPassword, String name, String surname, String email, String phoneNumber, Role role);

    Optional<User> findByUsername(String username);

}
