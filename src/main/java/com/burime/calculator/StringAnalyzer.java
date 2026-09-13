package com.burime.calculator;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public final class StringAnalyzer {
    private StringAnalyzer() {
    }

    public static boolean isPalindrome(String value) {
        String normalized = normalize(value);
        int left = 0;
        int right = normalized.length() - 1;

        while (left < right) {
            if (normalized.charAt(left) != normalized.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public static boolean isAnagram(String first, String second) {
        char[] firstLetters = normalize(first).toCharArray();
        char[] secondLetters = normalize(second).toCharArray();
        Arrays.sort(firstLetters);
        Arrays.sort(secondLetters);
        return Arrays.equals(firstLetters, secondLetters);
    }

    public static String[] getPalindromicSubstrings(String value) {
        Objects.requireNonNull(value, "Строка не может быть null");
        Set<String> substrings = new LinkedHashSet<>();

        for (int start = 0; start < value.length(); start++) {
            for (int end = start + 3; end <= value.length(); end++) {
                String substring = value.substring(start, end);
                if (isPalindrome(substring) && normalize(substring).length() >= 3) {
                    substrings.add(substring);
                }
            }
        }

        return substrings.toArray(String[]::new);
    }

    private static String normalize(String value) {
        Objects.requireNonNull(value, "Строка не может быть null");
        StringBuilder normalized = new StringBuilder();

        value.toLowerCase(Locale.ROOT).codePoints()
                .filter(Character::isLetter)
                .forEach(normalized::appendCodePoint);

        return normalized.toString();
    }
}
