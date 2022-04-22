package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.User;

public interface AuthService {

    public User login (String username, String password);

}
