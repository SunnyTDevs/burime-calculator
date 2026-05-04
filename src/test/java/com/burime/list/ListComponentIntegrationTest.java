package com.burime.list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для проверки работы паттерна Composite
 * с различными комбинациями узлов и терминаторов.
 */
@DisplayName("Интеграционные тесты паттерна Composite")
class ListComponentIntegrationTest {

    @Test
    @DisplayName("Пустой список (только Terminator)")
    void testEmptyList() {
        ListComponent list = new Terminator();
        assertEquals("", list.toString(),
                "Пустой список должен возвращать пустую строку");
    }

    @Test
    @DisplayName("Список из одного элемента")
    void testSingleElementList() {
        ListComponent list = new Node("Solo");
        assertEquals("Solo", list.toString());
    }

    @Test
    @DisplayName("Список из пяти элементов через add()")
    void testFiveElementList() {
        Node node1 = new Node("A");
        node1.add("B");
        node1.add("C");
        node1.add("D");
        node1.add("E");

        assertEquals("A -> B -> C -> D -> E", node1.toString());
    }

    @Test
    @DisplayName("Динамическое добавление элементов в конец списка через add()")
    void testDynamicListBuilding() {
        Node head = new Node("Start");
        head.add("Middle");
        head.add("End");

        assertEquals("Start -> Middle -> End", head.toString());
    }

    @Test
    @DisplayName("Добавление нескольких элементов через add()")
    void testMultipleAdds() {
        Node node1 = new Node("First");
        node1.add("Second");
        node1.add("Third");

        assertEquals("First -> Second -> Third", node1.toString());
    }

    @Test
    @DisplayName("Построение цепочки через add()")
    void testChainBuilding() {
        Node head = new Node("Head");
        head.add("New1");
        head.add("New2");

        assertEquals("Head -> New1 -> New2", head.toString());
    }

    @Test
    @DisplayName("Полиморфная работа с ListComponent")
    void testPolymorphism() {
        ListComponent component1 = new Node("Node");
        ListComponent component2 = new Terminator();

        assertNotNull(component1.toString());
        assertNotNull(component2.toString());
    }

    @Test
    @DisplayName("Длинный список (производительность) через add()")
    void testLongList() {
        Node head = new Node("0");

        for (int i = 1; i < 10; i++) {
            head.add(String.valueOf(i));
        }

        String expected = "0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9";
        assertEquals(expected, head.toString());
    }

    @Test
    @DisplayName("Display для сложного списка должен вызываться рекурсивно")
    void testComplexListDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Node node1 = new Node("A");
            node1.add("B");
            node1.add("C");

            node1.display();

            String output = outputStream.toString();
            String[] lines = output.trim().split(System.lineSeparator());

            assertEquals(4, lines.length, "Должно быть 4 строки вывода (3 узла + END)");
            assertEquals("[Node: A]", lines[0].trim());
            assertEquals("[Node: B]", lines[1].trim());
            assertEquals("[Node: C]", lines[2].trim());
            assertEquals("[END]", lines[3].trim());
        } finally {
            System.setOut(originalOut);
        }
    }
}
