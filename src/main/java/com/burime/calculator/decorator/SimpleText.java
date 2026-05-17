package com.burime.calculator.decorator;

/**
 * Базовый текстовый элемент без форматирования
 */
public class SimpleText implements TextElement {
    private final String text;

    public SimpleText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String render() {
        return text;
    }
}
