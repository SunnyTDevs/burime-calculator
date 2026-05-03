package com.burime.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты интегрирования по вариантам 8, 11, 13")
public class VariantIntegrationTest {

    private static final double EPS = 0.0001;

    // ===== Вариант 8: f(x) = 1 / (sqrt(x) * (exp(0.9*x) + 3)), a = 0.5, b = 2.0 =====

    @Test
    @DisplayName("Вариант 8: Интеграл от 1/(sqrt(x)*(exp(0.9x)+3)) на [0.5, 2.0]")
    public void testVariant8() {
        DoubleUnaryOperator f8 = x -> 1.0 / (Math.sqrt(x) * (Math.exp(0.9 * x) + 3));
        double a = 0.5;
        double b = 2.0;

        IntegrationResult result = Integrator.integrateWithStats(f8, a, b, EPS);

        System.out.println("\n===== Вариант 8 =====");
        System.out.println("Функция: f(x) = 1 / (sqrt(x) * (exp(0.9*x) + 3))");
        System.out.println("Отрезок: [" + a + ", " + b + "]");
        System.out.println("Точность eps: " + EPS);
        System.out.println("Результат интегрирования: " + result.value);
        System.out.println("Количество разбиений (n): " + result.finalN);
        System.out.println("Количество итераций: " + result.iterations);
        System.out.println("Погрешность (правило Рунге): " + result.rungeError);

        assertTrue(result.value > 0, "Интеграл должен быть положительным");
        assertTrue(result.value < 1, "Интеграл должен быть меньше 1");
        assertTrue(result.rungeError <= EPS, "Погрешность должна удовлетворять критерию Рунге");
        assertTrue(result.finalN >= 2, "Минимальное количество разбиений - 2");
    }

    // ===== Вариант 11: f(x) = sin(x + 2) / (0.4 + cos(x)), a = -1.0, b = 1.0 =====

    @Test
    @DisplayName("Вариант 11: Интеграл от sin(x+2)/(0.4+cos(x)) на [-1.0, 1.0]")
    public void testVariant11() {
        DoubleUnaryOperator f11 = x -> Math.sin(x + 2) / (0.4 + Math.cos(x));
        double a = -1.0;
        double b = 1.0;

        IntegrationResult result = Integrator.integrateWithStats(f11, a, b, EPS);

        System.out.println("\n===== Вариант 11 =====");
        System.out.println("Функция: f(x) = sin(x + 2) / (0.4 + cos(x))");
        System.out.println("Отрезок: [" + a + ", " + b + "]");
        System.out.println("Точность eps: " + EPS);
        System.out.println("Результат интегрирования: " + result.value);
        System.out.println("Количество разбиений (n): " + result.finalN);
        System.out.println("Количество итераций: " + result.iterations);
        System.out.println("Погрешность (правило Рунге): " + result.rungeError);

        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.rungeError <= EPS, "Погрешность должна удовлетворять критерию Рунге");
        assertTrue(result.iterations > 0, "Должна быть хотя бы одна итерация");
    }

    // ===== Вариант 13: f(x) = (x*x + sin(0.48*(x + 2))) / exp(x*x + 0.38), a = 0.4, b = 1.0 =====

    @Test
    @DisplayName("Вариант 13: Интеграл от (x^2+sin(0.48(x+2)))/exp(x^2+0.38) на [0.4, 1.0]")
    public void testVariant13() {
        DoubleUnaryOperator f13 = x -> {
            double numerator = x * x + Math.sin(0.48 * (x + 2));
            double denominator = Math.exp(x * x + 0.38);
            return numerator / denominator;
        };
        double a = 0.4;
        double b = 1.0;

        IntegrationResult result = Integrator.integrateWithStats(f13, a, b, EPS);

        System.out.println("\n===== Вариант 13 =====");
        System.out.println("Функция: f(x) = (x*x + sin(0.48*(x + 2))) / exp(x*x + 0.38)");
        System.out.println("Отрезок: [" + a + ", " + b + "]");
        System.out.println("Точность eps: " + EPS);
        System.out.println("Результат интегрирования: " + result.value);
        System.out.println("Количество разбиений (n): " + result.finalN);
        System.out.println("Количество итераций: " + result.iterations);
        System.out.println("Погрешность (правило Рунге): " + result.rungeError);

        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.rungeError <= EPS, "Погрешность должна удовлетворять критерию Рунге");
        assertTrue(result.value >= 0, "Интеграл должен быть неотрицательным (числитель положительный)");
    }

    // ===== Дополнительные тесты для проверки обобщённой формулы трапеций =====

    @Test
    @DisplayName("Проверка: интеграл от 1 на [0, 1] должен быть равен 1")
    public void testGeneralizedTrapezoidConstantFunction() {
        DoubleUnaryOperator f = x -> 1.0;
        double a = 0.0;
        double b = 1.0;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, EPS);

        System.out.println("\n===== Тест константной функции =====");
        System.out.println("Интеграл от 1 на [0, 1] = " + result.value);
        System.out.println("Погрешность: " + result.rungeError);

        assertEquals(1.0, result.value, 0.001, "Интеграл от 1 на [0,1] должен быть равен 1");
    }

    @Test
    @DisplayName("Проверка: интеграл от x на [0, 1] должен быть равен 0.5")
    public void testGeneralizedTrapezoidLinearFunction() {
        DoubleUnaryOperator f = x -> x;
        double a = 0.0;
        double b = 1.0;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, EPS);

        System.out.println("\n===== Тест линейной функции =====");
        System.out.println("Интеграл от x на [0, 1] = " + result.value);
        System.out.println("Погрешность: " + result.rungeError);

        assertEquals(0.5, result.value, EPS, "Интеграл от x на [0,1] должен быть равен 0.5");
    }

    // ===== Тест с различными точностями =====

    @Test
    @DisplayName("Вариант 8 с разной точностью")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testVariant8WithDifferentPrecisions() {
        DoubleUnaryOperator f8 = x -> 1.0 / (Math.sqrt(x) * (Math.exp(0.9 * x) + 3));
        double a = 0.5;
        double b = 2.0;

        System.out.println("\n===== Вариант 8 с разной точностью =====");

        double[] precisions = {0.001, 0.0001, 0.00001};
        double[] expectedResults = {0.21, 0.21, 0.21};

        for (int i = 0; i < precisions.length; i++) {
            IntegrationResult result = Integrator.integrateWithStats(f8, a, b, precisions[i]);
            System.out.printf("eps=%.5f: I=%.8f, n=%d, погрешность=%.10f%n",
                    precisions[i], result.value, result.finalN, result.rungeError);

            assertTrue(result.rungeError <= precisions[i],
                    "Погрешность должна быть <= eps для eps=" + precisions[i]);
        }
    }
}
