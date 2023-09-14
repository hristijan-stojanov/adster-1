package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpproject.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return  userRepository.findByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
        //return userRepository.findByUsernameAndPassword(username, password).
            //    orElseThrow(InvalidUserCredentialsException::new);
    }
}
