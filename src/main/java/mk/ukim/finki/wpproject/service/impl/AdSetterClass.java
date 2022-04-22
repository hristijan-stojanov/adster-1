package mk.ukim.finki.wpproject.service.impl;


import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;

public class AdSetterClass {

    public static void adEditing(Ad ad, String title, String description, boolean isExchangePossible,
                                 boolean isDeliveryPossible, Double price, City city, AdType type,
                                 Condition condition, Category category){

        ad.setTitle(title);
        ad.setDescription(description);
        ad.setExchangePossible(isExchangePossible);
        ad.setDeliveryPossible(isDeliveryPossible);
        ad.setPrice(price);
        ad.setCity(city);
        ad.setType(type);
        ad.setCondition(condition);
        ad.setCategory(category);
    }
}
