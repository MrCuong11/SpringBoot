package com.example.authdemo.controller;

import com.example.authdemo.dto.CustomerAddressDto;
import com.example.authdemo.dto.TopCustomerRevenueDto;
import com.example.authdemo.dto.CustomerAllCategoryRentalDto;
import com.example.authdemo.dto.CustomerRepeatRentalDto;
import com.example.authdemo.dto.CustomerFirstTimeCategoryRentalDto;
import com.example.authdemo.dto.CustomerLargeTransactionDto;
import com.example.authdemo.dto.CustomerFilmCountPerCategoryDto;
import com.example.authdemo.dto.CustomerNameDto;
import com.example.authdemo.dto.UpdateResultDto;
import com.example.authdemo.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/rented-all-categories-count")
    public List<CustomerFilmCountPerCategoryDto> getCustomersRentedFromAllCategoriesWithCount() {
        return customerService.getCustomersRentedFromAllCategoriesWithCount().stream()
                .map(c -> new CustomerFilmCountPerCategoryDto(c.getCustomerName(), c.getCategoryName(), c.getFilmsRented()))
                .collect(Collectors.toList());
    }

    @GetMapping("/new-category-no-long-films")
    public List<CustomerNameDto> getCustomersWithNewCategoryAndNoLongFilms() {
        return customerService.getCustomersWithNewCategoryAndNoLongFilms().stream()
                .map(c -> new CustomerNameDto(c.getCustomerName()))
                .collect(Collectors.toList());
    }

    @PutMapping("/update-addresses")
    public UpdateResultDto updateCustomerAddresses() {
        int updatedCount = customerService.updateCustomerAddresses();
        return new UpdateResultDto(updatedCount, "Successfully updated addresses for " + updatedCount + " customers");
    }

    @PutMapping("/update-email-horror-lovers")
    public UpdateResultDto updateEmailForHorrorLovers() {
        int updatedCount = customerService.updateEmailForHorrorLovers();
        return new UpdateResultDto(updatedCount, "Successfully updated email for " + updatedCount + " horror movie lovers");
    }

    @PutMapping("/update-address-same-city")
    public UpdateResultDto updateAddressForSameCityCustomers() {
        int updatedCount = customerService.updateAddressForSameCityCustomers();
        return new UpdateResultDto(updatedCount, "Successfully updated address for " + updatedCount + " customers living in same city with same last name");
    }
} 