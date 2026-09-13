import com.burime.calculator.ArrayModifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayModifierTest {

    @Test
    @DisplayName("Массив равен null -> IllegalArgumentException")
    void testNullArrayThrowsException() {
        int[] a = null;

        assertThrows(IllegalArgumentException.class, () -> ArrayModifier.replaceLast(a, 5, 10));
    }

    @Test
    @DisplayName("Пустой массив -> false, массив не изменился")
    void testEmptyArrayReturnsFalse() {
        int[] a = {};
        int[] expected = {};

        boolean result = ArrayModifier.replaceLast(a, 5, 10);

        assertFalse(result);
        assertArrayEquals(expected, a);
    }

    @Test
    @DisplayName("Искомый элемент на последней позиции -> true")
    void testTargetAtLastPositionReplacedImmediately() {
        int[] a = {1, 5};
        int[] expected = {1, 10};

        boolean result = ArrayModifier.replaceLast(a, 5, 10);

        assertTrue(result);
        assertArrayEquals(expected, a);
    }

    @Test
    @DisplayName("Элемент отсутствует в массиве -> false")
    void testTargetNotFoundReturnsFalse() {
        int[] a = {1, 2};
        int[] expected = {1, 2};

        boolean result = ArrayModifier.replaceLast(a, 5, 10);

        assertFalse(result);
        assertArrayEquals(expected, a);
    }

    @Test
    @DisplayName("Замена именно последнего вхождения при дубликатах")
    void testMultipleOccurrencesReplacesOnlyLastOne() {
        int[] a = {5, 2, 5, 3};
        int[] expected = {5, 2, 99, 3};

        boolean result = ArrayModifier.replaceLast(a, 5, 99);

        assertTrue(result);
        assertArrayEquals(expected, a);
    }

    @Test
    @DisplayName("Массив из одного элемента (совпадение)")
    void testSingleElementArrayMatch() {
        int[] a = {7};
        int[] expected = {42};

        boolean result = ArrayModifier.replaceLast(a, 7, 42);

        assertTrue(result);
        assertArrayEquals(expected, a);
    }
}