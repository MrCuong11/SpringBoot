package com.example.authdemo.controller;

import com.example.authdemo.dto.ChamCongResultDto;
import com.example.authdemo.service.ChamCongService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cham-cong")
@SecurityRequirement(name = "bearerAuth")
public class ChamCongController {

    private final ChamCongService chamCongService;

    public ChamCongController(ChamCongService chamCongService) {
        this.chamCongService = chamCongService;
    }

    @GetMapping("/process")
    public ResponseEntity<?> processChamCong() {
        try {
            List<ChamCongResultDto> results = chamCongService.processChamCongFile();
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the file: " + e.getMessage());
        }
    }
} 