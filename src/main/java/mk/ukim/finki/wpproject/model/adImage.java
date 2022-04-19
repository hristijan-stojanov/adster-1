package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "images")
public class adImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String location;

    public adImage() {
    }

    public adImage(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
