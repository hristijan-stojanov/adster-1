package mk.ukim.finki.wpproject.model;

import lombok.Data;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "ads")
@Inheritance(strategy = InheritanceType.JOINED)
@OnDelete(action = OnDeleteAction.CASCADE)
public class Ad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String description;

    private boolean isExchangePossible;

    private boolean isDeliveryPossible;

    private Double price;

    @Column(nullable = true)
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private AdType type;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<adImage> images;

    @ManyToOne
    private Category category;

    @ManyToOne
    private City city;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne
    private User advertisedByUser;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "savedAds")
    private List<User> savedByUsers = new ArrayList<>();

    public Ad() {
    }

    public Ad(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
              Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser) {
        this.title = title;
        this.description = description;
        this.isExchangePossible = isExchangePossible;
        this.isDeliveryPossible = isDeliveryPossible;
        this.price = price;
        this.city = city;
        this.dateCreated = LocalDateTime.now();
        this.type = type;
        this.condition = condition;
        this.category = category;
        this.images = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.advertisedByUser = advertisedByUser;
    }

}
