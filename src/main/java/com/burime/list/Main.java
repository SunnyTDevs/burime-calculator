package com.burime.list;

/**
 * Демонстрационный класс для проверки работы паттерна Composite
 * на примере связанного списка.
 *
 * Класс демонстрирует:
 * - Создание узлов списка
 * - Построение цепочки элементов
 * - Вывод списка через метод display()
 * - Получение строкового представления через toStringRepresentation()
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Демонстрация паттерна Composite для связанного списка ===\n");

        // Пример 1: Простой список из одного элемента
        demonstrateSingleNode();

        // Пример 2: Список из нескольких элементов (построение через конструктор)
        demonstrateListWithConstructor();

        // Пример 3: Динамическое построение списка через setNext()
        demonstrateDynamicListBuilding();

        // Пример 4: Список с разными типами данных
        demonstrateVariousDataTypes();

        // Пример 5: Только Terminator (пустой список)
        demonstrateEmptyList();

        System.out.println("\n=== Демонстрация завершена ===");
    }

    /**
     * Демонстрация списка из одного элемента
     */
    private static void demonstrateSingleNode() {
        System.out.println("--- Пример 1: Одиночный узел ---");
        Node singleNode = new Node("Единственный элемент");

        System.out.println("Вывод через display():");
        singleNode.display();

        System.out.println("\nСтроковое представление: " + singleNode.toStringRepresentation());
        System.out.println();
    }

    /**
     * Демонстрация списка, построенного через конструктор
     */
    private static void demonstrateListWithConstructor() {
        System.out.println("--- Пример 2: Список через конструктор ---");

        // Строим список с конца к началу
        Node node4 = new Node("Четвертый");
        Node node3 = new Node("Третий", node4);
        Node node2 = new Node("Второй", node3);
        Node node1 = new Node("Первый", node2);

        System.out.println("Вывод через display():");
        node1.display();

        System.out.println("\nСтроковое представление: " + node1.toStringRepresentation());
        System.out.println();
    }

    /**
     * Демонстрация динамического построения списка
     */
    private static void demonstrateDynamicListBuilding() {
        System.out.println("--- Пример 3: Динамическое построение списка ---");

        Node head = new Node("Голова");
        Node middle1 = new Node("Середина 1");
        Node middle2 = new Node("Середина 2");
        Node tail = new Node("Хвост");

        // Соединяем узлы
        head.setNext(middle1);
        middle1.setNext(middle2);
        middle2.setNext(tail);

        System.out.println("Вывод через display():");
        head.display();

        System.out.println("\nСтроковое представление: " + head.toStringRepresentation());
        System.out.println();
    }

    /**
     * Демонстрация списка с различными типами данных (числа, слова)
     */
    private static void demonstrateVariousDataTypes() {
        System.out.println("--- Пример 4: Список с разными данными ---");

        Node node5 = new Node("Java");
        Node node4 = new Node("Python", node5);
        Node node3 = new Node("C++", node4);
        Node node2 = new Node("JavaScript", node3);
        Node node1 = new Node("Go", node2);

        System.out.println("Список языков программирования:");
        System.out.println("Вывод через display():");
        node1.display();

        System.out.println("\nСтроковое представление: " + node1.toStringRepresentation());
        System.out.println();
    }

    /**
     * Демонстрация пустого списка (только Terminator)
     */
    private static void demonstrateEmptyList() {
        System.out.println("--- Пример 5: Пустой список (только Terminator) ---");

        ListComponent emptyList = new Terminator();

        System.out.println("Вывод через display():");
        emptyList.display();

        System.out.println("\nСтроковое представление: '" + emptyList.toStringRepresentation() + "'");
        System.out.println();
    }
}
