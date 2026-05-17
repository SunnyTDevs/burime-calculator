package com.burime.calculator.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FormattedTextBuilder Tests")
class FormattedTextBuilderTest {

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("should create builder with valid text")
        void createsBuilderWithValidText() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            assertEquals("Test", builder.getText());
        }

        @Test
        @DisplayName("should throw exception for null text")
        void throwsForNullText() {
            assertThrows(IllegalArgumentException.class, () -> new FormattedTextBuilder(null));
        }

        @Test
        @DisplayName("should throw exception for empty text")
        void throwsForEmptyText() {
            assertThrows(IllegalArgumentException.class, () -> new FormattedTextBuilder(""));
        }
    }

    @Nested
    @DisplayName("Setters")
    class SetterTests {

        @Test
        @DisplayName("setBold should enable bold style")
        void setBoldEnablesStyle() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            builder.setBold(true);
            assertTrue(builder.isBold());
        }

        @Test
        @DisplayName("setItalic should enable italic style")
        void setItalicEnablesStyle() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            builder.setItalic(true);
            assertTrue(builder.isItalic());
        }

        @Test
        @DisplayName("setUnderline should enable underline style")
        void setUnderlineEnablesStyle() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            builder.setUnderline(true);
            assertTrue(builder.isUnderline());
        }

        @Test
        @DisplayName("setStrikethrough should enable strikethrough style")
        void setStrikethroughEnablesStyle() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            builder.setStrikethrough(true);
            assertTrue(builder.isStrikethrough());
        }

        @Test
        @DisplayName("should return builder for chaining")
        void settersReturnBuilder() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            assertSame(builder, builder.setBold(true));
            assertSame(builder, builder.setItalic(true));
            assertSame(builder, builder.setUnderline(true));
            assertSame(builder, builder.setStrikethrough(true));
        }
    }

    @Nested
    @DisplayName("Build")
    class BuildTests {

        @Test
        @DisplayName("build with no styles should return plain text")
        void buildNoStylesReturnsPlainText() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Plain");
            TextElement result = builder.build();
            assertEquals("Plain", result.render());
        }

        @Test
        @DisplayName("build with bold should wrap in <b> tags")
        void buildBoldWrapsInBoldTags() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Bold");
            builder.setBold(true);
            assertEquals("<b>Bold</b>", builder.build().render());
        }

        @Test
        @DisplayName("build with italic should wrap in <i> tags")
        void buildItalicWrapsInItalicTags() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Italic");
            builder.setItalic(true);
            assertEquals("<i>Italic</i>", builder.build().render());
        }

        @Test
        @DisplayName("build with underline should wrap in <u> tags")
        void buildUnderlineWrapsInUnderlineTags() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Underline");
            builder.setUnderline(true);
            assertEquals("<u>Underline</u>", builder.build().render());
        }

        @Test
        @DisplayName("build with strikethrough should wrap in <s> tags")
        void buildStrikethroughWrapsInStrikethroughTags() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Strikethrough");
            builder.setStrikethrough(true);
            assertEquals("<s>Strikethrough</s>", builder.build().render());
        }

        @Test
        @DisplayName("build with all styles should apply all decorators")
        void buildAllStylesAppliesAllDecorators() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Full");
            builder.setBold(true);
            builder.setItalic(true);
            builder.setUnderline(true);
            builder.setStrikethrough(true);
            // Последний добавленный декоратор становится внешним
            assertEquals("<s><u><i><b>Full</b></i></u></s>", builder.build().render());
        }

        @Test
        @DisplayName("build with multiple styles should maintain order")
        void buildMultipleStylesMaintainsOrder() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Multi");
            builder.setBold(true);
            builder.setItalic(true);
            // Bold добавляется первым, italic - вторым (становится внешним)
            assertEquals("<i><b>Multi</b></i>", builder.build().render());
        }

        @Test
        @DisplayName("builder methods should be chainable")
        void builderMethodsAreChainable() {
            TextElement result = new FormattedTextBuilder("Chained")
                    .setBold(true)
                    .setItalic(true)
                    .setUnderline(true)
                    .setStrikethrough(true)
                    .build();
            // Strikethrough - последний, становится внешним
            assertEquals("<s><u><i><b>Chained</b></i></u></s>", result.render());
        }
    }

    @Nested
    @DisplayName("ToString")
    class ToStringTests {

        @Test
        @DisplayName("toString should contain text and style info")
        void toStringContainsRelevantInfo() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            builder.setBold(true);
            builder.setItalic(true);
            String str = builder.toString();
            assertTrue(str.contains("Test"));
            assertTrue(str.contains("bold=true"));
            assertTrue(str.contains("italic=true"));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("should handle single character text")
        void handlesSingleCharacter() {
            TextElement result = new FormattedTextBuilder("A")
                    .setBold(true)
                    .build();
            assertEquals("<b>A</b>", result.render());
        }

        @Test
        @DisplayName("should handle whitespace-only text with styles")
        void handlesWhitespaceWithStyles() {
            FormattedTextBuilder builder = new FormattedTextBuilder("   ");
            builder.setBold(true);
            TextElement result = builder.build();
            assertEquals("<b>   </b>", result.render());
        }

        @Test
        @DisplayName("getText should return original text regardless of styles")
        void getTextReturnsOriginalRegardlessOfStyles() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Original");
            builder.setBold(true);
            builder.setItalic(true);
            builder.setUnderline(true);
            builder.setStrikethrough(true);
            assertEquals("Original", builder.getText());
        }

        @Test
        @DisplayName("default style values should be false")
        void defaultStyleValuesAreFalse() {
            FormattedTextBuilder builder = new FormattedTextBuilder("Test");
            assertFalse(builder.isBold());
            assertFalse(builder.isItalic());
            assertFalse(builder.isUnderline());
            assertFalse(builder.isStrikethrough());
        }
    }
}