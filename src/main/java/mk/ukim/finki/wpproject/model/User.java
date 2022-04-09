package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "korisnik")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public User() {
    }
}
