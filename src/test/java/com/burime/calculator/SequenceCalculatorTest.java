package com.burime.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SequenceCalculatorTest {
    private final SequenceCalculator calculator = new SequenceCalculator();

    @Test
    void sumsArithmeticProgression() {
        assertEquals(100, calculator.sumArithmeticProgression(1, 2, 10));
    }

    @Test
    void detectsElementOverflow() {
        assertThrows(
                AssertionError.class,
                () -> calculator.sumArithmeticProgression(Integer.MAX_VALUE, 1, 2)
        );
    }

    @Test
    void rejectsNegativeElementCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.sumArithmeticProgression(1, 2, -1)
        );
    }
}
