package com.example.authdemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "film_actor")
@Data
public class FilmActor {
    @Id
    @Column(name = "actor_id", columnDefinition = "smallint unsigned")
    private Integer actorId;

    @Column(name = "film_id", columnDefinition = "smallint unsigned")
    private Integer filmId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
} 