package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFirstTimeCategoryRentalDto {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String categoryName;
} 