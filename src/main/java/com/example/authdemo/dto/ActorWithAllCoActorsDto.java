package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorWithAllCoActorsDto {
    private Integer actorId;
    private String actorName;
    private String coActorName;
    private Long filmsTogether;
} 