package com.example.authdemo.controller;

import com.example.authdemo.dto.StoreRevenueDto;
import com.example.authdemo.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/stores")
@PreAuthorize("hasRole('ADMIN')")
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