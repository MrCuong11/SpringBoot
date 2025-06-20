package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "store")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", columnDefinition = "tinyint unsigned")
    private Short storeId;

    @Column(name = "manager_staff_id", columnDefinition = "tinyint unsigned")
    private Short managerStaffId;

    @Column(name = "address_id", columnDefinition = "smallint unsigned")
    private Integer addressId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 