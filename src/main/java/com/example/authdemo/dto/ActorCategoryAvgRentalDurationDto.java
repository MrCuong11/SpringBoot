package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorCategoryAvgRentalDurationDto {
    private Integer actorId;
    private String actorName;
    private Short categoryId;
    private String categoryName;
    private Double avgRentalDuration;
} 