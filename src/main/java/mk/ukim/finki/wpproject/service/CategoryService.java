package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Category;

import java.util.*;

public interface CategoryService {

    List<Category> findAll();

    Category create (String name, List<String> subCategories);

    Category edit (Long id, String name, List<String> subCategories);

    void deleteById(Long id);



}
