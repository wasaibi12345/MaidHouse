package MaidHouse.experiment1;

import java.util.Arrays;

public class subArray {
    public static void main(String[] args) {
        int[] arr1 = {1, -2, 3, 5, -1};
        int[] arr2 = {1, -2, 3, -8, 5, 1};
        int[] arr3 = {1, -2, 3, -2, 5, 1};

        System.out.println(Arrays.toString(maxSubArray(arr1))); // [3, 5]
        System.out.println(Arrays.toString(maxSubArray(arr2))); // [5, 1]
        System.out.println(Arrays.toString(maxSubArray(arr3))); // [3, -2, 5, 1]
    }

    public static int[] maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int start = 0, end = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum > maxSum) {
                    maxSum = sum;
                    start = i;
                    end = j;
                }
            }
        }

        return Arrays.copyOfRange(nums, start, end + 1);
    }
}

