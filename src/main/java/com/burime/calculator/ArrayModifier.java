package com.burime.calculator;

public final class ArrayModifier {

    private ArrayModifier() {
    }

    public static boolean replaceLast(int[] a, int target, int replacement) {
        if (a == null) {                                                    // 1
            throw new IllegalArgumentException("Массив не может быть null"); // 2
        }

        for (int i = a.length - 1;                                         // 3
             i >= 0;                                                        // 4
             i--) {                                                         // 5
            if (a[i] == target) {                                           // 6
                a[i] = replacement;                                         // 7
                return true;                                                // 8
            }
        }

        return false;                                                       // 9
    }
}