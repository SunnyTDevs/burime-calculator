package com.burime.list;


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Демонстрация паттерна Composite для связанного списка ===\n");

        // Пример 1: Простой список из одного элемента
        demonstrateSingleNode();

        // Пример 2: Построение списка через метод add()
        demonstrateListWithAdd();

        // Пример 3: Список с разными данными
        demonstrateVariousDataTypes();

        // Пример 4: Только Terminator (пустой список)
        demonstrateEmptyList();

        System.out.println("\n=== Демонстрация завершена ===");
    }

    private static void demonstrateSingleNode() {
        System.out.println("--- Пример 1: Одиночный узел ---");
        Node singleNode = new Node("Единственный элемент");

        System.out.println("Вывод через display():");
        singleNode.display();

        System.out.println("\nСтроковое представление: " + singleNode);
        System.out.println();
    }

    private static void demonstrateListWithAdd() {
        System.out.println("--- Пример 2: Построение списка через add() ---");

        Node head = new Node("Первый");
        head.add("Второй");
        head.add("Третий");
        head.add("Четвертый");

        System.out.println("Вывод через display():");
        head.display();

        System.out.println("\nСтроковое представление: " + head);
        System.out.println();
    }

    private static void demonstrateVariousDataTypes() {
        System.out.println("--- Пример 3: Список с разными данными ---");

        Node languages = new Node("Go");
        languages.add("JavaScript");
        languages.add("C++");
        languages.add("Python");
        languages.add("Java");

        System.out.println("Список языков программирования:");
        System.out.println("Вывод через display():");
        languages.display();

        System.out.println("\nСтроковое представление: " + languages);
        System.out.println();
    }

    private static void demonstrateEmptyList() {
        System.out.println("--- Пример 4: Пустой список (только Terminator) ---");

        ListComponent emptyList = new Terminator();

        System.out.println("Вывод через display():");
        emptyList.display();

        System.out.println("\nСтроковое представление: '" + emptyList + "'");
        System.out.println();
    }
}
