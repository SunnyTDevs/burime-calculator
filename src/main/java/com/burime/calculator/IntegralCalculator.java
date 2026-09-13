package com.burime.calculator;

import lombok.extern.slf4j.Slf4j;
import java.util.function.DoubleUnaryOperator;

@Slf4j
public class IntegralCalculator {
    private static final double A = 1.0;
    private static final double B = 1.2;
    private static final double EPSILON = 0.0001;
    private static final int RUNGE_DIVISOR = 3;

    private final DoubleUnaryOperator function = x -> (x * x * x * (1.0 - x)) / (x + 1.0);

    public record Result(double value, int steps, double error) {}

    public Result calculate(int fn, int ln) {
        log.info("Старт вычисления интеграла. FN = {}, LN = {}", fn, ln);

        int steps = 1;
        double previous = integrate(steps);
        double current = previous;
        double error = Double.MAX_VALUE;
        int iteration = 1;

        int targetIterations = Math.max(fn, ln);

        do {
            steps *= 2;
            current = integrate(steps);
            error = Math.abs(current - previous) / RUNGE_DIVISOR;

            log.trace(
                    "Правило Рунге (итерация {}): шагов = {}, I(n) = {}, I(2n) = {}, погрешность = {}",
                    iteration, steps / 2, previous, current, error
            );

            if (iteration == fn) {
                log.debug("[FN-шаг = {}] Значение интеграла в отладчик: {}", fn, current);
            }

            if (iteration == ln) {
                log.trace("[LN-шаг = {}] Значение интеграла в трассировщик: {}", ln, current);
            }

            previous = current;
            iteration++;
        } while (error > EPSILON || iteration <= targetIterations);

        log.info("Интеграл вычислен: значение = {}, шагов разбиения = {}, погрешность = {}", current, steps, error);
        return new Result(current, steps, error);
    }

    double integrate(int steps) {
        if (steps <= 0) {
            log.error("Количество шагов должно быть положительным: {}", steps);
            throw new IllegalArgumentException("Количество шагов должно быть > 0");
        }

        double step = (B - A) / steps;
        double sum = (function.applyAsDouble(A) + function.applyAsDouble(B)) / 2.0;

        for (int i = 1; i < steps; i++) {
            double x = A + i * step;

            if (x < A || x > B) {
                log.error("Нарушение границ интегрирования: x = {}, допустимый интервал [{}, {}]", x, A, B);
                throw new IllegalStateException("Выход переменной x за границы интегрирования");
            }

            double fx = function.applyAsDouble(x);
            sum += fx;

            log.trace("Сетка: итерация = {}, x = {}, f(x) = {}, сумма = {}", i, x, fx, sum);
        }

        return sum * step;
    }
}