package com.burime.calculator.decorator;

import java.util.Scanner;

public class DecoratorDemo {
    
    public static void main(String[] args) {
        String text = "Hello World";
        int choice = 5; // Значение по умолчанию
        
        // Если передан аргумент - используем его как текст
        if (args.length > 0) {
            text = args[0];
        }
        // Второй аргумент - номер опции (1-7)
        if (args.length > 1) {
            try {
                choice = Integer.parseInt(args[1]);
                System.out.println("Опция передана через аргумент: " + choice);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат опции. Используется значение по умолчанию: " + choice);
            }
        }
        
        System.out.println("=== Lab11: Text Formatting (Decorator Pattern) ===");
        System.out.println();
        
        // Показываем ВСЕ доступные опции (CheckBox) и результат
        System.out.println("Доступные опции форматирования:");
        System.out.println();
        
        // Опция 1: только Bold
        System.out.println("[1] Bold (полужирный)");
        FormattedTextBuilder b1 = new FormattedTextBuilder(text);
        b1.setBold(true);
        System.out.println("    HTML: " + b1.build().render());
        System.out.println();
        
        // Опция 2: только Italic  
        System.out.println("[2] Italic (курсив)");
        FormattedTextBuilder b2 = new FormattedTextBuilder(text);
        b2.setItalic(true);
        System.out.println("    HTML: " + b2.build().render());
        System.out.println();
        
        // Опция 3: только Underline
        System.out.println("[3] Underline (подчеркнутый)");
        FormattedTextBuilder b3 = new FormattedTextBuilder(text);
        b3.setUnderline(true);
        System.out.println("    HTML: " + b3.build().render());
        System.out.println();
        
        // Опция 4: только Strikethrough
        System.out.println("[4] Strikethrough (зачеркнутый)");
        FormattedTextBuilder b4 = new FormattedTextBuilder(text);
        b4.setStrikethrough(true);
        System.out.println("    HTML: " + b4.build().render());
        System.out.println();
        
        // Опция 5: Bold + Italic
        System.out.println("[5] Bold + Italic (полужирный + курсив)");
        FormattedTextBuilder b5 = new FormattedTextBuilder(text);
        b5.setBold(true);
        b5.setItalic(true);
        System.out.println("    HTML: " + b5.build().render());
        System.out.println();
        
        // Опция 6: Bold + Italic + Underline
        System.out.println("[6] Bold + Italic + Underline");
        FormattedTextBuilder b6 = new FormattedTextBuilder(text);
        b6.setBold(true);
        b6.setItalic(true);
        b6.setUnderline(true);
        System.out.println("    HTML: " + b6.build().render());
        System.out.println();
        
        // Опция 7: Все стили
        System.out.println("[7] Все стили (Bold + Italic + Underline + Strikethrough)");
        FormattedTextBuilder b7 = new FormattedTextBuilder(text);
        b7.setBold(true);
        b7.setItalic(true);
        b7.setUnderline(true);
        b7.setStrikethrough(true);
        System.out.println("    HTML: " + b7.build().render());
        System.out.println();
        
        // Пример выбора конкретного стиля
        System.out.println("=".repeat(40));
        System.out.println();
        System.out.println("Пример выбора (выбрана опция 5):");
        
        // Демонстрация "выбора"
        // Сначала пытаемся получить из аргумента, потом из stdin
        if (args.length > 1) {
            // choice уже установлен из аргумента
        } else {
            // Пытаемся получить из stdin
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введите номер опции (1-7): ");
                choice = scanner.nextInt();
                System.out.println("Вы ввели: " + choice);
            } catch (java.util.NoSuchElementException e) {
                // stdin недоступен (например, при запуске через gradlew run)
                System.out.println("Внимание: stdin недоступен. Используется значение по умолчанию: " + choice);
            }
        }
        System.out.println();

        FormattedTextBuilder selected = getFormattedTextBuilder(text, choice);

        System.out.println("Выбрана опция " + choice + ": " + selected.build().render());
        System.out.println();
        System.out.println("=== Готово ===");
    }

    private static FormattedTextBuilder getFormattedTextBuilder(String text, int choice) {
        FormattedTextBuilder selected = new FormattedTextBuilder(text);
        if (choice == 1) selected.setBold(true);
        else if (choice == 2) selected.setItalic(true);
        else if (choice == 3) selected.setUnderline(true);
        else if (choice == 4) selected.setStrikethrough(true);
        else if (choice == 5) { selected.setBold(true); selected.setItalic(true); }
        else if (choice == 6) { selected.setBold(true); selected.setItalic(true); selected.setUnderline(true); }
        else if (choice == 7) { selected.setBold(true); selected.setItalic(true); selected.setUnderline(true); selected.setStrikethrough(true); }
        return selected;
    }
}