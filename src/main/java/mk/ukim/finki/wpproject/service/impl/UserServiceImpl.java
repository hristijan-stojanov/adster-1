package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wpproject.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpproject.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String email, String phoneNumber, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        else if (!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        else if (this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException(username);
        }
        User user = new User(username, password, name, surname, email, phoneNumber, role);

        return this.userRepository.save(user);
    }

}
