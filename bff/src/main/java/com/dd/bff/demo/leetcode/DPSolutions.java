package com.dd.bff.demo.leetcode;

/**
 * @author Bryce_dd 2022/2/19 18:38
 * 动态规划问题的几个典型特征：最优子结构、状态转移方程、边界、重叠字问题；
 */
public class DPSolutions {

    private static final int jumpParam = 4;
    private static final int[] lengthOfLISParam = {10, 9, 2, 5, 3, 7, 101, 18};

    public static void main(String[] args) {
        System.out.println("D688+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(D688.knightProbability(3,2,0,0));
        System.out.println("D300+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(D300.lengthOfLIS(lengthOfLISParam));
        System.out.println("DO10+++++++++++++++++++++++++++++++++++++++++");
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

    /**
     * <strong>688. 骑士在棋盘上的概率</strong><br/>
     * 在一个n x n的国际象棋棋盘上，一个骑士从单元格 (row, column开始，并尝试进行 k 次移动。<br/>
     * 行和列是 从 0 开始 的，所以左上单元格是 (0,0) ，右下单元格是 (n - 1, n - 1) 。<br/>
     * 象棋骑士有8种可能的走法，如下图所示。每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格。<br/>
     * 每次骑士要移动时，它都会随机从8种可能的移动中选择一种(即使棋子会离开棋盘)，然后移动到那里。<br/>
     * 骑士继续移动，直到它走了 k 步或离开了棋盘。<br/>
     * 返回 骑士在棋盘停止移动后仍留在棋盘上的概率 。<br/>
     * <strong>题解:</strong><br/>
     * <p>
     * f[i][j][p]=∑f[nx][ny][p−1]/8
     * <br/>
     * 一个骑士有 88 种可能的走法，骑士会从中以等概率随机选择一种。部分走法可能会让骑士离开棋盘，另外的走法则会让骑士移动到棋盘的其他位置，<br/>
     * 并且剩余的移动次数会减少 1。<br/>
     * 定义 dp[step][i][j] 表示骑士从棋盘上的点 (i,j) 出发，走了 step 步时仍然留在棋盘上的概率。特别地，当点 (i,j) 不在棋盘上时，<br/>
     * dp[step][i][j]=0；当点(i,j) 在棋盘上且step=0 时，dp[step][i][j]=1。对于其他情况，<br/>
     * dp[step][i][j]= (1/8) ∑dp[step -1][i + di][j + dj].(di,dj)表示走法对坐标的偏移量，其为：<br/>
     * (−2,−1),(−2,1),(2,−1),(2,1),(−1,−2),(−1,2),(1,−2),(1,2) 八种；<br/>
     */
    static class D688 {

        // 每一次移动可能出现的x，y轴变化可能性
        private static final int[][] dirs = new int[][]{{-1, -2}, {-1, 2}, {1, 2}, {1, -2}, {-2, -1}, {-2, 1}, {2, -1}, {2, 1}};

        public static double knightProbability(int n, int k, int row, int column) {
            // 有可能走0步，所以此处为k+1；
            double[][][] dp = new double[n][n][k + 1];
            // 初始化,先为走0步赋初始值
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j][0] = 1;
                }
            }
            // 开始从第一步循环，找出在棋盘内的每一步
            for (int p = 1; p <= k; p++) {
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        for (int[] dir : dirs) {
                            int nx = x + dir[0];
                            int ny = y + dir[1];
                            if (nx < 0 || ny < 0 || nx >= n || ny >= n) {
                                // 落在了棋盘外,此处的概率作废
                                continue;
                            }
                            // 落在棋盘内,讲所有可能性做加和；
                            // 此处概率等于走到某个下一步 反推走回来的概率；
                            dp[x][y][p] += dp[nx][ny][p - 1] / 8;
                        }
                    }
                }
            }
            return dp[row][column][k];
        }
    }
}
