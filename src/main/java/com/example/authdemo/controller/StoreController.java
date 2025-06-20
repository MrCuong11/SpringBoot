package com.example.authdemo.controller;

import com.example.authdemo.dto.StoreRevenueDto;
import com.example.authdemo.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/revenue-2021")
    public List<StoreRevenueDto> getStoreRevenue2021() {
        return storeService.getStoreRevenue2021().stream()
                .map(s -> new StoreRevenueDto(s.getStoreId(), s.getTotalRevenue()))
                .collect(Collectors.toList());
    }
} 