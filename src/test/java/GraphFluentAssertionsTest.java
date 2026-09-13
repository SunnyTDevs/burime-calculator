import com.burime.calculator.AdjacencyListGraphWriter;
import com.burime.calculator.EdgeListGraphReader;
import com.burime.calculator.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Лабораторная работа № 12: Тестирование с помощью Fluent Assertions (Вариант 8)")
class GraphFluentAssertionsTest {

    private EdgeListGraphReader reader;
    private AdjacencyListGraphWriter writer;
    private Graph graph;

    @BeforeEach
    void setUp() {
        reader = new EdgeListGraphReader();
        writer = new AdjacencyListGraphWriter();
        graph = new Graph(reader, writer);
    }

    @Test
    @DisplayName("Fluent: проверка алгоритма Обратного обхода дерева (Post-order)")
    void testPostOrderTraversalFluent() throws IOException {
        // Дерево:
        //       1
        //      / \
        //     2   3
        //        / \
        //       4   5
        String input = """
                4
                1 2
                1 3
                3 4
                3 5
                """;

        graph.load(new StringReader(input));
        List<Integer> postOrder = graph.postOrderTraversal();

        // Текучая цепочка проверок результата обхода
        assertThat(postOrder)
                .as("Проверка порядка вершин при обратном обходе дерева (Post-order)")
                .isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(2, 4, 5, 3, 1) // Строгий порядок: сначала дети (2), затем (4, 5, 3), в конце корень (1)
                .startsWith(2)
                .endsWith(1)
                .doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Fluent: проверка построения матрицы инцидентности (МИ)")
    void testIncidenceMatrixFluent() throws IOException {
        String input = """
                2
                1 2
                2 3
                """;

        graph.load(new StringReader(input));
        int[][] matrix = graph.getIncidenceMatrix();

        // Проверка размерностей матрицы и списка вершин
        assertThat(graph.getVertexList())
                .as("Список уникальных вершин графа")
                .hasSize(3)
                .containsExactly(1, 2, 3);

        assertThat(matrix)
                .as("Матрица инцидентности: 3 вершины (строки) на 2 ребра (столбца)")
                .hasDimensions(3, 2);

        // Проверка значений конкретного столбца (ребро 1 -> 2: вершина 1 = +1, вершина 2 = -1, вершина 3 = 0)
        assertThat(matrix[0][0]).as("Вершина 1 (источник ребра 0)").isEqualTo(1);
        assertThat(matrix[1][0]).as("Вершина 2 (приемник ребра 0)").isEqualTo(-1);
        assertThat(matrix[2][0]).as("Вершина 3 (не участвует в ребре 0)").isEqualTo(0);
    }

    @Test
    @DisplayName("Fluent: проверка преобразования МИ в Список связности (СС)")
    void testAdjacencyListFromIncidenceFluent() throws IOException {
        String input = """
                3
                10 20
                10 30
                20 30
                """;

        graph.load(new StringReader(input));
        Map<Integer, List<Integer>> adj = graph.buildAdjacencyListFromIncidence();

        // Проверка словаря связности (СС)
        assertThat(adj)
                .as("Словарь смежности графа")
                .isNotNull()
                .hasSize(3)
                .containsOnlyKeys(10, 20, 30);

        // Проверка списков смежных вершин для каждого узла
        assertThat(adj.get(10))
                .as("Соседи вершины 10")
                .containsExactlyInAnyOrder(20, 30);

        assertThat(adj.get(20))
                .as("Соседи вершины 20")
                .containsExactly(30);

        assertThat(adj.get(30))
                .as("Соседи вершины 30 (тупик)")
                .isEmpty();
    }

    @Test
    @DisplayName("Fluent: проверка текстового вывода сериализатора AdjacencyListGraphWriter")
    void testAdjacencyListWriterFormattingFluent() throws IOException {
        String input = """
                2
                1 2
                1 3
                """;

        graph.load(new StringReader(input));
        StringWriter stringWriter = new StringWriter();
        graph.save(stringWriter);

        String result = stringWriter.toString();

        // Проверка форматирования экспортируемого текста
        assertThat(result)
                .as("Форматированный вывод списка связности")
                .isNotBlank()
                .startsWith("1:")
                .contains("1: 2 3")
                .contains("2:")
                .contains("3:")
                .doesNotContain("null", "Exception");
    }

    @Test
    @DisplayName("Fluent: проверка реакции на null и некорректный ввод")
    void testExceptionsFluent() {
        // 1. Проверка передачи null в Reader
        assertThatThrownBy(() -> graph.load(null))
                .as("Попытка загрузки из null Reader")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reader не может быть null");

        // 2. Проверка вызова сохранения при отсутствующем IGraphWriter
        Graph brokenGraph = new Graph(reader, null);
        assertThatThrownBy(() -> brokenGraph.save(new StringWriter()))
                .as("Попытка сохранения без установленного Writer")
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("IGraphWriter не установлен");
    }
}