package com.burime.calculator;

import java.util.Scanner;

public class Lab1Part2 {

    static int sum(int[] x, int n) {
        int s = 0;
        for (int i = 0; i < n; i++)
            s += x[i];
        return s;
    }

    static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        int x = Integer.parseInt(scanner.nextLine().trim());
        return x;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int n = 10;
        int[] a = {1, 3, -5, 0, 4, 6, -1, 9, 3, 2};

        int m = a[0];
        for (int i = 1; i < n; i++)
            if (m < a[i])
                m = a[i];
        System.out.println(m);

        int s;
        s = sum(a, n);
        System.out.println(s);

        int z = s / m;
        int k = 0;
        for (int i = 0; i < n; i++)
            if (a[i] > z)
                k += a[i];
            else
                k -= a[i];
        System.out.println(k);

        int x = 3;
        int y = 5;
        s = 0;
        while ((x != 0) && (y != 0)) {
            x--;
            y--;
            s += x + y;
        }
        System.out.println(s);
    }
}
