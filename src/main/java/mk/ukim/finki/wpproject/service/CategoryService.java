package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Category;

import java.util.*;

public interface CategoryService {

    List<Category> findAll();

    Optional <Category> findById(Long id);

    Category findCategoryByName(String name);

    Category save (String name, Long parentCategoryId);

    Category edit (Long id, String name, Long parentCategoryId);

    void deleteById(Long id);

}
