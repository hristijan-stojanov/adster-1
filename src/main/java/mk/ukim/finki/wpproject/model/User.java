package mk.ukim.finki.wpproject.model;

import lombok.Data;
import mk.ukim.finki.wpproject.model.enums.Role;

import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany (mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "user_likes_ad",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ad_id"))
    private List<Ad> savedAds;

    @OneToMany
    private List<Ad> advertisedAds;

    public User() {
    }

    public User(String username, String password, String name, String surname, String email, String phoneNumber, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.comments = new ArrayList<>();
        this.savedAds = new ArrayList<>();
        this.advertisedAds = new ArrayList<>();
    }
}
