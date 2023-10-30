package com.dd.demo;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 7, 9, 0, 2, 1};
//        System.out.println(getMax(arr));
        System.out.println(getMax2(arr));
        Map<String, Integer> map = new HashMap<>();
    }

    public static int getMax(int[] arr) {
        if (arr.length <= 1) {
            return -1;
        }
        int max = 0;
        int maxI = 0;
        int maxJ = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == 0) {
                    continue;
                }
                int max1 = Math.max(max, arr[i] / arr[j]);
                if (max1 > max) {
                    maxI = i;
                    maxJ = j;
                    max = max1;
                }
            }
        }
        System.out.println("maxI: " + maxI + " maxJ: " + maxJ);
        System.out.println("max: " + max);
        return max;
    }

    public static int getMax2(int[] arr) {
        int maxJ = -1;
        int minI = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            maxJ = Math.max(maxJ, arr[i]);
            if (arr[i] != 0) {
                minI = Math.min(minI, arr[i]);
            }
        }
        return maxJ/minI;

    }
}
