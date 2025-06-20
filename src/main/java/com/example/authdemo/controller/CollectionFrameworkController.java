package com.example.authdemo.controller;

import com.example.authdemo.dto.AddCountriesRequest;
import com.example.authdemo.dto.DictionaryRequest;
import com.example.authdemo.dto.ProductRequest;
import com.example.authdemo.dto.SetOperationRequest;
import com.example.authdemo.service.CollectionFrameworkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/collections")
@SecurityRequirement(name = "bearerAuth")
public class CollectionFrameworkController {

    private final CollectionFrameworkService collectionService;

    public CollectionFrameworkController(CollectionFrameworkService collectionService) {
        this.collectionService = collectionService;
    }

    // --- ArrayList Endpoints ---

    @PostMapping("/sum")
    public ResponseEntity<Integer> sumNumbers(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sumNumbers(numbers));
    }

    @PostMapping("/max-min")
    public ResponseEntity<Map<String, Integer>> findMaxMin(@RequestBody List<Integer> numbers) {
        try {
            return ResponseEntity.ok(collectionService.findMaxMin(numbers));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/remove-element")
    public ResponseEntity<List<Integer>> removeElement(@RequestBody List<Integer> numbers, @RequestParam Integer element) {
        return ResponseEntity.ok(collectionService.removeElement(numbers, element));
    }

    @PostMapping("/check-existence")
    public ResponseEntity<Boolean> checkExistence(@RequestBody List<Integer> numbers, @RequestParam Integer element) {
        return ResponseEntity.ok(collectionService.checkExistence(numbers, element));
    }

    // --- HashSet Endpoints ---

    @PostMapping("/countries/add")
    public ResponseEntity<Set<String>> addCountries(@RequestBody AddCountriesRequest request) {
        return ResponseEntity.ok(collectionService.addCountries(request.existingCountries, request.newCountries));
    }

    @PostMapping("/countries/check")
    public ResponseEntity<Boolean> checkCountryExistence(@RequestBody Set<String> countries, @RequestParam String country) {
        return ResponseEntity.ok(collectionService.checkCountryExistence(countries, country));
    }

    @PostMapping("/countries/remove")
    public ResponseEntity<Set<String>> removeCountry(@RequestBody Set<String> countries, @RequestParam String country) {
        return ResponseEntity.ok(collectionService.removeCountry(countries, country));
    }
    
    @PostMapping("/countries/count")
    public ResponseEntity<Integer> countCountries(@RequestBody Set<String> countries) {
        return ResponseEntity.ok(collectionService.countCountries(countries));
    }
    
    // --- HashMap Endpoints ---

    @PostMapping("/people/names")
    public ResponseEntity<Set<String>> getPeopleNames(@RequestBody Map<String, Integer> people) {
        return ResponseEntity.ok(collectionService.getPeopleNames(people));
    }

    @PostMapping("/people/find-age")
    public ResponseEntity<Integer> findAgeByName(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        Integer age = collectionService.findAgeByName(people, name);
        if (age != null) {
            return ResponseEntity.ok(age);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/people/remove")
    public ResponseEntity<Map<String, Integer>> removePerson(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        return ResponseEntity.ok(collectionService.removePerson(people, name));
    }

    @PostMapping("/people/check-person")
    public ResponseEntity<Boolean> checkPersonExists(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        return ResponseEntity.ok(collectionService.checkPersonExists(people, name));
    }

    // --- More Endpoints ---

    @PostMapping("/sort-ascending")
    public ResponseEntity<List<Integer>> sortAscending(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sortAscending(numbers));
    }

    @PostMapping("/sort-descending")
    public ResponseEntity<List<Integer>> sortDescending(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sortDescending(numbers));
    }

    @PostMapping("/two-sum")
    public ResponseEntity<List<Integer>> twoSum(@RequestBody List<Integer> numbers, @RequestParam int target) {
        List<Integer> result = collectionService.twoSum(numbers, target);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/max-profit")
    public ResponseEntity<Integer> maxProfit(@RequestBody List<Integer> prices) {
        return ResponseEntity.ok(collectionService.maxProfit(prices));
    }

    @PostMapping("/student-averages")
    public ResponseEntity<Map<String, Double>> calculateAverages(@RequestBody Map<String, List<Integer>> scores) {
        return ResponseEntity.ok(collectionService.calculateAverages(scores));
    }

    // --- Product Management Endpoints (Bài 8) ---
    @PostMapping("/products/add")
    public ResponseEntity<Map<String, String>> addOrUpdateProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(collectionService.addOrUpdateProduct(request.products, request.code, request.info));
    }
    
    @PostMapping("/products/find")
    public ResponseEntity<String> findProduct(@RequestBody Map<String, String> products, @RequestParam String code) {
        String info = collectionService.findProduct(products, code);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }

    @PostMapping("/products/remove")
    public ResponseEntity<Map<String, String>> removeProduct(@RequestBody Map<String, String> products, @RequestParam String code) {
        return ResponseEntity.ok(collectionService.removeProduct(products, code));
    }
    
    // --- Word Count Endpoint (Bài 9) ---
    @PostMapping("/words/count")
    public ResponseEntity<Map<String, Integer>> countWords(@RequestBody String text) {
        return ResponseEntity.ok(collectionService.countWords(text));
    }

    // --- Score Distribution Endpoint (Bài 10) ---
    @PostMapping("/scores/distribution")
    public ResponseEntity<Map<String, Integer>> getScoreDistribution(@RequestBody List<Double> scores) {
        return ResponseEntity.ok(collectionService.calculateScoreDistribution(scores));
    }

    // --- Dictionary Endpoints (Bài 11) ---
    @PostMapping("/dictionary/add")
    public ResponseEntity<Map<String, String>> addWord(@RequestBody DictionaryRequest request) {
        return ResponseEntity.ok(collectionService.addWordToDictionary(request.dictionary, request.word, request.definition));
    }
    
    @PostMapping("/dictionary/lookup")
    public ResponseEntity<String> lookupWord(@RequestBody Map<String, String> dictionary, @RequestParam String word) {
        String definition = collectionService.lookupWord(dictionary, word);
        return definition != null ? ResponseEntity.ok(definition) : ResponseEntity.notFound().build();
    }

    // --- Create Product Endpoint (Bài 12) ---
    @PostMapping("/products/create")
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> productDetails) {
        return ResponseEntity.ok(collectionService.createProduct(productDetails));
    }
    
    // --- Set Operations Endpoints (Bài 13, 14, 15, 16) ---
    @PostMapping("/sets/duplicates")
    public ResponseEntity<Set<Integer>> findDuplicates(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.findDuplicates(numbers));
    }
    
    @PostMapping("/sets/intersection")
    public ResponseEntity<Set<Integer>> findIntersection(@RequestBody SetOperationRequest request) {
        return ResponseEntity.ok(collectionService.findIntersection(request.set1, request.set2));
    }
    
    @PostMapping("/sets/union")
    public ResponseEntity<Set<Integer>> findUnion(@RequestBody SetOperationRequest request) {
        return ResponseEntity.ok(collectionService.findUnion(request.set1, request.set2));
    }
    
    @PostMapping("/sets/min-max")
    public ResponseEntity<Map<String, Integer>> findSetMinMax(@RequestBody Set<Integer> set) {
        try {
            return ResponseEntity.ok(collectionService.findSetMinMax(set));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
} 