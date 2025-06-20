package com.example.authdemo.repository;

import com.example.authdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Short> {
    @Query(value = "SELECT c.name AS categoryName, AVG(f.rental_duration) AS avgRentalDuration " +
            "FROM category c " +
            "JOIN film_category fc ON c.category_id = fc.category_id " +
            "JOIN film f ON fc.film_id = f.film_id " +
            "GROUP BY c.name " +
            "ORDER BY avgRentalDuration DESC", nativeQuery = true)
    List<CategoryAvgRentalDurationProjection> findCategoryAvgRentalDuration();

    interface CategoryAvgRentalDurationProjection {
        String getCategoryName();
        Double getAvgRentalDuration();
    }
} 