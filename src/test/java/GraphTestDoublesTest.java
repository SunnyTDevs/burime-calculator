import com.burime.calculator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Лабораторная работа № 10: Модульное тестирование с Test Doubles")
class GraphTestDoublesTest {

    @Test
    @DisplayName("EdgeListGraphReader: чтение корректного Списка ребер из StringReader")
    void testEdgeListGraphReaderParse() throws IOException {
        String input = "3\n1 2\n2 3\n3 1\n";
        IGraphReader reader = new EdgeListGraphReader();

        List<Edge> edges = reader.loadEdgeList(new StringReader(input));

        assertEquals(3, edges.size());
        assertEquals(new Edge(1, 2), edges.get(0));
        assertEquals(new Edge(2, 3), edges.get(1));
        assertEquals(new Edge(3, 1), edges.get(2));
    }

    @Test
    @DisplayName("AdjacencyListGraphWriter: запись Списка связности в StringWriter")
    void testAdjacencyListGraphWriterOutput() throws IOException {
        Map<Integer, List<Integer>> adj = new LinkedHashMap<>();
        adj.put(1, List.of(2, 3));
        adj.put(2, List.of());
        adj.put(3, List.of(4));

        IGraphWriter writer = new AdjacencyListGraphWriter();
        StringWriter stringWriter = new StringWriter();

        writer.saveAdjacencyList(stringWriter, adj);
        String output = stringWriter.toString();

        assertTrue(output.contains("1: 2 3"));
        assertTrue(output.contains("2:"));
        assertTrue(output.contains("3: 4"));
    }

    static class DummyGraphWriter implements IGraphWriter {
        @Override
        public void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) {
            throw new UnsupportedOperationException("Dummy-объект не должен вызываться в данном тесте");
        }
    }

    @Test
    @DisplayName("Dummy: проверка инициализации и методов без вызова записи")
    void testGraphWithDummyWriter() {
        IGraphReader reader = new EdgeListGraphReader();
        IGraphWriter dummyWriter = new DummyGraphWriter();

        Graph graph = new Graph(reader, dummyWriter);

        assertNotNull(graph);
        assertEquals(0, graph.getVertexList().size());
        assertEquals(0, graph.getIncidenceMatrix().length);
    }

    static class ReaderStub implements IGraphReader {
        private final List<Edge> predefinedEdges;

        public ReaderStub(List<Edge> edges) {
            this.predefinedEdges = edges;
        }

        @Override
        public List<Edge> loadEdgeList(Reader reader) {
            return predefinedEdges; // Возврат заранее подготовленных данных
        }
    }

    @Test
    @DisplayName("Stub: проверка построения матрицы инцидентности из фиксированных ребер")
    void testIncidenceMatrixConstructionWithStub() throws IOException {
        // Дерево: 1 -> 2, 1 -> 3
        List<Edge> stubData = List.of(new Edge(1, 2), new Edge(1, 3));
        IGraphReader stubReader = new ReaderStub(stubData);
        IGraphWriter dummyWriter = new DummyGraphWriter();

        Graph graph = new Graph(stubReader, dummyWriter);
        graph.load(new StringReader("")); // Поток не важен, Stub возвращает данные напрямую

        int[][] m = graph.getIncidenceMatrix();
        List<Integer> v = graph.getVertexList();

        assertEquals(3, v.size()); // Вершины: 1, 2, 3
        assertEquals(3, m.length); // 3 строки матрицы
        assertEquals(2, m[0].length); // 2 столбца матрицы (2 ребра)

        // Ребро 0 (1 -> 2): вершина 1 = +1, вершина 2 = -1
        int idx1 = v.indexOf(1);
        int idx2 = v.indexOf(2);
        int idx3 = v.indexOf(3);
        assertEquals(1, m[idx1][0]);
        assertEquals(-1, m[idx2][0]);
        assertEquals(0, m[idx3][0]);
    }

    @Test
    @DisplayName("Stub: проверка алгоритма Обратного обхода дерева (Post-order)")
    void testPostOrderTraversalWithStub() throws IOException {
        // Дерево:
        //       1
        //      / \
        //     2   3
        //          \
        //           4
        List<Edge> treeEdges = List.of(
                new Edge(1, 2),
                new Edge(1, 3),
                new Edge(3, 4)
        );

        Graph graph = new Graph(new ReaderStub(treeEdges), new DummyGraphWriter());
        graph.load(new StringReader(""));

        List<Integer> postOrder = graph.postOrderTraversal();

        // Обратный обход (Post-order): сначала поддеревья (2), затем (4, 3), затем корень (1)
        List<Integer> expected = List.of(2, 4, 3, 1);
        assertEquals(expected, postOrder);
    }

    static class WriterSpy implements IGraphWriter {
        boolean saveWasInvoked = false;
        int invocationCount = 0;
        Map<Integer, List<Integer>> capturedAdjacencyList;

        @Override
        public void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) {
            this.saveWasInvoked = true;
            this.invocationCount++;
            this.capturedAdjacencyList = new LinkedHashMap<>(adjacencyList);
        }
    }

    @Test
    @DisplayName("Spy: фиксация факта вызова и переданных данных при сохранении графа")
    void testSaveWithSpy() throws IOException {
        List<Edge> edges = List.of(new Edge(10, 20), new Edge(10, 30));
        ReaderStub stubReader = new ReaderStub(edges);
        WriterSpy spyWriter = new WriterSpy();

        Graph graph = new Graph(stubReader, spyWriter);
        graph.load(new StringReader(""));

        // До сохранения вызов не зафиксирован
        assertFalse(spyWriter.saveWasInvoked);
        assertEquals(0, spyWriter.invocationCount);

        graph.save(new StringWriter());

        // Проверка состояния через шпиона
        assertTrue(spyWriter.saveWasInvoked);
        assertEquals(1, spyWriter.invocationCount);
        assertNotNull(spyWriter.capturedAdjacencyList);
        assertEquals(List.of(20, 30), spyWriter.capturedAdjacencyList.get(10));
        assertTrue(spyWriter.capturedAdjacencyList.get(20).isEmpty());
    }

    static class FakeGraphStorage implements IGraphReader, IGraphWriter {
        private final List<Edge> inMemoryEdges = new ArrayList<>();
        private Map<Integer, List<Integer>> inMemoryAdjacency = new HashMap<>();

        public void addEdge(int from, int to) {
            inMemoryEdges.add(new Edge(from, to));
        }

        @Override
        public List<Edge> loadEdgeList(Reader reader) {
            return new ArrayList<>(inMemoryEdges);
        }

        @Override
        public void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) {
            this.inMemoryAdjacency = new HashMap<>(adjacencyList);
        }

        public Map<Integer, List<Integer>> getStoredAdjacency() {
            return inMemoryAdjacency;
        }
    }

    @Test
    @DisplayName("Fake: комплексная проверка цикла загрузки-сохранения в памяти")
    void testGraphWithFakeStorage() throws IOException {
        FakeGraphStorage fakeStorage = new FakeGraphStorage();
        fakeStorage.addEdge(1, 2);
        fakeStorage.addEdge(2, 3);

        Graph graph = new Graph(fakeStorage, fakeStorage);
        graph.load(new StringReader(""));
        graph.save(new StringWriter());

        Map<Integer, List<Integer>> stored = fakeStorage.getStoredAdjacency();
        assertEquals(List.of(2), stored.get(1));
        assertEquals(List.of(3), stored.get(2));
        assertEquals(List.of(), stored.get(3));
    }

    static class MockGraphWriter implements IGraphWriter {
        private final Map<Integer, List<Integer>> expectedAdjacency;
        private boolean verified = false;

        public MockGraphWriter(Map<Integer, List<Integer>> expectedAdjacency) {
            this.expectedAdjacency = expectedAdjacency;
        }

        @Override
        public void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) {
            assertEquals(expectedAdjacency, adjacencyList, "Переданные данные не соответствуют ожиданиям мока");
            this.verified = true;
        }

        public void verify() {
            assertTrue(verified, "Ожидаемый метод saveAdjacencyList не был вызван!");
        }
    }

    @Test
    @DisplayName("Mock: строгая проверка ожиданий вызова метода сохранения")
    void testGraphWithMock() throws IOException {
        List<Edge> edges = List.of(new Edge(5, 6));
        ReaderStub reader = new ReaderStub(edges);

        Map<Integer, List<Integer>> expected = new HashMap<>();
        expected.put(5, List.of(6));
        expected.put(6, List.of());

        MockGraphWriter mockWriter = new MockGraphWriter(expected);

        Graph graph = new Graph(reader, mockWriter);
        graph.load(new StringReader(""));
        graph.save(new StringWriter());

        mockWriter.verify(); // Подтверждение выполнения контракта
    }
}