package com.burime.calculator.decorator;

/**
 * Strikethrough (зачеркнутый) decorator
 */
public class StrikethroughDecorator extends TextDecorator {
    public StrikethroughDecorator(TextElement element) {
        super(element);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String render() {
        return "<s>" + element.render() + "</s>";
    }
}
