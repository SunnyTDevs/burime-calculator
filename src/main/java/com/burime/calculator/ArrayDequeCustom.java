package com.burime.calculator;

import java.util.NoSuchElementException;

public class ArrayDequeCustom<E> {

    private final Object[] elements;
    private final int capacity;
    private int head; // индекс первого элемента
    private int tail; // индекс последнего элемента
    private int size; // текущее количество элементов

    public ArrayDequeCustom(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость дека должна быть > 0. Передано: " + capacity);
        }
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void addFirst(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Элемент не может быть null");
        }
        if (isFull()) {
            throw new IllegalStateException("Дек переполнен (емкость: " + capacity + ")");
        }

        if (isEmpty()) {
            head = 0;
            tail = 0;
        } else {
            // Кольцевой сдвиг влево
            head = (head - 1 + capacity) % capacity;
        }

        elements[head] = item;
        size++;
    }

    public void addLast(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Элемент не может быть null");
        }
        if (isFull()) {
            throw new IllegalStateException("Дек переполнен (емкость: " + capacity + ")");
        }

        if (isEmpty()) {
            head = 0;
            tail = 0;
        } else {
            // Кольцевой сдвиг вправо
            tail = (tail + 1) % capacity;
        }

        elements[tail] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Невозможно извлечь элемент: дек пуст");
        }

        E value = (E) elements[head];
        elements[head] = null; // очистка ссылки для GC

        if (size == 1) {
            head = 0;
            tail = 0;
        } else {
            head = (head + 1) % capacity;
        }

        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Невозможно извлечь элемент: дек пуст");
        }

        E value = (E) elements[tail];
        elements[tail] = null; // очистка ссылки для GC

        if (size == 1) {
            head = 0;
            tail = 0;
        } else {
            tail = (tail - 1 + capacity) % capacity;
        }

        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public E peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Дек пуст");
        }
        return (E) elements[head];
    }

    @SuppressWarnings("unchecked")
    public E peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Дек пуст");
        }
        return (E) elements[tail];
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            elements[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }
}