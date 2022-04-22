package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Category;

import java.util.*;

public interface CategoryService {

    List<Category> findAll();

    Optional <Category> findById(Long id);

    Category create (String name);

    Category edit (Long id, String name);

    void deleteById(Long id);

}
