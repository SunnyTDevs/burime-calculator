package com.burime.calculator;

public class Lab1Part1Example1 {

    static int sqr(int x) {
        int q = x * x;
        return q;
    }

    public static void main(String[] args) {
        final int n = 10;
        int[] a = {5, 2, 7, -9, 4, 8, -1, 0, 3, 6};
        int s = 0;
        for (int i = 0; i < n; i++)
            if (a[i] > 0) s += sqr(a[i]);
        System.out.println("Сумма квадратов равна: " + s);
    }
}
