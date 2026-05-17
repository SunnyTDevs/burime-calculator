package com.burime.calculator.decorator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * GUI приложение для форматирования текста с использованием паттерна Decorator
 * Позволяет выбирать опции форматирования через Checkbox'ы
 */
public class TextFormattingGUI extends JFrame {

    // Цветовая схема
    private static final Color CLR_HEADER_BG  = new Color(33, 37, 43);
    private static final Color CLR_PANEL_BG   = new Color(248, 249, 250);
    private static final Color CLR_LOG_BG     = new Color(24, 26, 31);
    private static final Color CLR_LOG_FG     = new Color(160, 215, 130);
    private static final Color CLR_MUTED      = new Color(100, 100, 110);

    private JTextField textInput;
    private JCheckBox boldCheckBox;
    private JCheckBox italicCheckBox;
    private JCheckBox underlineCheckBox;
    private JCheckBox strikethroughCheckBox;
    private JTextArea resultArea;

    public TextFormattingGUI() {
        setTitle("Decorator Pattern — HTML Text Formatting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setSize(620, 480);
        setMinimumSize(new Dimension(500, 400));
        getContentPane().setBackground(CLR_PANEL_BG);

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(),  BorderLayout.CENTER);
        add(buildResult(),  BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        updateResult();
    }

    // ── Заголовок ─────────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CLR_HEADER_BG);
        panel.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("Паттерн Decorator");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Форматирование текста в HTML");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(170, 178, 195));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    // ── Центр: поле ввода + чекбоксы ─────────────────────────────────────────

    private JPanel buildCenter() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(CLR_PANEL_BG);
        panel.setBorder(new EmptyBorder(16, 20, 10, 20));

        panel.add(buildInputPanel(),    BorderLayout.NORTH);
        panel.add(buildOptionsPanel(),  BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(CLR_PANEL_BG);

        JLabel label = new JLabel("Введите текст");
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(CLR_MUTED);

        textInput = new JTextField("Hello World");
        textInput.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        textInput.addActionListener(e -> updateResult());

        panel.add(label,     BorderLayout.NORTH);
        panel.add(textInput, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildOptionsPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 8));
        wrapper.setBackground(CLR_PANEL_BG);

        JLabel label = new JLabel("Опции форматирования");
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(CLR_MUTED);

        // Чекбоксы в строку
        JPanel checkBoxRow = new JPanel(new GridLayout(1, 4, 10, 0));
        checkBoxRow.setBackground(CLR_PANEL_BG);

        boldCheckBox          = createCheckBox("Ж  полужирный");
        italicCheckBox        = createCheckBox("К  курсив");
        underlineCheckBox     = createCheckBox("П  подчёркнутый");
        strikethroughCheckBox = createCheckBox("З  зачёркнутый");

        checkBoxRow.add(wrapCheckBox(boldCheckBox,          new Color(0, 100, 200, 20),  new Color(0, 100, 200)));
        checkBoxRow.add(wrapCheckBox(italicCheckBox,        new Color(111, 66, 193, 20), new Color(111, 66, 193)));
        checkBoxRow.add(wrapCheckBox(underlineCheckBox,     new Color(40, 167, 69, 20),  new Color(40, 167, 69)));
        checkBoxRow.add(wrapCheckBox(strikethroughCheckBox, new Color(220, 53, 69, 20),  new Color(220, 53, 69)));

        wrapper.add(label,        BorderLayout.NORTH);
        wrapper.add(checkBoxRow,  BorderLayout.CENTER);
        return wrapper;
    }

    private JCheckBox createCheckBox(String text) {
        JCheckBox cb = new JCheckBox(text);
        cb.setFont(new Font("SansSerif", Font.BOLD, 12));
        cb.setOpaque(false);
        cb.addItemListener(e -> updateResult());
        return cb;
    }

    private JPanel wrapCheckBox(JCheckBox cb, Color bg, Color border) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        cb.setForeground(border.darker());
        card.add(cb, BorderLayout.CENTER);
        return card;
    }

    // ── Результат ─────────────────────────────────────────────────────────────

    private JPanel buildResult() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(CLR_PANEL_BG);
        panel.setBorder(new EmptyBorder(0, 20, 18, 20));

        JLabel label = new JLabel("Результат (HTML)");
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(CLR_MUTED);

        resultArea = new JTextArea(4, 0);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(CLR_LOG_BG);
        resultArea.setForeground(CLR_LOG_FG);
        resultArea.setCaretColor(CLR_LOG_FG);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(new EmptyBorder(10, 14, 10, 14));

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 70)));

        panel.add(label,  BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ── Логика ────────────────────────────────────────────────────────────────

    private void updateResult() {
        String text = textInput.getText().isEmpty() ? "Hello World" : textInput.getText();

        TextElement result = new FormattedTextBuilder(text)
                .setBold(boldCheckBox.isSelected())
                .setItalic(italicCheckBox.isSelected())
                .setUnderline(underlineCheckBox.isSelected())
                .setStrikethrough(strikethroughCheckBox.isSelected())
                .build();

        resultArea.setText(result.render());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new TextFormattingGUI().setVisible(true));
    }
}
