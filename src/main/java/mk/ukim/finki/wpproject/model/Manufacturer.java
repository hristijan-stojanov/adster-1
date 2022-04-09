package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Manufacturer() {
    }

    public Manufacturer(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
