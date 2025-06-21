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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/rented-in-jan-2022")
    /**
     * Lấy danh sách khách hàng đã thuê phim trong tháng 1/2022
     * Input: Không có (GET request)
     * Output: List<CustomerAddressDto> - Danh sách khách hàng với tên và địa chỉ
     */
    public List<CustomerAddressDto> getCustomerNamesAndAddressesRentedInJan2022() {
        return customerService.getCustomerNamesAndAddressesRentedInJan2022().stream()
                .map(c -> new CustomerAddressDto(c.getFirstName(), c.getLastName(), c.getAddress()))
                .collect(Collectors.toList());
    }

    @GetMapping("/top-revenue")
    /**
     * Lấy top 10 khách hàng có doanh thu cao nhất
     * Input: Không có (GET request)
     * Output: List<TopCustomerRevenueDto> - Danh sách 10 khách hàng với tên và tổng doanh thu
     */
    public List<TopCustomerRevenueDto> getTop10CustomersByRevenue() {
        return customerService.getTop10CustomersByRevenue().stream()
                .map(c -> new TopCustomerRevenueDto(c.getFirstName(), c.getLastName(), c.getTotalRevenue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-all-categories")
    /**
     * Lấy danh sách khách hàng đã thuê phim thuộc tất cả các thể loại
     * Input: Không có (GET request)
     * Output: List<CustomerAllCategoryRentalDto> - Danh sách khách hàng đã thuê đủ tất cả thể loại
     */
    public List<CustomerAllCategoryRentalDto> getCustomersRentedAllCategories() {
        return customerService.getCustomersRentedAllCategories().stream()
                .map(c -> new CustomerAllCategoryRentalDto(c.getFirstName(), c.getLastName(), c.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/repeat-rentals")
    /**
     * Lấy danh sách khách hàng đã thuê cùng một bộ phim nhiều hơn 1 lần
     * Input: Không có (GET request)
     * Output: List<CustomerRepeatRentalDto> - Danh sách khách hàng với tên phim và số lần thuê
     */
    public List<CustomerRepeatRentalDto> getCustomersRentedSameFilmMoreThanOnce() {
        return customerService.getCustomersRentedSameFilmMoreThanOnce().stream()
                .map(c -> new CustomerRepeatRentalDto(c.getFirstName(), c.getLastName(), c.getFilmTitle(), c.getRentalCount()))
                .collect(Collectors.toList());
    }

    @GetMapping("/first-time-category-rental")
    /**
     * Lấy danh sách lần đầu tiên khách hàng thuê phim của một thể loại mới
     * Input: Không có (GET request)
     * Output: List<CustomerFirstTimeCategoryRentalDto> - Danh sách khách hàng với thể loại phim lần đầu thuê
     */
    public List<CustomerFirstTimeCategoryRentalDto> getCustomersFirstTimeCategoryRental() {
        return customerService.getCustomersFirstTimeCategoryRental().stream()
                .map(c -> new CustomerFirstTimeCategoryRentalDto(c.getCustomerId(), c.getFirstName(), c.getLastName(), c.getCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/large-transactions")
    /**
     * Lấy danh sách khách hàng có giao dịch lớn (thuê hơn 10 phim trong 1 ngày)
     * Input: Không có (GET request)
     * Output: List<CustomerLargeTransactionDto> - Danh sách khách hàng với thông tin giao dịch lớn
     */
    public List<CustomerLargeTransactionDto> getCustomersWithLargeTransactions() {
        return customerService.getCustomersWithLargeTransactions().stream()
                .map(c -> new CustomerLargeTransactionDto(c.getCustomerId(), c.getCustomerName(), c.getRentalDay(), c.getFilmsRented(), c.getTotalFee()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-all-categories-count")
    /**
     * Lấy số lượng phim thuê theo từng thể loại của khách hàng đã thuê tất cả thể loại
     * Input: Không có (GET request)
     * Output: List<CustomerFilmCountPerCategoryDto> - Danh sách khách hàng với số phim thuê theo thể loại
     */
    public List<CustomerFilmCountPerCategoryDto> getCustomersRentedFromAllCategoriesWithCount() {
        return customerService.getCustomersRentedFromAllCategoriesWithCount().stream()
                .map(c -> new CustomerFilmCountPerCategoryDto(c.getCustomerName(), c.getCategoryName(), c.getFilmsRented()))
                .collect(Collectors.toList());
    }

    @GetMapping("/new-category-no-long-films")
    /**
     * Lấy danh sách khách hàng đã thuê thể loại mới nhưng chưa thuê phim dài (trên 3 giờ)
     * Input: Không có (GET request)
     * Output: List<CustomerNameDto> - Danh sách khách hàng thỏa mãn điều kiện
     */
    public List<CustomerNameDto> getCustomersWithNewCategoryAndNoLongFilms() {
        return customerService.getCustomersWithNewCategoryAndNoLongFilms().stream()
                .map(c -> new CustomerNameDto(c.getCustomerName()))
                .collect(Collectors.toList());
    }

    @PutMapping("/update-addresses")
    /**
     * Cập nhật địa chỉ cho khách hàng đã thuê phim từ 2022 trở đi
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng khách hàng được cập nhật và thông báo
     */
    public UpdateResultDto updateCustomerAddresses() {
        int updatedCount = customerService.updateCustomerAddresses();
        return new UpdateResultDto(updatedCount, "Successfully updated addresses for " + updatedCount + " customers");
    }

    @PutMapping("/update-email-horror-lovers")
    /**
     * Cập nhật email cho khách hàng yêu thích phim Horror (thêm 'horrorlover' vào email)
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng khách hàng được cập nhật và thông báo
     */
    public UpdateResultDto updateEmailForHorrorLovers() {
        int updatedCount = customerService.updateEmailForHorrorLovers();
        return new UpdateResultDto(updatedCount, "Successfully updated email for " + updatedCount + " horror movie lovers");
    }

    @PutMapping("/update-address-same-city")
    /**
     * Cập nhật địa chỉ cho khách hàng có cùng họ và sống cùng thành phố
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng khách hàng được cập nhật và thông báo
     */
    public UpdateResultDto updateAddressForSameCityCustomers() {
        int updatedCount = customerService.updateAddressForSameCityCustomers();
        return new UpdateResultDto(updatedCount, "Successfully updated address for " + updatedCount + " customers living in same city with same last name");
    }
} 