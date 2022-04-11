package mk.ukim.finki.wpproject.model;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String description;

    private boolean isExchangePossible;

    private Long price;

    private String city;

    @ElementCollection
    private List<String> imagesPaths;

    private LocalDateTime dateCreated;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "adCommented")
    private List<Comment> comments;

    @ManyToMany
    private List<User> savedByUsers;

    @ManyToOne
    private User advertisedByUser;

    public Ad() {
    }

    public Ad(String title, boolean isExchangePossible, Long price, String city, LocalDateTime dateCreated, Category category, User advertisedByUser) {
        this.title = title;
        this.isExchangePossible = isExchangePossible;
        this.price = price;
        this.city = city;
        this.dateCreated = dateCreated;
        this.category = category;
        this.advertisedByUser = advertisedByUser;
        this.description = "";
        this.savedByUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.imagesPaths = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExchangePossible() {
        return isExchangePossible;
    }

    public void setExchangePossible(boolean exchangePossible) {
        isExchangePossible = exchangePossible;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getImagesPaths() {
        return imagesPaths;
    }

    public void setImagesPaths(List<String> imagesPaths) {
        this.imagesPaths = imagesPaths;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getSavedByUsers() {
        return savedByUsers;
    }

    public void setSavedByUsers(List<User> savedByUsers) {
        this.savedByUsers = savedByUsers;
    }
}
