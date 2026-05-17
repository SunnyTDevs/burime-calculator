package com.burime.calculator.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SimpleText Tests")
class SimpleTextTest {

    @Test
    @DisplayName("should return original text from getText()")
    void getText_returnsOriginalText() {
        SimpleText text = new SimpleText("Hello World");
        assertEquals("Hello World", text.getText());
    }

    @Test
    @DisplayName("should return plain text from render()")
    void render_returnsPlainText() {
        SimpleText text = new SimpleText("Plain Text");
        assertEquals("Plain Text", text.render());
    }

    @Test
    @DisplayName("should throw exception for null text")
    void constructor_throwsForNullText() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleText(null));
    }

    @Test
    @DisplayName("should throw exception for empty text")
    void constructor_throwsForEmptyText() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleText(""));
    }

    @Test
    @DisplayName("should handle multi-word text")
    void handlesMultiWordText() {
        String multiWord = "The quick brown fox";
        SimpleText text = new SimpleText(multiWord);
        assertEquals(multiWord, text.getText());
        assertEquals(multiWord, text.render());
    }

    @Test
    @DisplayName("should handle text with special characters")
    void handlesSpecialCharacters() {
        String special = "<b>HTML & \"special\" & 'chars'</b>";
        SimpleText text = new SimpleText(special);
        assertEquals(special, text.getText());
        assertEquals(special, text.render());
    }

    @Test
    @DisplayName("should handle unicode text")
    void handlesUnicodeText() {
        String unicode = "Привет мир! こんにちは مرحبا";
        SimpleText text = new SimpleText(unicode);
        assertEquals(unicode, text.getText());
        assertEquals(unicode, text.render());
    }
}