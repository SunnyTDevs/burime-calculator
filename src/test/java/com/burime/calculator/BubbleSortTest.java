package com.burime.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульные тесты для класса BubbleSort
 * Используется подход AAA (Arrange-Act-Assert)
 */
@DisplayName("Тесты пузырьковой сортировки")
public class BubbleSortTest {

    // ===== Тестирование метода sort (сортировка по возрастанию) =====

    @Test
    @DisplayName("Тест 1: Сортировка массива с уникальными элементами")
    public void sortTestNormalArrayWithUniqueElements() {
        // Arrange (Класс эквивалентности 1)
        int[] array = {5, 2, 9, 1, 7, 6};
        int[] expected = {1, 2, 5, 6, 7, 9};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив должен быть отсортирован по возрастанию");
    }

    @Test
    @DisplayName("Тест 2: Сортировка массива с одинаковыми элементами")
    public void sortTestNormalArrayWithSameElements() {
        // Arrange (Класс эквивалентности 2)
        int[] array = {3, 3, 3, 3};
        int[] expected = {3, 3, 3, 3};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив с одинаковыми элементами должен остаться неизменным");
    }

    @Test
    @DisplayName("Тест 3: Сортировка уже отсортированного массива")
    public void sortTestAlreadySortedArray() {
        // Arrange (Класс эквивалентности 3)
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Уже отсортированный массив должен остаться неизменным");
    }

    @Test
    @DisplayName("Тест 4: Сортировка массива, отсортированного в обратном порядке")
    public void sortTestReverseSortedArray() {
        // Arrange (Класс эквивалентности 4)
        int[] array = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив в обратном порядке должен быть отсортирован по возрастанию");
    }

    @Test
    @DisplayName("Тест 5: Сортировка массива с повторяющимися элементами")
    public void sortTestArrayWithRepeatedElements() {
        // Arrange (Класс эквивалентности 5)
        int[] array = {4, 2, 4, 1, 2, 4, 1};
        int[] expected = {1, 1, 2, 2, 4, 4, 4};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив с повторяющимися элементами должен быть правильно отсортирован");
    }

    @Test
    @DisplayName("Тест 6: Сортировка массива из одного элемента")
    public void sortTestArrayWithOneElement() {
        // Arrange (Класс эквивалентности 6 - граничный случай)
        int[] array = {42};
        int[] expected = {42};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив из одного элемента должен остаться неизменным");
    }

    @Test
    @DisplayName("Тест 7: Сортировка пустого массива")
    public void sortTestEmptyArray() {
        // Arrange (Класс эквивалентности 7 - граничный случай)
        int[] array = {};
        int[] expected = {};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Пустой массив должен остаться пустым");
    }

    @Test
    @DisplayName("Тест 8: Сортировка null массива (ошибочный вариант)")
    public void sortTestNullArray() {
        // Arrange (Класс эквивалентности 8 - ошибочный вариант)
        int[] array = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> BubbleSort.sort(array),
                "Метод должен выбросить IllegalArgumentException для null массива"
        );

        assertTrue(exception.getMessage().contains("null"),
                "Сообщение об ошибке должно содержать информацию о null");
    }

    @Test
    @DisplayName("Тест 9: Сортировка массива с отрицательными числами")
    public void sortTestArrayWithNegativeNumbers() {
        // Arrange (Дополнительный тест)
        int[] array = {-5, 3, -1, 0, -10, 8};
        int[] expected = {-10, -5, -1, 0, 3, 8};

        // Act
        int[] actual = BubbleSort.sort(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив с отрицательными числами должен быть правильно отсортирован");
    }

    @Test
    @DisplayName("Тест 10: Проверка неизменности исходного массива")
    public void sortTestOriginalArrayNotModified() {
        // Arrange
        int[] original = {5, 2, 8, 1};
        int[] originalCopy = original.clone();

        // Act
        BubbleSort.sort(original);

        // Assert
        assertArrayEquals(originalCopy, original,
                "Исходный массив не должен изменяться после сортировки");
    }

    // ===== Тестирование метода sortDescending (сортировка по убыванию) =====

    @Test
    @DisplayName("Тест 11: Сортировка по убыванию - массив с уникальными элементами")
    public void sortDescendingTestNormalArrayWithUniqueElements() {
        // Arrange
        int[] array = {5, 2, 9, 1, 7, 6};
        int[] expected = {9, 7, 6, 5, 2, 1};

        // Act
        int[] actual = BubbleSort.sortDescending(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив должен быть отсортирован по убыванию");
    }

    @Test
    @DisplayName("Тест 12: Сортировка по убыванию - массив с одинаковыми элементами")
    public void sortDescendingTestArrayWithSameElements() {
        // Arrange
        int[] array = {7, 7, 7};
        int[] expected = {7, 7, 7};

        // Act
        int[] actual = BubbleSort.sortDescending(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив с одинаковыми элементами должен остаться неизменным");
    }

    @Test
    @DisplayName("Тест 13: Сортировка по убыванию - массив из одного элемента")
    public void sortDescendingTestArrayWithOneElement() {
        // Arrange
        int[] array = {99};
        int[] expected = {99};

        // Act
        int[] actual = BubbleSort.sortDescending(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив из одного элемента должен остаться неизменным");
    }

    @Test
    @DisplayName("Тест 14: Сортировка по убыванию - пустой массив")
    public void sortDescendingTestEmptyArray() {
        // Arrange
        int[] array = {};
        int[] expected = {};

        // Act
        int[] actual = BubbleSort.sortDescending(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Пустой массив должен остаться пустым");
    }

    @Test
    @DisplayName("Тест 15: Сортировка по убыванию - null массив (ошибочный вариант)")
    public void sortDescendingTestNullArray() {
        // Arrange
        int[] array = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> BubbleSort.sortDescending(array),
                "Метод должен выбросить IllegalArgumentException для null массива"
        );

        assertTrue(exception.getMessage().contains("null"),
                "Сообщение об ошибке должно содержать информацию о null");
    }

    @Test
    @DisplayName("Тест 16: Сортировка по убыванию - массив с отрицательными числами")
    public void sortDescendingTestArrayWithNegativeNumbers() {
        // Arrange
        int[] array = {-5, 3, -1, 0, -10, 8};
        int[] expected = {8, 3, 0, -1, -5, -10};

        // Act
        int[] actual = BubbleSort.sortDescending(array);

        // Assert
        assertArrayEquals(expected, actual,
                "Массив с отрицательными числами должен быть правильно отсортирован по убыванию");
    }
}
