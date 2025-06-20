package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class Level4Service {

    /**
     * Đếm số lần lặp cần thiết để sắp xếp mảng bằng thuật toán Bubble Sort
     * 
     * Ý tưởng: Duyệt qua mảng, so sánh và đổi chỗ các phần tử liền kề
     * Dừng khi mảng đã sắp xếp (không còn đổi chỗ nào)
     * 
     * @param numbers Danh sách số cần sắp xếp
     * @return Số lần lặp đã thực hiện
     */
    public int countBubbleSortIterations(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        int[] temp = numbers.stream().mapToInt(Integer::intValue).toArray();
        int n = temp.length;
        int iterations = 0;
        boolean isSorted;

        // Duyệt qua từng vòng lặp
        for (int i = 0; i < n - 1; i++) {
            isSorted = true;
            // So sánh và đổi chỗ các phần tử liền kề
            for (int j = 0; j < n - i - 1; j++) {
                if (temp[j] > temp[j + 1]) {
                    // Đổi chỗ 2 phần tử
                    int t = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = t;
                    isSorted = false; // Có đổi chỗ = chưa sắp xếp
                }
            }
            iterations++;
            // Nếu không có đổi chỗ nào = đã sắp xếp xong
            if (isSorted) {
                return iterations;
            }
        }
        return iterations;
    }

    /**
     * Đếm số cách chọn các phần tử để có tổng bằng target
     * 
     * Ý tưởng: Sử dụng đệ quy - với mỗi phần tử, chọn hoặc không chọn
     * Nếu target = 0: tìm thấy 1 cách
     * Nếu index = length: hết phần tử, trả về 0
     * 
     * @param numbers Danh sách số
     * @param target Tổng mục tiêu
     * @return Số cách chọn
     */
    public int countSubsequencesWithSum(List<Integer> numbers, int target) {
        if (numbers == null) {
            return 0;
        }
        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        return count(arr, target, 0);
    }

    /**
     * Hàm đệ quy hỗ trợ đếm số cách chọn
     * 
     * @param arr Mảng số
     * @param target Tổng còn lại cần đạt
     * @param index Vị trí hiện tại đang xét
     * @return Số cách chọn
     */
    private int count(int[] arr, int target, int index) {
        // Nếu đạt được tổng mục tiêu
        if (target == 0) {
            return 1;
        }
        // Nếu hết phần tử để chọn
        if (index == arr.length) {
            return 0;
        }

        // Chọn phần tử hiện tại
        int include = count(arr, target - arr[index], index + 1);
        // Không chọn phần tử hiện tại
        int exclude = count(arr, target, index + 1);

        return include + exclude;
    }

    /**
     * Tìm độ dài chuỗi con chung dài nhất trong tất cả các chuỗi
     * 
     * Ý tưởng: Tìm chuỗi ngắn nhất làm chuẩn
     * Thử từ độ dài lớn nhất xuống nhỏ nhất
     * Với mỗi độ dài, tạo tất cả chuỗi con có thể và kiểm tra có trong tất cả chuỗi không
     * 
     * @param strings Danh sách chuỗi
     * @return Độ dài chuỗi con chung dài nhất
     */
    public int findLongestCommonSubstringLength(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return 0;
        }
        if (strings.size() == 1) {
            return strings.get(0).length();
        }

        // Tìm chuỗi ngắn nhất làm chuẩn
        String shortestString = strings.get(0);
        for (String s : strings) {
            if (s.length() < shortestString.length()) {
                shortestString = s;
            }
        }

        // Thử từ độ dài lớn nhất xuống nhỏ nhất
        for (int len = shortestString.length(); len > 0; len--) {
            // Tạo tất cả chuỗi con có độ dài len
            for (int i = 0; i <= shortestString.length() - len; i++) {
                String sub = shortestString.substring(i, i + len);
                boolean foundInAll = true;
                // Kiểm tra chuỗi con có trong tất cả chuỗi không
                for (String s : strings) {
                    if (!s.contains(sub)) {
                        foundInAll = false;
                        break;
                    }
                }
                if (foundInAll) {
                    return len;
                }
            }
        }

        return 0;
    }

    /**
     * Tìm tổng lớn nhất của các phần tử không liền kề
     * 
     * Ý tưởng: Quy hoạch động
     * dp[i] = max(dp[i-1], dp[i-2] + numbers[i])
     * Tại mỗi vị trí: chọn phần tử hiện tại + phần tử cách 2 vị trí, hoặc bỏ qua
     * 
     * @param numbers Danh sách số
     * @return Tổng lớn nhất
     */
    public int findMaxSumNoConsecutive(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        int[] dp = new int[numbers.size()];
        // Trường hợp cơ sở
        dp[0] = numbers.get(0);
        dp[1] = Math.max(numbers.get(0), numbers.get(1));

        // Quy hoạch động
        for (int i = 2; i < numbers.size(); i++) {
            // Chọn max của: bỏ qua phần tử hiện tại hoặc chọn phần tử hiện tại + phần tử cách 2 vị trí
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + numbers.get(i));
        }

        return dp[numbers.size() - 1];
    }
    
    /**
     * Tìm tích lớn nhất của 3 phần tử
     * 
     * Ý tưởng: Sắp xếp mảng
     * Có 2 trường hợp: 3 số lớn nhất hoặc 2 số nhỏ nhất × số lớn nhất
     * 
     * @param numbers Danh sách số
     * @return Tích lớn nhất
     */
    public int maxProductOfThree(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 3) {
            throw new IllegalArgumentException("List must contain at least 3 numbers.");
        }
        
        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(arr);

        int n = arr.length;
        // Trường hợp 1: 3 số lớn nhất
        int option1 = arr[n - 1] * arr[n - 2] * arr[n - 3];
        // Trường hợp 2: 2 số nhỏ nhất × số lớn nhất (cho trường hợp có số âm)
        int option2 = arr[0] * arr[1] * arr[n - 1];

        return Math.max(option1, option2);
    }
    
    /**
     * Sắp xếp chuỗi theo số từ duy nhất (giảm dần)
     * 
     * Ý tưởng: Đếm số từ duy nhất trong mỗi chuỗi
     * Sắp xếp theo: số từ duy nhất (giảm) → độ dài chuỗi (giảm) → thứ tự từ điển
     * 
     * @param strings Danh sách chuỗi
     * @return Danh sách đã sắp xếp
     */
    public List<String> sortStringsByDistinctWords(List<String> strings) {
        if (strings == null) {
            return new ArrayList<>();
        }

        List<String> sortedList = new ArrayList<>(strings);
        sortedList.sort((a, b) -> {
            int distinctA = countDistinctWords(a);
            int distinctB = countDistinctWords(b);

            // So sánh theo số từ duy nhất (giảm dần)
            if (distinctA != distinctB) {
                return Integer.compare(distinctB, distinctA);
            }

            // Nếu số từ duy nhất bằng nhau, so sánh theo độ dài (giảm dần)
            if (a.length() != b.length()) {
                return Integer.compare(b.length(), a.length());
            }

            // Cuối cùng so sánh theo thứ tự từ điển
            return a.compareTo(b);
        });
        return sortedList;
    }

    /**
     * Hàm hỗ trợ đếm số từ duy nhất trong chuỗi
     * 
     * @param s Chuỗi đầu vào
     * @return Số từ duy nhất
     */
    private int countDistinctWords(String s) {
        String[] words = s.toLowerCase().split("\\s+");
        Set<String> unique = new HashSet<>(Arrays.asList(words));
        return unique.size();
    }
    
    /**
     * Tìm tổng nhỏ nhất không thể tạo được (với điều kiện đặc biệt)
     * 
     * Ý tưởng: Tạo tất cả tổng có thể từ các phần tử
     * Điều kiện: không được tạo tổng liền kề (sum+num-1, sum+num+1)
     * Tìm số nhỏ nhất không có trong tập tổng
     * 
     * @param numbers Danh sách số
     * @return Tổng nhỏ nhất không thể tạo được
     */
    public int findSmallestMissingSumNoConsecutive(List<Integer> numbers) {
         if (numbers == null) return 1;
        
        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);
        TreeSet<Integer> sums = new TreeSet<>();
        sums.add(0);

        for (int num : nums) {
            Set<Integer> newSums = new HashSet<>();
            for (int sum : sums) {
                 // Điều kiện đặc biệt: không được tạo tổng liền kề
                if (!sums.contains(sum + num - 1) && !sums.contains(sum + num + 1)) {
                    newSums.add(sum + num);
                }
            }
            sums.addAll(newSums);
        }

        // Tìm số nhỏ nhất không có trong tập tổng
        int result = 1;
        while (sums.contains(result)) result++;
        return result;
    }

    /**
     * Tìm chuỗi con tăng dài nhất với hiệu số giữa 2 phần tử liền kề = 1
     * 
     * Ý tưởng: Quy hoạch động tương tự LIS thông thường
     * Thêm điều kiện: arr[i] - arr[j] == 1
     * Chỉ nối tiếp khi hiệu số chính xác bằng 1
     * 
     * @param numbers Danh sách số
     * @return Độ dài chuỗi con tăng dài nhất
     */
    public int longestIncreasingSubsequenceDiffOne(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) return 0;

        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLen = n > 0 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Điều kiện: phần tử hiện tại > phần tử trước VÀ hiệu số = 1
                if (arr[i] > arr[j] && arr[i] - arr[j] == 1) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    /**
     * Tìm 2 chuỗi có nhiều chuỗi con chung độ dài k nhất
     * 
     * Ý tưởng: Tạo tất cả chuỗi con độ dài k của mỗi chuỗi
     * Đếm số chuỗi con chung giữa 2 chuỗi
     * Trả về cặp chuỗi có nhiều chuỗi con chung nhất
     * 
     * @param strings Danh sách chuỗi
     * @param k Độ dài chuỗi con
     * @return Cặp chuỗi có nhiều chuỗi con chung nhất
     */
    public List<String> findLargestSubstringOverlap(List<String> strings, int k) {
        if (strings == null || strings.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 strings.");
        }
        
        int maxOverlap = -1;
        String[] result = new String[2];

        // Duyệt qua tất cả cặp chuỗi
        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                int overlap = countCommonSubstrings(strings.get(i), strings.get(j), k);
                if (overlap > maxOverlap) {
                    maxOverlap = overlap;
                    result[0] = strings.get(i);
                    result[1] = strings.get(j);
                }
            }
        }
        return Arrays.asList(result);
    }

    /**
     * Hàm hỗ trợ đếm số chuỗi con chung độ dài k giữa 2 chuỗi
     * 
     * @param s1 Chuỗi thứ nhất
     * @param s2 Chuỗi thứ hai
     * @param k Độ dài chuỗi con
     * @return Số chuỗi con chung
     */
    private int countCommonSubstrings(String s1, String s2, int k) {
        // Tạo tất cả chuỗi con độ dài k của chuỗi 1
        Set<String> subs1 = new HashSet<>();
        for (int i = 0; i <= s1.length() - k; i++) {
            subs1.add(s1.substring(i, i + k));
        }
        
        // Tạo tất cả chuỗi con độ dài k của chuỗi 2
        Set<String> subs2 = new HashSet<>();
        for (int i = 0; i <= s2.length() - k; i++) {
            subs2.add(s2.substring(i, i + k));
        }

        // Tìm giao của 2 tập hợp
        subs1.retainAll(subs2);
        return subs1.size();
    }
}
