package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "rental")
@Data
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "rental_date")
    private Date rentalDate;

    @Column(name = "inventory_id", columnDefinition = "mediumint unsigned")
    private Integer inventoryId;

    @Column(name = "customer_id", columnDefinition = "smallint unsigned")
    private Integer customerId;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "staff_id", columnDefinition = "tinyint unsigned")
    private Short staffId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 