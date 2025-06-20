package com.example.authdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmRepeatRentalByCustomerDto {
    private String title;
    private String customerName;
    private Date rentalDay;
    private Long timesRented;
} 