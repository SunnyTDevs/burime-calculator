package com.burime.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegralCalculatorTest {
    private final IntegralCalculator calculator = new IntegralCalculator();

    @Test
    void calculatesIntegralWithRequiredPrecision() {
        IntegralCalculator.Result result = calculator.calculate();

        assertEquals(-0.0129718713, result.value(), 0.0001);
        assertTrue(result.error() <= 0.0001);
        assertTrue(result.steps() > 1);
    }

    @Test
    void rejectsNonPositiveStepCount() {
        assertThrows(IllegalArgumentException.class, () -> calculator.integrate(0));
    }
}
