package com.example.authdemo.controller;

import com.example.authdemo.dto.MapKeyRequest;
import com.example.authdemo.dto.TemplateRequest;
import com.example.authdemo.service.Level5Service;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/level5")
@SecurityRequirement(name = "bearerAuth")
public class Level5Controller {

    private final Level5Service level5Service;

    public Level5Controller(Level5Service level5Service) {
        this.level5Service = level5Service;
    }

    @PostMapping("/reverse")
    public ResponseEntity<List<Integer>> reverseList(@RequestBody List<Integer> list) {
        List<Integer> reversedList = level5Service.reverseList(list);
        return ResponseEntity.ok(reversedList);
    }

    @PostMapping("/chunk-integers")
    public ResponseEntity<List<List<Integer>>> chunkIntegers(@RequestBody List<Integer> list, @RequestParam int size) {
        List<List<Integer>> chunkedList = level5Service.chunk(list, size);
        return ResponseEntity.ok(chunkedList);
    }
    
    @PostMapping("/chunk-strings")
    public ResponseEntity<List<List<String>>> chunkStrings(@RequestBody List<String> list, @RequestParam int size) {
        List<List<String>> chunkedList = level5Service.chunk(list, size);
        return ResponseEntity.ok(chunkedList);
    }

    @PostMapping("/uniq-integers")
    public ResponseEntity<List<Integer>> uniqIntegers(@RequestBody List<Integer> list) {
        List<Integer> uniqueList = level5Service.uniq(list);
        return ResponseEntity.ok(uniqueList);
    }

    @PostMapping("/uniq-objects")
    public ResponseEntity<List<Map<String, Object>>> uniqObjects(@RequestBody List<Map<String, Object>> list) {
        List<Map<String, Object>> uniqueList = level5Service.uniqObjects(list);
        return ResponseEntity.ok(uniqueList);
    }

    @PostMapping("/group-by")
    public ResponseEntity<Map<Object, List<Map<String, Object>>>> groupBy(
            @RequestBody List<Map<String, Object>> list,
            @RequestParam String field) {
        Map<Object, List<Map<String, Object>>> groupedMap = level5Service.groupBy(list, field);
        return ResponseEntity.ok(groupedMap);
    }
    
    @PostMapping("/trim-all")
    public ResponseEntity<String> trimAll(@RequestBody String input) {
        return ResponseEntity.ok(level5Service.trimAll(input));
    }

    @PostMapping("/map-key")
    public ResponseEntity<List<Map<String, Object>>> mapKey(@RequestBody MapKeyRequest request) {
        List<Map<String, Object>> result = level5Service.mapKey(request.keys, request.collections);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/switch-order")
    public ResponseEntity<List<Map<String, Object>>> switchOrder(
            @RequestBody List<Map<String, Object>> list,
            @RequestParam int targetId,
            @RequestParam int newOrder) {
        List<Map<String, Object>> result = level5Service.switchOrder(list, targetId, newOrder);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sum-all")
    public ResponseEntity<Map<String, Integer>> sumAll(@RequestBody List<Map<String, Object>> list) {
        Map<String, Integer> result = level5Service.sumAll(list);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate-from-template")
    public ResponseEntity<String> generateFromTemplate(@RequestBody TemplateRequest request) {
        String result = level5Service.generateFromTemplate(request.template, request.params);
        return ResponseEntity.ok(result);
    }
} 