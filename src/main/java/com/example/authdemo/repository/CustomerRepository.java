package com.example.authdemo.repository;

import com.example.authdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT c.first_name AS firstName, c.last_name AS lastName, a.address AS address " +
            "FROM customer c " +
            "JOIN address a ON c.address_id = a.address_id " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "WHERE r.rental_date >= '2022-01-01' AND r.rental_date < '2022-02-01' " +
            "GROUP BY c.customer_id, c.first_name, c.last_name, a.address", nativeQuery = true)
    List<CustomerAddressProjection> findCustomerNamesAndAddressesRentedInJan2022();


    interface CustomerAddressProjection {
        String getFirstName();
        String getLastName();
        String getAddress();
    }

    

    @Query(value = "SELECT c.first_name AS firstName, c.last_name AS lastName, SUM(p.amount) AS totalRevenue " +
            "FROM customer c " +
            "JOIN payment p ON c.customer_id = p.customer_id " +
            "GROUP BY c.customer_id, c.first_name, c.last_name " +
            "ORDER BY totalRevenue DESC " +
            "LIMIT 10", nativeQuery = true)
    List<TopCustomerRevenueProjection> findTop10CustomersByRevenue();
    interface TopCustomerRevenueProjection {
        String getFirstName();
        String getLastName();
        java.math.BigDecimal getTotalRevenue();
    }

    @Query(value = "SELECT c.first_name AS firstName, c.last_name AS lastName, c.email AS email " +
            "FROM customer c " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "JOIN film_category fc ON i.film_id = fc.film_id " +
            "GROUP BY c.customer_id, c.first_name, c.last_name, c.email " +
            "HAVING COUNT(DISTINCT fc.category_id) = (SELECT COUNT(*) FROM category)", nativeQuery = true)
    List<CustomerAllCategoryRentalProjection> findCustomersRentedAllCategories();

    interface CustomerAllCategoryRentalProjection {
        String getFirstName();
        String getLastName();
        String getEmail();
    }

    @Query(value = "SELECT c.first_name AS firstName, c.last_name AS lastName, f.title AS filmTitle, COUNT(*) AS rentalCount " +
            "FROM customer c " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "JOIN film f ON i.film_id = f.film_id " +
            "GROUP BY c.customer_id, f.film_id, f.title, c.first_name, c.last_name " +
            "HAVING COUNT(*) > 1", nativeQuery = true)
    List<CustomerRepeatRentalProjection> findCustomersRentedSameFilmMoreThanOnce();

    interface CustomerRepeatRentalProjection {
        String getFirstName();
        String getLastName();
        String getFilmTitle();
        Long getRentalCount();
    }

    @Query(value = "SELECT DISTINCT c.customer_id AS customerId, c.first_name AS firstName, c.last_name AS lastName, cat.name AS categoryName " +
            "FROM customer c " +
            "JOIN rental r1 ON c.customer_id = r1.customer_id " +
            "JOIN inventory i1 ON r1.inventory_id = i1.inventory_id " +
            "JOIN film_category fc1 ON i1.film_id = fc1.film_id " +
            "JOIN category cat ON fc1.category_id = cat.category_id " +
            "WHERE NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM rental r2 " +
            "    JOIN inventory i2 ON r2.inventory_id = i2.inventory_id " +
            "    JOIN film_category fc2 ON i2.film_id = fc2.film_id " +
            "    WHERE r2.customer_id = c.customer_id " +
            "      AND fc2.category_id = fc1.category_id " +
            "      AND r2.rental_date < r1.rental_date " +
            ")", nativeQuery = true)
    List<CustomerFirstTimeCategoryRentalProjection> findCustomersFirstTimeCategoryRental();

    interface CustomerFirstTimeCategoryRentalProjection {
        Integer getCustomerId();
        String getFirstName();
        String getLastName();
        String getCategoryName();
    }

    @Query(value = "SELECT c.customer_id AS customerId, CONCAT(c.first_name, ' ', c.last_name) AS customerName, DATE(r.rental_date) AS rentalDay, COUNT(*) AS filmsRented, SUM(p.amount) AS totalFee " +
            "FROM customer c " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "JOIN payment p ON r.rental_id = p.rental_id " +
            "GROUP BY c.customer_id, customerName, rentalDay " +
            "HAVING COUNT(*) > 10", nativeQuery = true)
    List<CustomerLargeTransactionProjection> findCustomersWithLargeTransactions();

    interface CustomerLargeTransactionProjection {
        Integer getCustomerId();
        String getCustomerName();
        java.sql.Date getRentalDay();
        Long getFilmsRented();
        java.math.BigDecimal getTotalFee();
    }
} 