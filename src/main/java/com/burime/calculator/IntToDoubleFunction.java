package com.burime.calculator;

/**
 * Функциональный интерфейс для преобразования int -> double.
 */
@FunctionalInterface
public interface IntToDoubleFunction {
    double applyAsDouble(int value);
}
