package com.example.authdemo.controller;

import com.example.authdemo.service.Level1Service;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level1")
@SecurityRequirement(name = "bearerAuth")
public class Level1Controller {

    private final Level1Service level1Service;

    public Level1Controller(Level1Service level1Service) {
        this.level1Service = level1Service;
    }

    @PostMapping("/sum")
    public ResponseEntity<String> sum(@RequestParam double num1, @RequestParam double num2) {
        double result = level1Service.sum(num1, num2);
        return ResponseEntity.ok("The sum of " + num1 + " and " + num2 + " is: " + result);
    }

    @GetMapping("/length")
    public ResponseEntity<String> getStringLength(@RequestParam String input) {
        int length = level1Service.getStringLength(input);
        return ResponseEntity.ok("The length of the string \"" + input + "\" is: " + length);
    }

    @GetMapping("/square")
    public ResponseEntity<String> getSquare(@RequestParam double number) {
        double square = level1Service.getSquare(number);
        return ResponseEntity.ok("The square of " + number + " is: " + square);
    }

    @PostMapping("/max-number")
    public ResponseEntity<String> findLargest(@RequestBody List<Double> numbers) {
        try {
            double max = level1Service.findLargest(numbers);
            return ResponseEntity.ok("The largest number is: " + max);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/shortest-string")
    public ResponseEntity<String> findShortest(@RequestBody List<String> strings) {
        try {
            String shortest = level1Service.findShortest(strings);
            return ResponseEntity.ok("The shortest string is: \"" + shortest + "\"");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-numbers")
    public ResponseEntity<List<Double>> sortNumbers(@RequestBody List<Double> numbers) {
        try {
            List<Double> sorted = level1Service.sortNumbers(numbers);
            return ResponseEntity.ok(sorted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sort-strings")
    public ResponseEntity<List<String>> sortStrings(@RequestBody List<String> strings) {
        try {
            List<String> sorted = level1Service.sortStrings(strings);
            return ResponseEntity.ok(sorted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/median")
    public ResponseEntity<String> findMedian(@RequestBody List<Double> numbers) {
        try {
            double median = level1Service.findMedian(numbers);
            return ResponseEntity.ok("Median is: " + median);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/count-words")
    public ResponseEntity<String> countWords(@RequestBody String input) {
        int wordCount = level1Service.countWords(input);
        return ResponseEntity.ok("Number of words: " + wordCount);
    }

    @PostMapping("/count-strings-with-a")
    public ResponseEntity<String> countStringsWithA(@RequestBody List<String> strings) {
        try {
            long count = level1Service.countStringsWithA(strings);
            return ResponseEntity.ok("Strings containing 'a': " + count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
