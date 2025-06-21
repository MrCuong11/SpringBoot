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
    /**
     * Tính tổng của hai số
     * Input: num1 (double), num2 (double) - Hai số cần tính tổng
     * Output: String - Kết quả tổng của hai số
     */
    public ResponseEntity<String> sum(@RequestParam double num1, @RequestParam double num2) {
        double result = level1Service.sum(num1, num2);
        return ResponseEntity.ok("The sum of " + num1 + " and " + num2 + " is: " + result);
    }

    @GetMapping("/length")
    /**
     * Tính độ dài của một chuỗi
     * Input: input (String) - Chuỗi cần tính độ dài
     * Output: String - Độ dài của chuỗi
     */
    public ResponseEntity<String> getStringLength(@RequestParam String input) {
        int length = level1Service.getStringLength(input);
        return ResponseEntity.ok("The length of the string \"" + input + "\" is: " + length);
    }

    @GetMapping("/square")
    /**
     * Tính bình phương của một số
     * Input: number (double) - Số cần tính bình phương
     * Output: String - Bình phương của số
     */
    public ResponseEntity<String> getSquare(@RequestParam double number) {
        double square = level1Service.getSquare(number);
        return ResponseEntity.ok("The square of " + number + " is: " + square);
    }

    @PostMapping("/max-number")
    /**
     * Tìm số lớn nhất trong danh sách
     * Input: List<Double> - Danh sách các số
     * Output: String - Số lớn nhất trong danh sách
     */
    public ResponseEntity<String> findLargest(@RequestBody List<Double> numbers) {
        try {
            double max = level1Service.findLargest(numbers);
            return ResponseEntity.ok("The largest number is: " + max);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/shortest-string")
    /**
     * Tìm chuỗi ngắn nhất trong danh sách
     * Input: List<String> - Danh sách các chuỗi
     * Output: String - Chuỗi ngắn nhất trong danh sách
     */
    public ResponseEntity<String> findShortest(@RequestBody List<String> strings) {
        try {
            String shortest = level1Service.findShortest(strings);
            return ResponseEntity.ok("The shortest string is: \"" + shortest + "\"");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-numbers")
    /**
     * Sắp xếp danh sách số theo thứ tự tăng dần
     * Input: List<Double> - Danh sách số cần sắp xếp
     * Output: List<Double> - Danh sách số đã được sắp xếp
     */
    public ResponseEntity<List<Double>> sortNumbers(@RequestBody List<Double> numbers) {
        try {
            List<Double> sorted = level1Service.sortNumbers(numbers);
            return ResponseEntity.ok(sorted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sort-strings")
    /**
     * Sắp xếp danh sách chuỗi theo thứ tự alphabet
     * Input: List<String> - Danh sách chuỗi cần sắp xếp
     * Output: List<String> - Danh sách chuỗi đã được sắp xếp
     */
    public ResponseEntity<List<String>> sortStrings(@RequestBody List<String> strings) {
        try {
            List<String> sorted = level1Service.sortStrings(strings);
            return ResponseEntity.ok(sorted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/median")
    /**
     * Tìm giá trị trung vị của danh sách số
     * Input: List<Double> - Danh sách số
     * Output: String - Giá trị trung vị của danh sách
     */
    public ResponseEntity<String> findMedian(@RequestBody List<Double> numbers) {
        try {
            double median = level1Service.findMedian(numbers);
            return ResponseEntity.ok("Median is: " + median);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/count-words")
    /**
     * Đếm số từ trong một chuỗi
     * Input: String - Chuỗi cần đếm từ
     * Output: String - Số lượng từ trong chuỗi
     */
    public ResponseEntity<String> countWords(@RequestBody String input) {
        int wordCount = level1Service.countWords(input);
        return ResponseEntity.ok("Number of words: " + wordCount);
    }

    @PostMapping("/count-strings-with-a")
    /**
     * Đếm số chuỗi chứa ký tự 'a' trong danh sách
     * Input: List<String> - Danh sách chuỗi
     * Output: String - Số chuỗi chứa ký tự 'a'
     */
    public ResponseEntity<String> countStringsWithA(@RequestBody List<String> strings) {
        try {
            long count = level1Service.countStringsWithA(strings);
            return ResponseEntity.ok("Strings containing 'a': " + count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
