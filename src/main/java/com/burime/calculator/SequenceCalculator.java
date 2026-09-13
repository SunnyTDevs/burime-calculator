package com.burime.calculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SequenceCalculator {

    public long sumArithmeticProgression(long first, long difference, int count) {
        if (count < 0) {
            log.error("Количество элементов не может быть отрицательным: {}", count);
            throw new IllegalArgumentException("Количество элементов не может быть отрицательным");
        }

        long sum = 0;
        long current = first;

        for (int i = 0; i < count; i++) {
            try {
                sum = Math.addExact(sum, current);
                log.trace("Прогрессия: шаг = {}, элемент = {}, текущая сумма = {}", i, current, sum);

                if (i + 1 < count) {
                    current = Math.addExact(current, difference);
                }
            } catch (ArithmeticException exception) {
                log.error(
                        "Зафиксировано арифметическое переполнение на шаге {}: элемент = {}, сумма = {}. Причина: {}",
                        i, current, sum, exception.getMessage()
                );
                throw exception;
            }
        }

        log.info("Сумма арифметической прогрессии успешно вычислена: {}", sum);
        return sum;
    }
}