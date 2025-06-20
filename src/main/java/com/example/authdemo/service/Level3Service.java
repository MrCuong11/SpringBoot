package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class Level3Service {

    public Integer findSecondSmallest(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 numbers");
        }

        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);

        int secondSmallest = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > secondSmallest) {
                secondSmallest = nums[i];
                break;
            }
        }

        return secondSmallest;
    }

    public int findMaxDifference(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 numbers");
        }

        int max = numbers.get(0);
        int min = numbers.get(0);
        
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        return max - min;
    }

    public int findLongestIncreasingSubsequence(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        int n = numbers.size();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (numbers.get(i) > numbers.get(j) && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    if (dp[i] > maxLength) {
                        maxLength = dp[i];
                    }
                }
            }
        }

        return maxLength;
    }

    public int findSmallestUnreachableSum(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);
        
        int res = 1;
        for (int num : nums) {
            if (num > res) {
                break;
            }
            res += num;
        }

        return res;
    }

    public double findMedianOfCombinedLists(List<Integer> list1, List<Integer> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Lists must not be null");
        }

        int[] combined = new int[list1.size() + list2.size()];
        
        int index = 0;
        for (int num : list1) {
            combined[index++] = num;
        }
        for (int num : list2) {
            combined[index++] = num;
        }

        Arrays.sort(combined);

        int n = combined.length;
        if (n % 2 == 0) {
            return (combined[n / 2 - 1] + combined[n / 2]) / 2.0;
        } else {
            return combined[n / 2];
        }
    }

    public int findDistinctPairsCount(List<Integer> numbers, int target) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        Set<Integer> seen = new HashSet<>();
        Set<String> pairs = new HashSet<>();

        for (int num : numbers) {
            int complement = target - num;

            if (seen.contains(complement)) {
                int a = Math.min(num, complement);
                int b = Math.max(num, complement);
                pairs.add(a + "," + b);
            }

            seen.add(num);
        }

        return pairs.size();
    }

    public List<String> findLargestOverlap(List<String> strings) {
        if (strings == null || strings.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 strings");
        }

        String[] result = new String[2];
        int maxOverlap = -1;

        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                String str1 = strings.get(i);
                String str2 = strings.get(j);

                int overlap = calculateOverlap(str1, str2);

                if (overlap > maxOverlap) {
                    maxOverlap = overlap;
                    result[0] = str1;
                    result[1] = str2;
                }
            }
        }

        return Arrays.asList(result);
    }

    private int calculateOverlap(String str1, String str2) {
        Set<Character> set1 = new HashSet<>();
        for (char c : str1.toCharArray()) {
            set1.add(c);
        }
        Set<Character> set2 = new HashSet<>();
        for (char c : str2.toCharArray()) {
            set2.add(c);
        }

        set1.retainAll(set2);

        return set1.size();
    }

    public int findLongestPalindromeLength(String s) {
        if (s == null) {
            return 0;
        }
        
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        int length = 0;
        boolean hasOdd = false;

        for (int count : charCount.values()) {
            length += (count / 2) * 2;
            if (count % 2 == 1) {
                hasOdd = true;
            }
        }

        if (hasOdd) {
            length++;
        }

        return length;
    }

    public int findMaxNonAdjacentSum(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        int include = numbers.get(0);
        int exclude = 0;

        for (int i = 1; i < numbers.size(); i++) {
            int temp = include;
            include = numbers.get(i) + exclude;
            exclude = Math.max(temp, exclude);
        }

        return Math.max(include, exclude);
    }

    public List<String> sortStringsByUniqueChars(List<String> strings) {
        if (strings == null) {
            return new ArrayList<>();
        }

        List<String> sortedList = new ArrayList<>(strings);
        sortedList.sort(Comparator.comparingInt(this::countDistinctCharacters)
                .thenComparingInt(String::length));

        return sortedList;
    }

    private int countDistinctCharacters(String str) {
        Set<Character> distinctChars = new HashSet<>();
        for (char c : str.trim().toCharArray()) {
            distinctChars.add(c);
        }
        return distinctChars.size();
    }
}
