package com.burime.calculator;

public final class Base3ToBase2Converter {

    public static String convert(String base3) {
        if (base3 == null) {
            throw new IllegalArgumentException("Входная строка не может быть null");
        }

        String trimmed = base3.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Входная строка не может быть пустой");
        }

        boolean isNegative = false;
        int startIndex = 0;
        char firstChar = trimmed.charAt(0);

        if (firstChar == '-') {
            isNegative = true;
            startIndex = 1;
        } else if (firstChar == '+') {
            startIndex = 1;
        }

        if (startIndex == trimmed.length()) {
            throw new IllegalArgumentException("Строка содержит только знак без цифр: " + trimmed);
        }

        // 1. Парсинг троичного числа в десятичный long с контролем переполнения
        long decimalValue = getDecimalValue(startIndex, trimmed);

        // 2. Частный случай: ноль (знак минус для нуля опускается)
        if (decimalValue == 0) {
            return "0";
        }

        // 3. Формирование двоичной строки делением на основание 2
        StringBuilder binary = new StringBuilder();
        long temp = decimalValue;
        while (temp > 0) {
            binary.append(temp % 2);
            temp /= 2;
        }

        if (isNegative) {
            binary.append('-');
        }

        return binary.reverse().toString();
    }

    private static long getDecimalValue(int startIndex, String trimmed) {
        long decimalValue = 0;
        for (int i = startIndex; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            int digit;
            if (c == '0') {
                digit = 0;
            } else if (c == '1') {
                digit = 1;
            } else if (c == '2') {
                digit = 2;
            } else {
                throw new IllegalArgumentException(
                        "Недопустимый символ '" + c + "' для троичной системы счисления на позиции " + i
                );
            }

            try {
                decimalValue = Math.multiplyExact(decimalValue, 3L);
                decimalValue = Math.addExact(decimalValue, (long) digit);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException(
                        "Арифметическое переполнение при парсинге троичного числа: " + trimmed, e
                );
            }
        }
        return decimalValue;
    }
}