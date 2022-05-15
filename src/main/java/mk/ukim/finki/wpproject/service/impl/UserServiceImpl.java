package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wpproject.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpproject.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wpproject.repository.UserRepository;
import mk.ukim.finki.wpproject.security.oauth2.CustomerOAuth2User;
import mk.ukim.finki.wpproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String email, String phoneNumber, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        if (!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException(username);
        }

        User user = new User(username, passwordEncoder.encode(password), name, surname, email, phoneNumber, role);

        return this.userRepository.save(user);
    }

    @Override
    public User register(String username, String email) {
        if (this.userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        }
        User user = new User(username, email, Role.ROLE_USER);

        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Ad> findAllSavedAdsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return user.getSavedAds();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Ad> findAllAdvertisedAdsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getAdvertisedAds();
    }

    @Override
    public Optional<User> edit(Long id, String username, String name, String surname, String email, String phoneNumber) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        return this.save(user);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public User getUserFromType(Object userType) {
        User user = null;

        if (userType.toString().equals("CustomerOAuth2User")) {
            CustomerOAuth2User customerOAuth2User = (CustomerOAuth2User) userType;

            user = this.register(customerOAuth2User.getName(), customerOAuth2User.getEmail());
        }
        else {
            Long userId = ((User) userType).getId();
            user = this.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        }

        return user;
    }
}
