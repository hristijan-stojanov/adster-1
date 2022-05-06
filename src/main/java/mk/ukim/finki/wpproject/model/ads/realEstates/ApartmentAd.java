package mk.ukim.finki.wpproject.model.ads.realEstates;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Heating;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "apartment_ads")
@OnDelete(action = OnDeleteAction.CASCADE)
public class ApartmentAd extends RealEstateAd {
    private int yearMade;

    private int numRooms;

    private int numFloors;

    private int floor;

    private boolean hasBasement;

    private boolean hasElevator;

    private boolean hasParkingSpot;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    public ApartmentAd() {
    }

    public ApartmentAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                       Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                       int quadrature, int yearMade, int numRooms, int numFloors, int floor,
                       boolean hasBasement, boolean hasElevator, boolean hasParkingSpot, Heating heating) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser, quadrature);
        this.yearMade = yearMade;
        this.numRooms = numRooms;
        this.numFloors = numFloors;
        this.floor = floor;
        this.hasBasement = hasBasement;
        this.hasElevator = hasElevator;
        this.hasParkingSpot = hasParkingSpot;
        this.heating = heating;
    }
}
