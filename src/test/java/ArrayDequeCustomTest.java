import com.burime.calculator.ArrayDequeCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Модульное тестирование дека как конечного автомата (Вариант 8)")
class ArrayDequeCustomTest {

    private ArrayDequeCustom<Integer> deque;

    @BeforeEach
    void setUp() {
        // Базовый дек с емкостью 4 для удобного перевода между состояниями
        deque = new ArrayDequeCustom<>(4);
    }

    @Test
    @DisplayName("Инициализация: корректное создание переводит автомат в состояние EMPTY")
    void testInitialEmptyState() {
        assertTrue(deque.isEmpty());
        assertFalse(deque.isFull());
        assertEquals(0, deque.size());
        assertEquals(4, deque.capacity());
    }

    @ParameterizedTest(name = "Некорректная вместимость: {0}")
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("Инициализация: capacity <= 0 вызывает IllegalArgumentException")
    void testInvalidCapacityThrowsException(int invalidCapacity) {
        assertThrows(IllegalArgumentException.class, () -> new ArrayDequeCustom<Integer>(invalidCapacity));
    }

    @Test
    @DisplayName("Добавление null запрещено")
    void testNullItemThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
        assertThrows(IllegalArgumentException.class, () -> deque.addLast(null));
    }

    @Test
    @DisplayName("Состояние EMPTY: извлечение и просмотр вызывают NoSuchElementException")
    void testEmptyStateExceptions() {
        assertThrows(NoSuchElementException.class, () -> deque.removeFirst());
        assertThrows(NoSuchElementException.class, () -> deque.removeLast());
        assertThrows(NoSuchElementException.class, () -> deque.peekFirst());
        assertThrows(NoSuchElementException.class, () -> deque.peekLast());
    }

    @Test
    @DisplayName("Переход: EMPTY -> PARTIALLY_FILLED при addLast")
    void testTransitionEmptyToPartiallyFilledViaAddLast() {
        deque.addLast(10);

        assertFalse(deque.isEmpty());
        assertFalse(deque.isFull());
        assertEquals(1, deque.size());
        assertEquals(10, deque.peekFirst());
        assertEquals(10, deque.peekLast());
    }

    @Test
    @DisplayName("Переход: EMPTY -> PARTIALLY_FILLED при addFirst")
    void testTransitionEmptyToPartiallyFilledViaAddFirst() {
        deque.addFirst(20);

        assertFalse(deque.isEmpty());
        assertFalse(deque.isFull());
        assertEquals(1, deque.size());
        assertEquals(20, deque.peekFirst());
        assertEquals(20, deque.peekLast());
    }

    @Test
    @DisplayName("Переход: PARTIALLY_FILLED -> FULL при заполнении до предела")
    void testTransitionPartiallyFilledToFull() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertFalse(deque.isFull()); // еще частично заполнен

        deque.addLast(4); // 4-й элемент при capacity=4
        assertTrue(deque.isFull());
        assertFalse(deque.isEmpty());
        assertEquals(4, deque.size());
    }

    @Test
    @DisplayName("Граничный случай capacity=1: прямой переход EMPTY -> FULL")
    void testTransitionEmptyDirectlyToFullForCapacityOne() {
        ArrayDequeCustom<String> singleDeque = new ArrayDequeCustom<>(1);
        assertTrue(singleDeque.isEmpty());

        singleDeque.addFirst("A");
        assertTrue(singleDeque.isFull());
        assertFalse(singleDeque.isEmpty());
        assertEquals(1, singleDeque.size());
    }

    @Test
    @DisplayName("Состояние FULL: добавление элементов вызывает IllegalStateException")
    void testFullStateOverflowThrowsException() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        assertTrue(deque.isFull());

        assertThrows(IllegalStateException.class, () -> deque.addLast(5));
        assertThrows(IllegalStateException.class, () -> deque.addFirst(0));
        // Состояние не должно повредиться
        assertEquals(4, deque.size());
        assertTrue(deque.isFull());
    }

    @Test
    @DisplayName("Переход: FULL -> PARTIALLY_FILLED -> EMPTY через removeFirst")
    void testUnloadingViaRemoveFirst() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        // FULL -> PARTIALLY_FILLED
        assertEquals(1, deque.removeFirst());
        assertFalse(deque.isFull());
        assertFalse(deque.isEmpty());
        assertEquals(3, deque.size());

        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());

        // PARTIALLY_FILLED -> EMPTY
        assertEquals(4, deque.removeFirst());
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    @DisplayName("Переход: FULL -> PARTIALLY_FILLED -> EMPTY через removeLast")
    void testUnloadingViaRemoveLast() {
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        deque.addLast(40);

        assertEquals(40, deque.removeLast());
        assertEquals(3, deque.size());

        assertEquals(30, deque.removeLast());
        assertEquals(20, deque.removeLast());
        assertEquals(10, deque.removeLast());

        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("Переход: сброс в состояние EMPTY через clear()")
    void testClearFromFullAndPartiallyFilled() {
        deque.addLast(1);
        deque.addLast(2);
        deque.clear();
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());

        // Повторное заполнение после clear
        deque.addFirst(99);
        assertEquals(1, deque.size());
        assertEquals(99, deque.peekFirst());
    }

    @Test
    @DisplayName("Кольцевой буфер: сдвиг head влево через индекс 0")
    void testCircularHeadWrapAround() {
        // Добавление в начало циклически сдвигает head в конец физического массива
        deque.addFirst(1); // index 0
        deque.addFirst(2); // index 3 (capacity - 1)
        deque.addFirst(3); // index 2
        deque.addFirst(4); // index 1

        assertTrue(deque.isFull());
        assertEquals(4, deque.peekFirst());
        assertEquals(1, deque.peekLast());

        // Извлечение должно точно соблюсти LIFO порядок для начала
        assertEquals(4, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(1, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("Кольцевой буфер: непрерывная работа (чередование add/remove)")
    void testContinuousCircularUsage() {
        for (int i = 0; i < 20; i++) {
            deque.addLast(i);
            assertEquals(i, deque.removeFirst());
            assertTrue(deque.isEmpty());
        }
    }

    @Test
    @DisplayName("Дек как стек (LIFO) и как очередь (FIFO)")
    void testQueueAndStackSemantics() {
        // Режим очереди: добавление в хвост, чтение из головы
        deque.addLast(100);
        deque.addLast(200);
        assertEquals(100, deque.removeFirst());
        assertEquals(200, deque.removeFirst());

        // Режим стека: добавление в голову, чтение из головы
        deque.addFirst(300);
        deque.addFirst(400);
        assertEquals(400, deque.removeFirst());
        assertEquals(300, deque.removeFirst());
    }
}