package com.burime.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Лабораторная работа № 11: Автоматические моки для Graph (Вариант 8)")
class GraphMockTest {

    @Mock
    private IGraphReader mockReader; // Динамический мок IGraphReader

    @Mock
    private IGraphWriter mockWriter; // Динамический мок IGraphWriter

    private Graph graph;

    @BeforeEach
    void setUp() {
        // Внедряем динамически сгенерированные моки через конструктор
        graph = new Graph(mockReader, mockWriter);
    }

    @Test
    @DisplayName("Stub: настройка возвращаемых ребер и проверка матрицы инцидентности")
    void testIncidenceMatrixWithMockStub() throws IOException {
        // Arrange: говорим моку вернуть 2 ребра при вызове loadEdgeList с любым Reader
        List<Edge> testEdges = List.of(new Edge(1, 2), new Edge(1, 3));
        when(mockReader.loadEdgeList(any(Reader.class))).thenReturn(testEdges);

        // Act
        graph.load(new StringReader(""));

        // Assert
        int[][] m = graph.getIncidenceMatrix();
        assertEquals(3, graph.getVertexList().size(), "Должно быть 3 вершины (1, 2, 3)");
        assertEquals(2, m[0].length, "Должно быть 2 столбца (2 ребра)");

        // Проверяем, что метод loadEdgeList действительно был вызван ровно 1 раз
        verify(mockReader, times(1)).loadEdgeList(any(Reader.class));
    }

    @Test
    @DisplayName("Stub: проверка алгоритма Обратного обхода дерева (Post-order)")
    void testPostOrderTraversalWithMock() throws IOException {
        // Дерево: 1 -> 2, 1 -> 3, 3 -> 4
        List<Edge> treeEdges = List.of(
                new Edge(1, 2),
                new Edge(1, 3),
                new Edge(3, 4)
        );
        when(mockReader.loadEdgeList(any())).thenReturn(treeEdges);

        graph.load(new StringReader(""));
        List<Integer> postOrder = graph.postOrderTraversal();

        // Ожидаем Post-order обход: сначала дети (2), затем (4, 3), затем корень (1)
        assertEquals(List.of(2, 4, 3, 1), postOrder);
    }

    @Test
    @DisplayName("Spy/Mock: проверка, что метод сохранения вызывается с точными аргументами")
    void testSaveVerificationAndArgumentMatching() throws IOException {
        // Arrange
        when(mockReader.loadEdgeList(any())).thenReturn(List.of(new Edge(10, 20)));
        graph.load(new StringReader(""));

        // До вызова save метод writer'а вызываться НЕ должен (аналог DidNotReceive)
        verifyNoInteractions(mockWriter);

        // Act
        graph.save(new java.io.StringWriter());

        // Assert: проверяем, что saveAdjacencyList был вызван ровно 1 раз (аналог Received(1))
        // и сопоставляем аргумент (Arg.Is / argThat): проверяем, что вершина 10 соединена с 20
        verify(mockWriter, times(1)).saveAdjacencyList(
                any(Writer.class),
                argThat(adjMap -> adjMap.containsKey(10) && adjMap.get(10).contains(20))
        );
    }

    @Test
    @DisplayName("Captor: инспектирование переданного списка смежности в writer")
    void testArgumentCaptorForWriter() throws IOException {
        when(mockReader.loadEdgeList(any())).thenReturn(List.of(new Edge(1, 2), new Edge(2, 3)));
        graph.load(new StringReader(""));

        graph.save(new java.io.StringWriter());

        // Захватываем фактический объект Map, переданный в метод saveAdjacencyList
        @SuppressWarnings("unchecked")
        ArgumentCaptor<Map<Integer, List<Integer>>> captor = ArgumentCaptor.forClass(Map.class);
        verify(mockWriter).saveAdjacencyList(any(), captor.capture());

        Map<Integer, List<Integer>> capturedAdjacency = captor.getValue();
        assertEquals(List.of(2), capturedAdjacency.get(1));
        assertEquals(List.of(3), capturedAdjacency.get(2));
        assertEquals(Collections.emptyList(), capturedAdjacency.get(3));
    }

    @Test
    @DisplayName("Exception: симуляция сбоя ввода-вывода (IOException) в IGraphReader")
    void testReaderThrowsException() throws IOException {
        // Настраиваем мок на выброс исключения (аналог .Returns(x => throw ...))
        when(mockReader.loadEdgeList(any())).thenThrow(new IOException("Диск недоступен"));

        assertThrows(IOException.class, () -> graph.load(new StringReader("")));

        // Убеждаемся, что при ошибке чтения матрица не строилась и writer не трогался
        verifyNoInteractions(mockWriter);
        assertEquals(0, graph.getVertexList().size());
    }

    @Test
    @DisplayName("Consecutive Calls: разные структуры графа при повторных вызовах")
    void testMultipleSequentialCalls() throws IOException {
        List<Edge> firstGraph = List.of(new Edge(1, 2));
        List<Edge> secondGraph = List.of(new Edge(10, 20), new Edge(20, 30));

        // Мок возвращает firstGraph на 1-й вызов и secondGraph на 2-й вызов
        when(mockReader.loadEdgeList(any()))
                .thenReturn(firstGraph)
                .thenReturn(secondGraph);

        // 1-й прогон
        graph.load(new StringReader(""));
        assertEquals(2, graph.getVertexList().size());

        // 2-й прогон
        graph.load(new StringReader(""));
        assertEquals(3, graph.getVertexList().size());

        verify(mockReader, times(2)).loadEdgeList(any());
    }
}