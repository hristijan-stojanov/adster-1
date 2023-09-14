package mk.ukim.finki.wpproject.model.DTO;

import mk.ukim.finki.wpproject.model.*;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdDto {

    public Long id;
    public String title;
    public String description;
    public boolean isExchangePossible;
    public boolean isDeliveryPossible;
    public Double price;
    public LocalDateTime dateCreated;
    public AdType type;
    public Condition condition;
    public List<adImage> images;
    public String category;
    public String city;
    public double lat;
    public double lon;
    public List<Comment> comments;
    public Long advertisedByUser;



    public AdDto(Long id, String title, String description, boolean isExchangePossible, boolean isDeliveryPossible, Double price, LocalDateTime dateCreated, AdType type, Condition condition, List<adImage> images, String category, String city, double lat, double lon, List<Comment> comments, Long advertisedByUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isExchangePossible = isExchangePossible;
        this.isDeliveryPossible = isDeliveryPossible;
        this.price = price;
        this.dateCreated = dateCreated;
        this.type = type;
        this.condition = condition;
        this.images = images;
        this.category = category;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.comments = comments;
        this.advertisedByUser = advertisedByUser;
    }

}
