package mk.ukim.finki.wpproject.model;

import lombok.Data;
import mk.ukim.finki.wpproject.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ad> savedAds = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ad> advertisedAds = new ArrayList<>();

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
        this.savedAds = new ArrayList<>();
        this.advertisedAds = new ArrayList<>();
    }

    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.savedAds = new ArrayList<>();
        this.advertisedAds = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User";
    }
}
