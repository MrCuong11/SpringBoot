package com.example.authdemo.controller;

import com.example.authdemo.dto.ActorFilmCountDto;
import com.example.authdemo.dto.ActorAllCategoryDto;
import com.example.authdemo.dto.ActorRevenueDto;
import com.example.authdemo.dto.ActorRNotGDto;
import com.example.authdemo.entity.Actor;
import com.example.authdemo.service.ActorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }
    @GetMapping("/more-than-20-films")
    public List<ActorFilmCountDto> getActorsWithMoreThan20Films() {
        return actorService.getActorsWithMoreThan20Films().stream()
                .map(a -> new ActorFilmCountDto(a.getFirstName(), a.getLastName(), a.getFilmCount()))
                .collect(Collectors.toList());
    }
    @GetMapping("/in-all-categories")
    public List<ActorAllCategoryDto> getActorsInAllCategories() {
        return actorService.getActorsInAllCategories().stream()
                .map(a -> new ActorAllCategoryDto(a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }
    @GetMapping("/total-revenue")
    public List<ActorRevenueDto> getActorsTotalRevenue() {
        return actorService.getActorsTotalRevenue().stream()
                .map(a -> new ActorRevenueDto(a.getFirstName(), a.getLastName(), a.getTotalRevenue()))
                .collect(Collectors.toList());
    }
    @GetMapping("/r-not-g")
    public List<ActorRNotGDto> getActorsInRNotInG() {
        return actorService.getActorsInRNotInG().stream()
                .map(a -> new ActorRNotGDto(a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }
}