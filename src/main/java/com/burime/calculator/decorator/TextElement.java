package com.burime.calculator.decorator;

/**
 * Lab11: Decorator pattern for HTML text formatting
 * Задание: Отображение текста в HTML-формате с различными атрибутами
 * (полужирный, курсив, подчеркнутый, зачеркнутый, текст)
 */
public interface TextElement {
    String getText();
    String render();
}