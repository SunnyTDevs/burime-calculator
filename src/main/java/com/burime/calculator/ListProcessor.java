package com.burime.calculator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Класс для обработки списков (массивов)
 * Реализует операции MAP и FOLD (REDUCE)
 */
public class ListProcessor {

    // ==================== MAP ====================

    /**
     * Отображение (MAP) элементов массива с помощью функции преобразования.
     *
     * @param array входной массив
     * @param func функция преобразования
     * @return новый массив, содержащий преобразованные элементы
     */
    public static double[] map(double[] array, DoubleUnaryOperator func) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = func.applyAsDouble(array[i]);
        }
        return result;
    }

    /**
     * Отображение (MAP) элементов списка с помощью функции преобразования.
     *
     * @param list входной список
     * @param func функция преобразования
     * @return новый список, содержащий преобразованные элементы
     */
    public static List<Double> map(List<Double> list, DoubleUnaryOperator func) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        List<Double> result = new ArrayList<>(list.size());
        for (Double value : list) {
            result.add(func.applyAsDouble(value));
        }
        return result;
    }

    /**
     * Отображение (MAP) элементов целочисленного массива.
     *
     * @param array входной массив
     * @param func функция преобразования (int -> double)
     * @return новый массив типа double
     */
    public static double[] mapIntToDouble(int[] array, IntToDoubleFunction func) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = func.applyAsDouble(array[i]);
        }
        return result;
    }

    /**
     * Отображение (MAP) с преобразованием типа элементов.
     *
     * @param array входной массив
     * @param func функция преобразования
     * @param <T> тип исходного элемента
     * @param <R> тип преобразованного элемента
     * @return новый массив преобразованных элементов
     */
    @SuppressWarnings("unchecked")
    public static <T, R> R[] mapGeneric(T[] array, Function<T, R> func, Class<R> resultType) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        R[] result = (R[]) java.lang.reflect.Array.newInstance(resultType, array.length);
        for (int i = 0; i < array.length; i++) {
            result[i] = func.apply(array[i]);
        }
        return result;
    }

    // ==================== FOLD (REDUCE) ====================

    /**
     * Свёртка (FOLD/REDUCE) элементов массива с помощью бинарной функции.
     * Вычисляет: func(arr[0], func(arr[1], func(arr[2], ... func(arr[n-1], identity))))
     * или в обратном порядке: func(func(...func(arr[0], arr[1]), arr[2]), ..., arr[n-1])
     *
     * @param array входной массив
     * @param func бинарная функция свёртки F(x, y)
     * @param identity начальное значение (нейтральный элемент)
     * @return результат свёртки
     */
    public static double fold(double[] array, DoubleBinaryOperator func, double identity) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        double result = identity;
        for (double value : array) {
            result = func.applyAsDouble(result, value);
        }
        return result;
    }

    /**
     * Свёртка (FOLD) с левой ассоциативностью.
     * Вычисляет: func(func(...func(arr[0], arr[1]), arr[2]), ..., arr[n-1])
     *
     * @param array входной массив
     * @param func бинарная функция свёртки F(x, y)
     * @return результат свёртки (или identity если массив пуст)
     */
    public static double foldLeft(double[] array, DoubleBinaryOperator func) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Для foldLeft без identity массив не может быть пустым");
        }

        double result = array[0];
        for (int i = 1; i < array.length; i++) {
            result = func.applyAsDouble(result, array[i]);
        }
        return result;
    }

    /**
     * Свёртка (FOLD) с правой ассоциативностью.
     * Вычисляет: func(arr[0], func(arr[1], func(arr[2], ... arr[n-1]...)))
     *
     * @param array входной массив
     * @param func бинарная функция свёртки F(x, y)
     * @return результат свёртки (или identity если массив пуст)
     */
    public static double foldRight(double[] array, DoubleBinaryOperator func) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Для foldRight без identity массив не может быть пустым");
        }

        double result = array[array.length - 1];
        for (int i = array.length - 2; i >= 0; i--) {
            result = func.applyAsDouble(array[i], result);
        }
        return result;
    }

    /**
     * Свёртка (FOLD) для списка.
     *
     * @param list входной список
     * @param func бинарная функция свёртки F(x, y)
     * @param identity начальное значение
     * @return результат свёртки
     */
    public static double fold(List<Double> list, DoubleBinaryOperator func, double identity) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        double result = identity;
        for (Double value : list) {
            result = func.applyAsDouble(result, value);
        }
        return result;
    }

    // ==================== Утилитарные методы ====================

    /**
     * Утилитарный метод для преобразования массива в строку.
     *
     * @param array массив
     * @return строковое представление массива
     */
    public static String arrayToString(double[] array) {
        if (array == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(String.format("%.4f", array[i]));
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Утилитарный метод для преобразования массива int в строку.
     *
     * @param array массив
     * @return строковое представление массива
     */
    public static String arrayToString(int[] array) {
        if (array == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Утилитарный метод для преобразования списка в строку.
     *
     * @param list список
     * @return строковое представление списка
     */
    public static String listToString(List<Double> list) {
        if (list == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(String.format("%.4f", list.get(i)));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Создание массива из набора значений.
     *
     * @param values значения
     * @return массив
     */
    public static double[] of(Double... values) {
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    /**
     * Создание массива int из набора значений.
     *
     * @param values значения
     * @return массив int
     */
    public static int[] ofInt(Integer... values) {
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    /**
     * Создание списка из набора значений.
     *
     * @param values значения
     * @return список
     */
    public static List<Double> listOf(Double... values) {
        return new ArrayList<>(Arrays.asList(values));
    }
}
