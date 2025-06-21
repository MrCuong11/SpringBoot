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
    /**
     * Tìm số nhỏ thứ hai trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Số nhỏ thứ hai trong danh sách
     */
    public ResponseEntity<String> findSecondSmallest(@RequestBody List<Integer> numbers) {
        try {
            Integer secondSmallest = level3Service.findSecondSmallest(numbers);
            return ResponseEntity.ok("The second smallest number is: " + secondSmallest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/max-difference")
    /**
     * Tìm hiệu số lớn nhất giữa hai phần tử bất kỳ trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Hiệu số lớn nhất giữa hai phần tử
     */
    public ResponseEntity<String> findMaxDifference(@RequestBody List<Integer> numbers) {
        try {
            int maxDiff = level3Service.findMaxDifference(numbers);
            return ResponseEntity.ok("The maximum difference between any two elements is: " + maxDiff);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-increasing-subsequence")
    /**
     * Tìm độ dài của dãy con tăng dài nhất (LIS algorithm)
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Độ dài của dãy con tăng dài nhất
     */
    public ResponseEntity<String> findLongestIncreasingSubsequence(@RequestBody List<Integer> numbers) {
        try {
            int maxLength = level3Service.findLongestIncreasingSubsequence(numbers);
            return ResponseEntity.ok("The length of the longest increasing subsequence is: " + maxLength);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/smallest-unreachable-sum")
    /**
     * Tìm số nguyên dương nhỏ nhất không thể biểu diễn bằng tổng các số trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên dương
     * Output: String - Số nguyên dương nhỏ nhất không thể biểu diễn
     */
    public ResponseEntity<String> findSmallestUnreachableSum(@RequestBody List<Integer> numbers) {
        try {
            int result = level3Service.findSmallestUnreachableSum(numbers);
            return ResponseEntity.ok("The smallest positive integer that cannot be represented as sum: " + result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/median-of-combined-lists")
    /**
     * Tìm trung vị của hai danh sách đã được kết hợp và sắp xếp
     * Input: list1 (List<Integer>), list2 (List<Integer>) - Hai danh sách số nguyên
     * Output: String - Trung vị của danh sách kết hợp
     */
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
    /**
     * Đếm số cặp phân biệt có tổng bằng giá trị mục tiêu
     * Input: List<Integer> - Danh sách số, target (int) - Giá trị mục tiêu
     * Output: String - Số cặp phân biệt có tổng bằng target
     */
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
    /**
     * Tìm các chuỗi có phần giao lớn nhất
     * Input: List<String> - Danh sách các chuỗi
     * Output: List<String> - Danh sách các chuỗi có phần giao lớn nhất
     */
    public ResponseEntity<?> findLargestOverlap(@RequestBody List<String> strings) {
        try {
            List<String> result = level3Service.findLargestOverlap(strings);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/longest-palindrome-length")
    /**
     * Tìm độ dài của palindrome dài nhất có thể tạo thành từ chuỗi
     * Input: String - Chuỗi đầu vào
     * Output: String - Độ dài của palindrome dài nhất có thể tạo thành
     */
    public ResponseEntity<String> findLongestPalindromeLength(@RequestBody String s) {
        try {
            int length = level3Service.findLongestPalindromeLength(s);
            return ResponseEntity.ok("Length of the longest palindrome that can be formed: " + length);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/max-non-adjacent-sum")
    /**
     * Tìm tổng lớn nhất của các phần tử không liền kề
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: String - Tổng lớn nhất của các phần tử không liền kề
     */
    public ResponseEntity<String> findMaxNonAdjacentSum(@RequestBody List<Integer> numbers) {
        try {
            int maxSum = level3Service.findMaxNonAdjacentSum(numbers);
            return ResponseEntity.ok("Maximum sum of non-adjacent elements: " + maxSum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sort-by-unique-chars")
    /**
     * Sắp xếp danh sách chuỗi theo số lượng ký tự duy nhất (tăng dần)
     * Input: List<String> - Danh sách các chuỗi
     * Output: List<String> - Danh sách chuỗi đã sắp xếp theo số ký tự duy nhất
     */
    public ResponseEntity<?> sortStringsByUniqueChars(@RequestBody List<String> strings) {
        try {
            List<String> sortedList = level3Service.sortStringsByUniqueChars(strings);
            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
}
