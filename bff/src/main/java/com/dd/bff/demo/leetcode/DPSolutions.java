package com.dd.bff.demo.leetcode;

/**
 * @author Bryce_dd 2022/2/19 18:38
 * 动态规划问题的几个典型特征：最优子结构、状态转移方程、边界、重叠字问题；
 */
public class DPSolutions {

    private static final int jumpParam = 4;
    private static final int[] lengthOfLISParam = {10,9,2,5,3,7,101,18};

    public static void main(String[] args) {
        System.out.println("D300+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(D300.lengthOfLIS(lengthOfLISParam));
        System.out.println("DD01+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(DO10.jump(jumpParam));

    }

    /**
     * <strong>剑指 Offer 10- II. 青蛙跳台阶问题</strong><br/>
     * 青蛙每次可以跳跃1或者2个台阶，求跳到台阶十有多少种跳法；
     * <strong>题解:</strong><br/>
     * 因为每次可以选择跳一次或者两次，故：fn = f(n-1) + f(n-2);<br/>
     * 其中 f1 = 1, f2 = 2; f3 = f2 + f1;
     */
    static class DO10 {
        public static int jump(int n) {
            int a = 1; // 上到第零个台阶方式只有一种
            int b = 1; // 上到第一个台阶方式有一种；
            int sum = 1; // 输入0时直接返回1；
            for (int i = 1; i < n; i++) {
                sum = (a + b) % 1000000007;
                a = b;
                b = sum;
            }
            return sum;
        }
    }

    /**
     * <strong>300. 最长递增子序列</strong><br/>
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。<br/>
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。<br/>
     * <strong>题解:</strong><br/>
     * 首先找出关系，每添加一个数字，那么，nums[i]的最长递增子序列就可能是： <br/>
     * 若存在j， 0 <= j < i, 且 nums[j] < nums[i ]那么 dp(i) = max(dp(j)) + 1 <br/>
     */
    static class D300 {
        public static int lengthOfLIS(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            // 初始化
            int[] dp = new int[nums.length]; // 用于存储每次计算结果
            dp[0] = 1;
            int max = 1;
            // 遍历并记忆
            for (int i = 1; i < nums.length; i++) {
                dp[i] = 1;
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        // 存在比新加入数字nums[i] 小的元素
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                max = Math.max(dp[i], max);
            }
            return max;
        }
    }

    static class D688 {

    }
}
