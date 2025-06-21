package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
@Service
public class Level3Service {

    /**
     * Tìm số nhỏ thứ hai trong danh sách các số nguyên.
     * 
     * 1. Sắp xếp mảng theo thứ tự tăng dần
     * 2. Duyệt từ đầu mảng, tìm phần tử đầu tiên khác với phần tử đầu tiên
     * 3. Đó chính là số nhỏ thứ hai
     * 
     * Ý tưởng: Sau khi sắp xếp, các phần tử sẽ theo thứ tự tăng dần.
     * Số nhỏ nhất ở vị trí đầu, số nhỏ thứ hai sẽ là phần tử đầu tiên
     * khác với số nhỏ nhất.
     */
    public Integer findSecondSmallest(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 numbers");
        }

        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);

        int secondSmallest = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > secondSmallest) {
                secondSmallest = nums[i];
                break;
            }
        }

        return secondSmallest;
    }

    /**
     * Tính hiệu số lớn nhất giữa hai số bất kỳ trong danh sách.
     * 
     * 1. Duyệt một lần qua mảng để tìm giá trị lớn nhất và nhỏ nhất
     * 2. Trả về hiệu số: max - min
     * 
     * Ý tưởng: Hiệu số lớn nhất giữa hai số bất kỳ trong mảng chính là
     * hiệu số giữa số lớn nhất và số nhỏ nhất. Không cần so sánh từng cặp.
     * 
     * 
     */
    public int findMaxDifference(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 numbers");
        }

        int max = numbers.get(0);
        int min = numbers.get(0);
        
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        return max - min;
    }

    /**
     * Tìm độ dài của dãy con tăng dài nhất (LIS) trong danh sách số nguyên.
     * 
     * Hướng giải quyết (Quy hoạch động):
     * 1. Tạo mảng dp[] với dp[i] = độ dài LIS kết thúc tại vị trí i
     * 2. Khởi tạo dp[i] = 1 cho tất cả i (mỗi phần tử là một LIS độ dài 1)
     * 3. Với mỗi phần tử i, kiểm tra tất cả phần tử j < i
     * 4. Nếu numbers[i] > numbers[j] thì có thể nối vào LIS kết thúc tại j
     * 5. Cập nhật dp[i] = max(dp[i], dp[j] + 1)
     * 
     * Ý tưởng: Sử dụng quy hoạch động để tránh tính toán lại.
     * Mỗi phần tử có thể nối vào bất kỳ dãy con tăng nào kết thúc trước nó.
     *
     */
    public int findLongestIncreasingSubsequence(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        int n = numbers.size();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (numbers.get(i) > numbers.get(j) && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    if (dp[i] > maxLength) {
                        maxLength = dp[i];
                    }
                }
            }
        }

        return maxLength;
    }

    /**
     * Tìm số nguyên dương nhỏ nhất không thể biểu diễn được dưới dạng tổng của bất kỳ tập con nào
     * của các số đã cho.
     * 
     * Hướng giải quyết (Thuật toán tham lam):
     * 1. Sắp xếp mảng theo thứ tự tăng dần
     * 2. Khởi tạo res = 1 (số nhỏ nhất cần kiểm tra)
     * 3. Duyệt qua từng số trong mảng đã sắp xếp
     * 4. Nếu số hiện tại <= res, thì có thể tạo được tất cả số từ 1 đến res + num - 1
     * 5. Cập nhật res = res + num
     * 6. Nếu số hiện tại > res, thì res chính là số không thể tạo được
     * 
     * Ý tưởng: Nếu có thể tạo được tất cả số từ 1 đến res-1, và có số num <= res,
     * thì có thể tạo được tất cả số từ 1 đến res + num - 1.

     */
    public int findSmallestUnreachableSum(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        int[] nums = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nums);
        
        int res = 1;
        for (int num : nums) {
            if (num > res) {
                break;
            }
            res += num;
        }

        return res;
    }

    /**
     * Tìm trung vị của hai danh sách đã sắp xếp bằng cách kết hợp chúng và tìm phần tử giữa.
     * 
     * 1. Kết hợp hai danh sách thành một mảng duy nhất
     * 2. Sắp xếp mảng kết hợp
     * 3. Tìm trung vị:
     *    - Nếu độ dài chẵn: trung bình của hai phần tử giữa
     *    - Nếu độ dài lẻ: phần tử giữa
     * 
     * Ý tưởng: Trung vị là phần tử ở giữa của mảng đã sắp xếp.
     * Kết hợp hai danh sách và sắp xếp để tìm trung vị chung.

     */
    public double findMedianOfCombinedLists(List<Integer> list1, List<Integer> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Lists must not be null");
        }

        int[] combined = new int[list1.size() + list2.size()];
        
        int index = 0;
        for (int num : list1) {
            combined[index++] = num;
        }
        for (int num : list2) {
            combined[index++] = num;
        }

        Arrays.sort(combined);

        int n = combined.length;
        if (n % 2 == 0) {
            return (combined[n / 2 - 1] + combined[n / 2]) / 2.0;
        } else {
            return combined[n / 2];
        }
    }

    /**
     * Đếm số cặp phân biệt của các số có tổng bằng giá trị mục tiêu.
     * 
     * 1. Sử dụng HashSet để theo dõi các số đã gặp
     * 2. Với mỗi số num, tính phần bù: complement = target - num
     * 3. Nếu complement đã có trong HashSet, tạo thành một cặp
     * 4. Lưu cặp dưới dạng chuỗi đã sắp xếp để tránh trùng lặp
     * 5. Thêm num vào HashSet để kiểm tra sau
     * 
     * Ý tưởng: Sử dụng HashSet để tìm kiếm O(1) thay vì duyệt lại mảng.
     * Lưu cặp dưới dạng chuỗi để đảm bảo không đếm trùng.
     * 
     */
    public int findDistinctPairsCount(List<Integer> numbers, int target) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }

        Set<Integer> seen = new HashSet<>();
        Set<String> pairs = new HashSet<>();

        for (int num : numbers) {
            int complement = target - num;

            if (seen.contains(complement)) {
                int a = Math.min(num, complement);
                int b = Math.max(num, complement);
                pairs.add(a + "," + b);
            }

            seen.add(num);
        }

        return pairs.size();
    }

    /**
     * Tìm cặp chuỗi có sự trùng lặp ký tự lớn nhất.
     * 
     * 1. So sánh tất cả các cặp chuỗi có thể (nC2)
     * 2. Với mỗi cặp, tính số ký tự chung bằng cách:
     *    - Chuyển mỗi chuỗi thành tập ký tự (HashSet)
     *    - Tìm giao của hai tập ký tự
     *    - Đếm số phần tử trong giao
     * 3. Theo dõi cặp có số ký tự chung lớn nhất
     * 
     * Ý tưởng: Sử dụng HashSet để tìm giao của hai tập ký tự.
     * Giao của hai tập chính là các ký tự xuất hiện trong cả hai chuỗi.

     */
    public List<String> findLargestOverlap(List<String> strings) {
        if (strings == null || strings.size() < 2) {
            throw new IllegalArgumentException("List must contain at least 2 strings");
        }

        String[] result = new String[2];
        int maxOverlap = -1;

        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                String str1 = strings.get(i);
                String str2 = strings.get(j);

                int overlap = calculateOverlap(str1, str2);

                if (overlap > maxOverlap) {
                    maxOverlap = overlap;
                    result[0] = str1;
                    result[1] = str2;
                }
            }
        }

        return Arrays.asList(result);
    }

    /**
     * Tính số ký tự chung giữa hai chuỗi.
     * 
     * 1. Chuyển chuỗi thứ nhất thành tập ký tự (HashSet)
     * 2. Chuyển chuỗi thứ hai thành tập ký tự (HashSet)
     * 3. Tìm giao của hai tập bằng retainAll()
     * 4. Trả về kích thước của giao
     * 
     * Ý tưởng: Giao của hai tập ký tự chính là các ký tự xuất hiện trong cả hai chuỗi.
     * Sử dụng HashSet để loại bỏ trùng lặp và tìm giao hiệu quả.
     * 
     */
    private int calculateOverlap(String str1, String str2) {
        Set<Character> set1 = new HashSet<>();
        for (char c : str1.toCharArray()) {
            set1.add(c);
        }
        Set<Character> set2 = new HashSet<>();
        for (char c : str2.toCharArray()) {
            set2.add(c);
        }

        set1.retainAll(set2);

        return set1.size();
    }

    /**
     * Tìm độ dài của palindrome dài nhất có thể tạo thành từ một chuỗi.
     * 
     * 1. Đếm tần suất xuất hiện của từng ký tự trong chuỗi
     * 2. Với mỗi ký tự:
     *    - Sử dụng số lượng chẵn để tạo các cặp palindrome
     *    - Nếu có số lượng lẻ, có thể sử dụng một ký tự ở giữa
     * 3. Tính tổng: (số lượng chẵn * 2) + (có ký tự lẻ ? 1 : 0)
     * 
     * Ý tưởng: Palindrome có thể tạo thành từ các cặp ký tự giống nhau.
     * Nếu có ký tự xuất hiện lẻ lần, có thể đặt ở giữa palindrome.

     */
    public int findLongestPalindromeLength(String s) {
        if (s == null) {
            return 0;
        }
        
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        int length = 0;
        boolean hasOdd = false;

        for (int count : charCount.values()) {
            length += (count / 2) * 2;
            if (count % 2 == 1) {
                hasOdd = true;
            }
        }

        if (hasOdd) {
            length++;
        }

        return length;
    }

    /**
     * Tìm tổng lớn nhất của các số không liền kề trong danh sách.
     * 
     * Hướng giải quyết (Quy hoạch động):
     * 1. Sử dụng hai biến: include (bao gồm phần tử hiện tại) và exclude (loại trừ)
     * 2. Với mỗi phần tử:
     *    - include mới = phần tử hiện tại + exclude cũ
     *    - exclude mới = max(include cũ, exclude cũ)
     * 3. Kết quả = max(include cuối, exclude cuối)
     * 
     * Ý tưởng: Tại mỗi vị trí, có hai lựa chọn:
     * - Bao gồm phần tử hiện tại (không thể bao gồm phần tử trước đó)
     * - Loại trừ phần tử hiện tại (có thể bao gồm hoặc loại trừ phần tử trước đó)
     */
    public int findMaxNonAdjacentSum(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        int include = numbers.get(0);
        int exclude = 0;

        for (int i = 1; i < numbers.size(); i++) {
            int temp = include;
            include = numbers.get(i) + exclude;
            exclude = Math.max(temp, exclude);
        }

        return Math.max(include, exclude);
    }

    /**
     * Sắp xếp danh sách chuỗi theo số ký tự duy nhất, sau đó theo độ dài.
     * 
     * 1. Tạo comparator tùy chỉnh với hai tiêu chí sắp xếp:
     *    - Tiêu chí chính: số ký tự duy nhất (giảm dần)
     *    - Tiêu chí phụ: độ dài chuỗi (giảm dần)
     * 2. Sử dụng Comparator.comparingInt() và thenComparingInt()
     * 3. Đếm ký tự duy nhất bằng HashSet
     * 
     * Ý tưởng: Sử dụng comparator tùy chỉnh để sắp xếp theo nhiều tiêu chí.
     * HashSet tự động loại bỏ trùng lặp khi đếm ký tự duy nhất.
     */
    public List<String> sortStringsByUniqueChars(List<String> strings) {
        if (strings == null) {
            return new ArrayList<>();
        }

        List<String> sortedList = new ArrayList<>(strings);
        sortedList.sort(Comparator.comparingInt(this::countDistinctCharacters)
                .thenComparingInt(String::length));

        return sortedList;
    }

    /**
     * Đếm số ký tự duy nhất trong một chuỗi.
     * 
     * 1. Cắt khoảng trắng đầu cuối chuỗi
     * 2. Chuyển chuỗi thành mảng ký tự
     * 3. Thêm từng ký tự vào HashSet (tự động loại bỏ trùng lặp)
     * 4. Trả về kích thước của HashSet
     * 
     * Ý tưởng: HashSet chỉ lưu trữ các phần tử duy nhất.
     * Khi thêm ký tự vào HashSet, nếu đã tồn tại sẽ không thêm vào nữa.
     * Kích thước HashSet chính là số ký tự duy nhất.

     */
    private int countDistinctCharacters(String str) {
        Set<Character> distinctChars = new HashSet<>();
        for (char c : str.trim().toCharArray()) {
            distinctChars.add(c);
        }
        return distinctChars.size();
    }
}
