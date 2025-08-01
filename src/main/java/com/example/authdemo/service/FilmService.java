package com.example.authdemo.service;

import com.example.authdemo.entity.Film;
import com.example.authdemo.repository.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Page<Film> getAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
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

    public List<FilmRepository.FilmRentedByAllActionCustomersProjection> getFilmsRentedByAllActionCustomers() {
        return filmRepository.findFilmsRentedByAllActionCustomers();
    }

    public List<FilmRepository.FilmRepeatRentalByCustomerProjection> getFilmsRepeatRentedByCustomerInOneDay() {
        return filmRepository.findFilmsRepeatRentedByCustomerInOneDay();
    }

    public List<FilmRepository.FilmTitleProjection> getFilmsRentedMoreThan100TimesAndNeverByGCustomers() {
        return filmRepository.findFilmsRentedMoreThan100TimesAndNeverByGCustomers();
    }

    public int updateRentalRateForPopularFilms() {
        return filmRepository.updateRentalRateForPopularFilms();
    }

    public int updateRentalDurationForPopularFilms() {
        return filmRepository.updateRentalDurationForPopularFilms();
    }

    public int updateRentalRateForOldActionFilms() {
        return filmRepository.updateRentalRateForOldActionFilms();
    }

    public int updateRentalRateForPopularFilmsWithLimit() {
        return filmRepository.updateRentalRateForPopularFilmsWithLimit();
    }

    public int updateRentalRateForPG13LongFilms() {
        return filmRepository.updateRentalRateForPG13LongFilms();
    }

    public int updateRentalDurationForSciFi2010() {
        return filmRepository.updateRentalDurationForSciFi2010();
    }

    public int updateRentalRateForComedy2007Plus() {
        return filmRepository.updateRentalRateForComedy2007Plus();
    }

    public int updateRentalRateForGShortFilms() {
        return filmRepository.updateRentalRateForGShortFilms();
    }
} 