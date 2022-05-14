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

    @OneToMany(mappedBy = "category")
    private List<Ad> ads;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategories;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        ads = new ArrayList<>();
        parentCategory = null;
        this.subCategories = new ArrayList<>();
    }

    public Category(String name, Category parentCategory){
        this.name = name;
        this.parentCategory = parentCategory;
        ads = new ArrayList<>();
        this.subCategories = new ArrayList<>();
    }

    public String getSubCategoriesToString() {
        StringBuilder sb = new StringBuilder("");
        getSubCategories().forEach(c -> sb.append(c.getName()).append(", "));

        return sb.substring(0, sb.length() - 2);
    }
}
