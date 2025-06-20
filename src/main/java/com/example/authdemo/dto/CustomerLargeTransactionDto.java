package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLargeTransactionDto {
    private Integer customerId;
    private String customerName;
    private Date rentalDay;
    private Long filmsRented;
    private BigDecimal totalFee;
} 