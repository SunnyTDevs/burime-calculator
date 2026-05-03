package com.burime.calculator;

import java.util.List;
import java.util.ArrayList;

/**
 * Демонстрация работы MAP-операции для обработки списков.
 */
public class MapMain {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  MAP - Отображение элементов списка");
        System.out.println("========================================\n");

        // Пример 1: Умножение каждого элемента на 2
        demonstrate("Умножение на 2",
                new double[]{1.0, 2.0, 3.0, 4.0, 5.0},
                x -> x * 2);

        // Пример 2: Возведение каждого элемента в квадрат
        demonstrate("Возведение в квадрат",
                new double[]{1.0, 2.0, 3.0, 4.0, 5.0},
                x -> x * x);

        // Пример 3: sin(x) для каждого элемента
        demonstrate("sin(x)",
                new double[]{0.0, Math.PI / 6, Math.PI / 4, Math.PI / 2},
                Math::sin);

        // Пример 4: Сдвиг элементов (x + 5)
        demonstrate("Сдвиг на +5",
                new double[]{10.0, 20.0, 30.0},
                x -> x + 5);

        // Пример 5: Извлечение квадратного корня
        demonstrate("Квадратный корень",
                new double[]{1.0, 4.0, 9.0, 16.0, 25.0},
                Math::sqrt);

        // Пример 6: Работа со списком List<Double>
        demonstrateWithList("Умножение на 3 (список)",
                List.of(1.0, 2.0, 3.0, 4.0, 5.0),
                x -> x * 3);

        // Пример 7: Преобразование температуры (Цельсий -> Фаренгейт)
        demonstrate("Цельсий -> Фаренгейт",
                new double[]{0.0, 20.0, 37.0, 100.0},
                x -> x * 9 / 5 + 32);

        // Пример 8: Комплексное преобразование: sin(x) + cos(x)
        demonstrate("sin(x) + cos(x)",
                new double[]{0.0, Math.PI / 4, Math.PI / 2},
                x -> Math.sin(x) + Math.cos(x));

        // Пример 9: С использованием UnaryOperator
        System.out.println("========================================");
        System.out.println("  Пример 9: UnaryOperator");
        System.out.println("========================================");
        double[] original9 = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] result9 = ListProcessor.map(original9, x -> Math.pow(x, 3));
        System.out.println("Исходный:  " + ListProcessor.arrayToString(original9));
        System.out.println("x³:        " + ListProcessor.arrayToString(result9));
        System.out.println();

        // Пример 10: Работа с int[] через IntToDoubleFunction
        System.out.println("========================================");
        System.out.println("  Пример 10: Преобразование int[] -> double[]");
        System.out.println("========================================");
        int[] intArray = {1, 2, 3, 4, 5};
        double[] doubleResult = ListProcessor.mapIntToDouble(intArray, x -> x * 1.5);
        System.out.print("int[]:  [");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + (i < intArray.length - 1 ? ", " : "]\n"));
        }
        System.out.println("x*1.5:  " + ListProcessor.arrayToString(doubleResult));
        System.out.println();

        // Пример 11: Обобщённый MAP для строк
        System.out.println("========================================");
        System.out.println("  Пример 11: Обобщённый MAP");
        System.out.println("========================================");
        String[] words = {"hello", "world", "map"};
        Function<String, Integer> lengthFunc = String::length;
        Integer[] lengths = ListProcessor.mapGeneric(words, lengthFunc, Integer.class);
        System.out.print("Слова:      [");
        for (int i = 0; i < words.length; i++) {
            System.out.print("\"" + words[i] + "\"" + (i < words.length - 1 ? ", " : "]\n"));
        }
        System.out.print("Длины:      [");
        for (int i = 0; i < lengths.length; i++) {
            System.out.print(lengths[i] + (i < lengths.length - 1 ? ", " : "]\n"));
        }
    }

    private static void demonstrate(String title, double[] array, java.util.function.DoubleUnaryOperator func) {
        System.out.println("========================================");
        System.out.println("  " + title);
        System.out.println("========================================");
        double[] result = ListProcessor.map(array, func);
        System.out.println("Исходный:  " + ListProcessor.arrayToString(array));
        System.out.println("Результат:  " + ListProcessor.arrayToString(result));
        System.out.println();
    }

    private static void demonstrateWithList(String title, List<Double> list, java.util.function.DoubleUnaryOperator func) {
        System.out.println("========================================");
        System.out.println("  " + title);
        System.out.println("========================================");
        List<Double> result = ListProcessor.map(list, func);
        System.out.println("Исходный:  " + ListProcessor.listToString(list));
        System.out.println("Результат:  " + ListProcessor.listToString(result));
        System.out.println();
    }
}
