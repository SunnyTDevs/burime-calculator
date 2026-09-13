
import com.burime.calculator.AreaClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AreaClassifierTest {

    private AreaClassifier classifier;

    @BeforeEach
    void setUp() {
        classifier = new AreaClassifier(2.0);
    }

    @Test
    @DisplayName("R1: Инициализация с корректным положительным радиусом")
    void testValidRadius() {
        AreaClassifier ac = new AreaClassifier(2.0);
        assertEquals(2.0, ac.getR(), 1e-9);
    }

    @Test
    @DisplayName("R2: Нижняя граница допустимого радиуса (0.001)")
    void testBoundaryValidRadius() {
        AreaClassifier ac = new AreaClassifier(0.001);
        assertEquals(0.001, ac.getR(), 1e-9);
    }

    @ParameterizedTest(name = "R3-R5: Некорректный радиус R = {0} вызывает исключение")
    @ValueSource(doubles = {0.0, -0.001, -5.0})
    @DisplayName("Проверка генерации исключения при R <= 0")
    void testInvalidRadiusThrowsException(double invalidR) {
        assertThrows(IllegalArgumentException.class, () -> new AreaClassifier(invalidR));
        assertThrows(IllegalArgumentException.class, () -> classifier.setR(invalidR));
    }

    @Test
    @DisplayName("T01: Внутренняя точка III четверти (-1, -1)")
    void testThirdQuadrantInterior() {
        assertEquals(1, classifier.testPoint(-1.0, -1.0));
    }

    @Test
    @DisplayName("T02: Начало координат (0, 0)")
    void testOrigin() {
        assertEquals(1, classifier.testPoint(0.0, 0.0));
    }

    @Test
    @DisplayName("T03: Отрицательная полуось OX (-1, 0) -> Область 1")
    void testNegativeXAxis() {
        assertEquals(1, classifier.testPoint(-1.0, 0.0));
    }

    @Test
    @DisplayName("T05: Отрицательная полуось OY (0, -1) -> Область 1")
    void testNegativeYAxis() {
        assertEquals(1, classifier.testPoint(0.0, -1.0));
    }

    @Test
    @DisplayName("T07: Внутренняя точка I четверти над параболой (0.5, 1.0)")
    void testFirstQuadrantAboveParabolaInterior() {
        assertEquals(1, classifier.testPoint(0.5, 1.0));
    }

    @Test
    @DisplayName("T08: Вершина параболы (1, 0)")
    void testParabolaVertex() {
        assertEquals(1, classifier.testPoint(1.0, 0.0));
    }

    @Test
    @DisplayName("T09: Смещение чуть выше вершины (1.0, 0.001)")
    void testSlightlyAboveVertex() {
        assertEquals(1, classifier.testPoint(1.0, 0.001));
    }

    @Test
    @DisplayName("T11-T12: Граница левой ветви параболы и точка чуть выше (x = 0.5)")
    void testLeftBranchParabola() {
        assertEquals(1, classifier.testPoint(0.5, 0.25));
        assertEquals(1, classifier.testPoint(0.5, 0.251));
    }

    @Test
    @DisplayName("T14-T15: Граница правой ветви параболы и точка чуть выше (x = 1.5)")
    void testRightBranchParabola() {
        assertEquals(1, classifier.testPoint(1.5, 0.25));
        assertEquals(1, classifier.testPoint(1.5, 0.251));
    }

    @Test
    @DisplayName("T17: Пересечение параболы с осью OY (0.0, 1.0)")
    void testParabolaYIntercept() {
        assertEquals(1, classifier.testPoint(0.0, 1.0));
    }

    @Test
    @DisplayName("T20: Ось OY выше точки (0, 1) -> (0.0, 1.5)")
    void testYAxisAboveIntercept() {
        assertEquals(1, classifier.testPoint(0.0, 1.5));
    }

    @Test
    @DisplayName("T25: Верхняя граница окружности на оси OY (0.0, 2.0)")
    void testCircleTopBoundary() {
        assertEquals(1, classifier.testPoint(0.0, 2.0));
    }

    @Test
    @DisplayName("T29: Граница окружности в III четверти (-1.414, -1.414)")
    void testCircleBoundaryQuadrantThree() {
        assertEquals(1, classifier.testPoint(-1.414, -1.414));
    }

    @Test
    @DisplayName("T04: Смещение из III четверти чуть выше оси OX во II четверть (-1.0, 0.001)")
    void testSlightlyAboveNegativeXAxis() {
        assertEquals(2, classifier.testPoint(-1.0, 0.001));
    }

    @Test
    @DisplayName("T06: Смещение из III четверти чуть правее оси OY в IV четверть (0.001, -1.0)")
    void testSlightlyRightOfNegativeYAxis() {
        assertEquals(2, classifier.testPoint(0.001, -1.0));
    }

    @Test
    @DisplayName("T10: Смещение из вершины параболы чуть ниже в IV четверть (1.0, -0.001)")
    void testSlightlyBelowVertex() {
        assertEquals(2, classifier.testPoint(1.0, -0.001));
    }

    @Test
    @DisplayName("T13: Чуть ниже параболы при x = 0.5 -> (0.5, 0.249)")
    void testSlightlyBelowLeftBranch() {
        assertEquals(2, classifier.testPoint(0.5, 0.249));
    }

    @Test
    @DisplayName("T16: Чуть ниже параболы при x = 1.5 -> (1.5, 0.249)")
    void testSlightlyBelowRightBranch() {
        assertEquals(2, classifier.testPoint(1.5, 0.249));
    }

    @Test
    @DisplayName("T18: На оси OY чуть ниже параболы (0.0, 0.999)")
    void testSlightlyBelowYIntercept() {
        assertEquals(2, classifier.testPoint(0.0, 0.999));
    }

    @Test
    @DisplayName("T19: Смещение от (0, 1) влево во II четверть (-0.001, 1.0)")
    void testSlightlyLeftOfYIntercept() {
        assertEquals(2, classifier.testPoint(-0.001, 1.0));
    }

    @Test
    @DisplayName("T21: Смещение от оси OY влево во II четверть (-0.001, 1.5)")
    void testSlightlyLeftOfYAxisAboveIntercept() {
        assertEquals(2, classifier.testPoint(-0.001, 1.5));
    }

    @Test
    @DisplayName("T22: Внутренняя точка II четверти (-1.0, 1.0)")
    void testQuadrantTwoInterior() {
        assertEquals(2, classifier.testPoint(-1.0, 1.0));
    }

    @Test
    @DisplayName("T23: Внутренняя точка IV четверти (1.0, -1.0)")
    void testQuadrantFourInterior() {
        assertEquals(2, classifier.testPoint(1.0, -1.0));
    }

    @Test
    @DisplayName("T24: I четверть строго под параболой (0.5, 0.1)")
    void testQuadrantOneBelowParabolaInterior() {
        assertEquals(2, classifier.testPoint(0.5, 0.1));
    }

    @Test
    @DisplayName("T27: Правая граница окружности на оси OX (2.0, 0.0)")
    void testCircleRightBoundary() {
        assertEquals(2, classifier.testPoint(2.0, 0.0));
    }

    @Test
    @DisplayName("T26: Выход за границу окружности вверх (0.0, 2.001)")
    void testCircleExitTop() {
        assertEquals(3, classifier.testPoint(0.0, 2.001));
    }

    @Test
    @DisplayName("T28: Выход за границу окружности вправо (2.001, 0.0)")
    void testCircleExitRight() {
        assertEquals(3, classifier.testPoint(2.001, 0.0));
    }

    @Test
    @DisplayName("T30: Выход за границу окружности в III четверти (-1.415, -1.415)")
    void testCircleExitQuadrantThree() {
        assertEquals(3, classifier.testPoint(-1.415, -1.415));
    }

    @Test
    @DisplayName("T31: Заведомо удаленная внешняя точка (3.0, 3.0)")
    void testFarOutside() {
        assertEquals(3, classifier.testPoint(3.0, 3.0));
    }
}