package com.burime.calculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        int fn = 4;
        int ln = 1;

        IntegralCalculator integralCalculator = new IntegralCalculator();
        IntegralCalculator.Result result = integralCalculator.calculate(fn, ln);

        System.out.printf(
                "%n[ИТОГ] Интеграл: %.8f, Шагов: %d, Погрешность: %.8f%n%n",
                result.value(), result.steps(), result.error()
        );

        SequenceCalculator sequenceCalculator = new SequenceCalculator();
        long normalSum = sequenceCalculator.sumArithmeticProgression(1, 2, 5);
        System.out.println("[ИТОГ] Штатная сумма прогрессии: " + normalSum + "\n");

        try {
            log.info("Тест: намеренный вызов переполнения...");
            sequenceCalculator.sumArithmeticProgression(Long.MAX_VALUE - 10, 5, 10);
        } catch (ArithmeticException e) {
            log.warn("Переполнение было успешно зафиксировано и перехвачено в Main");
        }

        log.info("=== Завершение лабораторной работы № 2 ===");
    }
}