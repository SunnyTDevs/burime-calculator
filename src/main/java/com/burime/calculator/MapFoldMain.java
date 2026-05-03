package com.burime.calculator;

import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Демонстрация MAP и FOLD операций для обработки списков.
 */
public class MapFoldMain {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  MAP и FOLD - Обработка списков");
        System.out.println("========================================\n");

        // Исходные данные для демонстрации
        double[] sourceArray = {1.0, 2.0, 3.0, 4.0, 5.0};
        List<Double> sourceList = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

        // ==================== MAP ====================
        System.out.println("=".repeat(50));
        System.out.println("  MAP - Отображение элементов списка");
        System.out.println("=".repeat(50));
        System.out.println("\nИсходный массив: " + ListProcessor.arrayToString(sourceArray));
        System.out.println();

        // MAP: Вариант 3 - F(x) = exp(x)
        demonstrateMap("MAP: F(x) = exp(x)",
                sourceArray,
                Math::exp,
                "Вариант 3");

        // MAP: Вариант 8 - F(x) = sin(x)
        demonstrateMap("MAP: F(x) = sin(x)",
                sourceArray,
                Math::sin,
                "Вариант 8");

        // MAP: Вариант 12 - F(x) = floor(x)
        demonstrateMap("MAP: F(x) = floor(x)",
                new double[]{1.7, 2.3, 3.8, 4.1, 5.5},
                Math::floor,
                "Вариант 12");

        // ==================== FOLD ====================
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("  FOLD - Свёртка элементов списка");
        System.out.println("=".repeat(50));
        System.out.println("\nИсходный массив: " + ListProcessor.arrayToString(sourceArray));
        System.out.println();

        // FOLD: Вариант 2 - F(x, y) = x * y
        demonstrateFold("FOLD: F(x,y) = x * y (произведение)",
                sourceArray,
                (x, y) -> x * y,
                1.0,  // нейтральный элемент для умножения
                "Вариант 2");

        // FOLD: Вариант 4 - F(x, y) = min(x, y)
        demonstrateFold("FOLD: F(x,y) = min(x,y) (минимум)",
                sourceArray,
                Math::min,
                Double.MAX_VALUE,  // нейтральный элемент для min
                "Вариант 4");

        // FOLD: Вариант 7 - F(x, y) = sqrt(x * y)
        demonstrateFold("FOLD: F(x,y) = sqrt(x*y) (среднее геометрическое)",
                sourceArray,
                (x, y) -> Math.sqrt(x * y),
                1.0,  // начальное значение
                "Вариант 7");

        // ==================== Комбинированные операции ====================
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("  Комбинирование MAP и FOLD");
        System.out.println("=".repeat(50));

        // Пример: MAP + FOLD
        double[] data = {1.0, 2.0, 3.0, 4.0, 5.0};
        System.out.println("\nИсходный массив: " + ListProcessor.arrayToString(data));

        // Сначала MAP (exp), потом FOLD (произведение)
        double[] expData = ListProcessor.map(data, Math::exp);
        double product = ListProcessor.fold(expData, (x, y) -> x * y, 1.0);
        System.out.println("MAP: exp(x) => " + ListProcessor.arrayToString(expData));
        System.out.println("Затем FOLD: x*y (произведение) => " + product);

        // MAP (sin), затем FOLD (минимум)
        double[] sinData = ListProcessor.map(data, Math::sin);
        double minSin = ListProcessor.fold(sinData, Math::min, Double.MAX_VALUE);
        System.out.println("MAP: sin(x) => " + ListProcessor.arrayToString(sinData));
        System.out.println("Затем FOLD: min(x,y) (минимум) => " + minSin);

        // ==================== Дополнительные примеры FOLD ====================
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("  Дополнительные примеры FOLD");
        System.out.println("=".repeat(50));

        double[] nums = {1.0, 2.0, 3.0, 4.0, 5.0};
        System.out.println("\nМассив: " + ListProcessor.arrayToString(nums));

        // Сумма (fold с identity = 0)
        double sum = ListProcessor.fold(nums, (x, y) -> x + y, 0.0);
        System.out.println("FOLD: x+y (сумма) => " + sum);

        // Среднее арифметическое (сначала сумма, потом деление)
        double avg = sum / nums.length;
        System.out.println("Среднее арифметическое => " + avg);

        // Максимум
        double max = ListProcessor.fold(nums, Math::max, Double.MIN_VALUE);
        System.out.println("FOLD: max(x,y) (максимум) => " + max);

        // foldLeft и foldRight
        System.out.println("\nfoldLeft:  ((1*2)*3)*4*5 = " + ListProcessor.foldLeft(nums, (x, y) -> x * y));
        System.out.println("foldRight: 1*(2*(3*(4*5))) = " + ListProcessor.foldRight(nums, (x, y) -> x * y));
    }

    private static void demonstrateMap(String title, double[] array,
                                       java.util.function.DoubleUnaryOperator func,
                                       String variant) {
        System.out.println("-".repeat(50));
        System.out.println("  " + title + " [" + variant + "]");
        System.out.println("-".repeat(50));
        double[] result = ListProcessor.map(array, func);
        System.out.println("Вход:  " + ListProcessor.arrayToString(array));
        System.out.println("Выход: " + ListProcessor.arrayToString(result));
        System.out.println();
    }

    private static void demonstrateFold(String title, double[] array,
                                         DoubleBinaryOperator func,
                                         double identity,
                                         String variant) {
        System.out.println("-".repeat(50));
        System.out.println("  " + title + " [" + variant + "]");
        System.out.println("-".repeat(50));
        double result = ListProcessor.fold(array, func, identity);
        System.out.println("Вход:    " + ListProcessor.arrayToString(array));
        System.out.println("Identity: " + identity);
        System.out.println("Результат: " + result);
        System.out.println();
    }
}
