package com.dd.bff.demo.leetcode;

import java.util.ArrayList;

/**
 * @author Bryce_dd 2021/12/26 18:14
 * 给出第一个词first 和第二个词second，考虑在某些文本text中可能以 "first second third" 形式出现的情况，其中second紧随first出现，third紧随second出现。
 *
 * 对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
 */
public class L1078 {

    public static void main(String[] args) {
        final String[] ocurrences = findOcurrences("alice is a good girl she is a good student", "a", "good");
        for (String ocurrence : ocurrences) {
            System.out.print(ocurrence + " ");
        }
    }

    public static String[] findOcurrences(String text, String a, String b) {
        String[] str = text.split(" ");
        int length = str.length;
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i + 2 < length; i++) {
            if (str[i].equals(a) && str[i + 1].equals(b)) {
                list.add(str[i + 2]);
            }
        }
        return list.toArray(new String[0]);
    }
}
