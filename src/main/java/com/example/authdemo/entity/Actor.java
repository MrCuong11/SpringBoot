package com.example.authdemo.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", columnDefinition = "smallint unsigned")
    private Integer actorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;

    // Getters and setters
    public Integer getActorId() { return actorId; }
    public void setActorId(Integer actorId) { this.actorId = actorId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public java.sql.Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(java.sql.Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
}