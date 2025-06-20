package com.example.authdemo.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Level5Service {

    /**
     * 5.1: Đảo ngược thứ tự các phần tử trong danh sách số nguyên.
     * 
     * Ý tưởng: Tạo bản sao của danh sách đầu vào và dùng Collections.reverse() để đảo ngược.
     * Tránh thay đổi danh sách gốc.
     */
    public List<Integer> reverseList(List<Integer> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<Integer> reversedList = new ArrayList<>(list);
        Collections.reverse(reversedList);
        return reversedList;
    }

    /**
     * 5.2: Chia danh sách đầu vào thành nhiều danh sách con có kích thước tối đa là `size`.
     * 
     * Ý tưởng: Duyệt danh sách theo bước nhảy `size`, và tạo từng subList từ i đến i + size.
     */
    public <T> List<List<T>> chunk(List<T> list, int size) {
        if (list == null || list.isEmpty() || size <= 0) {
            return new ArrayList<>();
        }

        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            int end = Math.min(list.size(), i + size);
            result.add(new ArrayList<>(list.subList(i, end)));
        }
        return result;
    }

    /**
     * 5.3: Loại bỏ phần tử trùng lặp trong danh sách số nguyên, giữ nguyên thứ tự ban đầu.
     * 
     * Ý tưởng: Dùng LinkedHashSet để vừa loại trùng, vừa giữ thứ tự.
     */
    public List<Integer> uniq(List<Integer> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * 5.4: Loại bỏ các Map trùng lặp trong danh sách (xét theo nội dung, không phân biệt thứ tự key).
     * 
     * Ý tưởng: Dùng TreeMap để chuẩn hóa các map (sort theo key), convert thành chuỗi rồi check trùng.
     */
    public List<Map<String, Object>> uniqObjects(List<Map<String, Object>> list) {
        if (list == null) {
            return new ArrayList<>();
        }

        Set<String> seen = new HashSet<>();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> obj : list) {
            String normalized = normalizeMap(obj);
            if (!seen.contains(normalized)) {
                seen.add(normalized);
                result.add(obj);
            }
        }
        return result;
    }

    // Chuẩn hóa Map bằng cách sắp xếp key rồi chuyển thành chuỗi
    private String normalizeMap(Map<String, Object> map) {
        return new TreeMap<>(map).toString();
    }

    /**
     * 5.5: Gom nhóm các phần tử theo giá trị của một trường nhất định.
     * 
     * Ý tưởng: Dùng giá trị tại field làm key, gom các map lại theo nhóm.
     */
    public Map<Object, List<Map<String, Object>>> groupBy(List<Map<String, Object>> list, String field) {
        if (list == null || field == null || field.isEmpty()) {
            return new HashMap<>();
        }

        Map<Object, List<Map<String, Object>>> grouped = new HashMap<>();
        for (Map<String, Object> obj : list) {
            Object key = obj.get(field);
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(obj);
        }
        return grouped;
    }

    /**
     * 5.6: Xóa khoảng trắng đầu/cuối chuỗi và thay nhiều khoảng trắng liên tiếp bằng 1 khoảng trắng.
     * 
     * Ý tưởng: trim() + replaceAll với regex "\\s+".
     */
    public String trimAll(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * 5.7: Lọc danh sách các map, chỉ giữ lại các key nằm trong danh sách cho trước.
     * 
     * Ý tưởng: Với mỗi map, tạo map mới chỉ chứa các key có trong danh sách keys.
     */
    public List<Map<String, Object>> mapKey(List<String> keys, List<Map<String, Object>> collections) {
        if (keys == null || collections == null) {
            return new ArrayList<>();
        }
        return collections.stream()
                .map(obj -> {
                    Map<String, Object> filtered = new LinkedHashMap<>();
                    for (String key : keys) {
                        if (obj.containsKey(key)) {
                            filtered.put(key, obj.get(key));
                        }
                    }
                    return filtered;
                })
                .collect(Collectors.toList());
    }

    /**
     * 5.8: Di chuyển phần tử có id = targetId đến vị trí newOrder và cập nhật lại trường "order".
     * 
     * Ý tưởng:
     * 1. Sắp xếp theo order ban đầu.
     * 2. Tìm phần tử cần di chuyển, đưa nó đến vị trí mới.
     * 3. Cập nhật lại giá trị order theo thứ tự mới.
     */
    public List<Map<String, Object>> switchOrder(List<Map<String, Object>> list, int targetId, int newOrder) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> mutableList = new ArrayList<>(list);
        mutableList.sort(Comparator.comparingInt(o -> ((Number) o.getOrDefault("order", 0)).intValue()));

        Optional<Map<String, Object>> targetOptional = mutableList.stream()
                .filter(obj -> obj.containsKey("id") && ((Number) obj.get("id")).intValue() == targetId)
                .findFirst();

        if (!targetOptional.isPresent()) {
            return list;
        }

        Map<String, Object> target = targetOptional.get();
        int currentOrder = mutableList.indexOf(target);

        if (newOrder < 0) newOrder = 0;
        if (newOrder >= mutableList.size()) newOrder = mutableList.size() - 1;

        if (currentOrder == newOrder) {
            return mutableList;
        }

        mutableList.remove(target);
        mutableList.add(newOrder, target);

        for (int i = 0; i < mutableList.size(); i++) {
            mutableList.get(i).put("order", i);
        }

        return mutableList;
    }

    /**
     * 5.9: Cộng dồn tất cả các giá trị số (Number) theo từng key trong danh sách các map.
     * 
     * Ý tưởng: Với mỗi map, duyệt qua từng entry, nếu value là số thì cộng dồn vào result.
     */
    public Map<String, Integer> sumAll(List<Map<String, Object>> list) {
        if (list == null) {
            return new HashMap<>();
        }

        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> obj : list) {
            for (Map.Entry<String, Object> entry : obj.entrySet()) {
                if (entry.getValue() instanceof Number) {
                    int num = ((Number) entry.getValue()).intValue();
                    result.put(entry.getKey(), result.getOrDefault(entry.getKey(), 0) + num);
                }
            }
        }
        return result;
    }

    /**
     * 5.10: Tạo chuỗi hoàn chỉnh từ template chứa placeholder {{key}}, thay thế bằng giá trị từ map.
     * 
     * Ý tưởng: Với mỗi entry trong params, tìm {{key}} trong template và thay bằng value tương ứng.
     */
    public String generateFromTemplate(String template, Map<String, String> params) {
        if (template == null) {
            return "";
        }
        if (params == null) {
            return template;
        }

        String content = template;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            content = content.replace(placeholder, entry.getValue());
        }
        return content;
    }
}
