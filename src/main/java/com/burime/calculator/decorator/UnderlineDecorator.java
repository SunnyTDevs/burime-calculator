package com.burime.calculator.decorator;

/**
 * Underline (подчеркнутый) decorator
 */
public class UnderlineDecorator extends TextDecorator {
    public UnderlineDecorator(TextElement element) {
        super(element);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String render() {
        return "<u>" + element.render() + "</u>";
    }
}
