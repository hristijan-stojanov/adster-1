package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.User;

public interface UserService {

    User register (String username, String password, String repeatPassword, String name, String surname, String email, String phoneNumber, Role role);

}
