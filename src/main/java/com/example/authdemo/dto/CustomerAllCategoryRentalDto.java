package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAllCategoryRentalDto {
    private String firstName;
    private String lastName;
    private String email;
} 