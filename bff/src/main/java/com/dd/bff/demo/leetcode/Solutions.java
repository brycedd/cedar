package com.dd.bff.demo.leetcode;

/**
 * @author Bryce_dd 2022/2/14 22:49
 */
public class Solutions {

    private static final int[] singleNonDuplicateParam = new int[]{1, 1, 2, 2, 8, 8, 7, 12, 12, 13, 13};

    public static void main(String[] args) {
        System.out.println(singleNonDuplicate(singleNonDuplicateParam));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * <strong>540. 有序数组中的单一元素 </strong><br/>
     * 给你一个仅由整数组成的有序数组，其中每个元素都会出现两次，唯有一个数只会出现一次。<br/>
     * 请你找出并返回只出现一次的那个数。<br/>
     * 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。<br/>
     * <strong>题解:</strong><br/>
     * 通过时间和空间复杂度要求，可以看出使用二分法；</br>
     * 由于数组是有序的，所以所有相同的数字应该都是相邻的；所以目标数字的下标一定是偶数</br>
     * x一定为偶数，二分查找的目标是找到满足nums[x]!=nums[x + 1] </br>
     */
    public static int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            // 取二分数
            int mid = (high - low) / 2 + low;
            // 由题意可知，我们需要检索的只有偶数下标;奇数&1为1，偶数&1为0
            mid -= mid & 1;
            if (nums[mid] == nums[mid + 1]) {
                // 这种情况说明，mid == mid + 1 是一对数， 并且mid之前是偶数个数字，那么，mid之前的一定是成对出现的数
                low = mid + 2;
            } else {
                // 这种情况说明，mid ！= mid + 1,且mid + 1 及之后的数为偶数个数，那么后半段一定不会出现单个数
                high = mid;
            }
        }
        return nums[low];
    }
}
