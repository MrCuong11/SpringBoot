package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "smallint unsigned")
    private Integer customerId;

    @Column(name = "store_id", columnDefinition = "tinyint unsigned")
    private Short storeId;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "address_id", columnDefinition = "smallint unsigned")
    private Integer addressId;

    @Column(name = "active", columnDefinition = "tinyint(1)")
    private Boolean active;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 