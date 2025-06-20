package com.example.authdemo.controller;

import com.example.authdemo.service.Level3Service;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level3")
@SecurityRequirement(name = "bearerAuth")
public class Level3Controller {

    private final Level3Service level3Service;

    public Level3Controller(Level3Service level3Service) {
        this.level3Service = level3Service;
    }

    @PostMapping("/second-smallest")
    public ResponseEntity<String> findSecondSmallest(@RequestBody List<Integer> numbers) {
        try {
            Integer secondSmallest = level3Service.findSecondSmallest(numbers);
            return ResponseEntity.ok("The second smallest number is: " + secondSmallest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/max-difference")
    public ResponseEntity<String> findMaxDifference(@RequestBody List<Integer> numbers) {
        try {
            int maxDiff = level3Service.findMaxDifference(numbers);
            return ResponseEntity.ok("The maximum difference between any two elements is: " + maxDiff);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-increasing-subsequence")
    public ResponseEntity<String> findLongestIncreasingSubsequence(@RequestBody List<Integer> numbers) {
        try {
            int maxLength = level3Service.findLongestIncreasingSubsequence(numbers);
            return ResponseEntity.ok("The length of the longest increasing subsequence is: " + maxLength);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/smallest-unreachable-sum")
    public ResponseEntity<String> findSmallestUnreachableSum(@RequestBody List<Integer> numbers) {
        try {
            int result = level3Service.findSmallestUnreachableSum(numbers);
            return ResponseEntity.ok("The smallest positive integer that cannot be represented as sum: " + result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/median-of-combined-lists")
    public ResponseEntity<String> findMedianOfCombinedLists(
            @RequestParam List<Integer> list1,
            @RequestParam List<Integer> list2) {
        try {
            double median = level3Service.findMedianOfCombinedLists(list1, list2);
            return ResponseEntity.ok("The median of the combined lists is: " + median);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/distinct-pairs-count")
    public ResponseEntity<String> findDistinctPairsCount(
            @RequestBody List<Integer> numbers,
            @RequestParam int target) {
        try {
            int count = level3Service.findDistinctPairsCount(numbers, target);
            return ResponseEntity.ok("Number of distinct pairs that sum to " + target + ": " + count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/largest-overlap")
    public ResponseEntity<?> findLargestOverlap(@RequestBody List<String> strings) {
        try {
            List<String> result = level3Service.findLargestOverlap(strings);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-palindrome-length")
    public ResponseEntity<String> findLongestPalindromeLength(@RequestBody String s) {
        try {
            int length = level3Service.findLongestPalindromeLength(s);
            return ResponseEntity.ok("Length of the longest palindrome that can be formed: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-non-adjacent-sum")
    public ResponseEntity<String> findMaxNonAdjacentSum(@RequestBody List<Integer> numbers) {
        try {
            int maxSum = level3Service.findMaxNonAdjacentSum(numbers);
            return ResponseEntity.ok("Maximum sum of non-adjacent elements: " + maxSum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-by-unique-chars")
    public ResponseEntity<?> sortStringsByUniqueChars(@RequestBody List<String> strings) {
        try {
            List<String> sortedList = level3Service.sortStringsByUniqueChars(strings);
            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
}
