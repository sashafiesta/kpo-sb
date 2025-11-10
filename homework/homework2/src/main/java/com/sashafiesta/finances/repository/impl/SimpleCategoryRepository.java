package com.sashafiesta.finances.repository.impl;

import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.repository.CategoryRepository;

public class SimpleCategoryRepository extends SimpleRepository<Category> implements CategoryRepository {
    public SimpleCategoryRepository() {
        super(Category::getId);
    }
}