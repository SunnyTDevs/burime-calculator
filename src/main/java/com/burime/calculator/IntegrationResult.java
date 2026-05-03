package com.burime.calculator;

import java.util.function.DoubleUnaryOperator;

public class IntegrationResult {
    public final double value;
    public final int iterations;
    public final int finalN;
    public final double rungeError;

    public IntegrationResult(double value, int iterations, int finalN, double rungeError) {
        this.value = value;
        this.iterations = iterations;
        this.finalN = finalN;
        this.rungeError = rungeError;
    }
}
