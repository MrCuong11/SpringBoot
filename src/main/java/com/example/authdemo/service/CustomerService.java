package com.example.authdemo.service;

import com.example.authdemo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerRepository.CustomerAddressProjection> getCustomerNamesAndAddressesRentedInJan2022() {
        return customerRepository.findCustomerNamesAndAddressesRentedInJan2022();
    }

    public List<CustomerRepository.TopCustomerRevenueProjection> getTop10CustomersByRevenue() {
        return customerRepository.findTop10CustomersByRevenue();
    }

    public List<CustomerRepository.CustomerAllCategoryRentalProjection> getCustomersRentedAllCategories() {
        return customerRepository.findCustomersRentedAllCategories();
    }

    public List<CustomerRepository.CustomerRepeatRentalProjection> getCustomersRentedSameFilmMoreThanOnce() {
        return customerRepository.findCustomersRentedSameFilmMoreThanOnce();
    }

    public List<CustomerRepository.CustomerFirstTimeCategoryRentalProjection> getCustomersFirstTimeCategoryRental() {
        return customerRepository.findCustomersFirstTimeCategoryRental();
    }

    public List<CustomerRepository.CustomerLargeTransactionProjection> getCustomersWithLargeTransactions() {
        return customerRepository.findCustomersWithLargeTransactions();
    }

    public List<CustomerRepository.CustomerFilmCountPerCategoryProjection> getCustomersRentedFromAllCategoriesWithCount() {
        return customerRepository.findCustomersRentedFromAllCategoriesWithCount();
    }
} 