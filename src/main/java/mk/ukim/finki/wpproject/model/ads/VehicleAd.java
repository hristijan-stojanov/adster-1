package mk.ukim.finki.wpproject.model.ads;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "vehicle_ads")
@OnDelete(action = OnDeleteAction.CASCADE)
public class VehicleAd extends Ad {
    @Enumerated(EnumType.STRING)
    private CarBrand brand;

    private int yearMade;

    private int enginePower;//KW

    private double milesTraveled;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    @Enumerated(EnumType.STRING)
    private Registration registration;

    public VehicleAd() {
    }

    public VehicleAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                     Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                     CarBrand brand, int yearMade, Color color, double milesTraveled, Fuel fuel, int enginePower,
                     Gearbox gearbox, Registration registration) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.brand = brand;
        this.yearMade = yearMade;
        this.color = color;
        this.milesTraveled = milesTraveled;
        this.fuel = fuel;
        this.enginePower = enginePower;
        this.gearbox = gearbox;
        this.registration = registration;
    }
}
