package com.example.authdemo.repository;

import com.example.authdemo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    // Query 1: Tìm top 5 phim được thuê nhiều nhất
    // Logic: JOIN film -> inventory -> rental, đếm số lần thuê cho mỗi phim
    // ORDER BY rentalCount DESC và LIMIT 5 để lấy top 5
    @Query(value = "SELECT f.title AS title, COUNT(r.rental_id) AS rentalCount " +
            "FROM film f " +
            "JOIN inventory i ON f.film_id = i.film_id " +
            "JOIN rental r ON i.inventory_id = r.inventory_id " +
            "GROUP BY f.title " +
            "ORDER BY rentalCount DESC " +
            "LIMIT 5", nativeQuery = true)
    List<TopRentedFilmProjection> findTop5MostRentedFilms();

    interface TopRentedFilmProjection {
        String getTitle();
        Long getRentalCount();
    }

    // Query 2: Tìm phim PG-13 có độ dài hơn 120 phút
    // Logic: Lọc theo rating = 'PG-13' và length > 120
    @Query(value = "SELECT f.title AS title, f.rating AS rating, f.length AS length FROM film f WHERE f.rating = 'PG-13' AND f.length > 120", nativeQuery = true)
    List<FilmPG13LongProjection> findPG13FilmsLongerThan120();
    
    interface FilmPG13LongProjection {
        String getTitle();
        String getRating();
        Integer getLength();
    }

    // Query 3: Tìm phim được thuê bởi hơn 50 khách hàng khác nhau và mỗi khách chỉ thuê 1 lần
    // Logic: HAVING COUNT(DISTINCT r.customer_id) > 50 để đảm bảo có hơn 50 khách hàng
    // NOT IN subquery để loại bỏ phim có khách hàng thuê nhiều lần
    @Query(value = "SELECT f.title AS title " +
            "FROM film f " +
            "JOIN inventory i ON f.film_id = i.film_id " +
            "JOIN rental r ON i.inventory_id = r.inventory_id " +
            "GROUP BY f.film_id, f.title " +
            "HAVING COUNT(DISTINCT r.customer_id) > 50 " +
            "AND f.film_id NOT IN ( " +
            "    SELECT i2.film_id " +
            "    FROM inventory i2 " +
            "    JOIN rental r2 ON i2.inventory_id = r2.inventory_id " +
            "    GROUP BY i2.film_id, r2.customer_id " +
            "    HAVING COUNT(*) > 1 " +
            ")", nativeQuery = true)
    List<FilmRentedByManyOnceProjection> findFilmsRentedByMoreThan50CustomersOnce();

    interface FilmRentedByManyOnceProjection {
        String getTitle();
    }

    // Query 4: Tìm phim được thuê bởi tất cả khách hàng thích phim Action
    // Logic: Subquery tìm tất cả khách hàng đã thuê phim Action
    // HAVING COUNT(DISTINCT r.customer_id) = subquery để đảm bảo thuê bởi tất cả khách hàng Action
    @Query(value = "SELECT f.title AS title " +
            "FROM film f " +
            "JOIN inventory i ON f.film_id = i.film_id " +
            "JOIN rental r ON i.inventory_id = r.inventory_id " +
            "JOIN customer c ON r.customer_id = c.customer_id " +
            "WHERE r.customer_id IN ( " +
            "    SELECT DISTINCT r2.customer_id " +
            "    FROM rental r2 " +
            "    JOIN inventory i2 ON r2.inventory_id = i2.inventory_id " +
            "    JOIN film_category fc2 ON i2.film_id = fc2.film_id " +
            "    JOIN category cat ON fc2.category_id = cat.category_id " +
            "    WHERE cat.name = 'Action' " +
            ") " +
            "GROUP BY f.film_id, f.title " +
            "HAVING COUNT(DISTINCT r.customer_id) = ( " +
            "    SELECT COUNT(DISTINCT r3.customer_id) " +
            "    FROM rental r3 " +
            "    JOIN inventory i3 ON r3.inventory_id = i3.inventory_id " +
            "    JOIN film_category fc3 ON i3.film_id = fc3.film_id " +
            "    JOIN category cat2 ON fc3.category_id = cat2.category_id " +
            "    WHERE cat2.name = 'Action' " +
            ")", nativeQuery = true)
    List<FilmRentedByAllActionCustomersProjection> findFilmsRentedByAllActionCustomers();

    interface FilmRentedByAllActionCustomersProjection {
        String getTitle();
    }

    // Query 5: Tìm phim được khách hàng thuê lại nhiều lần trong cùng 1 ngày
    // Logic: GROUP BY theo film, customer và ngày thuê
    // HAVING COUNT(*) > 1 để lọc những trường hợp thuê nhiều lần trong ngày
    @Query(value = "SELECT f.title AS title, CONCAT(c.first_name, ' ', c.last_name) AS customerName, DATE(r.rental_date) AS rentalDay, COUNT(*) AS timesRented " +
            "FROM rental r " +
            "JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "JOIN film f ON i.film_id = f.film_id " +
            "JOIN customer c ON r.customer_id = c.customer_id " +
            "GROUP BY f.film_id, r.customer_id, DATE(r.rental_date), f.title, customerName " +
            "HAVING COUNT(*) > 1", nativeQuery = true)
    List<FilmRepeatRentalByCustomerProjection> findFilmsRepeatRentedByCustomerInOneDay();

    interface FilmRepeatRentalByCustomerProjection {
        String getTitle();
        String getCustomerName();
        java.sql.Date getRentalDay();
        Long getTimesRented();
    }

    // Query 6: Tìm phim được thuê hơn 100 lần và chưa bao giờ được thuê bởi khách hàng thích phim G
    // Logic: NOT IN subquery để loại bỏ phim được thuê bởi khách hàng thích phim G
    // HAVING COUNT(*) > 100 để đảm bảo phim được thuê nhiều lần
    @Query(value = "SELECT f.title AS title " +
            "FROM film f " +
            "JOIN inventory i ON f.film_id = i.film_id " +
            "JOIN rental r ON i.inventory_id = r.inventory_id " +
            "WHERE f.film_id NOT IN ( " +
            "    SELECT DISTINCT f2.film_id " +
            "    FROM film f2 " +
            "    JOIN inventory i2 ON f2.film_id = i2.film_id " +
            "    JOIN rental r2 ON i2.inventory_id = r2.inventory_id " +
            "    WHERE r2.customer_id IN ( " +
            "        SELECT DISTINCT r3.customer_id " +
            "        FROM rental r3 " +
            "        JOIN inventory i3 ON r3.inventory_id = i3.inventory_id " +
            "        JOIN film f3 ON i3.film_id = f3.film_id " +
            "        WHERE f3.rating = 'G' " +
            "    ) " +
            ") " +
            "GROUP BY f.film_id, f.title " +
            "HAVING COUNT(*) > 100", nativeQuery = true)
    List<FilmTitleProjection> findFilmsRentedMoreThan100TimesAndNeverByGCustomers();

    interface FilmTitleProjection {
        String getTitle();
    }

    // Query 7: Cập nhật rental_rate tăng 10% cho phim được thuê hơn 100 lần
    // Logic: Subquery tìm phim có COUNT(*) > 100, sau đó UPDATE với rental_rate * 1.10
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = rental_rate * 1.10 WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN inventory i ON f.film_id = i.film_id " +
            "    JOIN rental r ON i.inventory_id = r.inventory_id " +
            "    GROUP BY f.film_id " +
            "    HAVING COUNT(*) > 100 " +
            ")", nativeQuery = true)
    int updateRentalRateForPopularFilms();

    // Query 8: Cập nhật rental_duration tăng 5% cho phim được thuê hơn 5 lần
    // Logic: Subquery tìm phim có COUNT(*) > 5, sau đó UPDATE với ROUND(rental_duration * 1.05)
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_duration = ROUND(rental_duration * 1.05) WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN inventory i ON f.film_id = i.film_id " +
            "    JOIN rental r ON i.inventory_id = r.inventory_id " +
            "    GROUP BY f.film_id " +
            "    HAVING COUNT(*) > 5 " +
            ")", nativeQuery = true)
    int updateRentalDurationForPopularFilms();

    // Query 9: Cập nhật rental_rate tăng 20% cho phim Action cũ (trước 2005)
    // Logic: JOIN film -> film_category -> category, lọc theo name = 'Action' và release_year < 2005
    // UPDATE với rental_rate * 1.20
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = rental_rate * 1.20 WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN film_category fc ON f.film_id = fc.film_id " +
            "    JOIN category c ON fc.category_id = c.category_id " +
            "    WHERE c.name = 'Action' " +
            "      AND f.release_year < 2005 " +
            ")", nativeQuery = true)
    int updateRentalRateForOldActionFilms();

    // Query 10: Cập nhật rental_rate tăng 5% cho phim được thuê bởi hơn 10 khách hàng khác nhau, giới hạn tối đa 4.00
    // Logic: Subquery tìm phim có COUNT(DISTINCT r.customer_id) > 10
    // UPDATE với LEAST(rental_rate * 1.05, 4.00) để giới hạn giá tối đa
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = LEAST(rental_rate * 1.05, 4.00) WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN inventory i ON f.film_id = i.film_id " +
            "    JOIN rental r ON i.inventory_id = r.inventory_id " +
            "    GROUP BY f.film_id " +
            "    HAVING COUNT(DISTINCT r.customer_id) > 10 " +
            ")", nativeQuery = true)
    int updateRentalRateForPopularFilmsWithLimit();

    // Query 11: Cập nhật rental_rate = 3.50 cho phim PG-13 có độ dài hơn 120 phút
    // Logic: Lọc theo rating = 'PG-13' và length > 120, UPDATE với giá cố định 3.50
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = 3.50 WHERE rating = 'PG-13' AND length > 120", nativeQuery = true)
    int updateRentalRateForPG13LongFilms();

    // Query 12: Cập nhật rental_duration = length cho phim Sci-Fi năm 2010
    // Logic: JOIN film -> film_category -> category, lọc theo name = 'Sci-Fi' và release_year = 2010
    // UPDATE với rental_duration = length (độ dài phim)
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_duration = length WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN film_category fc ON f.film_id = fc.film_id " +
            "    JOIN category c ON fc.category_id = c.category_id " +
            "    WHERE c.name = 'Sci-Fi' " +
            "      AND f.release_year = 2010 " +
            ")", nativeQuery = true)
    int updateRentalDurationForSciFi2010();

    // Query 13: Cập nhật rental_rate giảm 15% cho phim Comedy từ năm 2007 trở đi
    // Logic: JOIN film -> film_category -> category, lọc theo name = 'Comedy' và release_year >= 2007
    // UPDATE với rental_rate * 0.85 (giảm 15%)
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = rental_rate * 0.85 WHERE film_id IN ( " +
            "    SELECT f.film_id " +
            "    FROM film f " +
            "    JOIN film_category fc ON f.film_id = fc.film_id " +
            "    JOIN category c ON fc.category_id = c.category_id " +
            "    WHERE c.name = 'Comedy' " +
            "      AND f.release_year >= 2007 " +
            ")", nativeQuery = true)
    int updateRentalRateForComedy2007Plus();

    // Query 14: Cập nhật rental_rate = 1.50 cho phim G có độ dài dưới 60 phút
    // Logic: Lọc theo rating = 'G' và length < 60, UPDATE với giá cố định 1.50
    @Modifying
    @Transactional
    @Query(value = "UPDATE film SET rental_rate = 1.50 WHERE rating = 'G' AND length < 60", nativeQuery = true)
    int updateRentalRateForGShortFilms();
} 