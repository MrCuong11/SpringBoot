package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Level1Service {

    public double sum(double num1, double num2) {
        return num1 + num2;
    }

    public int getStringLength(String input) {
        return input.length();
    }

    public double getSquare(double number) {
        return number * number;
    }

    public double findLargest(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        double max = numbers.get(0);
        for (double num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public String findShortest(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        String shortest = strings.get(0);
        for (String str : strings) {
            if (str.length() < shortest.length()) {
                shortest = str;
            }
        }
        return shortest;
    }

    public List<Double> sortNumbers(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        List<Double> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        return sorted;
    }

    public List<String> sortStrings(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        List<String> sorted = new ArrayList<>(strings);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }

    public double findMedian(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        List<Double> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        int n = sorted.size();
        if (n % 2 == 0) {
            return (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
        } else {
            return sorted.get(n / 2);
        }
    }

    public int countWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        return input.trim().split("\\s+").length;
    }

    public long countStringsWithA(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        return strings.stream()
                .filter(s -> s.toLowerCase().contains("a"))
                .count();
    }
} 