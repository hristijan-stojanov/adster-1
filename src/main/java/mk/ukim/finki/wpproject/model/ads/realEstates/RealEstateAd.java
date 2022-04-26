package mk.ukim.finki.wpproject.model.ads.realEstates;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "real_estate_ads")
public class RealEstateAd extends Ad {

    private int quadrature;//m^2

    public RealEstateAd() {
    }

    public RealEstateAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                        Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                        int quadrature) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.quadrature = quadrature;
    }
}

// dali nesto ke smene ako se izostava IsDevliveryPossible
// ili da se naprave default false