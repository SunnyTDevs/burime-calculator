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
        assertEquals("", list.toStringRepresentation(),
                "Пустой список должен возвращать пустую строку");
    }

    @Test
    @DisplayName("Список из одного элемента")
    void testSingleElementList() {
        ListComponent list = new Node("Solo");
        assertEquals("Solo", list.toStringRepresentation());
    }

    @Test
    @DisplayName("Список из пяти элементов")
    void testFiveElementList() {
        Node node5 = new Node("E");
        Node node4 = new Node("D", node5);
        Node node3 = new Node("C", node4);
        Node node2 = new Node("B", node3);
        Node node1 = new Node("A", node2);

        assertEquals("A -> B -> C -> D -> E", node1.toStringRepresentation());
    }

    @Test
    @DisplayName("Динамическое добавление элементов в конец списка")
    void testDynamicListBuilding() {
        Node head = new Node("Start");
        Node middle = new Node("Middle");
        Node end = new Node("End");

        head.setNext(middle);
        middle.setNext(end);

        assertEquals("Start -> Middle -> End", head.toStringRepresentation());
    }

    @Test
    @DisplayName("Вставка узла в середину списка")
    void testInsertInMiddle() {
        Node node3 = new Node("Third");
        Node node1 = new Node("First", node3);

        Node node2 = new Node("Second", node3);
        node1.setNext(node2);

        assertEquals("First -> Second -> Third", node1.toStringRepresentation());
    }

    @Test
    @DisplayName("Замена части списка")
    void testReplacePartOfList() {
        Node oldNode = new Node("Old");
        Node head = new Node("Head", oldNode);

        Node newChain = new Node("New1", new Node("New2"));
        head.setNext(newChain);

        assertEquals("Head -> New1 -> New2", head.toStringRepresentation());
    }

    @Test
    @DisplayName("Полиморфная работа с ListComponent")
    void testPolymorphism() {
        ListComponent component1 = new Node("Node");
        ListComponent component2 = new Terminator();

        assertNotNull(component1.toStringRepresentation());
        assertNotNull(component2.toStringRepresentation());
    }

    @Test
    @DisplayName("Длинный список (производительность)")
    void testLongList() {
        Node head = new Node("0");
        Node current = head;

        for (int i = 1; i < 10; i++) {
            Node next = new Node(String.valueOf(i));
            current.setNext(next);
            current = next;
        }

        String expected = "0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9";
        assertEquals(expected, head.toStringRepresentation());
    }

    @Test
    @DisplayName("Display для сложного списка должен вызываться рекурсивно")
    void testComplexListDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Node node3 = new Node("C");
            Node node2 = new Node("B", node3);
            Node node1 = new Node("A", node2);

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
