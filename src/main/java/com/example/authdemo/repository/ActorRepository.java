package com.example.authdemo.repository;

import com.example.authdemo.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    
    /**
     * Tìm diễn viên đã xuất hiện trong hơn 20 bộ phim.
     * 
     * 
     * 1. JOIN bảng actor với film_actor để lấy thông tin diễn viên và phim
     * 2. GROUP BY theo actor_id, first_name, last_name để nhóm theo từng diễn viên
     * 3. COUNT(fa.film_id) để đếm số phim của mỗi diễn viên
     * 4. HAVING COUNT(fa.film_id) > 20 để lọc chỉ những diễn viên có > 20 phim
     * 5. ORDER BY filmCount DESC để sắp xếp theo số phim giảm dần
     */
    @Query(value = "SELECT a.first_name AS firstName, a.last_name AS lastName, COUNT(fa.film_id) AS filmCount " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "GROUP BY a.actor_id, a.first_name, a.last_name " +
            "HAVING COUNT(fa.film_id) > 20 " +
            "ORDER BY filmCount DESC", nativeQuery = true)
    List<ActorFilmCountProjection> findActorsWithMoreThan20Films();

    /**
     * Interface projection cho kết quả đếm số phim của diễn viên
     */
    interface ActorFilmCountProjection {
        String getFirstName();
        String getLastName();
        Long getFilmCount();
    }

    /**
     * Tìm diễn viên đã xuất hiện trong tất cả các thể loại phim có sẵn.
     * 
     * 
     * 1. JOIN actor → film_actor → film_category để lấy thông tin diễn viên và thể loại
     * 2. GROUP BY theo actor để nhóm theo từng diễn viên
     * 3. COUNT(DISTINCT fc.category_id) để đếm số thể loại duy nhất mỗi diễn viên đã tham gia
     * 4. HAVING COUNT(DISTINCT fc.category_id) = (SELECT COUNT(*) FROM category) để so sánh với tổng số thể loại
     * 5. Subquery (SELECT COUNT(*) FROM category) đếm tổng số thể loại trong hệ thống
     */
    @Query(value = "SELECT a.first_name AS firstName, a.last_name AS lastName " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film_category fc ON fa.film_id = fc.film_id " +
            "GROUP BY a.actor_id, a.first_name, a.last_name " +
            "HAVING COUNT(DISTINCT fc.category_id) = (SELECT COUNT(*) FROM category)", nativeQuery = true)
    List<ActorAllCategoryProjection> findActorsInAllCategories();
    
    /**
     * Interface projection cho kết quả diễn viên trong tất cả thể loại
     */
    interface ActorAllCategoryProjection {
        String getFirstName();
        String getLastName();
    }

    /**
     * Tính tổng doanh thu được tạo ra bởi mỗi diễn viên thông qua việc cho thuê phim.
     * 
     * 
     * 1. JOIN chuỗi: actor → film_actor → film → inventory → rental → payment
     * 2. GROUP BY theo actor_id, first_name, last_name để nhóm theo từng diễn viên
     * 3. SUM(p.amount) để tính tổng số tiền thanh toán cho mỗi diễn viên
     * 4. ORDER BY totalRevenue DESC để sắp xếp theo doanh thu giảm dần
     * 5. Chuỗi JOIN này theo dõi từ diễn viên → phim → kho → cho thuê → thanh toán
     */
    @Query(value = "SELECT a.first_name AS firstName, a.last_name AS lastName, SUM(p.amount) AS totalRevenue " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film f ON fa.film_id = f.film_id " +
            "JOIN inventory i ON f.film_id = i.film_id " +
            "JOIN rental r ON i.inventory_id = r.inventory_id " +
            "JOIN payment p ON r.rental_id = p.rental_id " +
            "GROUP BY a.actor_id, a.first_name, a.last_name " +
            "ORDER BY totalRevenue DESC", nativeQuery = true)
    List<ActorRevenueProjection> findActorsTotalRevenue();

    /**
     * Interface projection cho kết quả doanh thu của diễn viên
     */
    interface ActorRevenueProjection {
        String getFirstName();
        String getLastName();
        java.math.BigDecimal getTotalRevenue();
    }

    /**
     * Tìm diễn viên đã xuất hiện trong phim xếp hạng R nhưng không xuất hiện trong phim xếp hạng G.
     * 
     * 
     * 1. JOIN actor → film_actor → film để lấy diễn viên trong phim R-rated
     * 2. WHERE f.rating = 'R' để lọc chỉ phim R-rated
     * 3. NOT IN subquery để loại trừ diễn viên xuất hiện trong phim G-rated
     * 4. Subquery tìm tất cả diễn viên xuất hiện trong phim G-rated
     * 5. Kết hợp điều kiện để tìm diễn viên chỉ xuất hiện trong R nhưng không có trong G
     */
    @Query(value = "SELECT DISTINCT a.first_name AS firstName, a.last_name AS lastName " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film f ON fa.film_id = f.film_id " +
            "WHERE f.rating = 'R' " +
            "AND a.actor_id NOT IN ( " +
            "    SELECT a2.actor_id " +
            "    FROM actor a2 " +
            "    JOIN film_actor fa2 ON a2.actor_id = fa2.actor_id " +
            "    JOIN film f2 ON fa2.film_id = f2.film_id " +
            "    WHERE f2.rating = 'G' " +
            ")", nativeQuery = true)
    List<ActorRNotGProjection> findActorsInRNotInG();

    /**
     * Interface projection cho kết quả diễn viên trong R nhưng không có trong G
     */
    interface ActorRNotGProjection {
        String getFirstName();
        String getLastName();
    }

    /**
     * Tính thời gian cho thuê trung bình cho mỗi diễn viên theo thể loại phim.
     * 
     * 
     * 1. JOIN chuỗi: actor → film_actor → film → film_category → category
     * 2. GROUP BY theo actor_id, category_id để nhóm theo diễn viên và thể loại
     * 3. AVG(f.rental_duration) để tính thời gian cho thuê trung bình
     * 4. CONCAT để kết hợp first_name và last_name thành tên đầy đủ
     * 5. Kết quả cho thấy mỗi diễn viên hoạt động tốt trong thể loại nào
     */
    @Query(value = "SELECT a.actor_id AS actorId, CONCAT(a.first_name, ' ', a.last_name) AS actorName, c.category_id AS categoryId, c.name AS categoryName, AVG(f.rental_duration) AS avgRentalDuration " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film f ON fa.film_id = f.film_id " +
            "JOIN film_category fc ON f.film_id = fc.film_id " +
            "JOIN category c ON fc.category_id = c.category_id " +
            "GROUP BY a.actor_id, c.category_id, actorName, categoryName", nativeQuery = true)
    List<ActorCategoryAvgRentalDurationProjection> findActorCategoryAvgRentalDuration();

    /**
     * Interface projection cho kết quả thời gian cho thuê trung bình theo thể loại
     */
    interface ActorCategoryAvgRentalDurationProjection {
        Integer getActorId();
        String getActorName();
        Short getCategoryId();
        String getCategoryName();
        Double getAvgRentalDuration();
    }

    /**
     * Tìm diễn viên đã xuất hiện trong phim R-rated dài (>120 phút) nhưng không xuất hiện trong phim G-rated.
     * 
     * 
     * 1. JOIN actor → film_actor → film để lấy diễn viên trong phim R-rated dài
     * 2. WHERE f.rating = 'R' AND f.length > 120 để lọc phim R-rated dài
     * 3. NOT IN subquery để loại trừ diễn viên xuất hiện trong phim G-rated
     * 4. Subquery tìm diễn viên xuất hiện trong phim G-rated
     * 5. Kết hợp điều kiện để tìm diễn viên chỉ xuất hiện trong R dài nhưng không có trong G
     */
    @Query(value = "SELECT DISTINCT CONCAT(a.first_name, ' ', a.last_name) AS actorName " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film f ON fa.film_id = f.film_id " +
            "WHERE f.rating = 'R' AND f.length > 120 " +
            "AND a.actor_id NOT IN ( " +
            "    SELECT fa2.actor_id " +
            "    FROM film_actor fa2 " +
            "    JOIN film f2 ON fa2.film_id = f2.film_id " +
            "    WHERE f2.rating = 'G' " +
            ")", nativeQuery = true)
    List<ActorRLongNotGProjection> findActorsInRLongNotInG();

    /**
     * Interface projection cho kết quả diễn viên trong R dài nhưng không có trong G
     */
    interface ActorRLongNotGProjection {
        String getActorName();
    }

    /**
     * Tìm diễn viên đã làm việc với tất cả đồng diễn viên của họ trong nhiều bộ phim.
     * 
     * 
     * 1. Self-join trên bảng film_actor để tìm mối quan hệ đồng diễn viên
     * 2. JOIN a1 (diễn viên chính) → fa1 → fa2 → a2 (đồng diễn viên)
     * 3. WHERE a1.actor_id <> a2.actor_id để loại trừ tự join
     * 4. GROUP BY theo diễn viên chính và đồng diễn viên
     * 5. COUNT(DISTINCT fa1.film_id) để đếm số phim làm chung
     * 6. HAVING để lọc diễn viên có đủ số phim làm chung với tất cả đồng diễn viên
     */
    @Query(value = "SELECT a1.actor_id AS actorId, CONCAT(a1.first_name, ' ', a1.last_name) AS actorName, CONCAT(a2.first_name, ' ', a2.last_name) AS coActorName, COUNT(DISTINCT fa1.film_id) AS filmsTogether " +
            "FROM actor a1 " +
            "JOIN film_actor fa1 ON a1.actor_id = fa1.actor_id " +
            "JOIN film_actor fa2 ON fa1.film_id = fa2.film_id " +
            "JOIN actor a2 ON fa2.actor_id = a2.actor_id " +
            "WHERE a1.actor_id <> a2.actor_id " +
            "GROUP BY a1.actor_id, actorName, coActorName " +
            "HAVING COUNT(DISTINCT fa1.film_id) >= (SELECT COUNT(*) - 1 FROM actor)", nativeQuery = true)
    List<ActorWithAllCoActorsProjection> findActorsWithAllCoActors();

    /**
     * Interface projection cho kết quả diễn viên với đồng diễn viên
     */
    interface ActorWithAllCoActorsProjection {
        Integer getActorId();
        String getActorName();
        String getCoActorName();
        Long getFilmsTogether();
    }

    /**
     * Tìm diễn viên đã xuất hiện trong cả phim PG-13 dài (>120 phút) và phim R-rated ngắn (<90 phút).
     * 
     * 
     * 1. Sử dụng EXISTS subquery để kiểm tra điều kiện đầu tiên
     * 2. EXISTS 1: Tìm diễn viên trong phim PG-13 dài (>120 phút)
     * 3. EXISTS 2: Tìm diễn viên trong phim R-rated ngắn (<90 phút)
     * 4. Kết hợp cả hai điều kiện EXISTS để tìm diễn viên thỏa mãn cả hai
     * 5. Subquery sử dụng JOIN film_actor → film để kiểm tra điều kiện
     */
    @Query(value = "SELECT CONCAT(a.first_name, ' ', a.last_name) AS actorName " +
            "FROM actor a " +
            "WHERE EXISTS ( " +
            "    SELECT 1 " +
            "    FROM film_actor fa1 " +
            "    JOIN film f1 ON fa1.film_id = f1.film_id " +
            "    WHERE fa1.actor_id = a.actor_id " +
            "      AND f1.rating = 'PG-13' " +
            "      AND f1.length > 120 " +
            ") " +
            "AND EXISTS ( " +
            "    SELECT 1 " +
            "    FROM film_actor fa2 " +
            "    JOIN film f2 ON fa2.film_id = f2.film_id " +
            "    WHERE fa2.actor_id = a.actor_id " +
            "      AND f2.rating = 'R' " +
            "      AND f2.length < 90 " +
            ")", nativeQuery = true)
    List<ActorNameProjection> findActorsInPg13LongAndRShortFilms();

    /**
     * Interface projection cho kết quả tên diễn viên
     */
    interface ActorNameProjection {
        String getActorName();
    }
}