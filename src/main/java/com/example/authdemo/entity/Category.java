package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition = "tinyint unsigned")
    private Short categoryId;

    @Column(name = "name", length = 25)
    private String name;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 