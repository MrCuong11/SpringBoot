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
    /**
     * Đảo ngược thứ tự các phần tử trong danh sách số nguyên
     * Input: List<Integer> - Danh sách số nguyên cần đảo ngược
     * Output: List<Integer> - Danh sách đã được đảo ngược thứ tự
     */
    public ResponseEntity<List<Integer>> reverseList(@RequestBody List<Integer> list) {
        List<Integer> reversedList = level5Service.reverseList(list);
        return ResponseEntity.ok(reversedList);
    }

    @PostMapping("/chunk-integers")
    /**
     * Chia danh sách số nguyên thành các nhóm nhỏ có kích thước cố định
     * Input: List<Integer> - Danh sách số nguyên, size (int) - Kích thước mỗi nhóm
     * Output: List<List<Integer>> - Danh sách các nhóm số nguyên
     */
    public ResponseEntity<List<List<Integer>>> chunkIntegers(@RequestBody List<Integer> list, @RequestParam int size) {
        List<List<Integer>> chunkedList = level5Service.chunk(list, size);
        return ResponseEntity.ok(chunkedList);
    }
    
    @PostMapping("/chunk-strings")
    /**
     * Chia danh sách chuỗi thành các nhóm nhỏ có kích thước cố định
     * Input: List<String> - Danh sách chuỗi, size (int) - Kích thước mỗi nhóm
     * Output: List<List<String>> - Danh sách các nhóm chuỗi
     */
    public ResponseEntity<List<List<String>>> chunkStrings(@RequestBody List<String> list, @RequestParam int size) {
        List<List<String>> chunkedList = level5Service.chunk(list, size);
        return ResponseEntity.ok(chunkedList);
    }

    @PostMapping("/uniq-integers")
    /**
     * Loại bỏ các phần tử trùng lặp trong danh sách số nguyên
     * Input: List<Integer> - Danh sách số nguyên có thể có trùng lặp
     * Output: List<Integer> - Danh sách số nguyên không có trùng lặp
     */
    public ResponseEntity<List<Integer>> uniqIntegers(@RequestBody List<Integer> list) {
        List<Integer> uniqueList = level5Service.uniq(list);
        return ResponseEntity.ok(uniqueList);
    }

    @PostMapping("/uniq-objects")
    /**
     * Loại bỏ các object trùng lặp trong danh sách Map
     * Input: List<Map<String, Object>> - Danh sách các object có thể có trùng lặp
     * Output: List<Map<String, Object>> - Danh sách object không có trùng lặp
     */
    public ResponseEntity<List<Map<String, Object>>> uniqObjects(@RequestBody List<Map<String, Object>> list) {
        List<Map<String, Object>> uniqueList = level5Service.uniqObjects(list);
        return ResponseEntity.ok(uniqueList);
    }

    @PostMapping("/group-by")
    /**
     * Nhóm danh sách object theo một trường cụ thể
     * Input: List<Map<String, Object>> - Danh sách object, field (String) - Trường để nhóm
     * Output: Map<Object, List<Map<String, Object>>> - Map với key là giá trị trường, value là danh sách object
     */
    public ResponseEntity<Map<Object, List<Map<String, Object>>>> groupBy(
            @RequestBody List<Map<String, Object>> list,
            @RequestParam String field) {
        Map<Object, List<Map<String, Object>>> groupedMap = level5Service.groupBy(list, field);
        return ResponseEntity.ok(groupedMap);
    }
    
    @PostMapping("/trim-all")
    /**
     * Loại bỏ khoảng trắng thừa ở đầu và cuối chuỗi
     * Input: String - Chuỗi có thể có khoảng trắng thừa
     * Output: String - Chuỗi đã được loại bỏ khoảng trắng thừa
     */
    public ResponseEntity<String> trimAll(@RequestBody String input) {
        return ResponseEntity.ok(level5Service.trimAll(input));
    }

    @PostMapping("/map-key")
    /**
     * Tạo danh sách object mới với key được chỉ định từ danh sách collection
     * Input: MapKeyRequest - Chứa keys và collections để map
     * Output: List<Map<String, Object>> - Danh sách object mới với key được map
     */
    public ResponseEntity<List<Map<String, Object>>> mapKey(@RequestBody MapKeyRequest request) {
        List<Map<String, Object>> result = level5Service.mapKey(request.keys, request.collections);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/switch-order")
    /**
     * Thay đổi thứ tự của một object trong danh sách theo ID và vị trí mới
     * Input: List<Map<String, Object>> - Danh sách object, targetId (int) - ID object cần di chuyển, newOrder (int) - Vị trí mới
     * Output: List<Map<String, Object>> - Danh sách object với thứ tự đã được thay đổi
     */
    public ResponseEntity<List<Map<String, Object>>> switchOrder(
            @RequestBody List<Map<String, Object>> list,
            @RequestParam int targetId,
            @RequestParam int newOrder) {
        List<Map<String, Object>> result = level5Service.switchOrder(list, targetId, newOrder);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sum-all")
    /**
     * Tính tổng tất cả các giá trị số trong danh sách object
     * Input: List<Map<String, Object>> - Danh sách object chứa các giá trị số
     * Output: Map<String, Integer> - Map với key là tên trường, value là tổng giá trị
     */
    public ResponseEntity<Map<String, Integer>> sumAll(@RequestBody List<Map<String, Object>> list) {
        Map<String, Integer> result = level5Service.sumAll(list);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate-from-template")
    /**
     * Tạo chuỗi từ template và các tham số được cung cấp
     * Input: TemplateRequest - Chứa template và params để generate
     * Output: String - Chuỗi được generate từ template
     */
    public ResponseEntity<String> generateFromTemplate(@RequestBody TemplateRequest request) {
        String result = level5Service.generateFromTemplate(request.template, request.params);
        return ResponseEntity.ok(result);
    }
} 