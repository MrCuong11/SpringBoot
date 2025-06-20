package com.example.authdemo.controller;

import com.example.authdemo.entity.Film;
import com.example.authdemo.service.FilmService;
import com.example.authdemo.dto.TopRentedFilmDto;
import com.example.authdemo.dto.FilmPG13LongDto;
import com.example.authdemo.dto.FilmDto;
import com.example.authdemo.dto.FilmRentedByManyOnceDto;
import com.example.authdemo.dto.FilmRentedByAllActionCustomersDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/films")
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
} 