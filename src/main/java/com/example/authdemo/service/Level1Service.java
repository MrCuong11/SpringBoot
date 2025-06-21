package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Level1Service {

    /**
     * Phép cộng cơ bản giữa hai số
     * - Sử dụng toán tử + để cộng hai số double
     * - Return kết quả trực tiếp
     */
    public double sum(double num1, double num2) {
        return num1 + num2;
    }

    /**
     * Tính độ dài của chuỗi
     * - Sử dụng length() method của String
     * - Return số ký tự trong chuỗi
     */
    public int getStringLength(String input) {
        return input.length();
    }

    /**
     * Tính bình phương của số
     * - Sử dụng toán tử * để nhân số với chính nó
     * - Return kết quả bình phương
     */
    public double getSquare(double number) {
        return number * number;
    }

    /**
     * Tìm số lớn nhất trong danh sách
     * - Kiểm tra null và empty trước khi xử lý
     * - Khởi tạo max = phần tử đầu tiên
     * - Duyệt qua từng phần tử, cập nhật max nếu tìm thấy số lớn hơn
     * - Return số lớn nhất
     */
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

    /**
     * Tìm chuỗi ngắn nhất trong danh sách
     * - Kiểm tra null và empty trước khi xử lý
     * - Khởi tạo shortest = chuỗi đầu tiên
     * - Duyệt qua từng chuỗi, cập nhật shortest nếu tìm thấy chuỗi ngắn hơn
     * - So sánh bằng length() method
     * - Return chuỗi ngắn nhất
     */
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

    /**
     * Sắp xếp danh sách số theo thứ tự tăng dần
     * - Kiểm tra null và empty trước khi xử lý
     * - Tạo bản sao của list để tránh thay đổi list gốc
     * - Sử dụng Collections.sort() để sắp xếp
     * - Return list đã được sắp xếp
     */
    public List<Double> sortNumbers(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        List<Double> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Sắp xếp danh sách chuỗi theo thứ tự alphabet (không phân biệt hoa thường)
     * - Kiểm tra null và empty trước khi xử lý
     * - Tạo bản sao của list để tránh thay đổi list gốc
     * - Sử dụng sort() với String.CASE_INSENSITIVE_ORDER
     * - Return list đã được sắp xếp
     */
    public List<String> sortStrings(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        List<String> sorted = new ArrayList<>(strings);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }

    /**
     * Tìm giá trị trung vị của danh sách số
     * - Kiểm tra null và empty trước khi xử lý
     * - Tạo bản sao và sắp xếp danh sách
     * - Nếu số phần tử chẵn: trung vị = (phần tử giữa + phần tử giữa+1) / 2
     * - Nếu số phần tử lẻ: trung vị = phần tử giữa
     * - Return giá trị trung vị
     */
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

    /**
     * Đếm số từ trong chuỗi
     * - Kiểm tra null và chuỗi rỗng
     * - Loại bỏ khoảng trắng thừa bằng trim()
     * - Split theo whitespace (\\s+) để tách từ
     * - Return số lượng từ
     */
    public int countWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        return input.trim().split("\\s+").length;
    }

    /**
     * Đếm số chuỗi chứa ký tự 'a' (không phân biệt hoa thường)
     * - Kiểm tra null và empty trước khi xử lý
     * - Sử dụng Stream API với filter()
     * - Chuyển chuỗi thành lowercase và kiểm tra contains("a")
     * - Sử dụng count() để đếm kết quả
     * - Return số chuỗi chứa ký tự 'a'
     */
    public long countStringsWithA(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        return strings.stream()
                .filter(s -> s.toLowerCase().contains("a"))
                .count();
    }
} 