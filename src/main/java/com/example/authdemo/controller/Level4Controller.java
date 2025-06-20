package com.example.authdemo.controller;

import com.example.authdemo.service.Level4Service;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level4")
@SecurityRequirement(name = "bearerAuth")
public class Level4Controller {

    private final Level4Service level4Service;

    public Level4Controller(Level4Service level4Service) {
        this.level4Service = level4Service;
    }

    @PostMapping("/bubble-sort-moves")
    public ResponseEntity<String> countBubbleSortMoves(@RequestBody List<Integer> numbers) {
        try {
            int moves = level4Service.countBubbleSortIterations(numbers);
            return ResponseEntity.ok("Minimum moves to sort using bubble sort: " + moves);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/subsequence-sum-count")
    public ResponseEntity<String> countSubsequencesWithSum(
            @RequestBody List<Integer> numbers,
            @RequestParam int target) {
        try {
            int count = level4Service.countSubsequencesWithSum(numbers, target);
            return ResponseEntity.ok("Number of distinct subsequences summing to " + target + ": " + count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/longest-common-substring-length")
    public ResponseEntity<String> findLongestCommonSubstringLength(@RequestBody List<String> strings) {
        try {
            int length = level4Service.findLongestCommonSubstringLength(strings);
            return ResponseEntity.ok("Length of the longest common substring: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-sum-no-consecutive")
    public ResponseEntity<String> findMaxSumNoConsecutive(@RequestBody List<Integer> numbers) {
        try {
            int maxSum = level4Service.findMaxSumNoConsecutive(numbers);
            return ResponseEntity.ok("Maximum sum of non-consecutive elements: " + maxSum);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-product-of-three")
    public ResponseEntity<String> maxProductOfThree(@RequestBody List<Integer> numbers) {
        try {
            int maxProduct = level4Service.maxProductOfThree(numbers);
            return ResponseEntity.ok("Maximum product of three distinct elements: " + maxProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-by-distinct-words")
    public ResponseEntity<?> sortStringsByDistinctWords(@RequestBody List<String> strings) {
        try {
            List<String> sortedList = level4Service.sortStringsByDistinctWords(strings);
            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/smallest-missing-sum-no-consecutive")
    public ResponseEntity<String> findSmallestMissingSumNoConsecutive(@RequestBody List<Integer> numbers) {
        try {
            int result = level4Service.findSmallestMissingSumNoConsecutive(numbers);
            return ResponseEntity.ok("Smallest missing sum with no consecutive elements in subset: " + result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/lis-diff-one")
    public ResponseEntity<String> longestIncreasingSubsequenceDiffOne(@RequestBody List<Integer> numbers) {
        try {
            int length = level4Service.longestIncreasingSubsequenceDiffOne(numbers);
            return ResponseEntity.ok("Length of LIS with diff <= 1: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/largest-substring-overlap")
    public ResponseEntity<?> findLargestSubstringOverlap(
            @RequestBody List<String> strings,
            @RequestParam int k) {
        try {
            List<String> result = level4Service.findLargestSubstringOverlap(strings, k);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
