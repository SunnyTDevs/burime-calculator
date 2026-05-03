package com.burime.calculator;

import java.util.function.DoubleUnaryOperator;

public class Main {
    private static final double EPS = 0.0001;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Численное интегрирование");
        System.out.println("  Метод обобщённых трапеций");
        System.out.println("  Правило Рунге");
        System.out.println("========================================\n");

        // Вариант 8: f(x) = 1 / (sqrt(x) * (exp(0.9*x) + 3)), a = 0.5, b = 2.0
        DoubleUnaryOperator f8 = x -> 1.0 / (Math.sqrt(x) * (Math.exp(0.9 * x) + 3));
        printResult("Вариант 8", f8, 0.5, 2.0);

        // Вариант 11: f(x) = sin(x + 2) / (0.4 + cos(x)), a = -1.0, b = 1.0
        DoubleUnaryOperator f11 = x -> Math.sin(x + 2) / (0.4 + Math.cos(x));
        printResult("Вариант 11", f11, -1.0, 1.0);

        // Вариант 13: f(x) = (x*x + sin(0.48*(x + 2))) / exp(x*x + 0.38), a = 0.4, b = 1.0
        DoubleUnaryOperator f13 = x -> {
            double numerator = x * x + Math.sin(0.48 * (x + 2));
            double denominator = Math.exp(x * x + 0.38);
            return numerator / denominator;
        };
        printResult("Вариант 13", f13, 0.4, 1.0);

        System.out.println("\n========================================");
        System.out.println("  Проверочные тесты");
        System.out.println("========================================\n");

        // Проверочный тест: интеграл от x на [0, 1] = 0.5
        printResult("∫x dx на [0,1] = 0.5", x -> x, 0.0, 1.0);

        // Проверочный тест: интеграл от 1 на [0, 1] = 1
        printResult("∫1 dx на [0,1] = 1", x -> 1.0, 0.0, 1.0);

        // Проверочный тест: интеграл от x² на [0, 1] = 1/3
        printResult("∫x² dx на [0,1] = 1/3", x -> x * x, 0.0, 1.0);
    }

    private static void printResult(String title, DoubleUnaryOperator f, double a, double b) {
        IntegrationResult result = Integrator.integrateWithStats(f, a, b, EPS);

        System.out.println(title);
        System.out.println("-".repeat(45));
        System.out.printf("  Отрезок интегрирования: [%.1f, %.1f]%n", a, b);
        System.out.printf("  Точность (eps): %.4f%n", EPS);
        System.out.printf("  Результат I = %.10f%n", result.value);
        System.out.printf("  Количество разбиений (n): %d%n", result.finalN);
        System.out.printf("  Количество итераций: %d%n", result.iterations);
        System.out.printf("  Погрешность (Рунге): %.10f%n", result.rungeError);
        System.out.printf("  Критерий Рунге (|I(2n)-I(n)|/3 <= eps): %s%n",
                result.rungeError <= EPS ? "✓ ВЫПОЛНЕН" : "✗ НЕ ВЫПОЛНЕН");
        System.out.println();
    }
}
