package com.example.authdemo.controller;

import com.example.authdemo.dto.CustomerAddressDto;
import com.example.authdemo.dto.TopCustomerRevenueDto;
import com.example.authdemo.dto.CustomerAllCategoryRentalDto;
import com.example.authdemo.dto.CustomerRepeatRentalDto;
import com.example.authdemo.dto.CustomerFirstTimeCategoryRentalDto;
import com.example.authdemo.dto.CustomerLargeTransactionDto;
import com.example.authdemo.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/rented-in-jan-2022")
    public List<CustomerAddressDto> getCustomerNamesAndAddressesRentedInJan2022() {
        return customerService.getCustomerNamesAndAddressesRentedInJan2022().stream()
                .map(c -> new CustomerAddressDto(c.getFirstName(), c.getLastName(), c.getAddress()))
                .collect(Collectors.toList());
    }

    @GetMapping("/top-revenue")
    public List<TopCustomerRevenueDto> getTop10CustomersByRevenue() {
        return customerService.getTop10CustomersByRevenue().stream()
                .map(c -> new TopCustomerRevenueDto(c.getFirstName(), c.getLastName(), c.getTotalRevenue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-all-categories")
    public List<CustomerAllCategoryRentalDto> getCustomersRentedAllCategories() {
        return customerService.getCustomersRentedAllCategories().stream()
                .map(c -> new CustomerAllCategoryRentalDto(c.getFirstName(), c.getLastName(), c.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/repeat-rentals")
    public List<CustomerRepeatRentalDto> getCustomersRentedSameFilmMoreThanOnce() {
        return customerService.getCustomersRentedSameFilmMoreThanOnce().stream()
                .map(c -> new CustomerRepeatRentalDto(c.getFirstName(), c.getLastName(), c.getFilmTitle(), c.getRentalCount()))
                .collect(Collectors.toList());
    }

    @GetMapping("/first-time-category-rental")
    public List<CustomerFirstTimeCategoryRentalDto> getCustomersFirstTimeCategoryRental() {
        return customerService.getCustomersFirstTimeCategoryRental().stream()
                .map(c -> new CustomerFirstTimeCategoryRentalDto(c.getCustomerId(), c.getFirstName(), c.getLastName(), c.getCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/large-transactions")
    public List<CustomerLargeTransactionDto> getCustomersWithLargeTransactions() {
        return customerService.getCustomersWithLargeTransactions().stream()
                .map(c -> new CustomerLargeTransactionDto(c.getCustomerId(), c.getCustomerName(), c.getRentalDay(), c.getFilmsRented(), c.getTotalFee()))
                .collect(Collectors.toList());
    }
} 