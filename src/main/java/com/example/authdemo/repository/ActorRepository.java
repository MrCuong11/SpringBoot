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
}