package com.burime.calculator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String text = "А роза упала на лапу Азора";

        System.out.println("Палиндром: " + StringAnalyzer.isPalindrome(text));
        System.out.println("Анаграммы: " + StringAnalyzer.isAnagram("апельсин", "спаниель"));
        System.out.println(
                "Палиндромные подстроки: "
                        + Arrays.toString(StringAnalyzer.getPalindromicSubstrings("шалаш"))
        );
    }
}
