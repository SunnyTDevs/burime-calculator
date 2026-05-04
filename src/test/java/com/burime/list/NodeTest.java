package com.burime.list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Node (узла списка).
 * Проверяет корректность работы composite-элемента.
 */
@DisplayName("Тесты для Node (Composite)")
class NodeTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Создание узла с данными должно инициализировать пустой конец списка")
    void testNodeCreationWithData() {
        Node node = new Node("Test");
        assertEquals("Test", node.getData(), "Данные узла должны быть 'Test'");
        assertNotNull(node.getNext(), "Следующий элемент не должен быть null");
        assertEquals("Test", node.toString(),
                "Одиночный узел должен возвращать только свои данные без стрелок");
    }

    @Test
    @DisplayName("Метод add() добавляет элемент в конец списка")
    void testAddMethod() {
        Node node = new Node("First");
        node.add("Second");
        node.add("Third");

        assertEquals("First -> Second -> Third", node.toString(),
                "Метод add должен добавлять элементы в конец списка");
    }

    @Test
    @DisplayName("Метод add() создает правильную цепочку")
    void testAddCreatesChain() {
        Node head = new Node("A");
        head.add("B");
        head.add("C");
        head.add("D");

        assertEquals("A -> B -> C -> D", head.toString(),
                "Метод add должен создавать правильную цепочку элементов");
    }

    @Test
    @DisplayName("display одиночного узла должен вывести узел и [END]")
    void testDisplaySingleNode() {
        Node node = new Node("Data1");
        node.display();
        String output = outputStream.toString();
        assertTrue(output.contains("[Node: Data1]"), "Вывод должен содержать '[Node: Data1]'");
        assertTrue(output.contains("[END]"), "Вывод должен содержать '[END]'");
    }

    @Test
    @DisplayName("display цепочки узлов должен вывести все узлы")
    void testDisplayMultipleNodes() {
        Node node1 = new Node("Node1");
        node1.add("Node2");
        node1.add("Node3");

        node1.display();
        String output = outputStream.toString();

        assertTrue(output.contains("[Node: Node1]"), "Вывод должен содержать первый узел");
        assertTrue(output.contains("[Node: Node2]"), "Вывод должен содержать второй узел");
        assertTrue(output.contains("[Node: Node3]"), "Вывод должен содержать третий узел");
        assertTrue(output.contains("[END]"), "Вывод должен содержать '[END]'");
    }

    @Test
    @DisplayName("toString одиночного узла")
    void testToStringSingleNode() {
        Node node = new Node("Single");
        assertEquals("Single", node.toString(),
                "Одиночный узел должен возвращать только свои данные");
    }

    @Test
    @DisplayName("toString цепочки узлов")
    void testToStringMultipleNodes() {
        Node node3 = new Node("C");
        Node node2 = new Node("B");
        Node node1 = new Node("A");

        node1.add("B");
        node1.add("C");

        assertEquals("A -> B -> C", node1.toString(),
                "Цепочка должна вернуть 'A -> B -> C'");
    }

    @Test
    @DisplayName("Node реализует интерфейс ListComponent")
    void testImplementsListComponent() {
        Node node = new Node("Test");
        assertTrue(node instanceof ListComponent,
                "Node должен реализовывать интерфейс ListComponent");
    }

    @Test
    @DisplayName("getData возвращает корректные данные")
    void testGetData() {
        String testData = "TestData";
        Node node = new Node(testData);
        assertEquals(testData, node.getData(), "getData должен вернуть переданные данные");
    }

    @Test
    @DisplayName("Метод add() работает с пустым списком")
    void testAddToSingleNode() {
        Node node = new Node("Only");

        assertEquals("Only", node.toString(),
                "Одиночный узел должен корректно отображаться");

        node.add("Added");
        assertEquals("Only -> Added", node.toString(),
                "После добавления должна получиться цепочка");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
