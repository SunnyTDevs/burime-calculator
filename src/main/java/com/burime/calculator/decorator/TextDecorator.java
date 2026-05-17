package com.burime.calculator.decorator;

public abstract class TextDecorator implements TextElement {
    protected TextElement element;

    public TextDecorator(TextElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        this.element = element;
    }
}
