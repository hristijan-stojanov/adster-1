package mk.ukim.finki.wpproject.model.ads;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Registration;
import mk.ukim.finki.wpproject.model.enums.TypeMemory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "itEquipment_ads")
public class ITEquipmentAd extends Ad {

    private String brand;

    private String processor;

    @Enumerated(EnumType.STRING)
    private TypeMemory typeMemory;

    private int memorySize;

    private int ramMemorySize;

    public ITEquipmentAd() {
    }

    public ITEquipmentAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                         Double price, String city, AdType type, Condition condition, Category category, User advertisedByUser,
                         String brand, String processor, TypeMemory typeMemory, int memorySize, int ramMemorySize) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.brand = brand;
        this.processor = processor;
        this.typeMemory = typeMemory;
        this.memorySize = memorySize;
        this.ramMemorySize = ramMemorySize;
    }
}
