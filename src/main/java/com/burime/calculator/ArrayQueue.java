package com.burime.calculator;

import java.util.NoSuchElementException;

/**
 * ArrayQueue - implementation of the IQueue interface using a circular array.
 *
 * Uses a fixed-size array with two pointers (front, rear) to efficiently
 * implement FIFO behavior without shifting elements.
 *
 * @param <T> the type of elements in the queue
 */
public class ArrayQueue<T> implements IQueue<T> {

    private final Object[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int count;

    /**
     * Creates a queue with the specified capacity.
     *
     * @param capacity the maximum number of elements
     * @throws IllegalArgumentException if capacity is not positive
     */
    public ArrayQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
    }

    @Override
    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        data[rear] = item;
        count++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = (T) data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        count--;
        return item;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) data[front];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean isFull() {
        return count == capacity;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            int index = (front + i) % capacity;
            sb.append(data[index]);
            if (i < count - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
