package com.example.authdemo.repository;

import com.example.authdemo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Short> {
    @Query(value = "SELECT s.store_id AS storeId, SUM(p.amount) AS totalRevenue " +
            "FROM payment p " +
            "JOIN customer c ON p.customer_id = c.customer_id " +
            "JOIN store s ON c.store_id = s.store_id " +
            "WHERE p.payment_date >= '2021-01-01' AND p.payment_date < '2022-01-01' " +
            "GROUP BY s.store_id", nativeQuery = true)
    List<StoreRevenueProjection> findStoreRevenue2021();

    interface StoreRevenueProjection {
        Short getStoreId();
        BigDecimal getTotalRevenue();
    }
} 