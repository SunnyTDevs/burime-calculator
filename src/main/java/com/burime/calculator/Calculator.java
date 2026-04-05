package com.burime.calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первое число: ");
        int a = scanner.nextInt();
        System.out.println("Введите операцию (+, -):");
        String op = scanner.next();
        System.out.println("Введите второе число:");
        int b = scanner.nextInt();
        int result = op.equals("+") ? a + b : a - b;
        System.out.println("Результат: " + result);
    }
}
