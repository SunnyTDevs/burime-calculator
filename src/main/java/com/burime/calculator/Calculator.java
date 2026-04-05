package com.burime.calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Простой калькулятор");
        System.out.println("Введите первое число:");
        double firstNumber = scanner.nextDouble();
        System.out.println("Введите операцию (+, -, *, /):");
        String operation = scanner.next();
        System.out.println("Введите второе число:");
        double secondNumber = scanner.nextDouble();
        double result = 0;
        // TODO: Студенты будут дописывать логику вычислений построчно
        scanner.close();
    }
}
