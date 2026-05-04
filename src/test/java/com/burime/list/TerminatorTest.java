package com.burime.list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Terminator (конечной точки списка).
 * Проверяет корректность работы методов листового элемента.
 */
@DisplayName("Тесты для Terminator (Leaf)")
class TerminatorTest {

    private Terminator terminator;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        terminator = new Terminator();
        // Перехватываем System.out для проверки вывода
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("toString должен возвращать пустую строку")
    void testToString() {
        String result = terminator.toString();
        assertEquals("", result, "Terminator должен возвращать пустую строку");
    }

    @Test
    @DisplayName("add не должен выбрасывать исключение")
    void testAdd() {
        assertDoesNotThrow(() -> terminator.add("Test"),
                "Terminator.add не должен выбрасывать исключение");
    }

    @Test
    @DisplayName("display должен выводить [END]")
    void testDisplay() {
        terminator.display();
        String output = outputStream.toString().trim();
        assertEquals("[END]", output, "Terminator должен выводить '[END]'");
    }

    @Test
    @DisplayName("Terminator реализует интерфейс ListComponent")
    void testImplementsListComponent() {
        assertTrue(terminator instanceof ListComponent,
                "Terminator должен реализовывать интерфейс ListComponent");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Восстанавливаем System.out
        System.setOut(originalOut);
    }
}
