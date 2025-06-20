package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilmCountPerCategoryDto {
    private String customerName;
    private String categoryName;
    private Long filmsRented;
} 