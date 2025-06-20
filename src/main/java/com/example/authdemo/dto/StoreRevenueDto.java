package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRevenueDto {
    private Short storeId;
    private BigDecimal totalRevenue;
} 