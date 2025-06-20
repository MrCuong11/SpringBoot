package com.example.authdemo.service;

import com.example.authdemo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryRepository.CategoryAvgRentalDurationProjection> getCategoryAvgRentalDuration() {
        return categoryRepository.findCategoryAvgRentalDuration();
    }
} 