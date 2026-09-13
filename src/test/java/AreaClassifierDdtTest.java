import com.burime.calculator.AreaClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Параметризованное тестирование классификатора областей (Data-Driven Tests)")
class AreaClassifierDdtTest {

    private AreaClassifier classifier;

    @BeforeEach
    void setUp() {
        classifier = new AreaClassifier(2.0);
    }

    // Тестирование радиуса с использованием встроенного набора данных
    @ParameterizedTest(name = "Проверка некорректного радиуса R = {0}")
    @ValueSource(doubles = {0.0, -0.001, -5.0})
    @DisplayName("DDT: Инициализация с недопустимым R выбрасывает IllegalArgumentException")
    void testInvalidRadiusDdt(double invalidR) {
        assertThrows(IllegalArgumentException.class, () -> new AreaClassifier(invalidR));
        assertThrows(IllegalArgumentException.class, () -> classifier.setR(invalidR));
    }

    // Тестирование точек через метод-источник тестовых данных
    @ParameterizedTest(name = "[{index}] {3} -> Point({0}; {1}) ожидается Область {2}")
    @MethodSource("providePointsForAreaClassification")
    @DisplayName("DDT: Классификация принадлежности точки областям (31 тестовый случай)")
    void testPointClassificationDdt(double x, double y, int expectedArea, String description) {
        int actualArea = classifier.testPoint(x, y);

        assertEquals(expectedArea, actualArea, "Ошибка в тесте: " + description);
    }

    static Stream<Arguments> providePointsForAreaClassification() {
        return Stream.of(
                // Область 1 (Закрашенная зона: III четверть и над параболой)
                Arguments.of(-1.0, -1.0, 1, "T01: III четверть, внутренняя точка"),
                Arguments.of(0.0, 0.0, 1, "T02: Начало координат (граница III четверти)"),
                Arguments.of(-1.0, 0.0, 1, "T03: Отрицательная полуось OX (граница)"),
                Arguments.of(0.0, -1.0, 1, "T05: Отрицательная полуось OY (граница)"),
                Arguments.of(0.5, 1.0, 1, "T07: I четверть над параболой (внутри)"),
                Arguments.of(1.0, 0.0, 1, "T08: Вершина параболы (1, 0)"),
                Arguments.of(1.0, 0.001, 1, "T09: Чуть выше вершины параболы"),
                Arguments.of(0.5, 0.25, 1, "T11: На левой ветви параболы y=(0.5-1)^2"),
                Arguments.of(0.5, 0.251, 1, "T12: Чуть выше левой ветви параболы"),
                Arguments.of(1.5, 0.25, 1, "T14: На правой ветви параболы y=(1.5-1)^2"),
                Arguments.of(1.5, 0.251, 1, "T15: Чуть выше правой ветви параболы"),
                Arguments.of(0.0, 1.0, 1, "T17: Пересечение параболы с осью OY"),
                Arguments.of(0.0, 1.5, 1, "T20: Ось OY выше параболы"),
                Arguments.of(0.0, 2.0, 1, "T25: Верхняя граница окружности на оси OY"),
                Arguments.of(-1.414, -1.414, 1, "T29: Граница окружности в III четверти"),

                // Область 2 (Незакрашенная зона внутри круга)
                Arguments.of(-1.0, 0.001, 2, "T04: Чуть выше оси OX во II четверть"),
                Arguments.of(0.001, -1.0, 2, "T06: Чуть правее оси OY в IV четверть"),
                Arguments.of(1.0, -0.001, 2, "T10: Чуть ниже вершины параболы в IV четверть"),
                Arguments.of(0.5, 0.249, 2, "T13: Чуть ниже левой ветви параболы"),
                Arguments.of(1.5, 0.249, 2, "T16: Чуть ниже правой ветви параболы"),
                Arguments.of(0.0, 0.999, 2, "T18: На оси OY чуть ниже параболы"),
                Arguments.of(-0.001, 1.0, 2, "T19: Смещение от точки (0, 1) влево во II четверть"),
                Arguments.of(-0.001, 1.5, 2, "T21: Смещение от оси OY влево во II четверть"),
                Arguments.of(-1.0, 1.0, 2, "T22: II четверть, внутренняя точка"),
                Arguments.of(1.0, -1.0, 2, "T23: IV четверть, внутренняя точка"),
                Arguments.of(0.5, 0.1, 2, "T24: I четверть строго под параболой"),
                Arguments.of(2.0, 0.0, 2, "T27: Правая граница окружности на оси OX"),

                // Область 3 (Снаружи окружности x^2 + y^2 > R^2)
                Arguments.of(0.0, 2.001, 3, "T26: Выход за окружность вверх"),
                Arguments.of(2.001, 0.0, 3, "T28: Выход за окружность вправо"),
                Arguments.of(-1.415, -1.415, 3, "T30: Выход за окружность в III четверти"),
                Arguments.of(3.0, 3.0, 3, "T31: Заведомо внешняя точка плоскости")
        );
    }
}