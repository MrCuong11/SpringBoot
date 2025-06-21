package com.example.authdemo.controller;

import com.example.authdemo.service.Level2Service;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level2")
@SecurityRequirement(name = "bearerAuth")
public class Level2Controller {

    private final Level2Service level2Service;

    public Level2Controller(Level2Service level2Service) {
        this.level2Service = level2Service;
    }

    @PostMapping("/second-largest")
    /**
     * Tìm số lớn thứ hai trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Số lớn thứ hai hoặc thông báo nếu tất cả số bằng nhau
     */
    public ResponseEntity<String> findSecondLargest(@RequestBody List<Integer> numbers) {
        try {
            Integer secondLargest = level2Service.findSecondLargest(numbers);
            if (secondLargest == null) {
                return ResponseEntity.ok("No second largest number found (all numbers are equal)");
            }
            return ResponseEntity.ok("The second largest number is: " + secondLargest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-word")
    /**
     * Tìm từ dài nhất trong danh sách từ
     * Input: List<String> - Danh sách các từ
     * Output: String - Từ dài nhất trong danh sách
     */
    public ResponseEntity<String> findLongestWord(@RequestBody List<String> words) {
        try {
            String longestWord = level2Service.findLongestWord(words);
            return ResponseEntity.ok("The longest word is: \"" + longestWord + "\"");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-common-subsequence")
    /**
     * Tìm chuỗi con chung dài nhất giữa hai chuỗi
     * Input: string1 (String), string2 (String) - Hai chuỗi cần tìm chuỗi con chung
     * Output: String - Chuỗi con chung dài nhất
     */
    public ResponseEntity<String> findLongestCommonSubsequence(
            @RequestParam String string1,
            @RequestParam String string2) {
        try {
            String lcs = level2Service.findLongestCommonSubsequence(string1, string2);
            return ResponseEntity.ok("The longest common subsequence is: \"" + lcs + "\"");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sum-divisible-by-3-and-5")
    /**
     * Tính tổng các số chia hết cho cả 3 và 5
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Tổng các số chia hết cho cả 3 và 5
     */
    public ResponseEntity<String> sumDivisibleBy3And5(@RequestBody List<Integer> numbers) {
        try {
            int sum = level2Service.sumDivisibleBy3And5(numbers);
            return ResponseEntity.ok("Sum of numbers divisible by both 3 and 5: " + sum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/max-subarray-sum")
    /**
     * Tìm tổng lớn nhất của một dãy con liên tiếp (Kadane's algorithm)
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Tổng lớn nhất của dãy con liên tiếp
     */
    public ResponseEntity<String> findMaxSubarraySum(@RequestBody List<Integer> numbers) {
        try {
            int maxSum = level2Service.findMaxSubarraySum(numbers);
            return ResponseEntity.ok("Maximum sum of any contiguous subarray: " + maxSum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
