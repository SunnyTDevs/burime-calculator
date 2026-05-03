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
    @DisplayName("Создание узла с данными должно инициализировать Terminator")
    void testNodeCreationWithData() {
        Node node = new Node("Test");
        assertEquals("Test", node.getData(), "Данные узла должны быть 'Test'");
        assertNotNull(node.getNext(), "Следующий элемент не должен быть null");
        assertTrue(node.getNext() instanceof Terminator,
                "По умолчанию следующий элемент должен быть Terminator");
    }

    @Test
    @DisplayName("Создание узла с указанием следующего элемента")
    void testNodeCreationWithNext() {
        ListComponent next = new Terminator();
        Node node = new Node("Test", next);
        assertEquals("Test", node.getData());
        assertSame(next, node.getNext(), "Следующий элемент должен быть переданным объектом");
    }

    @Test
    @DisplayName("setNext должен устанавливать следующий элемент")
    void testSetNext() {
        Node node = new Node("First");
        Node secondNode = new Node("Second");
        node.setNext(secondNode);
        assertSame(secondNode, node.getNext(), "setNext должен установить новый следующий элемент");
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
        Node node3 = new Node("Node3");
        Node node2 = new Node("Node2", node3);
        Node node1 = new Node("Node1", node2);

        node1.display();
        String output = outputStream.toString();

        assertTrue(output.contains("[Node: Node1]"), "Вывод должен содержать первый узел");
        assertTrue(output.contains("[Node: Node2]"), "Вывод должен содержать второй узел");
        assertTrue(output.contains("[Node: Node3]"), "Вывод должен содержать третий узел");
        assertTrue(output.contains("[END]"), "Вывод должен содержать '[END]'");
    }

    @Test
    @DisplayName("toStringRepresentation одиночного узла")
    void testToStringRepresentationSingleNode() {
        Node node = new Node("Single");
        assertEquals("Single", node.toStringRepresentation(),
                "Одиночный узел должен возвращать только свои данные");
    }

    @Test
    @DisplayName("toStringRepresentation цепочки узлов")
    void testToStringRepresentationMultipleNodes() {
        Node node3 = new Node("C");
        Node node2 = new Node("B", node3);
        Node node1 = new Node("A", node2);

        assertEquals("A -> B -> C", node1.toStringRepresentation(),
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
    @DisplayName("Построение списка с помощью setNext")
    void testBuildListWithSetNext() {
        Node node1 = new Node("First");
        Node node2 = new Node("Second");
        Node node3 = new Node("Third");

        node1.setNext(node2);
        node2.setNext(node3);

        assertEquals("First -> Second -> Third", node1.toStringRepresentation(),
                "Список должен быть построен корректно через setNext");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
