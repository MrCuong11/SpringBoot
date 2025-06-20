package com.example.authdemo.controller;

import com.example.authdemo.entity.Film;
import com.example.authdemo.service.FilmService;
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

    public static class FilmDto {
        private String title;
        private java.math.BigDecimal rentalRate;
        private java.math.BigDecimal replacementCost;

        public FilmDto(String title, java.math.BigDecimal rentalRate, java.math.BigDecimal replacementCost) {
            this.title = title;
            this.rentalRate = rentalRate;
            this.replacementCost = replacementCost;
        }

        public String getTitle() { return title; }
        public java.math.BigDecimal getRentalRate() { return rentalRate; }
        public java.math.BigDecimal getReplacementCost() { return replacementCost; }
    }
} 