package com.burime.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    private IQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new ArrayQueue<>(5);
    }

    @Test
    void newQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void newQueueIsNotFull() {
        assertFalse(queue.isFull());
    }

    @Test
    void enqueueIncreasesSize() {
        queue.enqueue(10);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void enqueueMultipleElements() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
    }

    @Test
    void dequeueReturnsFrontElement() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.dequeue());
    }

    @Test
    void dequeueDecreasesSize() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.dequeue();
        assertEquals(1, queue.size());
    }

    @Test
    void fifoOrder() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    void peekReturnsFrontWithoutRemoving() {
        queue.enqueue(42);
        assertEquals(42, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void peekDoesNotChangeQueue() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void dequeueFromEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    @Test
    void peekFromEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    void enqueueToFullThrows() {
        IQueue<Integer> smallQueue = new ArrayQueue<>(2);
        smallQueue.enqueue(1);
        smallQueue.enqueue(2);
        assertTrue(smallQueue.isFull());
        assertThrows(IllegalStateException.class, () -> smallQueue.enqueue(3));
    }

    @Test
    void circularBehavior() {
        // Fill the queue completely
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        // Remove some elements from the front
        queue.dequeue();
        queue.dequeue();
        // Add new elements — they wrap around in the array
        queue.enqueue(6);
        queue.enqueue(7);
        assertEquals(5, queue.size());
        // Verify FIFO order is preserved
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        assertEquals(6, queue.dequeue());
        assertEquals(7, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void enqueueAndDequeueAlternating() {
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
        queue.enqueue(2);
        assertEquals(2, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void invalidCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayQueue<>(0));
        assertThrows(IllegalArgumentException.class, () -> new ArrayQueue<>(-5));
    }

    @Test
    void toStringEmpty() {
        assertEquals("[]", queue.toString());
    }

    @Test
    void toStringWithElements() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals("[1, 2, 3]", queue.toString());
    }

    @Test
    void worksWithStrings() {
        IQueue<String> stringQueue = new ArrayQueue<>(3);
        stringQueue.enqueue("hello");
        stringQueue.enqueue("world");
        assertEquals("hello", stringQueue.peek());
        assertEquals("hello", stringQueue.dequeue());
        assertEquals("world", stringQueue.dequeue());
    }
}
