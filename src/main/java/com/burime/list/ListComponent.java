package com.burime.list;

/**
 * Базовый интерфейс компонента списка (Component в паттерне Composite).
 * Определяет общий функционал для всех элементов списка.
 */
public interface ListComponent {

    /**
     * Отображает элемент и все последующие элементы списка.
     */
    void display();

    /**
     * Возвращает строковое представление списка.
     */
    String toStringRepresentation();
}