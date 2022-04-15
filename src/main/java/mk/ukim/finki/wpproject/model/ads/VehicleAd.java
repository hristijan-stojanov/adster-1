package mk.ukim.finki.wpproject.model.ads;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "vehicle_ads")
public class VehicleAd extends Ad {

    private String brand;

    private int yearMade;

    private String color;

    private double milesTraveled;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    private int enginePowerKW;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    @Enumerated(EnumType.STRING)
    private Registration registration;

    public VehicleAd() {
    }

    public VehicleAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                     Double price, String city, AdType type, Condition condition, Category category, User advertisedByUser,
                     String brand, int yearMade, String color, double milesTraveled, Fuel fuel, int enginePowerKW,
                     Gearbox gearbox, Registration registration) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.brand = brand;
        this.yearMade = yearMade;
        this.color = color;
        this.milesTraveled = milesTraveled;
        this.fuel = fuel;
        this.enginePowerKW = enginePowerKW;
        this.gearbox = gearbox;
        this.registration = registration;
    }
}
