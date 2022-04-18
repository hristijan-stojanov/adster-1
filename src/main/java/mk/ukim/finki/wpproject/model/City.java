package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cities")
public class City {
    @Id
    private String name;

    @OneToMany(mappedBy = "city")
    private List<Ad> ads;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }
}
