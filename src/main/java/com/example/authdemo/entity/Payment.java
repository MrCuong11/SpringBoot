package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", columnDefinition = "smallint unsigned")
    private Integer paymentId;

    @Column(name = "customer_id", columnDefinition = "smallint unsigned")
    private Integer customerId;

    @Column(name = "staff_id", columnDefinition = "tinyint unsigned")
    private Short staffId;

    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "amount", precision = 5, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 