package com.burime.calculator.decorator;

/**
 * Класс для создания форматированного текста с использованием CheckBox'ов
 * (в консольном приложении реализовано через выбор опций)
 */
public class FormattedTextBuilder {
    private TextElement baseText;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikethrough;
    
    public FormattedTextBuilder(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        this.baseText = new SimpleText(text);
    }
    
    /**
     * Установить полужирный стиль
     */
    public FormattedTextBuilder setBold(boolean bold) {
        this.bold = bold;
        return this;
    }
    
    /**
     * Установить курсивный стиль
     */
    public FormattedTextBuilder setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }
    
    /**
     * Установить подчеркнутый стиль
     */
    public FormattedTextBuilder setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }
    
    /**
     * Установить зачеркнутый стиль
     */
    public FormattedTextBuilder setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }
    
    /**
     * Построить форматированный текст
     */
    public TextElement build() {
        TextElement result = baseText;
        
        // Применяем декораторы в определенном порядке
        if (bold) {
            result = new BoldDecorator(result);
        }
        if (italic) {
            result = new ItalicDecorator(result);
        }
        if (underline) {
            result = new UnderlineDecorator(result);
        }
        if (strikethrough) {
            result = new StrikethroughDecorator(result);
        }
        
        return result;
    }
    
    /**
     * Получить текст без форматирования
     */
    public String getText() {
        return baseText.getText();
    }
    
    /**
     * Проверить, установлен ли полужирный стиль
     */
    public boolean isBold() {
        return bold;
    }
    
    /**
     * Проверить, установлен ли курсивный стиль
     */
    public boolean isItalic() {
        return italic;
    }
    
    /**
     * Проверить, установлен ли подчеркнутый стиль
     */
    public boolean isUnderline() {
        return underline;
    }
    
    /**
     * Проверить, установлен ли зачеркнутый стиль
     */
    public boolean isStrikethrough() {
        return strikethrough;
    }
    
    @Override
    public String toString() {
        return "FormattedTextBuilder{" +
                "text='" + baseText.getText() + '\'' +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underline=" + underline +
                ", strikethrough=" + strikethrough +
                '}';
    }
}