package com.example.authdemo.repository;

import com.example.authdemo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
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

    @Query(value = "SELECT f.title AS title, f.rating AS rating, f.length AS length FROM film f WHERE f.rating = 'PG-13' AND f.length > 120", nativeQuery = true)
    List<FilmPG13LongProjection> findPG13FilmsLongerThan120();
    interface FilmPG13LongProjection {
        String getTitle();
        String getRating();
        Integer getLength();
    }

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
} 