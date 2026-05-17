package com.burime.calculator.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Text Decorator Tests")
class TextDecoratorTest {

    @Nested
    @DisplayName("BoldDecorator")
    class BoldDecoratorTests {

        @Test
        @DisplayName("should wrap text in <b> tags")
        void wrapsTextInBoldTags() {
            TextElement text = new SimpleText("Bold");
            TextElement decorated = new BoldDecorator(text);
            assertEquals("<b>Bold</b>", decorated.render());
        }

        @Test
        @DisplayName("should return original text from getText()")
        void getTextReturnsOriginal() {
            TextElement text = new SimpleText("Content");
            TextElement decorated = new BoldDecorator(text);
            assertEquals("Content", decorated.getText());
        }

        @Test
        @DisplayName("should throw exception for null element")
        void throwsForNullElement() {
            assertThrows(IllegalArgumentException.class, () -> new BoldDecorator(null));
        }

        @Test
        @DisplayName("should be nestable - bold inside bold")
        void nestableDecorators() {
            TextElement text = new SimpleText("Nested");
            TextElement decorated = new BoldDecorator(new BoldDecorator(text));
            assertEquals("<b><b>Nested</b></b>", decorated.render());
        }
    }

    @Nested
    @DisplayName("ItalicDecorator")
    class ItalicDecoratorTests {

        @Test
        @DisplayName("should wrap text in <i> tags")
        void wrapsTextInItalicTags() {
            TextElement text = new SimpleText("Italic");
            TextElement decorated = new ItalicDecorator(text);
            assertEquals("<i>Italic</i>", decorated.render());
        }

        @Test
        @DisplayName("should return original text from getText()")
        void getTextReturnsOriginal() {
            TextElement text = new SimpleText("Content");
            TextElement decorated = new ItalicDecorator(text);
            assertEquals("Content", decorated.getText());
        }

        @Test
        @DisplayName("should throw exception for null element")
        void throwsForNullElement() {
            assertThrows(IllegalArgumentException.class, () -> new ItalicDecorator(null));
        }
    }

    @Nested
    @DisplayName("UnderlineDecorator")
    class UnderlineDecoratorTests {

        @Test
        @DisplayName("should wrap text in <u> tags")
        void wrapsTextInUnderlineTags() {
            TextElement text = new SimpleText("Underline");
            TextElement decorated = new UnderlineDecorator(text);
            assertEquals("<u>Underline</u>", decorated.render());
        }

        @Test
        @DisplayName("should return original text from getText()")
        void getTextReturnsOriginal() {
            TextElement text = new SimpleText("Content");
            TextElement decorated = new UnderlineDecorator(text);
            assertEquals("Content", decorated.getText());
        }

        @Test
        @DisplayName("should throw exception for null element")
        void throwsForNullElement() {
            assertThrows(IllegalArgumentException.class, () -> new UnderlineDecorator(null));
        }
    }

    @Nested
    @DisplayName("StrikethroughDecorator")
    class StrikethroughDecoratorTests {

        @Test
        @DisplayName("should wrap text in <s> tags")
        void wrapsTextInStrikethroughTags() {
            TextElement text = new SimpleText("Strikethrough");
            TextElement decorated = new StrikethroughDecorator(text);
            assertEquals("<s>Strikethrough</s>", decorated.render());
        }

        @Test
        @DisplayName("should return original text from getText()")
        void getTextReturnsOriginal() {
            TextElement text = new SimpleText("Content");
            TextElement decorated = new StrikethroughDecorator(text);
            assertEquals("Content", decorated.getText());
        }

        @Test
        @DisplayName("should throw exception for null element")
        void throwsForNullElement() {
            assertThrows(IllegalArgumentException.class, () -> new StrikethroughDecorator(null));
        }
    }

    @Nested
    @DisplayName("Combined Decorators")
    class CombinedDecoratorTests {

        @Test
        @DisplayName("should apply multiple decorators correctly")
        void multipleDecorators() {
            TextElement text = new SimpleText("Combined");
            TextElement decorated = new BoldDecorator(
                    new ItalicDecorator(
                            new UnderlineDecorator(text)
                    )
            );
            assertEquals("<b><i><u>Combined</u></i></b>", decorated.render());
        }

        @Test
        @DisplayName("should preserve getText through decorators")
        void getTextThroughDecorators() {
            TextElement text = new SimpleText("Preserved");
            TextElement decorated = new BoldDecorator(
                    new ItalicDecorator(
                            new UnderlineDecorator(
                                    new StrikethroughDecorator(text)
                            )
                    )
            );
            assertEquals("Preserved", decorated.getText());
        }

        @Test
        @DisplayName("should handle all decorators combined")
        void allDecoratorsCombined() {
            TextElement text = new SimpleText("Full");
            TextElement decorated = new BoldDecorator(
                    new ItalicDecorator(
                            new UnderlineDecorator(
                                    new StrikethroughDecorator(text)
                            )
                    )
            );
            assertEquals("<b><i><u><s>Full</s></u></i></b>", decorated.render());
        }

        @Test
        @DisplayName("should handle text with special characters in decorators")
        void specialCharsWithDecorators() {
            TextElement text = new SimpleText("<script>alert('xss')</script>");
            TextElement decorated = new BoldDecorator(text);
            assertEquals("<b><script>alert('xss')</script></b>", decorated.render());
        }
    }
}