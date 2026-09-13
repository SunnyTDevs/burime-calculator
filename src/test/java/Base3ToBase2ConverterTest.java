import com.burime.calculator.Base3ToBase2Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тестирование методом серого ящика: Конвертер Base 3 -> Base 2")
class Base3ToBase2ConverterTest {

    @ParameterizedTest(name = "[{index}] Вход: \"{0}\" -> Ожидается: \"{1}\" ({2})")
    @CsvSource(delimiter = ';', value = {
            "0; 0; Ноль",
            "-0; 0; Отрицательный ноль нормализуется в 0",
            "1; 1; Минимальное положительное",
            "2; 10; Старшая цифра основания",
            "10; 11; Переход через основание 3",
            "11; 100; Четверка (степень двойки)",
            "12; 101; Пятерка",
            "22; 1000; Восьмерка (степень двойки)",
            "102; 1011; Составное троичное 11_10",
            "-102; -1011; Отрицательное число",
            "+102; 1011; Число с явным знаком плюс",
            "0012; 101; Ведущие нули",
            "000; 0; Серия нулей",
            "'  21  '; 111; Пробелы по краям",
            "201201201; 11100000101111; Длинное число"
    })
    @DisplayName("Проверка корректных преобразований (Positive Tests)")
    void testValidConversions(String input, String expected, String description) {
        String actual = Base3ToBase2Converter.convert(input);
        assertEquals(expected, actual, "Ошибка в тесте: " + description);
    }

    @Test
    @DisplayName("Передача null вызывает IllegalArgumentException")
    void testNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Base3ToBase2Converter.convert(null));
    }

    @ParameterizedTest(name = "Некорректный пустой/знаковый ввод: \"{0}\"")
    @ValueSource(strings = {"", "   ", "+", "-"})
    @DisplayName("Проверка пустых строк и строк, состоящих только из знака")
    void testEmptyAndSignOnlyThrowsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> Base3ToBase2Converter.convert(input));
    }

    @ParameterizedTest(name = "Недопустимые символы: \"{0}\"")
    @ValueSource(strings = {"3", "124", "12a", "1 2", "12.0"})
    @DisplayName("Проверка обнаружения недопустимых символов для алфавита {0, 1, 2}")
    void testInvalidCharactersThrowsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> Base3ToBase2Converter.convert(input));
    }

    @Test
    @DisplayName("Арифметическое переполнение аккумулятора long")
    void testOverflowThrowsException() {
        String overflowStr = "2222222222222222222222222222222222222222";
        assertThrows(IllegalArgumentException.class, () -> Base3ToBase2Converter.convert(overflowStr));
    }
}