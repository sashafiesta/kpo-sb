package com.sashafiesta.finances.facade;

import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.factory.DomainFactory;
import com.sashafiesta.finances.repository.CategoryRepository;
import com.sashafiesta.finances.repository.Repository;
import com.sashafiesta.finances.repository.impl.SimpleCategoryRepository;

import java.util.List;
import java.util.Optional;

public class CategoryFacade {
    private CategoryRepository repository;
    private DomainFactory factory;
    private long idGen;

    public CategoryFacade(CategoryRepository repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
        this.idGen = 1;
    }

    public Category createCategory(String name, CategoryType type) throws IllegalArgumentException {
        Category category = factory.createCategory(idGen++, name, type);
        repository.save(category);
        return category;
    }

    public Optional<Category> getCategory(long id) {
        return repository.find(id);
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public List<Category> getCategories(CategoryType type) {
        return getCategories().stream().filter(category -> category.getType() == type).toList();
    }

    public void updateCategory(long id, String name, CategoryType type) throws IllegalArgumentException {
        Optional<Category> result = repository.find(id);
        Category category = result.orElseThrow(() -> new IllegalArgumentException("category not found"));
        category.setName(name);
        category.setType(type);
        repository.update(category);
    }

    public void deleteCategory(long id) {
        repository.delete(id);
    }
}
