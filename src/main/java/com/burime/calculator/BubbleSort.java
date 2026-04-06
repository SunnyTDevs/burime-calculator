package com.burime.calculator;

/**
 * Класс для сортировки массивов методом пузырьковой сортировки (Bubble Sort)
 */
public class BubbleSort {

    /**
     * Сортирует массив целых чисел методом пузырьковой сортировки
     *
     * @param array массив для сортировки
     * @return отсортированный массив
     * @throws IllegalArgumentException если массив равен null
     */
    public static int[] sort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Ошибка. В метод передан пустой массив (null).");
        }

        // Создаем копию массива, чтобы не изменять исходный
        int[] result = array.clone();

        // Если массив пустой или содержит один элемент, он уже отсортирован
        if (result.length <= 1) {
            return result;
        }

        // Пузырьковая сортировка
        int n = result.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (result[j] > result[j + 1]) {
                    // Меняем местами элементы
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }

            // Если за проход не было обменов, массив уже отсортирован
            if (!swapped) {
                break;
            }
        }

        return result;
    }

    /**
     * Сортирует массив целых чисел методом пузырьковой сортировки в порядке убывания
     *
     * @param array массив для сортировки
     * @return отсортированный массив в порядке убывания
     * @throws IllegalArgumentException если массив равен null
     */
    public static int[] sortDescending(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Ошибка. В метод передан пустой массив (null).");
        }

        int[] result = array.clone();

        if (result.length <= 1) {
            return result;
        }

        int n = result.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (result[j] < result[j + 1]) {
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return result;
    }
}
