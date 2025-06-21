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
    /**
     * Đếm số lần swap tối thiểu cần thiết để sắp xếp danh sách bằng bubble sort
     * Input: List<Integer> - Danh sách các số nguyên cần sắp xếp
     * Output: String - Số lần swap tối thiểu để sắp xếp
     */
    public ResponseEntity<String> countBubbleSortMoves(@RequestBody List<Integer> numbers) {
        try {
            int moves = level4Service.countBubbleSortIterations(numbers);
            return ResponseEntity.ok("Minimum moves to sort using bubble sort: " + moves);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/subsequence-sum-count")
    /**
     * Đếm số dãy con phân biệt có tổng bằng giá trị mục tiêu
     * Input: List<Integer> - Danh sách số, target (int) - Giá trị mục tiêu
     * Output: String - Số dãy con phân biệt có tổng bằng target
     */
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
    /**
     * Tìm độ dài của chuỗi con chung dài nhất giữa tất cả các chuỗi trong danh sách
     * Input: List<String> - Danh sách các chuỗi
     * Output: String - Độ dài của chuỗi con chung dài nhất
     */
    public ResponseEntity<String> findLongestCommonSubstringLength(@RequestBody List<String> strings) {
        try {
            int length = level4Service.findLongestCommonSubstringLength(strings);
            return ResponseEntity.ok("Length of the longest common substring: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-sum-no-consecutive")
    /**
     * Tìm tổng lớn nhất của các phần tử không liên tiếp (không có 2 phần tử kề nhau)
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Tổng lớn nhất của các phần tử không liên tiếp
     */
    public ResponseEntity<String> findMaxSumNoConsecutive(@RequestBody List<Integer> numbers) {
        try {
            int maxSum = level4Service.findMaxSumNoConsecutive(numbers);
            return ResponseEntity.ok("Maximum sum of non-consecutive elements: " + maxSum);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-product-of-three")
    /**
     * Tìm tích lớn nhất của ba phần tử phân biệt trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Tích lớn nhất của ba phần tử phân biệt
     */
    public ResponseEntity<String> maxProductOfThree(@RequestBody List<Integer> numbers) {
        try {
            int maxProduct = level4Service.maxProductOfThree(numbers);
            return ResponseEntity.ok("Maximum product of three distinct elements: " + maxProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-by-distinct-words")
    /**
     * Sắp xếp danh sách chuỗi theo số lượng từ duy nhất (tăng dần)
     * Input: List<String> - Danh sách các chuỗi
     * Output: List<String> - Danh sách chuỗi đã sắp xếp theo số từ duy nhất
     */
    public ResponseEntity<?> sortStringsByDistinctWords(@RequestBody List<String> strings) {
        try {
            List<String> sortedList = level4Service.sortStringsByDistinctWords(strings);
            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/smallest-missing-sum-no-consecutive")
    /**
     * Tìm tổng nhỏ nhất không thể tạo thành từ các tập con không có phần tử liên tiếp
     * Input: List<Integer> - Danh sách các số nguyên dương
     * Output: String - Tổng nhỏ nhất không thể tạo thành
     */
    public ResponseEntity<String> findSmallestMissingSumNoConsecutive(@RequestBody List<Integer> numbers) {
        try {
            int result = level4Service.findSmallestMissingSumNoConsecutive(numbers);
            return ResponseEntity.ok("Smallest missing sum with no consecutive elements in subset: " + result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/lis-diff-one")
    /**
     * Tìm độ dài của dãy con tăng dài nhất với hiệu số giữa các phần tử liên tiếp <= 1
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Độ dài của dãy con tăng với hiệu số <= 1
     */
    public ResponseEntity<String> longestIncreasingSubsequenceDiffOne(@RequestBody List<Integer> numbers) {
        try {
            int length = level4Service.longestIncreasingSubsequenceDiffOne(numbers);
            return ResponseEntity.ok("Length of LIS with diff <= 1: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/largest-substring-overlap")
    /**
     * Tìm k chuỗi có phần giao chuỗi con lớn nhất
     * Input: List<String> - Danh sách chuỗi, k (int) - Số chuỗi cần tìm
     * Output: List<String> - K chuỗi có phần giao chuỗi con lớn nhất
     */
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
