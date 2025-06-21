package com.example.authdemo.repository;

import com.example.authdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Short> {
    
    /**
     * Tính thời gian cho thuê trung bình cho mỗi thể loại phim.
     * 
     * 1. JOIN category → film_category → film để lấy thông tin thể loại và phim
     * 2. GROUP BY theo c.name để nhóm theo từng thể loại
     * 3. AVG(f.rental_duration) để tính thời gian cho thuê trung bình của mỗi thể loại
     * 4. ORDER BY avgRentalDuration DESC để sắp xếp theo thời gian cho thuê giảm dần
     * 5. Kết quả cho thấy thể loại nào có thời gian cho thuê trung bình cao nhất
     */
    @Query(value = "SELECT c.name AS categoryName, AVG(f.rental_duration) AS avgRentalDuration " +
            "FROM category c " +
            "JOIN film_category fc ON c.category_id = fc.category_id " +
            "JOIN film f ON fc.film_id = f.film_id " +
            "GROUP BY c.name " +
            "ORDER BY avgRentalDuration DESC", nativeQuery = true)
    List<CategoryAvgRentalDurationProjection> findCategoryAvgRentalDuration();

    /**
     * Interface projection cho kết quả thời gian cho thuê trung bình theo thể loại
     */
    interface CategoryAvgRentalDurationProjection {
        String getCategoryName();
        Double getAvgRentalDuration();
    }
} 