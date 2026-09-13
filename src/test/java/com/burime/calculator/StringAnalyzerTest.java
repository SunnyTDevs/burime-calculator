package com.burime.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringAnalyzerTest {
    @Test
    void isPalindromeReturnsTrueForRegularPalindrome() {
        String value = "шалаш";

        boolean actual = StringAnalyzer.isPalindrome(value);

        assertTrue(actual);
    }

    @Test
    void isPalindromeIgnoresCaseAndNonLetterCharacters() {
        String value = "А роза упала на лапу Азора!";

        boolean actual = StringAnalyzer.isPalindrome(value);

        assertTrue(actual);
    }

    @Test
    void isPalindromeReturnsFalseForNonPalindrome() {
        String value = "лаборатория";

        boolean actual = StringAnalyzer.isPalindrome(value);

        assertFalse(actual);
    }

    @Test
    void isPalindromeReturnsTrueForEmptyString() {
        String value = "";

        boolean actual = StringAnalyzer.isPalindrome(value);

        assertTrue(actual);
    }

    @Test
    void isPalindromeThrowsExceptionForNull() {
        String value = null;

        assertThrows(NullPointerException.class, () -> StringAnalyzer.isPalindrome(value));
    }

    @Test
    void isAnagramReturnsTrueForAnagrams() {
        String first = "апельсин";
        String second = "спаниель";

        boolean actual = StringAnalyzer.isAnagram(first, second);

        assertTrue(actual);
    }

    @Test
    void isAnagramIgnoresCaseAndNonLetterCharacters() {
        String first = "Том Нарволо Реддл";
        String second = "Лорд Волан-де-Морт";

        boolean actual = StringAnalyzer.isAnagram(first, second);

        assertTrue(actual);
    }

    @Test
    void isAnagramAccountsForRepeatedLetters() {
        String first = "тест";
        String second = "тес";

        boolean actual = StringAnalyzer.isAnagram(first, second);

        assertFalse(actual);
    }

    @Test
    void isAnagramReturnsFalseForDifferentStrings() {
        String first = "кот";
        String second = "кит";

        boolean actual = StringAnalyzer.isAnagram(first, second);

        assertFalse(actual);
    }

    @Test
    void isAnagramThrowsExceptionForNull() {
        String first = null;
        String second = "строка";

        assertThrows(NullPointerException.class, () -> StringAnalyzer.isAnagram(first, second));
    }

    @Test
    void getPalindromicSubstringsReturnsAllUniqueSubstrings() {
        String value = "шалаш";

        String[] actual = StringAnalyzer.getPalindromicSubstrings(value);

        assertArrayEquals(new String[]{"шалаш", "ала"}, actual);
    }

    @Test
    void getPalindromicSubstringsDoesNotReturnDuplicates() {
        String value = "aaaa";

        String[] actual = StringAnalyzer.getPalindromicSubstrings(value);

        assertArrayEquals(new String[]{"aaa", "aaaa"}, actual);
    }

    @Test
    void getPalindromicSubstringsReturnsEmptyArrayWhenNoneExist() {
        String value = "abc";

        String[] actual = StringAnalyzer.getPalindromicSubstrings(value);

        assertArrayEquals(new String[0], actual);
    }

    @Test
    void getPalindromicSubstringsReturnsEmptyArrayForShortString() {
        String value = "aa";

        String[] actual = StringAnalyzer.getPalindromicSubstrings(value);

        assertArrayEquals(new String[0], actual);
    }

    @Test
    void getPalindromicSubstringsThrowsExceptionForNull() {
        String value = null;

        assertThrows(NullPointerException.class, () -> StringAnalyzer.getPalindromicSubstrings(value));
    }
}
