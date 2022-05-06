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
@Table(name = "house_ads")
@OnDelete(action = OnDeleteAction.CASCADE)
public class HouseAd extends RealEstateAd {
    private int yearMade;

    private int yardArea;//m^2

    private int numRooms;

    private int numFloors;

    private boolean hasBasement;

    @Enumerated(EnumType.STRING)
    private Heating heating;

    public HouseAd() {
    }

    public HouseAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                   Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                   int quadrature, int yearMade, int yardArea, int numRooms, int numFloors, boolean hasBasement, Heating heating) {
        super(title, description, isExchangePossible, isDeliveryPossible,
                price, city, type, condition, category, advertisedByUser, quadrature);
        this.yearMade = yearMade;
        this.yardArea = yardArea;
        this.numRooms = numRooms;
        this.numFloors = numFloors;
        this.hasBasement = hasBasement;
        this.heating = heating;
    }
}
