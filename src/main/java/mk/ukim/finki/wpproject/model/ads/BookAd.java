package mk.ukim.finki.wpproject.model.ads;


import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Genre;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "book_ads")
@OnDelete(action = OnDeleteAction.CASCADE)
public class BookAd extends Ad {

    private String author;

    private int yearMade;

    private int numPages;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    public BookAd() {
    }

    public BookAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                  Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                  String author, int yearMade, int numPages, Genre genre) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.author = author;
        this.yearMade = yearMade;
        this.numPages = numPages;
        this.genre = genre;
    }
}
