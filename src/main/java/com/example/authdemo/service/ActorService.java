package com.example.authdemo.service;

import com.example.authdemo.entity.Actor;
import com.example.authdemo.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public List<ActorRepository.ActorFilmCountProjection> getActorsWithMoreThan20Films() {
        return actorRepository.findActorsWithMoreThan20Films();
    }

    public List<ActorRepository.ActorAllCategoryProjection> getActorsInAllCategories() {
        return actorRepository.findActorsInAllCategories();
    }

    public List<ActorRepository.ActorRevenueProjection> getActorsTotalRevenue() {
        return actorRepository.findActorsTotalRevenue();
    }

    public List<ActorRepository.ActorRNotGProjection> getActorsInRNotInG() {
        return actorRepository.findActorsInRNotInG();
    }

    public List<ActorRepository.ActorCategoryAvgRentalDurationProjection> getActorCategoryAvgRentalDuration() {
        return actorRepository.findActorCategoryAvgRentalDuration();
    }

    public List<ActorRepository.ActorRLongNotGProjection> getActorsInRLongNotInG() {
        return actorRepository.findActorsInRLongNotInG();
    }

    public List<ActorRepository.ActorWithAllCoActorsProjection> getActorsWithAllCoActors() {
        return actorRepository.findActorsWithAllCoActors();
    }
}