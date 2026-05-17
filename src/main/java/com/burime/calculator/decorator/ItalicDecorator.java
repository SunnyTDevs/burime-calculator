package com.burime.calculator.decorator;

/**
 * Italic (курсив) decorator
 */
public class ItalicDecorator extends TextDecorator {
    public ItalicDecorator(TextElement element) {
        super(element);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String render() {
        return "<i>" + element.render() + "</i>";
    }
}
