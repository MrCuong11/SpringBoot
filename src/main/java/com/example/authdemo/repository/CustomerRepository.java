package com.example.authdemo.repository;

import com.example.authdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Query 1: Tìm tên và địa chỉ của khách hàng đã thuê phim trong tháng 1/2022
    // Logic: JOIN customer với address và rental, lọc theo khoảng thời gian tháng 1/2022
    // GROUP BY để loại bỏ duplicate khi một khách hàng thuê nhiều phim
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

    // Query 2: Tìm top 10 khách hàng có doanh thu cao nhất
    // Logic: JOIN customer với payment, tính tổng amount cho mỗi khách hàng
    // ORDER BY totalRevenue DESC và LIMIT 10 để lấy top 10
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

    // Query 3: Tìm khách hàng đã thuê phim từ tất cả các category
    // Logic: JOIN customer -> rental -> inventory -> film_category -> category
    // HAVING COUNT(DISTINCT fc.category_id) = (SELECT COUNT(*) FROM category) để đảm bảo thuê đủ tất cả category
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

    // Query 4: Tìm khách hàng đã thuê cùng một phim nhiều hơn 1 lần
    // Logic: JOIN customer -> rental -> inventory -> film, GROUP BY theo customer và film
    // HAVING COUNT(*) > 1 để lọc những phim được thuê nhiều lần
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

    // Query 5: Tìm lần thuê đầu tiên của khách hàng cho mỗi category
    // Logic: Sử dụng NOT EXISTS để tìm rental đầu tiên của mỗi category cho mỗi khách hàng
    // Subquery kiểm tra không có rental nào trước đó cho cùng category
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

    // Query 6: Tìm khách hàng có giao dịch lớn (thuê hơn 10 phim trong 1 ngày)
    // Logic: JOIN customer -> rental -> payment, GROUP BY theo customer và ngày thuê
    // HAVING COUNT(*) > 10 để lọc những ngày thuê nhiều phim
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

    // Query 7: Tìm số lượng phim thuê theo category của những khách hàng đã thuê tất cả category
    // Logic: Subquery tìm khách hàng thuê đủ tất cả category, sau đó JOIN để lấy chi tiết theo category
    @Query(value = "SELECT CONCAT(c.first_name, ' ', c.last_name) AS customerName, cat.name AS categoryName, COUNT(*) AS filmsRented " +
            "FROM customer c " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "JOIN film_category fc ON i.film_id = fc.film_id " +
            "JOIN category cat ON fc.category_id = cat.category_id " +
            "WHERE c.customer_id IN ( " +
            "    SELECT r_inner.customer_id " +
            "    FROM rental r_inner " +
            "    JOIN inventory i_inner ON r_inner.inventory_id = i_inner.inventory_id " +
            "    JOIN film_category fc_inner ON i_inner.film_id = fc_inner.film_id " +
            "    GROUP BY r_inner.customer_id " +
            "    HAVING COUNT(DISTINCT fc_inner.category_id) = (SELECT COUNT(*) FROM category) " +
            ") " +
            "GROUP BY customerName, categoryName " +
            "ORDER BY customerName, categoryName", nativeQuery = true)
    List<CustomerFilmCountPerCategoryProjection> findCustomersRentedFromAllCategoriesWithCount();

    interface CustomerFilmCountPerCategoryProjection {
        String getCustomerName();
        String getCategoryName();
        Long getFilmsRented();
    }

    // Query 8: Tìm khách hàng thuê category mới và không thuê phim dài (>180 phút)
    // Logic: NOT EXISTS để đảm bảo không thuê phim dài, EXISTS để đảm bảo có thuê category mới
    // Subquery kiểm tra category chưa được thuê trước đó
    @Query(value = "SELECT DISTINCT CONCAT(c.first_name, ' ', c.last_name) AS customerName " +
            "FROM customer c " +
            "JOIN rental r ON c.customer_id = r.customer_id " +
            "JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "JOIN film f ON i.film_id = f.film_id " +
            "JOIN film_category fc ON f.film_id = fc.film_id " +
            "WHERE NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM rental r2 " +
            "    JOIN inventory i2 ON r2.inventory_id = i2.inventory_id " +
            "    JOIN film f2 ON i2.film_id = f2.film_id " +
            "    WHERE r2.customer_id = c.customer_id AND f2.length > 180 " +
            ") " +
            "AND EXISTS ( " +
            "    SELECT 1 " +
            "    FROM rental r3 " +
            "    JOIN inventory i3 ON r3.inventory_id = i3.inventory_id " +
            "    JOIN film_category fc3 ON i3.film_id = fc3.film_id " +
            "    WHERE r3.customer_id = c.customer_id " +
            "    AND fc3.category_id NOT IN ( " +
            "        SELECT DISTINCT fc4.category_id " +
            "        FROM rental r4 " +
            "        JOIN inventory i4 ON r4.inventory_id = i4.inventory_id " +
            "        JOIN film_category fc4 ON i4.film_id = fc4.film_id " +
            "        WHERE r4.customer_id = c.customer_id " +
            "        AND r4.rental_date < r3.rental_date " +
            "    ) " +
            ")", nativeQuery = true)
    List<CustomerNameProjection> findCustomersWithNewCategoryAndNoLongFilms();

    interface CustomerNameProjection {
        String getCustomerName();
    }

    // Query 9: Cập nhật email cho khách hàng thích phim Horror trong tháng 10/2022
    // Logic: Subquery tìm khách hàng thuê phim Horror trong tháng 10/2022
    // UPDATE với CONCAT để thêm 'horrorlover' vào email
    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET email = CONCAT(email, 'horrorlover') WHERE customer_id IN ( " +
            "    SELECT DISTINCT c.customer_id " +
            "    FROM customer c " +
            "    JOIN rental r ON c.customer_id = r.customer_id " +
            "    JOIN inventory i ON r.inventory_id = i.inventory_id " +
            "    JOIN film f ON i.film_id = f.film_id " +
            "    JOIN film_category fc ON f.film_id = fc.film_id " +
            "    JOIN category cat ON fc.category_id = cat.category_id " +
            "    WHERE cat.name = 'Horror' " +
            "      AND r.rental_date >= '2022-10-01' " +
            "      AND r.rental_date <  '2022-11-01' " +
            ")", nativeQuery = true)
    int updateEmailForHorrorLovers();

    // Query 10: Cập nhật address cho khách hàng có cùng last_name và city_id
    // Logic: Self-join customer table để tìm những cặp khách hàng có cùng last_name và city_id
    // UPDATE với CONCAT để thêm 'samecity' vào address
    @Modifying
    // Mục đích: Đánh dấu rằng query này sẽ thay đổi dữ liệu (INSERT, UPDATE, DELETE)
    // Mặc định: Spring Data JPA cho rằng @Query chỉ để đọc dữ liệu (SELECT)
    @Transactional
    // Mục đích: Đảm bảo toàn bộ operation được thực hiện trong một transaction
    @Query(value = "UPDATE customer SET address = CONCAT(address, 'samecity') WHERE customer_id IN ( " +
            "    SELECT c1.customer_id " +
            "    FROM customer c1 " +
            "    JOIN customer c2 " +
            "      ON c1.last_name = c2.last_name " +
            "     AND c1.city_id = c2.city_id " +
            "     AND c1.customer_id <> c2.customer_id " +
            ")", nativeQuery = true)
    int updateAddressForSameCityCustomers();

    // Query 11: Cập nhật address cho khách hàng đã thuê phim từ năm 2022
    // Logic: Subquery tìm khách hàng có rental từ 2022-01-01 trở đi
    // UPDATE với CONCAT để thêm 'updated' vào address
    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET address = CONCAT(address, 'updated') WHERE customer_id IN ( " +
            "    SELECT DISTINCT c.customer_id " +
            "    FROM customer c " +
            "    JOIN rental r ON c.customer_id = r.customer_id " +
            "    WHERE r.rental_date >= '2022-01-01' " +
            ")", nativeQuery = true)
    int updateCustomerAddresses();
} 