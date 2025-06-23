package com.example.authdemo.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectionFrameworkService {

    // --- Bài 1: ArrayList Operations ---

    /**
     * Sử dụng Stream API để tính tổng
     * - Chuyển List<Integer> thành IntStream bằng mapToInt()
     * - Sử dụng sum() để tính tổng
     * - Xử lý trường hợp null bằng cách return 0
     */
    public int sumNumbers(List<Integer> numbers) {
        if (numbers == null) return 0;
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Sử dụng Collections.max() và Collections.min()
     * - Kiểm tra null và empty trước khi xử lý
     * - Sử dụng Collections.max() để tìm số lớn nhất
     * - Sử dụng Collections.min() để tìm số nhỏ nhất
     * - Lưu kết quả vào Map với key "max" và "min"
     */
    public Map<String, Integer> findMaxMin(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty.");
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("max", Collections.max(numbers));
        result.put("min", Collections.min(numbers));
        return result;
    }

    /**
     * Tạo bản sao của list để tránh thay đổi list gốc
     * - Tạo ArrayList mới từ list gốc
     * - Sử dụng remove() để loại bỏ phần tử
     * - Return list mới đã được loại bỏ phần tử
     */
    public List<Integer> removeElement(List<Integer> numbers, Integer elementToRemove) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> mutableList = new ArrayList<>(numbers);
        mutableList.remove(elementToRemove);
        return mutableList;
    }

    /**
     * Sử dụng contains() method của List
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng contains() để kiểm tra sự tồn tại
     * - Return true nếu tồn tại, false nếu không
     */
    public boolean checkExistence(List<Integer> numbers, Integer elementToCheck) {
        if (numbers == null) return false;
        return numbers.contains(elementToCheck);
    }

    // --- Bài 2: HashSet Operations ---

    /**
     * Sử dụng HashSet để tự động loại bỏ trùng lặp
     * - Tạo HashSet mới
     * - Thêm tất cả quốc gia hiện có vào HashSet
     * - Thêm tất cả quốc gia mới vào HashSet
     * - HashSet tự động loại bỏ trùng lặp
     */
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

    /**
     * Sử dụng contains() method của Set
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng contains() để kiểm tra sự tồn tại
     * - Return true nếu tồn tại, false nếu không
     */
    public boolean checkCountryExistence(Set<String> countries, String countryToCheck) {
        if (countries == null) return false;
        return countries.contains(countryToCheck);
    }

    /**
     * Tạo bản sao của Set để tránh thay đổi Set gốc
     * - Tạo HashSet mới từ Set gốc
     * - Sử dụng remove() để loại bỏ quốc gia
     * - Return Set mới đã được loại bỏ
     */
    public Set<String> removeCountry(Set<String> countries, String countryToRemove) {
        if (countries == null) return new HashSet<>();
        Set<String> mutableSet = new HashSet<>(countries);
        mutableSet.remove(countryToRemove);
        return mutableSet;
    }

    /**
     * Sử dụng size() method của Set
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng size() để đếm số phần tử
     * - Return 0 nếu Set null
     */
    public int countCountries(Set<String> countries) {
        return countries == null ? 0 : countries.size();
    }

    // --- Bài 3: HashMap Operations ---
    
    /**
     * Sử dụng keySet() method của Map
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng keySet() để lấy tất cả keys (tên người)
     * - Return Set<String> chứa tất cả tên
     */
    public Set<String> getPeopleNames(Map<String, Integer> people) {
        if (people == null) return new HashSet<>();
        return people.keySet();
    }
    
    /**
     * Sử dụng get() method của Map
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng get() để lấy value theo key
     * - Return null nếu không tìm thấy key
     */
    public Integer findAgeByName(Map<String, Integer> people, String name) {
        if (people == null) return null;
        return people.get(name); // Returns null if not found
    }

    /**
     * Tạo bản sao của Map để tránh thay đổi Map gốc
     * - Tạo HashMap mới từ Map gốc
     * - Sử dụng remove() để loại bỏ entry theo key
     * - Return Map mới đã được loại bỏ
     */
    public Map<String, Integer> removePerson(Map<String, Integer> people, String name) {
        if (people == null) return new HashMap<>();
        Map<String, Integer> mutableMap = new HashMap<>(people);
        mutableMap.remove(name);
        return mutableMap;
    }

    /**
     * Sử dụng containsKey() method của Map
     * - Kiểm tra null trước khi xử lý
     * - Sử dụng containsKey() để kiểm tra sự tồn tại của key
     * - Return true nếu tồn tại, false nếu không
     */
    public boolean checkPersonExists(Map<String, Integer> people, String name) {
        if (people == null) return false;
        return people.containsKey(name);
    }

    // --- Bài 4: Sorting ---
    /**
     * Sử dụng Collections.sort() để sắp xếp tăng dần
     * - Tạo bản sao của list để tránh thay đổi list gốc
     * - Sử dụng Collections.sort() để sắp xếp tăng dần
     * - Return list đã được sắp xếp
     */
    public List<Integer> sortAscending(List<Integer> numbers) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Sử dụng sort() với Collections.reverseOrder()
     * - Tạo bản sao của list để tránh thay đổi list gốc
     * - Sử dụng sort() với Collections.reverseOrder() để sắp xếp giảm dần
     * - Return list đã được sắp xếp
     */
    public List<Integer> sortDescending(List<Integer> numbers) {
        if (numbers == null) return new ArrayList<>();
        List<Integer> sorted = new ArrayList<>(numbers);
        sorted.sort(Collections.reverseOrder());
        return sorted;
    }

    // --- Bài 5: Two Sum ---
    /**
     * Sử dụng HashMap để tối ưu thời gian O(n)
     * - Tạo HashMap để lưu số và index
     * - Duyệt qua từng số, tính complement = target - current
     * - Nếu complement có trong HashMap, return [index của complement, current index]
     * - Nếu không, thêm current number và index vào HashMap
     * - Return empty list nếu không tìm thấy
     */
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
    /**
     * Sử dụng thuật toán một lần duyệt O(n)
     * - Duyệt qua từng giá một lần
     * - Cập nhật minPrice = min(minPrice, currentPrice)
     * - Cập nhật maxProfit = max(maxProfit, currentPrice - minPrice)
     * - Return maxProfit cuối cùng
     */
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
    /**
     * Sử dụng Stream API để tính trung bình
     * - Duyệt qua từng entry trong Map scores
     * - Với mỗi học sinh, chuyển List<Integer> thành IntStream
     * - Sử dụng average() để tính trung bình
     * - Lưu kết quả vào Map mới với key là tên học sinh
     */
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
    /**
     * Sử dụng put() method của Map
     * - Tạo bản sao của Map để tránh thay đổi Map gốc
     * - Sử dụng put() để thêm hoặc cập nhật entry
     * - put() sẽ ghi đè nếu key đã tồn tại
     */
    public Map<String, String> addOrUpdateProduct(Map<String, String> products, String code, String info) {
        Map<String, String> mutableProducts = new HashMap<>(products);
        mutableProducts.put(code, info);
        return mutableProducts;
    }

    /**
     * Sử dụng get() method của Map
     * - Sử dụng get() để lấy value theo key
     * - Return null nếu không tìm thấy key
     */
    public String findProduct(Map<String, String> products, String code) {
        return products.get(code); // Returns null if not found
    }

    /**
     * Sử dụng remove() method của Map
     * - Tạo bản sao của Map để tránh thay đổi Map gốc
     * - Sử dụng remove() để loại bỏ entry theo key
     * - Return Map mới đã được loại bỏ
     */
    public Map<String, String> removeProduct(Map<String, String> products, String code) {
        Map<String, String> mutableProducts = new HashMap<>(products);
        mutableProducts.remove(code);
        return mutableProducts;
    }

    // --- Bài 9: Word Count ---
    /**
     * Sử dụng regex và HashMap để đếm từ
     * - Chuyển text thành lowercase
     * - Loại bỏ ký tự đặc biệt bằng regex [^a-zA-Z0-9 ]
     * - Split theo whitespace để tách từ
     * - Sử dụng HashMap để đếm số lần xuất hiện
     * - getOrDefault() để tăng count
     */
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
    /**
     * Sử dụng LinkedHashMap để giữ thứ tự và phân loại điểm
     * - Tạo LinkedHashMap với 3 category: Cao (>=8), Trung bình (>=5), Trượt (<5)
     * - Duyệt qua từng điểm và phân loại
     * - Sử dụng LinkedHashMap để giữ thứ tự các category
     */
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
    /**
     * Sử dụng put() method của Map
     * - Tạo bản sao của Map để tránh thay đổi Map gốc
     * - Sử dụng put() để thêm từ và định nghĩa
     * - put() sẽ ghi đè nếu từ đã tồn tại
     */
    public Map<String, String> addWordToDictionary(Map<String, String> dict, String word, String definition) {
        Map<String, String> mutableDict = new HashMap<>(dict);
        mutableDict.put(word, definition);
        return mutableDict;
    }

    /**
     * Sử dụng get() method của Map
     * - Sử dụng get() để lấy định nghĩa theo từ
     * - Return null nếu không tìm thấy từ
     */
    public String lookupWord(Map<String, String> dict, String word) {
        return dict.get(word);
    }
    
    // --- Bài 12: Create Product Object ---
    /**
     * Tạo bản sao của Map để tránh thay đổi Map gốc
     * - Tạo HashMap mới từ productDetails
     * - Có thể thêm validation hoặc default values nếu cần
     * - Return Map mới
     */
    public Map<String, Object> createProduct(Map<String, Object> productDetails) {
        // This service method primarily validates or defaults fields if necessary.
        // For now, it just returns the map as is.
        return new HashMap<>(productDetails);
    }

    // --- Bài 13: Find Duplicates ---
    /**
     * Sử dụng 2 HashSet để tìm trùng lặp
     * - HashSet "seen" để theo dõi các số đã gặp
     * - HashSet "duplicates" để lưu các số trùng lặp
     * - Nếu add() vào "seen" return false, nghĩa là số đã tồn tại -> thêm vào "duplicates"
     */
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
    /**
     * Sử dụng retainAll() method của Set
     * - Tạo bản sao của set1
     * - Sử dụng retainAll(set2) để giữ lại chỉ các phần tử có trong cả 2 set
     * - retainAll() thực hiện phép giao (intersection)
     */
    public Set<Integer> findIntersection(Set<Integer> set1, Set<Integer> set2) {
        if (set1 == null || set2 == null) return new HashSet<>();
        Set<Integer> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    // --- Bài 15: Set Union ---
    /**
     * Sử dụng addAll() method của Set
     * - Tạo bản sao của set1 (hoặc HashSet rỗng nếu set1 null)
     * - Sử dụng addAll(set2) để thêm tất cả phần tử của set2
     * - addAll() thực hiện phép hợp (union)
     */
    public Set<Integer> findUnion(Set<Integer> set1, Set<Integer> set2) {
        if (set1 == null && set2 == null) return new HashSet<>();
        Set<Integer> union = new HashSet<>(set1 != null ? set1 : new HashSet<>());
        if (set2 != null) {
            union.addAll(set2);
        }
        return union;
    }

    // --- Bài 16: Set Min/Max ---
    /**
     * Sử dụng Collections.min() và Collections.max()
     * - Kiểm tra null và empty trước khi xử lý
     * - Sử dụng Collections.min() để tìm số nhỏ nhất
     * - Sử dụng Collections.max() để tìm số lớn nhất
     * - Lưu kết quả vào Map với key "min" và "max"
     */
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