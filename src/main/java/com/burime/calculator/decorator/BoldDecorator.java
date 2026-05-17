package com.burime.calculator.decorator;

/**
 * Bold (полужирный) decorator
 */
public class BoldDecorator extends TextDecorator {
    public BoldDecorator(TextElement element) {
        super(element);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String render() {
        return "<b>" + element.render() + "</b>";
    }
}
