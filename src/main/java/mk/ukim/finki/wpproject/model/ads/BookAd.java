package mk.ukim.finki.wpproject.model.ads;


import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import javax.persistence.*;

@Entity
@Data
@Table(name = "book_ads")
public class BookAd extends Ad {

    private String author;

    private int yearMade;

    private int numPages;

//    private String genre;

    public BookAd() {
    }

    public BookAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                  Double price, String city, AdType type, Condition condition, Category category, User advertisedByUser,
                  String author, int yearMade, int numPages) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.author = author;
        this.yearMade = yearMade;
        this.numPages = numPages;
    }
}