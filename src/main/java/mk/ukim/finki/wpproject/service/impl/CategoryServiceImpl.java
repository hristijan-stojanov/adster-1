package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Category;
import mk.ukim.finki.wpproject.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wpproject.repository.CategoryRepository;
import mk.ukim.finki.wpproject.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return Optional.of(category);
    }

    @Override
    public Category findCategoryByName(String name) {
        Category category = this.categoryRepository.findByName(name);
        return category;
    }

    @Override
    public Category save(String name, Long parentCategoryId) {
        Category category;

        if (parentCategoryId != null && !parentCategoryId.toString().isEmpty()) {
            Category parentCategory = this.categoryRepository.findById(parentCategoryId).orElseThrow(() -> new CategoryNotFoundException(parentCategoryId));
            category = new Category(name, parentCategory);
        }
        else
            category = new Category(name);

        return this.categoryRepository.save(category);
    }

    @Override
    public Category edit(Long id, String name, Long parentCategoryId) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        if (parentCategoryId != null && !parentCategoryId.toString().isEmpty()) {
            Category parentCategory = this.categoryRepository.findById(parentCategoryId).orElseThrow(() -> new CategoryNotFoundException(parentCategoryId));
            category.setParentCategory(parentCategory);
        }

        category.setName(name);

        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        this.categoryRepository.delete(category);
    }

}
