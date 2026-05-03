package com.burime.calculator;

/**
 * Обобщённый функциональный интерфейс для преобразования T -> R.
 */
@FunctionalInterface
public interface Function<T, R> {
    R apply(T value);
}
