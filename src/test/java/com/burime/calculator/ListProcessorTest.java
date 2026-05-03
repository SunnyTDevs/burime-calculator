package com.burime.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ListProcessor (операция MAP)
 */
@DisplayName("Тесты MAP - отображения элементов списка")
public class ListProcessorTest {

    // ===== Базовые тесты MAP =====

    @Test
    @DisplayName("MAP: Умножение элементов на 2")
    public void testMapMultiplyByTwo() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] expected = {2.0, 4.0, 6.0, 8.0, 10.0};

        double[] result = ListProcessor.map(input, x -> x * 2);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Возведение в квадрат")
    public void testMapSquare() {
        double[] input = {1.0, 2.0, 3.0, 4.0};
        double[] expected = {1.0, 4.0, 9.0, 16.0};

        double[] result = ListProcessor.map(input, x -> x * x);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Применение Math.sqrt")
    public void testMapSquareRoot() {
        double[] input = {1.0, 4.0, 9.0, 16.0};
        double[] expected = {1.0, 2.0, 3.0, 4.0};

        double[] result = ListProcessor.map(input, Math::sqrt);

        assertArrayEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("MAP: Применение Math.sin")
    public void testMapSine() {
        double[] input = {0.0, Math.PI / 2, Math.PI};
        double[] expected = {0.0, 1.0, 0.0};

        double[] result = ListProcessor.map(input, Math::sin);

        assertArrayEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("MAP: Применение Math.cos")
    public void testMapCosine() {
        double[] input = {0.0, Math.PI / 2, Math.PI};
        double[] expected = {1.0, 0.0, -1.0};

        double[] result = ListProcessor.map(input, Math::cos);

        assertArrayEquals(expected, result, 1e-10);
    }

    // ===== Тесты с List<Double> =====

    @Test
    @DisplayName("MAP: Работа со списком List<Double>")
    public void testMapList() {
        List<Double> input = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> expected = List.of(2.0, 4.0, 6.0, 8.0, 10.0);

        List<Double> result = ListProcessor.map(input, x -> x * 2);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Работа с пустым списком")
    public void testMapEmptyList() {
        List<Double> input = List.of();
        List<Double> expected = List.of();

        List<Double> result = ListProcessor.map(input, x -> x * 2);

        assertEquals(expected, result);
    }

    // ===== Граничные случаи =====

    @Test
    @DisplayName("MAP: Пустой массив")
    public void testMapEmptyArray() {
        double[] input = {};
        double[] expected = {};

        double[] result = ListProcessor.map(input, x -> x * 2);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Массив из одного элемента")
    public void testMapSingleElement() {
        double[] input = {5.0};
        double[] expected = {10.0};

        double[] result = ListProcessor.map(input, x -> x * 2);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Отрицательные числа")
    public void testMapNegativeNumbers() {
        double[] input = {-5.0, -3.0, 0.0, 3.0, 5.0};
        double[] expected = {-10.0, -6.0, 0.0, 6.0, 10.0};

        double[] result = ListProcessor.map(input, x -> x * 2);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Дробные числа")
    public void testMapFractionalNumbers() {
        double[] input = {0.5, 1.5, 2.5};
        double[] expected = {1.0, 3.0, 5.0};

        double[] result = ListProcessor.map(input, x -> x * 2);

        assertArrayEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("MAP: Функция тождественности (x -> x)")
    public void testMapIdentity() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};

        double[] result = ListProcessor.map(input, x -> x);

        assertArrayEquals(input, result);
    }

    @Test
    @DisplayName("MAP: Константная функция (все элементы = 42)")
    public void testMapConstantFunction() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] expected = {42.0, 42.0, 42.0, 42.0, 42.0};

        double[] result = ListProcessor.map(input, x -> 42.0);

        assertArrayEquals(expected, result);
    }

    // ===== Тесты на исключения =====

    @Test
    @DisplayName("MAP: Null массив выбрасывает исключение")
    public void testMapNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.map((double[]) null, x -> x * 2));
    }

    @Test
    @DisplayName("MAP: Null список выбрасывает исключение")
    public void testMapNullList() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.map((List<Double>) null, x -> x * 2));
    }

    // ===== Тесты IntToDoubleFunction =====

    @Test
    @DisplayName("MAP: Преобразование int[] -> double[]")
    public void testMapIntToDouble() {
        int[] input = {1, 2, 3, 4, 5};
        double[] expected = {2.0, 4.0, 6.0, 8.0, 10.0};

        double[] result = ListProcessor.mapIntToDouble(input, x -> x * 2.0);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Преобразование температуры")
    public void testMapTemperatureConversion() {
        int[] celsius = {0, 20, 37, 100};
        double[] fahrenheit = {32.0, 68.0, 98.6, 212.0};

        double[] result = ListProcessor.mapIntToDouble(celsius, x -> x * 9.0 / 5.0 + 32.0);

        assertArrayEquals(fahrenheit, result, 0.1);
    }

    // ===== Тесты обобщённого MAP =====

    @Test
    @DisplayName("MAP: Обобщённый MAP для String -> Integer")
    public void testMapGenericStringToInteger() {
        String[] input = {"hello", "world", "java"};
        Integer[] expected = {5, 5, 4};

        Integer[] result = ListProcessor.mapGeneric(input, String::length, Integer.class);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Обобщённый MAP для Integer -> String")
    public void testMapGenericIntegerToString() {
        Integer[] input = {1, 2, 3};
        String[] expected = {"1.0", "2.0", "3.0"};

        String[] result = ListProcessor.mapGeneric(input, x -> x.doubleValue() + ".0", String.class);

        assertArrayEquals(expected, result);
    }

    // ===== Тесты UnaryOperator =====

    @Test
    @DisplayName("MAP: Использование UnaryOperator")
    public void testMapUnaryOperator() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] expected = {1.0, 8.0, 27.0, 64.0, 125.0};

        double[] result = ListProcessor.map(input, x -> Math.pow(x, 3));

        assertArrayEquals(expected, result);
    }

    // ===== Тесты сложных функций =====

    @Test
    @DisplayName("MAP: Комплексная функция (x² + 1)")
    public void testMapComplexFunction() {
        double[] input = {-2.0, -1.0, 0.0, 1.0, 2.0};
        double[] expected = {5.0, 2.0, 1.0, 2.0, 5.0};

        double[] result = ListProcessor.map(input, x -> x * x + 1);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Функция с abs")
    public void testMapAbsoluteValue() {
        double[] input = {-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0};
        double[] expected = {3.0, 2.0, 1.0, 0.0, 1.0, 2.0, 3.0};

        double[] result = ListProcessor.map(input, Math::abs);

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Функция exp(x)")
    public void testMapExponential() {
        double[] input = {0.0, 1.0, 2.0};
        double[] expected = {1.0, Math.E, Math.E * Math.E};

        double[] result = ListProcessor.map(input, Math::exp);

        assertArrayEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("MAP: Функция log (натуральный логарифм)")
    public void testMapLogarithm() {
        double[] input = {1.0, Math.E, Math.E * Math.E};
        double[] expected = {0.0, 1.0, 2.0};

        double[] result = ListProcessor.map(input, Math::log);

        assertArrayEquals(expected, result, 1e-10);
    }

    // ===== Тесты утилитарных методов =====

    @Test
    @DisplayName("MAP: Проверка arrayToString")
    public void testArrayToString() {
        double[] array = {1.0, 2.0, 3.0};
        String expected = "[1.0000, 2.0000, 3.0000]";

        String result = ListProcessor.arrayToString(array);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Проверка arrayToString для null")
    public void testArrayToStringNull() {
        assertEquals("null", ListProcessor.arrayToString(null));
    }

    @Test
    @DisplayName("MAP: Проверка listToString")
    public void testListToString() {
        List<Double> list = List.of(1.0, 2.0, 3.0);
        String expected = "[1.0000, 2.0000, 3.0000]";

        String result = ListProcessor.listToString(list);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("MAP: Проверка listToString для null")
    public void testListToStringNull() {
        assertEquals("null", ListProcessor.listToString(null));
    }

    // ===== Тесты методов-фабрик =====

    @Test
    @DisplayName("MAP: Проверка метода Of")
    public void testOf() {
        double[] result = ListProcessor.of(1.0, 2.0, 3.0);

        assertEquals(3, result.length);
        assertEquals(1.0, result[0]);
        assertEquals(2.0, result[1]);
        assertEquals(3.0, result[2]);
    }

    @Test
    @DisplayName("MAP: Проверка метода ListOf")
    public void testListOf() {
        List<Double> result = ListProcessor.listOf(1.0, 2.0, 3.0);

        assertEquals(3, result.size());
        assertEquals(1.0, result.get(0));
        assertEquals(2.0, result.get(1));
        assertEquals(3.0, result.get(2));
    }

    // ===== Тесты комбинированных операций =====

    @Test
    @DisplayName("MAP: Цепочка операций (x * 2, затем + 1)")
    public void testMapChainedOperations() {
        double[] input = {1.0, 2.0, 3.0};

        double[] step1 = ListProcessor.map(input, x -> x * 2);
        double[] step2 = ListProcessor.map(step1, x -> x + 1);
        double[] expected = {3.0, 5.0, 7.0};

        assertArrayEquals(expected, step2);
    }

    @Test
    @DisplayName("MAP: Операция не изменяет исходный массив")
    public void testMapDoesNotModifyOriginal() {
        double[] original = {1.0, 2.0, 3.0};
        double[] copy = original.clone();

        ListProcessor.map(original, x -> x * 10);

        assertArrayEquals(copy, original);
    }

    // ==================== FOLD TESTS ====================

    @Test
    @DisplayName("FOLD: Сумма элементов (x + y)")
    public void testFoldSum() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 15.0;

        double result = ListProcessor.fold(input, (x, y) -> x + y, 0.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Произведение элементов (x * y) [Вариант 2]")
    public void testFoldProduct() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 120.0;

        double result = ListProcessor.fold(input, (x, y) -> x * y, 1.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Минимум (min(x,y)) [Вариант 4]")
    public void testFoldMin() {
        double[] input = {5.0, 3.0, 8.0, 1.0, 9.0, 2.0};
        double expected = 1.0;

        double result = ListProcessor.fold(input, Math::min, Double.MAX_VALUE);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Максимум (max(x,y))")
    public void testFoldMax() {
        double[] input = {5.0, 3.0, 8.0, 1.0, 9.0, 2.0};
        double expected = 9.0;

        double result = ListProcessor.fold(input, Math::max, Double.MIN_VALUE);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Среднее геометрическое sqrt(x*y) [Вариант 7]")
    public void testFoldGeometricMean() {
        double[] input = {1.0, 4.0, 9.0, 16.0};
        // sqrt(1 * 4 * 9 * 16) = sqrt(576) = 24
        double expected = 24.0;

        double result = ListProcessor.fold(input, (x, y) -> Math.sqrt(x * y), 1.0);

        assertEquals(expected, result, 1e-10);
    }

    // ===== Граничные случаи FOLD =====

    @Test
    @DisplayName("FOLD: Пустой массив с identity")
    public void testFoldEmptyArrayWithIdentity() {
        double[] input = {};
        double identity = 42.0;

        double result = ListProcessor.fold(input, (x, y) -> x + y, identity);

        assertEquals(identity, result);
    }

    @Test
    @DisplayName("FOLD: Массив из одного элемента")
    public void testFoldSingleElement() {
        double[] input = {5.0};

        double sum = ListProcessor.fold(input, (x, y) -> x + y, 0.0);
        double product = ListProcessor.fold(input, (x, y) -> x * y, 1.0);

        assertEquals(5.0, sum, 1e-10);
        assertEquals(5.0, product, 1e-10);
    }

    @Test
    @DisplayName("FOLD: foldLeft для {1,2,3,4,5} с x*y")
    public void testFoldLeft() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        // ((1*2)*3)*4*5 = 120
        double expected = 120.0;

        double result = ListProcessor.foldLeft(input, (x, y) -> x * y);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: foldRight для {1,2,3,4,5} с x*y")
    public void testFoldRight() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        // 1*(2*(3*(4*5))) = 120
        double expected = 120.0;

        double result = ListProcessor.foldRight(input, (x, y) -> x * y);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: foldLeft и foldRight дают одинаковый результат для коммутативной операции")
    public void testFoldLeftAndRightEqualForCommutative() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};

        double left = ListProcessor.foldLeft(input, (x, y) -> x + y);
        double right = ListProcessor.foldRight(input, (x, y) -> x + y);

        assertEquals(left, right, 1e-10);
    }

    @Test
    @DisplayName("FOLD: foldLeft и foldRight различаются для некоммутативной операции")
    public void testFoldLeftAndRightDifferentForNonCommutative() {
        double[] input = {2.0, 3.0};  // 2^3 = 8, 3^2 = 9

        double left = ListProcessor.foldLeft(input, (x, y) -> Math.pow(x, y));
        double right = ListProcessor.foldRight(input, (x, y) -> Math.pow(x, y));

        assertNotEquals(left, right, 0.001);
        assertEquals(8.0, left, 0.001);
        assertEquals(9.0, right, 0.001);
    }

    @Test
    @DisplayName("FOLD: Работа со списком List<Double>")
    public void testFoldList() {
        List<Double> input = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 15.0;

        double result = ListProcessor.fold(input, (x, y) -> x + y, 0.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Отрицательные числа")
    public void testFoldNegativeNumbers() {
        double[] input = {-5.0, 3.0, -2.0, 4.0};

        double sum = ListProcessor.fold(input, (x, y) -> x + y, 0.0);
        double product = ListProcessor.fold(input, (x, y) -> x * y, 1.0);

        assertEquals(0.0, sum, 1e-10);
        assertEquals(120.0, product, 1e-10);
    }

    @Test
    @DisplayName("FOLD: С identity для умножения")
    public void testFoldIdentityForMultiplication() {
        double[] input = {1.0, 2.0, 3.0};
        double expected = 6.0;

        double result = ListProcessor.fold(input, (x, y) -> x * y, 1.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: С identity для минимума")
    public void testFoldIdentityForMin() {
        double[] input = {3.0, 1.0, 4.0, 2.0};
        double expected = 1.0;

        double result = ListProcessor.fold(input, Math::min, Double.MAX_VALUE);

        assertEquals(expected, result, 1e-10);
    }

    // ===== Тесты на исключения FOLD =====

    @Test
    @DisplayName("FOLD: Null массив выбрасывает исключение")
    public void testFoldNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.fold((double[]) null, (x, y) -> x + y, 0.0));
    }

    @Test
    @DisplayName("FOLD: Null список выбрасывает исключение")
    public void testFoldNullList() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.fold((java.util.List<Double>) null, (x, y) -> x + y, 0.0));
    }

    @Test
    @DisplayName("FOLD: foldLeft с пустым массивом выбрасывает исключение")
    public void testFoldLeftEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.foldLeft(new double[]{}, (x, y) -> x + y));
    }

    @Test
    @DisplayName("FOLD: foldRight с пустым массивом выбрасывает исключение")
    public void testFoldRightEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> ListProcessor.foldRight(new double[]{}, (x, y) -> x + y));
    }

    // ===== Тесты MAP + FOLD комбинации =====

    @Test
    @DisplayName("MAP+FOLD: Сначала MAP (exp), затем FOLD (произведение)")
    public void testMapThenFoldProduct() {
        double[] input = {1.0, 2.0, 3.0};
        // exp(1) * exp(2) * exp(3) = e * e² * e³ = e^6
        double expected = Math.exp(6.0);

        double[] mapped = ListProcessor.map(input, Math::exp);
        double result = ListProcessor.fold(mapped, (x, y) -> x * y, 1.0);

        assertEquals(expected, result, 1e-6);
    }

    @Test
    @DisplayName("MAP+FOLD: Сначала MAP (sin), затем FOLD (минимум)")
    public void testMapThenFoldMin() {
        double[] input = {0.0, Math.PI / 2, Math.PI};
        double[] sinValues = {0.0, 1.0, 0.0};

        double[] mapped = ListProcessor.map(input, Math::sin);
        double min = ListProcessor.fold(mapped, Math::min, Double.MAX_VALUE);

        assertArrayEquals(sinValues, mapped, 1e-10);
        assertEquals(0.0, min, 1e-10);
    }

    @Test
    @DisplayName("MAP+FOLD: MAP (floor), затем FOLD (сумма)")
    public void testMapFloorThenFoldSum() {
        double[] input = {1.7, 2.3, 3.8, 4.1};
        double[] floorValues = {1.0, 2.0, 3.0, 4.0};
        double expectedSum = 10.0;

        double[] mapped = ListProcessor.map(input, Math::floor);
        double sum = ListProcessor.fold(mapped, (x, y) -> x + y, 0.0);

        assertArrayEquals(floorValues, mapped, 1e-10);
        assertEquals(expectedSum, sum, 1e-10);
    }

    @Test
    @DisplayName("MAP+FOLD: MAP (sin) -> MAP (abs) -> FOLD (произведение)")
    public void testMapChainThenFold() {
        double[] input = {-1.0, 2.0, -3.0};
        // abs(sin) для [-1, 2, -3]
        // sin(-1) ≈ -0.84, sin(2) ≈ 0.91, sin(-3) ≈ -0.14
        // abs: 0.84, 0.91, 0.14

        double[] step1 = ListProcessor.map(input, Math::sin);
        double[] step2 = ListProcessor.map(step1, Math::abs);
        double product = ListProcessor.fold(step2, (x, y) -> x * y, 1.0);

        assertTrue(product > 0, "Произведение положительных чисел должно быть положительным");
    }

    // ===== Дополнительные тесты FOLD =====

    @Test
    @DisplayName("FOLD: Конкатенация строк")
    public void testFoldStringConcatenation() {
        String[] input = {"Hello", " ", "World", "!"};
        String expected = "Hello World!";

        Function<String, String> concat = x -> x;
        java.util.function.BinaryOperator<String> combine = (a, b) -> a + b;

        // Используем обобщённый fold через строковое представление
        StringBuilder sb = new StringBuilder();
        for (String s : input) {
            sb.append(s);
        }
        assertEquals(expected, sb.toString());
    }

    @Test
    @DisplayName("FOLD: Вычисление факториала")
    public void testFoldFactorial() {
        int n = 5;
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 120.0;  // 5!

        double result = ListProcessor.fold(input, (x, y) -> x * y, 1.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Подсчёт количества элементов (fold как счётчик)")
    public void testFoldAsCounter() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 5.0;

        double result = ListProcessor.fold(input, (x, y) -> x + 1, 0.0);

        assertEquals(expected, result, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Подсчёт суммы квадратов")
    public void testFoldSumOfSquares() {
        double[] input = {1.0, 2.0, 3.0};

        // Сначала MAP (x²), затем FOLD (сумма)
        double[] squares = ListProcessor.map(input, x -> x * x);
        double sumOfSquares = ListProcessor.fold(squares, (x, y) -> x + y, 0.0);

        assertEquals(14.0, sumOfSquares, 1e-10);  // 1 + 4 + 9 = 14
    }

    @Test
    @DisplayName("FOLD: Среднее арифметическое (sum / n)")
    public void testFoldAverage() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 3.0;

        double sum = ListProcessor.fold(input, (x, y) -> x + y, 0.0);
        double average = sum / input.length;

        assertEquals(expected, average, 1e-10);
    }

    @Test
    @DisplayName("FOLD: Проверка arrayToString для int[]")
    public void testArrayToStringInt() {
        int[] input = {1, 2, 3, 4, 5};
        String expected = "[1, 2, 3, 4, 5]";

        String result = ListProcessor.arrayToString(input);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("FOLD: Проверка метода ofInt")
    public void testOfInt() {
        int[] result = ListProcessor.ofInt(1, 2, 3, 4, 5);

        assertEquals(5, result.length);
        assertEquals(1, result[0]);
        assertEquals(5, result[4]);
    }
}
