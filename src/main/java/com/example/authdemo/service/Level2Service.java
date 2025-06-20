package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class Level2Service {

    public Integer findSecondLargest(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 numbers");
        }
        
        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);
        int largest = nums[nums.length - 1];

        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < largest) {
                return nums[i];
            }
        }
        return null;
    }

    public String findLongestWord(List<String> words) {
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        String longestWord = words.get(0);
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord;
    }

    public String findLongestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (s1.charAt(i) == s2.charAt(j))
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                else
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);

        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.append(s1.charAt(i - 1));
                i--; j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) i--;
            else j--;
        }
        return lcs.reverse().toString();
    }

    public int sumDivisibleBy3And5(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("List must not be null");
        }

        return numbers.stream()
                .filter(num -> num % 3 == 0 && num % 5 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int findMaxSubarraySum(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        int maxSoFar = numbers.get(0);
        int currentMax = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            int num = numbers.get(i);
            currentMax = Math.max(num, currentMax + num);
            maxSoFar = Math.max(maxSoFar, currentMax);
        }

        return maxSoFar;
    }
} 