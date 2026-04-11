package com.burime.calculator;

/**
 * IQueue - interface for the Queue abstract data type (ADT).
 * Queue operates on FIFO principle (First In - First Out).
 *
 * @param <T> the type of elements in the queue
 */
public interface IQueue<T> {

    /**
     * Adds an element to the end of the queue.
     *
     * @param item the element to add
     * @throws IllegalStateException if the queue is full
     */
    void enqueue(T item);

    /**
     * Removes and returns the element from the front of the queue.
     *
     * @return the front element
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    T dequeue();

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the front element
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    T peek();

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements
     */
    boolean isEmpty();

    /**
     * Checks if the queue is full.
     *
     * @return true if the queue has reached its capacity
     */
    boolean isFull();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements
     */
    int size();
}
