package com.example.authdemo.controller;

import com.example.authdemo.entity.Film;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<FilmDto> getAllFilms() {
        return filmService.getAllFilms().stream()
                .map(film -> new FilmDto(film.getTitle(), film.getRentalRate(), film.getReplacementCost()))
                .collect(Collectors.toList());
    }

    @GetMapping("/top-rented")
    public List<TopRentedFilmDto> getTop5MostRentedFilms() {
        return filmService.getTop5MostRentedFilms().stream()
                .map(f -> new TopRentedFilmDto(f.getTitle(), f.getRentalCount()))
                .collect(Collectors.toList());
    }

    @GetMapping("/pg13-long")
    public List<FilmPG13LongDto> getPG13FilmsLongerThan120() {
        return filmService.getPG13FilmsLongerThan120().stream()
                .map(f -> new FilmPG13LongDto(f.getTitle(), f.getRating(), f.getLength()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-by-many-once")
    public List<FilmRentedByManyOnceDto> getFilmsRentedByMoreThan50CustomersOnce() {
        return filmService.getFilmsRentedByMoreThan50CustomersOnce().stream()
                .map(f -> new FilmRentedByManyOnceDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-by-all-action-customers")
    public List<FilmRentedByAllActionCustomersDto> getFilmsRentedByAllActionCustomers() {
        return filmService.getFilmsRentedByAllActionCustomers().stream()
                .map(f -> new FilmRentedByAllActionCustomersDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @GetMapping("/repeat-rental-by-customer")
    public List<FilmRepeatRentalByCustomerDto> getFilmsRepeatRentedByCustomerInOneDay() {
        return filmService.getFilmsRepeatRentedByCustomerInOneDay().stream()
                .map(f -> new FilmRepeatRentalByCustomerDto(f.getTitle(), f.getCustomerName(), f.getRentalDay(), f.getTimesRented()))
                .collect(Collectors.toList());
    }

    @GetMapping("/rented-100-times-no-g-customers")
    public List<FilmTitleDto> getFilmsRentedMoreThan100TimesAndNeverByGCustomers() {
        return filmService.getFilmsRentedMoreThan100TimesAndNeverByGCustomers().stream()
                .map(f -> new FilmTitleDto(f.getTitle()))
                .collect(Collectors.toList());
    }

    @PutMapping("/update-rental-rate-popular")
    public UpdateResultDto updateRentalRateForPopularFilms() {
        int updatedCount = filmService.updateRentalRateForPopularFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " popular films (10% increase)");
    }

    @PutMapping("/update-rental-duration-popular")
    public UpdateResultDto updateRentalDurationForPopularFilms() {
        int updatedCount = filmService.updateRentalDurationForPopularFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental duration for " + updatedCount + " popular films (5% increase)");
    }

    @PutMapping("/update-rental-rate-old-action")
    public UpdateResultDto updateRentalRateForOldActionFilms() {
        int updatedCount = filmService.updateRentalRateForOldActionFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " old Action films (20% increase)");
    }

    @PutMapping("/update-rental-rate-popular-with-limit")
    public UpdateResultDto updateRentalRateForPopularFilmsWithLimit() {
        int updatedCount = filmService.updateRentalRateForPopularFilmsWithLimit();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " popular films (5% increase, max $4.00)");
    }

    @PutMapping("/update-rental-rate-pg13-long")
    public UpdateResultDto updateRentalRateForPG13LongFilms() {
        int updatedCount = filmService.updateRentalRateForPG13LongFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate to $3.50 for " + updatedCount + " PG-13 films longer than 2 hours");
    }

    @PutMapping("/update-rental-duration-scifi-2010")
    public UpdateResultDto updateRentalDurationForSciFi2010() {
        int updatedCount = filmService.updateRentalDurationForSciFi2010();
        return new UpdateResultDto(updatedCount, "Successfully updated rental duration to film length for " + updatedCount + " Sci-Fi films from 2010");
    }

    @PutMapping("/update-rental-rate-comedy-2007-plus")
    public UpdateResultDto updateRentalRateForComedy2007Plus() {
        int updatedCount = filmService.updateRentalRateForComedy2007Plus();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate for " + updatedCount + " Comedy films from 2007 onwards (15% decrease)");
    }

    @PutMapping("/update-rental-rate-g-short")
    public UpdateResultDto updateRentalRateForGShortFilms() {
        int updatedCount = filmService.updateRentalRateForGShortFilms();
        return new UpdateResultDto(updatedCount, "Successfully updated rental rate to $1.50 for " + updatedCount + " G-rated films shorter than 1 hour");
    }
} 