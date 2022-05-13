package mk.ukim.finki.wpproject.model.ads;

import lombok.Data;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "itEquipment_ads")
@OnDelete(action = OnDeleteAction.CASCADE)
public class ITEquipmentAd extends Ad {
    @Enumerated(EnumType.STRING)
    private ITBrand brand;

    @Enumerated(EnumType.STRING)
    private ProcessorBrand processor;

    private String processorModel;

    @Enumerated(EnumType.STRING)
    private TypeMemory typeMemory;

    private int memorySize;

    private int ramMemorySize;

    public ITEquipmentAd() {
    }

    public ITEquipmentAd(String title, String description, boolean isExchangePossible, boolean isDeliveryPossible,
                         Double price, City city, AdType type, Condition condition, Category category, User advertisedByUser,
                         ITBrand brand, ProcessorBrand processor, String processorModel, TypeMemory typeMemory, int memorySize, int ramMemorySize) {
        super(title, description, isExchangePossible, isDeliveryPossible, price, city, type, condition, category, advertisedByUser);
        this.brand = brand;
        this.processor = processor;
        this.processorModel = processorModel;
        this.typeMemory = typeMemory;
        this.memorySize = memorySize;
        this.ramMemorySize = ramMemorySize;
    }
}
