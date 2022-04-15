package mk.ukim.finki.wpproject.model;

import lombok.Data;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "ads")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String description;

    private boolean isExchangePossible;

    private boolean isDeliveryPossible;

    private Double price;

    private String city;

    @Enumerated(EnumType.STRING)
    private AdType type;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @OneToMany
    private List<adImage> images;

    private LocalDateTime dateCreated;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "adCommented")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "savedAds")
    private List<User> savedByUsers;

    @ManyToOne
    private User advertisedByUser;

    public Ad() {
    }

    public Ad(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
              Double price, String city, AdType type, Condition condition, Category category, User advertisedByUser) {
        this.title = title;
        this.description = description;
        this.isExchangePossible = isExchangePossible;
        this.isDeliveryPossible = isDeliveryPossible;
        this.price = price;
        this.city = city;
        this.type = type;
        this.condition = condition;
        this.category = category;
        this.advertisedByUser = advertisedByUser;
        this.images = new ArrayList<>();
        this.dateCreated = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.savedByUsers = new ArrayList<>();
    }
}
