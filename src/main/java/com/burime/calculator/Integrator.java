package com.burime.calculator;

import java.util.function.DoubleUnaryOperator;

public class Integrator {

    private static final double DEFAULT_EPS = 0.0001;

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        return integrate(f, a, b, DEFAULT_EPS);
    }

    public static double integrate(DoubleUnaryOperator f, double a, double b, double eps) {
        IntegrationResult result = integrateWithStats(f, a, b, eps);
        return result.value;
    }

    public static IntegrationResult integrateWithStats(DoubleUnaryOperator f, double a, double b, double eps) {
        int n = 2;
        double i_n = generalizedTrapezoid(f, a, b, n);
        double i_2n = generalizedTrapezoid(f, a, b, n * 2);
        int iterations = 1;

        while (Math.abs(i_2n - i_n) / 3 > eps) {
            n *= 2;
            i_n = i_2n;
            i_2n = generalizedTrapezoid(f, a, b, n * 2);
            iterations++;

            if (iterations > 1000) {
                throw new RuntimeException("Интегрирование не сошлось за 1000 итераций");
            }
        }

        double rungeError = Math.abs(i_2n - i_n) / 3;

        return new IntegrationResult(i_2n, iterations, n * 2, rungeError);
    }

    public static double generalizedTrapezoid(DoubleUnaryOperator f, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i <= n; i++) {
            double x_i = a + i * h;
            double coefficient;

            if (i == 0 || i == n) {
                coefficient = 1.0;
            } else {
                coefficient = 2.0;
            }

            sum += coefficient * f.applyAsDouble(x_i);
        }

        return (h / 2.0) * sum;
    }
}
