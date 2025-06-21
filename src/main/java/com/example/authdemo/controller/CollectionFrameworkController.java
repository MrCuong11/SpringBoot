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
    /**
     * Tính tổng tất cả các số trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: Integer - Tổng của tất cả các số
     */
    public ResponseEntity<Integer> sumNumbers(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sumNumbers(numbers));
    }

    @PostMapping("/max-min")
    /**
     * Tìm số lớn nhất và nhỏ nhất trong danh sách
     * Input: List<Integer> - Danh sách các số nguyên
     * Output: Map<String, Integer> - Map chứa "max" và "min"
     */
    public ResponseEntity<Map<String, Integer>> findMaxMin(@RequestBody List<Integer> numbers) {
        try {
            return ResponseEntity.ok(collectionService.findMaxMin(numbers));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/remove-element")
    /**
     * Loại bỏ một phần tử cụ thể khỏi danh sách
     * Input: List<Integer> - Danh sách số, element (Integer) - Phần tử cần loại bỏ
     * Output: List<Integer> - Danh sách sau khi loại bỏ phần tử
     */
    public ResponseEntity<List<Integer>> removeElement(@RequestBody List<Integer> numbers, @RequestParam Integer element) {
        return ResponseEntity.ok(collectionService.removeElement(numbers, element));
    }

    @PostMapping("/check-existence")
    /**
     * Kiểm tra xem một phần tử có tồn tại trong danh sách không
     * Input: List<Integer> - Danh sách số, element (Integer) - Phần tử cần kiểm tra
     * Output: Boolean - true nếu tồn tại, false nếu không
     */
    public ResponseEntity<Boolean> checkExistence(@RequestBody List<Integer> numbers, @RequestParam Integer element) {
        return ResponseEntity.ok(collectionService.checkExistence(numbers, element));
    }

    // --- HashSet Endpoints ---

    @PostMapping("/countries/add")
    /**
     * Thêm các quốc gia mới vào tập hợp quốc gia hiện có
     * Input: AddCountriesRequest - Chứa existingCountries và newCountries
     * Output: Set<String> - Tập hợp quốc gia sau khi thêm
     */
    public ResponseEntity<Set<String>> addCountries(@RequestBody AddCountriesRequest request) {
        return ResponseEntity.ok(collectionService.addCountries(request.existingCountries, request.newCountries));
    }

    @PostMapping("/countries/check")
    /**
     * Kiểm tra xem một quốc gia có tồn tại trong tập hợp không
     * Input: Set<String> - Tập hợp quốc gia, country (String) - Quốc gia cần kiểm tra
     * Output: Boolean - true nếu tồn tại, false nếu không
     */
    public ResponseEntity<Boolean> checkCountryExistence(@RequestBody Set<String> countries, @RequestParam String country) {
        return ResponseEntity.ok(collectionService.checkCountryExistence(countries, country));
    }

    @PostMapping("/countries/remove")
    /**
     * Loại bỏ một quốc gia khỏi tập hợp
     * Input: Set<String> - Tập hợp quốc gia, country (String) - Quốc gia cần loại bỏ
     * Output: Set<String> - Tập hợp quốc gia sau khi loại bỏ
     */
    public ResponseEntity<Set<String>> removeCountry(@RequestBody Set<String> countries, @RequestParam String country) {
        return ResponseEntity.ok(collectionService.removeCountry(countries, country));
    }
    
    @PostMapping("/countries/count")
    /**
     * Đếm số lượng quốc gia trong tập hợp
     * Input: Set<String> - Tập hợp quốc gia
     * Output: Integer - Số lượng quốc gia
     */
    public ResponseEntity<Integer> countCountries(@RequestBody Set<String> countries) {
        return ResponseEntity.ok(collectionService.countCountries(countries));
    }
    
    // --- HashMap Endpoints ---

    @PostMapping("/people/names")
    /**
     * Lấy tất cả tên người từ Map (tên -> tuổi)
     * Input: Map<String, Integer> - Map chứa tên và tuổi
     * Output: Set<String> - Tập hợp tất cả tên người
     */
    public ResponseEntity<Set<String>> getPeopleNames(@RequestBody Map<String, Integer> people) {
        return ResponseEntity.ok(collectionService.getPeopleNames(people));
    }

    @PostMapping("/people/find-age")
    /**
     * Tìm tuổi của một người theo tên
     * Input: Map<String, Integer> - Map chứa tên và tuổi, name (String) - Tên cần tìm
     * Output: Integer - Tuổi của người đó hoặc 404 nếu không tìm thấy
     */
    public ResponseEntity<Integer> findAgeByName(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        Integer age = collectionService.findAgeByName(people, name);
        if (age != null) {
            return ResponseEntity.ok(age);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/people/remove")
    /**
     * Loại bỏ một người khỏi Map theo tên
     * Input: Map<String, Integer> - Map chứa tên và tuổi, name (String) - Tên cần loại bỏ
     * Output: Map<String, Integer> - Map sau khi loại bỏ
     */
    public ResponseEntity<Map<String, Integer>> removePerson(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        return ResponseEntity.ok(collectionService.removePerson(people, name));
    }

    @PostMapping("/people/check-person")
    /**
     * Kiểm tra xem một người có tồn tại trong Map không
     * Input: Map<String, Integer> - Map chứa tên và tuổi, name (String) - Tên cần kiểm tra
     * Output: Boolean - true nếu tồn tại, false nếu không
     */
    public ResponseEntity<Boolean> checkPersonExists(@RequestBody Map<String, Integer> people, @RequestParam String name) {
        return ResponseEntity.ok(collectionService.checkPersonExists(people, name));
    }

    // --- More Endpoints ---

    @PostMapping("/sort-ascending")
    /**
     * Sắp xếp danh sách số theo thứ tự tăng dần
     * Input: List<Integer> - Danh sách số cần sắp xếp
     * Output: List<Integer> - Danh sách đã sắp xếp tăng dần
     */
    public ResponseEntity<List<Integer>> sortAscending(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sortAscending(numbers));
    }

    @PostMapping("/sort-descending")
    /**
     * Sắp xếp danh sách số theo thứ tự giảm dần
     * Input: List<Integer> - Danh sách số cần sắp xếp
     * Output: List<Integer> - Danh sách đã sắp xếp giảm dần
     */
    public ResponseEntity<List<Integer>> sortDescending(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.sortDescending(numbers));
    }

    @PostMapping("/two-sum")
    /**
     * Tìm hai số có tổng bằng giá trị mục tiêu
     * Input: List<Integer> - Danh sách số, target (int) - Giá trị mục tiêu
     * Output: List<Integer> - Danh sách chứa hai số có tổng bằng target hoặc rỗng nếu không tìm thấy
     */
    public ResponseEntity<List<Integer>> twoSum(@RequestBody List<Integer> numbers, @RequestParam int target) {
        List<Integer> result = collectionService.twoSum(numbers, target);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/max-profit")
    /**
     * Tính lợi nhuận tối đa có thể thu được từ việc mua bán (một lần mua, một lần bán)
     * Input: List<Integer> - Danh sách giá cổ phiếu theo thời gian
     * Output: Integer - Lợi nhuận tối đa có thể thu được
     */
    public ResponseEntity<Integer> maxProfit(@RequestBody List<Integer> prices) {
        return ResponseEntity.ok(collectionService.maxProfit(prices));
    }

    @PostMapping("/student-averages")
    /**
     * Tính điểm trung bình của từng học sinh
     * Input: Map<String, List<Integer>> - Map chứa tên học sinh và danh sách điểm
     * Output: Map<String, Double> - Map chứa tên học sinh và điểm trung bình
     */
    public ResponseEntity<Map<String, Double>> calculateAverages(@RequestBody Map<String, List<Integer>> scores) {
        return ResponseEntity.ok(collectionService.calculateAverages(scores));
    }

    // --- Product Management Endpoints (Bài 8) ---
    @PostMapping("/products/add")
    /**
     * Thêm hoặc cập nhật thông tin sản phẩm
     * Input: ProductRequest - Chứa products, code và info
     * Output: Map<String, String> - Map sản phẩm sau khi thêm/cập nhật
     */
    public ResponseEntity<Map<String, String>> addOrUpdateProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(collectionService.addOrUpdateProduct(request.products, request.code, request.info));
    }
    
    @PostMapping("/products/find")
    /**
     * Tìm thông tin sản phẩm theo mã
     * Input: Map<String, String> - Map sản phẩm, code (String) - Mã sản phẩm
     * Output: String - Thông tin sản phẩm hoặc 404 nếu không tìm thấy
     */
    public ResponseEntity<String> findProduct(@RequestBody Map<String, String> products, @RequestParam String code) {
        String info = collectionService.findProduct(products, code);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }

    @PostMapping("/products/remove")
    /**
     * Loại bỏ sản phẩm theo mã
     * Input: Map<String, String> - Map sản phẩm, code (String) - Mã sản phẩm cần loại bỏ
     * Output: Map<String, String> - Map sản phẩm sau khi loại bỏ
     */
    public ResponseEntity<Map<String, String>> removeProduct(@RequestBody Map<String, String> products, @RequestParam String code) {
        return ResponseEntity.ok(collectionService.removeProduct(products, code));
    }
    
    // --- Word Count Endpoint (Bài 9) ---
    @PostMapping("/words/count")
    /**
     * Đếm số lần xuất hiện của từng từ trong văn bản
     * Input: String - Văn bản cần đếm từ
     * Output: Map<String, Integer> - Map chứa từ và số lần xuất hiện
     */
    public ResponseEntity<Map<String, Integer>> countWords(@RequestBody String text) {
        return ResponseEntity.ok(collectionService.countWords(text));
    }

    // --- Score Distribution Endpoint (Bài 10) ---
    @PostMapping("/scores/distribution")
    /**
     * Tính phân phối điểm theo khoảng (0-5, 6-10, 11-15, 16-20)
     * Input: List<Double> - Danh sách điểm
     * Output: Map<String, Integer> - Map chứa khoảng điểm và số lượng học sinh
     */
    public ResponseEntity<Map<String, Integer>> getScoreDistribution(@RequestBody List<Double> scores) {
        return ResponseEntity.ok(collectionService.calculateScoreDistribution(scores));
    }

    // --- Dictionary Endpoints (Bài 11) ---
    @PostMapping("/dictionary/add")
    /**
     * Thêm từ và định nghĩa vào từ điển
     * Input: DictionaryRequest - Chứa dictionary, word và definition
     * Output: Map<String, String> - Từ điển sau khi thêm
     */
    public ResponseEntity<Map<String, String>> addWord(@RequestBody DictionaryRequest request) {
        return ResponseEntity.ok(collectionService.addWordToDictionary(request.dictionary, request.word, request.definition));
    }
    
    @PostMapping("/dictionary/lookup")
    /**
     * Tra cứu định nghĩa của từ trong từ điển
     * Input: Map<String, String> - Từ điển, word (String) - Từ cần tra cứu
     * Output: String - Định nghĩa của từ hoặc 404 nếu không tìm thấy
     */
    public ResponseEntity<String> lookupWord(@RequestBody Map<String, String> dictionary, @RequestParam String word) {
        String definition = collectionService.lookupWord(dictionary, word);
        return definition != null ? ResponseEntity.ok(definition) : ResponseEntity.notFound().build();
    }

    // --- Create Product Endpoint (Bài 12) ---
    @PostMapping("/products/create")
    /**
     * Tạo sản phẩm mới với thông tin chi tiết
     * Input: Map<String, Object> - Thông tin chi tiết sản phẩm
     * Output: Map<String, Object> - Sản phẩm đã được tạo
     */
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> productDetails) {
        return ResponseEntity.ok(collectionService.createProduct(productDetails));
    }
    
    // --- Set Operations Endpoints (Bài 13, 14, 15, 16) ---
    @PostMapping("/sets/duplicates")
    /**
     * Tìm các phần tử trùng lặp trong danh sách
     * Input: List<Integer> - Danh sách số có thể có trùng lặp
     * Output: Set<Integer> - Tập hợp các phần tử trùng lặp
     */
    public ResponseEntity<Set<Integer>> findDuplicates(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(collectionService.findDuplicates(numbers));
    }
    
    @PostMapping("/sets/intersection")
    /**
     * Tìm phần giao của hai tập hợp
     * Input: SetOperationRequest - Chứa set1 và set2
     * Output: Set<Integer> - Tập hợp phần giao
     */
    public ResponseEntity<Set<Integer>> findIntersection(@RequestBody SetOperationRequest request) {
        return ResponseEntity.ok(collectionService.findIntersection(request.set1, request.set2));
    }
    
    @PostMapping("/sets/union")
    /**
     * Tìm phần hợp của hai tập hợp
     * Input: SetOperationRequest - Chứa set1 và set2
     * Output: Set<Integer> - Tập hợp phần hợp
     */
    public ResponseEntity<Set<Integer>> findUnion(@RequestBody SetOperationRequest request) {
        return ResponseEntity.ok(collectionService.findUnion(request.set1, request.set2));
    }
    
    @PostMapping("/sets/min-max")
    /**
     * Tìm số nhỏ nhất và lớn nhất trong tập hợp
     * Input: Set<Integer> - Tập hợp số
     * Output: Map<String, Integer> - Map chứa "min" và "max"
     */
    public ResponseEntity<Map<String, Integer>> findSetMinMax(@RequestBody Set<Integer> set) {
        try {
            return ResponseEntity.ok(collectionService.findSetMinMax(set));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
} 