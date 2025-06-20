package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", columnDefinition = "mediumint unsigned")
    private Integer inventoryId;

    @Column(name = "film_id", columnDefinition = "smallint unsigned")
    private Integer filmId;

    @Column(name = "store_id", columnDefinition = "tinyint unsigned")
    private Short storeId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 