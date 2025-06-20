package com.example.authdemo.service;

import com.example.authdemo.entity.Film;
import com.example.authdemo.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public List<FilmRepository.TopRentedFilmProjection> getTop5MostRentedFilms() {
        return filmRepository.findTop5MostRentedFilms();
    }

    public List<FilmRepository.FilmPG13LongProjection> getPG13FilmsLongerThan120() {
        return filmRepository.findPG13FilmsLongerThan120();
    }

    public List<FilmRepository.FilmRentedByManyOnceProjection> getFilmsRentedByMoreThan50CustomersOnce() {
        return filmRepository.findFilmsRentedByMoreThan50CustomersOnce();
    }
} 