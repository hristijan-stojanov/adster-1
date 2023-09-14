package mk.ukim.finki.wpproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Ad> ads;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Category parentCategory;
    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
