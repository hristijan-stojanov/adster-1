package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> subCategories;

    public Category() {
    }

    public Category(String name, List<String> subCategories) {
        this.name = name;
        this.subCategories = subCategories;
    }

}
