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
} 