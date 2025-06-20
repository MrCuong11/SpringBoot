package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "film_category")
@Data
public class FilmCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "smallint unsigned")
    private Integer filmId;

    @Column(name = "category_id", columnDefinition = "tinyint unsigned")
    private Short categoryId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 