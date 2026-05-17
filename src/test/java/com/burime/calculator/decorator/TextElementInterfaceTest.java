package com.burime.calculator.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TextElement Interface Tests")
class TextElementInterfaceTest {

    @Test
    @DisplayName("SimpleText should implement TextElement")
    void simpleTextImplementsTextElement() {
        TextElement text = new SimpleText("Test");
        assertInstanceOf(TextElement.class, text);
    }

    @Test
    @DisplayName("BoldDecorator should implement TextElement")
    void boldDecoratorImplementsTextElement() {
        TextElement text = new SimpleText("Test");
        TextElement decorated = new BoldDecorator(text);
        assertInstanceOf(TextElement.class, decorated);
    }

    @Test
    @DisplayName("ItalicDecorator should implement TextElement")
    void italicDecoratorImplementsTextElement() {
        TextElement text = new SimpleText("Test");
        TextElement decorated = new ItalicDecorator(text);
        assertInstanceOf(TextElement.class, decorated);
    }

    @Test
    @DisplayName("UnderlineDecorator should implement TextElement")
    void underlineDecoratorImplementsTextElement() {
        TextElement text = new SimpleText("Test");
        TextElement decorated = new UnderlineDecorator(text);
        assertInstanceOf(TextElement.class, decorated);
    }

    @Test
    @DisplayName("StrikethroughDecorator should implement TextElement")
    void strikethroughDecoratorImplementsTextElement() {
        TextElement text = new SimpleText("Test");
        TextElement decorated = new StrikethroughDecorator(text);
        assertInstanceOf(TextElement.class, decorated);
    }

    @Nested
    @DisplayName("Polymorphic Behavior")
    class PolymorphicBehaviorTests {

        @Test
        @DisplayName("all decorators should return original text via getText()")
        void allDecoratorsReturnOriginalText() {
            TextElement base = new SimpleText("Base");
            TextElement decorated = new BoldDecorator(new ItalicDecorator(new UnderlineDecorator(base)));
            assertEquals("Base", decorated.getText());
        }

        @Test
        @DisplayName("decorator can be used through TextElement interface")
        void decoratorCanBeUsedPolymorphically() {
            TextElement text = new SimpleText("Hello");
            TextElement decorated = createDecoratedText(text, true, false, true, false);
            // Bold добавляется первым (inner), underline - вторым (outer)
            assertEquals("<u><b>Hello</b></u>", decorated.render());
        }

        @Test
        @DisplayName("decorators can wrap other decorators")
        void decoratorsCanWrapDecorators() {
            TextElement text = new SimpleText("Nested");
            TextElement bold = new BoldDecorator(text);
            TextElement italic = new ItalicDecorator(bold);
            TextElement underline = new UnderlineDecorator(italic);
            assertEquals("<u><i><b>Nested</b></i></u>", underline.render());
        }
    }

    private TextElement createDecoratedText(TextElement element, boolean bold, boolean italic, boolean underline, boolean strikethrough) {
        TextElement result = element;
        if (bold) result = new BoldDecorator(result);
        if (italic) result = new ItalicDecorator(result);
        if (underline) result = new UnderlineDecorator(result);
        if (strikethrough) result = new StrikethroughDecorator(result);
        return result;
    }
}