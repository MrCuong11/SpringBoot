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
} 