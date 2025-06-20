package com.example.authdemo.repository;

import com.example.authdemo.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    @Query(value = "SELECT a.first_name AS firstName, a.last_name AS lastName, COUNT(fa.film_id) AS filmCount " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "GROUP BY a.actor_id, a.first_name, a.last_name " +
            "HAVING COUNT(fa.film_id) > 20 " +
            "ORDER BY filmCount DESC", nativeQuery = true)
    List<ActorFilmCountProjection> findActorsWithMoreThan20Films();


    interface ActorFilmCountProjection {
        String getFirstName();
        String getLastName();
        Long getFilmCount();
    }



    @Query(value = "SELECT a.first_name AS firstName, a.last_name AS lastName " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film_category fc ON fa.film_id = fc.film_id " +
            "GROUP BY a.actor_id, a.first_name, a.last_name " +
            "HAVING COUNT(DISTINCT fc.category_id) = (SELECT COUNT(*) FROM category)", nativeQuery = true)
    List<ActorAllCategoryProjection> findActorsInAllCategories();
    interface ActorAllCategoryProjection {
        String getFirstName();
        String getLastName();
    }

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

    interface ActorRevenueProjection {
        String getFirstName();
        String getLastName();
        java.math.BigDecimal getTotalRevenue();
    }

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

    interface ActorRNotGProjection {
        String getFirstName();
        String getLastName();
    }

    @Query(value = "SELECT a.actor_id AS actorId, CONCAT(a.first_name, ' ', a.last_name) AS actorName, c.category_id AS categoryId, c.name AS categoryName, AVG(f.rental_duration) AS avgRentalDuration " +
            "FROM actor a " +
            "JOIN film_actor fa ON a.actor_id = fa.actor_id " +
            "JOIN film f ON fa.film_id = f.film_id " +
            "JOIN film_category fc ON f.film_id = fc.film_id " +
            "JOIN category c ON fc.category_id = c.category_id " +
            "GROUP BY a.actor_id, c.category_id, actorName, categoryName", nativeQuery = true)
    List<ActorCategoryAvgRentalDurationProjection> findActorCategoryAvgRentalDuration();

    interface ActorCategoryAvgRentalDurationProjection {
        Integer getActorId();
        String getActorName();
        Short getCategoryId();
        String getCategoryName();
        Double getAvgRentalDuration();
    }

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

    interface ActorRLongNotGProjection {
        String getActorName();
    }

    @Query(value = "SELECT a1.actor_id AS actorId, CONCAT(a1.first_name, ' ', a1.last_name) AS actorName, CONCAT(a2.first_name, ' ', a2.last_name) AS coActorName, COUNT(DISTINCT fa1.film_id) AS filmsTogether " +
            "FROM actor a1 " +
            "JOIN film_actor fa1 ON a1.actor_id = fa1.actor_id " +
            "JOIN film_actor fa2 ON fa1.film_id = fa2.film_id " +
            "JOIN actor a2 ON fa2.actor_id = a2.actor_id " +
            "WHERE a1.actor_id <> a2.actor_id " +
            "GROUP BY a1.actor_id, actorName, coActorName " +
            "HAVING COUNT(DISTINCT fa1.film_id) >= (SELECT COUNT(*) - 1 FROM actor)", nativeQuery = true)
    List<ActorWithAllCoActorsProjection> findActorsWithAllCoActors();

    interface ActorWithAllCoActorsProjection {
        Integer getActorId();
        String getActorName();
        String getCoActorName();
        Long getFilmsTogether();
    }
}