package com.example.authdemo.controller;


import com.example.authdemo.service.FilmService;
import com.example.authdemo.dto.TopRentedFilmDto;
import com.example.authdemo.dto.FilmPG13LongDto;
import com.example.authdemo.dto.FilmDto;
import com.example.authdemo.dto.FilmRentedByManyOnceDto;
import com.example.authdemo.dto.FilmRentedByAllActionCustomersDto;
import com.example.authdemo.dto.FilmRepeatRentalByCustomerDto;
import com.example.authdemo.dto.FilmTitleDto;
import com.example.authdemo.dto.UpdateResultDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/films")
@PreAuthorize("hasRole('ADMIN')")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
/**
 * Lấy danh sách tất cả phim với thông tin cơ bản và hỗ trợ phân trang
 * Input: page, size - các tham số phân trang từ URL
 * Output: Page<FilmDto> - Một trang của danh sách phim với thông tin phân trang
 */
public Page<FilmDto> getAllFilms(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return filmService.getAllFilms(pageable)
            .map(film -> new FilmDto(film.getTitle(), film.getRentalRate(), film.getReplacementCost()));
}

    @GetMapping("/top-rented")
    /**
     * Lấy top 5 phim được thuê nhiều nhất
     * Input: Không có (GET request)
     * Output: List<TopRentedFilmDto> - Danh sách 5 phim với tên và số lần thuê
     */
    public List<TopRentedFilmDto> getTop5MostRentedFilms() {
        return filmService.getTop5MostRentedFilms().stream()
                .map(f -> new TopRentedFilmDto(f.getTitle(), f.getRentalCount()))
                .collect(Collectors.toList());
    }

    @GetMapping("/pg13-long")
    /**
     * Lấy danh sách phim PG-13 dài hơn 120 phút
     * Input: Không có (GET request)
     * Output: List<FilmPG13LongDto> - Danh sách phim PG-13 với tên, rating và độ dài
     */
    public List<FilmPG13LongDto> getPG13FilmsLongerThan120() {
        return filmService.getPG13FilmsLongerThan120().stream()
                .map(f -> new FilmPG13LongDto(f.getTitle(), f.getRating(), f.getLength()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-by-many-once")
    /**
     * Lấy danh sách phim được thuê bởi hơn 50 khách hàng khác nhau (mỗi khách chỉ thuê 1 lần)
     * Input: Không có (GET request)
     * Output: List<FilmRentedByManyOnceDto> - Danh sách phim được thuê bởi nhiều khách hàng khác nhau
     */
    public List<FilmRentedByManyOnceDto> getFilmsRentedByMoreThan50CustomersOnce() {
        return filmService.getFilmsRentedByMoreThan50CustomersOnce().stream()
                .map(f -> new FilmRentedByManyOnceDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-by-all-action-customers")
    /**
     * Lấy danh sách phim được thuê bởi tất cả khách hàng đã từng thuê phim Action
     * Input: Không có (GET request)
     * Output: List<FilmRentedByAllActionCustomersDto> - Danh sách phim được thuê bởi tất cả fan phim Action
     */
    public List<FilmRentedByAllActionCustomersDto> getFilmsRentedByAllActionCustomers() {
        return filmService.getFilmsRentedByAllActionCustomers().stream()
                .map(f -> new FilmRentedByAllActionCustomersDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @GetMapping("/repeat-rental-by-customer")
    /**
     * Lấy danh sách phim được thuê lại bởi cùng một khách hàng trong cùng một ngày
     * Input: Không có (GET request)
     * Output: List<FilmRepeatRentalByCustomerDto> - Danh sách phim với thông tin khách hàng và số lần thuê trong ngày
     */
    public List<FilmRepeatRentalByCustomerDto> getFilmsRepeatRentedByCustomerInOneDay() {
        return filmService.getFilmsRepeatRentedByCustomerInOneDay().stream()
                .map(f -> new FilmRepeatRentalByCustomerDto(f.getTitle(), f.getCustomerName(), f.getRentalDay(), f.getTimesRented()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-100-times-no-g-customers")
    /**
     * Lấy danh sách phim được thuê hơn 100 lần và chưa bao giờ được thuê bởi khách hàng G-rated
     * Input: Không có (GET request)
     * Output: List<FilmTitleDto> - Danh sách phim phổ biến nhưng không được fan phim G thuê
     */
    public List<FilmTitleDto> getFilmsRentedMoreThan100TimesAndNeverByGCustomers() {
        return filmService.getFilmsRentedMoreThan100TimesAndNeverByGCustomers().stream()
                .map(f -> new FilmTitleDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @PutMapping("/update-rental-rate-popular")
    /**
     * Tăng giá thuê 10% cho các phim phổ biến (được thuê nhiều)
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForPopularFilms() {
        int updatedCount = filmService.updateRentalRateForPopularFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " popular films (10% increase)");
    }

    @PutMapping("/update-rental-duration-popular")
    /**
     * Tăng thời gian thuê 5% cho các phim phổ biến
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalDurationForPopularFilms() {
        int updatedCount = filmService.updateRentalDurationForPopularFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental duration for " + updatedCount + " popular films (5% increase)");
    }

    @PutMapping("/update-rental-rate-old-action")
    /**
     * Tăng giá thuê 20% cho các phim Action cũ
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForOldActionFilms() {
        int updatedCount = filmService.updateRentalRateForOldActionFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " old Action films (20% increase)");
    }

    @PutMapping("/update-rental-rate-popular-with-limit")
    /**
     * Tăng giá thuê 5% cho phim phổ biến với giới hạn tối đa $4.00
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForPopularFilmsWithLimit() {
        int updatedCount = filmService.updateRentalRateForPopularFilmsWithLimit();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " popular films (5% increase, max $4.00)");
    }

    @PutMapping("/update-rental-rate-pg13-long")
    /**
     * Cập nhật giá thuê thành $3.50 cho các phim PG-13 dài hơn 2 giờ
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForPG13LongFilms() {
        int updatedCount = filmService.updateRentalRateForPG13LongFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate to $3.50 for " + updatedCount + " PG-13 films longer than 2 hours");
    }

    @PutMapping("/update-rental-duration-scifi-2010")
    /**
     * Cập nhật thời gian thuê bằng độ dài phim cho các phim Sci-Fi từ năm 2010
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalDurationForSciFi2010() {
        int updatedCount = filmService.updateRentalDurationForSciFi2010();
        return new UpdateResultDto(updatedCount, "Successfully updated rental duration to film length for " + updatedCount + " Sci-Fi films from 2010");
    }

    @PutMapping("/update-rental-rate-comedy-2007-plus")
    /**
     * Giảm giá thuê 15% cho các phim Comedy từ năm 2007 trở đi
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForComedy2007Plus() {
        int updatedCount = filmService.updateRentalRateForComedy2007Plus();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " Comedy films from 2007 onwards (15% decrease)");
    }

    @PutMapping("/update-rental-rate-g-short")
    /**
     * Cập nhật giá thuê thành $1.50 cho các phim G-rated ngắn hơn 1 giờ
     * Input: Không có (PUT request)
     * Output: UpdateResultDto - Số lượng phim được cập nhật và thông báo
     */
    public UpdateResultDto updateRentalRateForGShortFilms() {
        int updatedCount = filmService.updateRentalRateForGShortFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate to $1.50 for " + updatedCount + " G-rated films shorter than 1 hour");
    }
} 