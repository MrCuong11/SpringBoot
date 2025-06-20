package com.example.authdemo.controller;

import com.example.authdemo.dto.CustomerAddressDto;
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
} 