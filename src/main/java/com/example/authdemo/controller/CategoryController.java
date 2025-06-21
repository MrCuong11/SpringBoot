package com.example.authdemo.controller;

import com.example.authdemo.dto.CategoryAvgRentalDurationDto;
import com.example.authdemo.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/avg-rental-duration")
    @SecurityRequirement(name = "bearerAuth")
    /**
     * Lấy thời gian thuê trung bình của phim theo từng thể loại
     * Output: List<CategoryAvgRentalDurationDto> - Danh sách thể loại với thời gian thuê trung bình
     */
    public List<CategoryAvgRentalDurationDto> getCategoryAvgRentalDuration() {
        return categoryService.getCategoryAvgRentalDuration().stream()
                .map(c -> new CategoryAvgRentalDurationDto(c.getCategoryName(), c.getAvgRentalDuration()))
                .collect(Collectors.toList());
    }
} 