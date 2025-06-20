package com.example.authdemo.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CollectionFrameworkService {

    // --- Bài 1: ArrayList Operations ---

    public int sumNumbers(List<Integer> numbers) {
        if (numbers == null) return 0;
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String, Integer> findMaxMin(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty.");
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("max", Collections.max(numbers));
        result.put("min", Collections.min(numbers));
        return result;
    }

    public List<Integer> removeElement(List<Integer> numbers, Integer elementToRemove) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> mutableList = new ArrayList<>(numbers);
        mutableList.remove(elementToRemove);
        return mutableList;
    }

    public boolean checkExistence(List<Integer> numbers, Integer elementToCheck) {
        if (numbers == null) return false;
        return numbers.contains(elementToCheck);
    }

    // --- Bài 2: HashSet Operations ---

    public Set<String> addCountries(List<String> existingCountries, List<String> newCountries) {
        Set<String> resultSet = new HashSet<>();
        if (existingCountries != null) {
            resultSet.addAll(existingCountries);
        }
        if (newCountries != null) {
            resultSet.addAll(newCountries);
        }
        return resultSet;
    }

    public boolean checkCountryExistence(Set<String> countries, String countryToCheck) {
        if (countries == null) return false;
        return countries.contains(countryToCheck);
    }

    public Set<String> removeCountry(Set<String> countries, String countryToRemove) {
        if (countries == null) return new HashSet<>();
        Set<String> mutableSet = new HashSet<>(countries);
        mutableSet.remove(countryToRemove);
        return mutableSet;
    }

    public int countCountries(Set<String> countries) {
        return countries == null ? 0 : countries.size();
    }

    // --- Bài 3: HashMap Operations ---
    
    public Set<String> getPeopleNames(Map<String, Integer> people) {
        if (people == null) return new HashSet<>();
        return people.keySet();
    }
    
    public Integer findAgeByName(Map<String, Integer> people, String name) {
        if (people == null) return null;
        return people.get(name); // Returns null if not found
    }

    public Map<String, Integer> removePerson(Map<String, Integer> people, String name) {
        if (people == null) return new HashMap<>();
        Map<String, Integer> mutableMap = new HashMap<>(people);
        mutableMap.remove(name);
        return mutableMap;
    }

    public boolean checkPersonExists(Map<String, Integer> people, String name) {
        if (people == null) return false;
        return people.containsKey(name);
    }

    // --- Bài 4: Sorting ---
    public List<Integer> sortAscending(List<Integer> numbers) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        return sorted;
    }

    public List<Integer> sortDescending(List<Integer> numbers) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> sorted = new ArrayList<>(numbers);
        sorted.sort(Collections.reverseOrder());
        return sorted;
    }

    // --- Bài 5: Two Sum ---
    public List<Integer> twoSum(List<Integer> nums, int target) {
        if (nums == null) return new ArrayList<>();
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            int complement = target - nums.get(i);
            if (numMap.containsKey(complement)) {
                return Arrays.asList(numMap.get(complement), i);
            }
            numMap.put(nums.get(i), i);
        }
        return new ArrayList<>(); // Return empty list if no solution is found
    }

    // --- Bài 6: Max Profit (Stock) ---
    public int maxProfit(List<Integer> prices) {
        if (prices == null || prices.size() < 2) return 0;
        
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }

    // --- Bài 7: Student Averages ---
    public Map<String, Double> calculateAverages(Map<String, List<Integer>> scores) {
        if (scores == null) return new HashMap<>();
        
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : scores.entrySet()) {
            String student = entry.getKey();
            List<Integer> marks = entry.getValue();
            
            double average = marks.stream()
                                  .mapToInt(Integer::intValue)
                                  .average()
                                  .orElse(0.0);
            averages.put(student, average);
        }
        return averages;
    }

    // --- Bài 8: Product Management ---
    public Map<String, String> addOrUpdateProduct(Map<String, String> products, String code, String info) {
        Map<String, String> mutableProducts = new HashMap<>(products);
        mutableProducts.put(code, info);
        return mutableProducts;
    }

    public String findProduct(Map<String, String> products, String code) {
        return products.get(code); // Returns null if not found
    }

    public Map<String, String> removeProduct(Map<String, String> products, String code) {
        Map<String, String> mutableProducts = new HashMap<>(products);
        mutableProducts.remove(code);
        return mutableProducts;
    }

    // --- Bài 9: Word Count ---
    public Map<String, Integer> countWords(String text) {
        if (text == null || text.isEmpty()) {
            return new HashMap<>();
        }
        String[] words = text.toLowerCase()
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .split("\\s+");
        
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        return map;
    }

    // --- Bài 10: Score Distribution ---
    public Map<String, Integer> calculateScoreDistribution(List<Double> scores) {
        Map<String, Integer> stats = new LinkedHashMap<>(); // Use LinkedHashMap to preserve order
        stats.put("Cao", 0);
        stats.put("Trung bình", 0);
        stats.put("Trượt", 0);

        if (scores == null) return stats;

        for (double score : scores) {
            if (score >= 8.0) {
                stats.put("Cao", stats.get("Cao") + 1);
            } else if (score >= 5.0) {
                stats.put("Trung bình", stats.get("Trung bình") + 1);
            } else {
                stats.put("Trượt", stats.get("Trượt") + 1);
            }
        }
        return stats;
    }
    
    // --- Bài 11: Dictionary ---
    public Map<String, String> addWordToDictionary(Map<String, String> dict, String word, String definition) {
        Map<String, String> mutableDict = new HashMap<>(dict);
        mutableDict.put(word, definition);
        return mutableDict;
    }

    public String lookupWord(Map<String, String> dict, String word) {
        return dict.get(word);
    }
    
    // --- Bài 12: Create Product Object ---
    public Map<String, Object> createProduct(Map<String, Object> productDetails) {
        // This service method primarily validates or defaults fields if necessary.
        // For now, it just returns the map as is.
        return new HashMap<>(productDetails);
    }

    // --- Bài 13: Find Duplicates ---
    public Set<Integer> findDuplicates(List<Integer> numbers) {
        if (numbers == null) return new HashSet<>();
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (int num : numbers) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        return duplicates;
    }

    // --- Bài 14: Set Intersection ---
    public Set<Integer> findIntersection(Set<Integer> set1, Set<Integer> set2) {
        if (set1 == null || set2 == null) return new HashSet<>();
        Set<Integer> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    // --- Bài 15: Set Union ---
    public Set<Integer> findUnion(Set<Integer> set1, Set<Integer> set2) {
        if (set1 == null && set2 == null) return new HashSet<>();
        Set<Integer> union = new HashSet<>(set1 != null ? set1 : new HashSet<>());
        if (set2 != null) {
            union.addAll(set2);
        }
        return union;
    }

    // --- Bài 16: Set Min/Max ---
    public Map<String, Integer> findSetMinMax(Set<Integer> set) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be null or empty.");
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("min", Collections.min(set));
        result.put("max", Collections.max(set));
        return result;
    }
} 