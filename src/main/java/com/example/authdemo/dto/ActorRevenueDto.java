package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorRevenueDto {
    private String firstName;
    private String lastName;
    private BigDecimal totalRevenue;
} 