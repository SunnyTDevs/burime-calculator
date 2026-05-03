package com.burime.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульные тесты для класса Integrator
 * Тестирование обобщённого метода трапеций с адаптивным выбором шага (критерий Рунге)
 */
@DisplayName("Тесты численного интегрирования")
public class IntegratorTest {

    private static final double EPS = 0.0001;

    // ===== Тестирование интегрирования различных функций =====

    @Test
    @DisplayName("Тест: Интеграл от f(x) = x на [0, 1] (аналитически: 0.5)")
    public void integrateTestLinearFunction() {
        DoubleUnaryOperator f = x -> x;
        double a = 0.0;
        double b = 1.0;
        double expected = 0.5;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от x на [0, 1] должен быть равен 0.5");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = 1 на [0, 10] (аналитически: 10)")
    public void integrateTestConstantFunction() {
        DoubleUnaryOperator f = x -> 1.0;
        double a = 0.0;
        double b = 10.0;
        double expected = 10.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от 1 на [0, 10] должен быть равен 10");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = x² на [0, 1] (аналитически: 1/3 ≈ 0.3333)")
    public void integrateTestQuadraticFunction() {
        DoubleUnaryOperator f = x -> x * x;
        double a = 0.0;
        double b = 1.0;
        double expected = 1.0 / 3.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от x² на [0, 1] должен быть равен 1/3");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = x³ на [0, 2] (аналитически: 4)")
    public void integrateTestCubicFunction() {
        DoubleUnaryOperator f = x -> x * x * x;
        double a = 0.0;
        double b = 2.0;
        double expected = 4.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от x³ на [0, 2] должен быть равен 4");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = sin(x) на [0, π] (аналитически: 2)")
    public void integrateTestSineFunction() {
        DoubleUnaryOperator f = Math::sin;
        double a = 0.0;
        double b = Math.PI;
        double expected = 2.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от sin(x) на [0, π] должен быть равен 2");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = cos(x) на [0, π/2] (аналитически: 1)")
    public void integrateTestCosineFunction() {
        DoubleUnaryOperator f = Math::cos;
        double a = 0.0;
        double b = Math.PI / 2;
        double expected = 1.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от cos(x) на [0, π/2] должен быть равен 1");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = e^x на [0, 1] (аналитически: e - 1 ≈ 1.7183)")
    public void integrateTestExponentialFunction() {
        DoubleUnaryOperator f = Math::exp;
        double a = 0.0;
        double b = 1.0;
        double expected = Math.E - 1;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(expected, actual, EPS,
                "Интеграл от e^x на [0, 1] должен быть равен e - 1");
    }

    // ===== Тестирование IntegrationResult =====

    @Test
    @DisplayName("Тест: Проверка возврата статистики интегрирования")
    public void testIntegrationResultStats() {
        DoubleUnaryOperator f = x -> x * x;
        double a = 0.0;
        double b = 1.0;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, EPS);

        assertNotNull(result);
        assertTrue(result.finalN >= 2, "Количество разбиений должно быть >= 2");
        assertTrue(result.iterations >= 1, "Должна быть хотя бы одна итерация");
        assertTrue(result.rungeError <= EPS, "Погрешность должна удовлетворять критерию Рунге");
    }

    @Test
    @DisplayName("Тест: Интеграл на отрезке нулевой длины [a, a]")
    public void integrateTestZeroLengthInterval() {
        DoubleUnaryOperator f = x -> x * x;
        double a = 5.0;
        double b = 5.0;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, EPS);

        assertEquals(0.0, result.value, EPS,
                "Интеграл на отрезке нулевой длины должен быть равен 0");
    }

    @Test
    @DisplayName("Тест: Интеграл от f(x) = 0 (тождественный ноль)")
    public void integrateTestZeroFunction() {
        DoubleUnaryOperator f = x -> 0.0;
        double a = -100.0;
        double b = 100.0;

        double actual = Integrator.integrate(f, a, b);

        assertEquals(0.0, actual, EPS,
                "Интеграл от нулевой функции должен быть равен 0");
    }

    // ===== Тестирование с пользовательской точностью =====

    @Test
    @DisplayName("Тест: Интеграл с пользовательской точностью eps = 0.001")
    public void integrateTestCustomEpsilon() {
        DoubleUnaryOperator f = x -> x * x;
        double a = 0.0;
        double b = 1.0;
        double customEps = 0.001;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, customEps);

        assertTrue(result.rungeError <= customEps,
                "Погрешность должна быть <= customEps");
    }

    @Test
    @DisplayName("Тест: Интеграл с высокой точностью eps = 0.00001")
    public void integrateTestHighPrecision() {
        DoubleUnaryOperator f = x -> x;
        double a = 0.0;
        double b = 1.0;
        double highEps = 0.00001;

        IntegrationResult result = Integrator.integrateWithStats(f, a, b, highEps);

        assertTrue(result.rungeError <= highEps,
                "Погрешность должна быть <= highEps");
    }

    // ===== Тестирование обобщённой формулы трапеций напрямую =====

    @Test
    @DisplayName("Тест: Проверка обобщённой формулы трапеций для n=2")
    public void testGeneralizedTrapezoidN2() {
        DoubleUnaryOperator f = x -> x;
        double a = 0.0;
        double b = 1.0;

        double result = Integrator.generalizedTrapezoid(f, a, b, 2);

        assertEquals(0.625, result, 0.001,
                "Трапеция с 2 разбиениями должна давать 0.625");
    }

    @Test
    @DisplayName("Тест: Проверка обобщённой формулы трапеций для константы")
    public void testGeneralizedTrapezoidConstant() {
        DoubleUnaryOperator f = x -> 1.0;
        double a = 0.0;
        double b = 1.0;

        double result = Integrator.generalizedTrapezoid(f, a, b, 100);

        assertEquals(1.0, result, 1e-10,
                "Интеграл от 1 должен быть точно равен 1");
    }
}
