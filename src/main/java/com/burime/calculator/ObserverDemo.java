package com.burime.calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Демонстрационная программа с GUI - визуальное представление паттерна Observer
 */
public class ObserverDemo extends JFrame {
    private static final DateTimeFormatter LOG_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Цветовая схема
    private static final Color CLR_HEADER_BG = new Color(33, 37, 43);
    private static final Color CLR_PANEL_BG = new Color(248, 249, 250);
    private static final Color CLR_LOG_BG = new Color(24, 26, 31);
    private static final Color CLR_LOG_FG = new Color(160, 215, 130);
    private static final Color CLR_INACTIVE = new Color(150, 150, 155);
    private static final Color CLR_ACTIVE = new Color(40, 167, 69);
    private static final Color CLR_PAUSED = new Color(230, 160, 0);
    private static final Color CLR_PROCESSING = new Color(111, 66, 193);
    private static final Color CLR_COMPLETED = new Color(0, 123, 255);


    private final Subject subject;
    private final TimeObserver timeObserver;
    private final StateObserver stateObserver;

    // GUI компоненты
    private JLabel stateBadge;
    private JLabel timeValueLabel;
    private JLabel timeLastStateLabel;
    private JLabel stateValueLabel;
    private JLabel stateLastStateLabel;
    private JCheckBox checkBoxTime, checkBoxState;
    private JTextArea logArea;

    public ObserverDemo() {
        // Инициализация Subject и Observers
        subject = new Subject();
        timeObserver = new TimeObserver("Наблюдатель времени");
        stateObserver = new StateObserver("Наблюдатель состояния");

        subject.attach(timeObserver);
        subject.attach(stateObserver);

        setTitle("Паттерн Observer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setSize(680, 580);
        setMinimumSize(new Dimension(580, 480));
        getContentPane().setBackground(CLR_PANEL_BG);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildLogPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        log("Программа запущена. Наблюдатели подключены.", false);
    }

    // ── Заголовок ─────────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CLR_HEADER_BG);
        panel.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("Паттерн Observer");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Управление состоянием субъекта");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(170, 178, 195));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        // Бейдж текущего состояния
        stateBadge = new JLabel("НЕАКТИВЕН", SwingConstants.CENTER);
        stateBadge.setFont(new Font("SansSerif", Font.BOLD, 13));
        stateBadge.setForeground(Color.WHITE);
        stateBadge.setOpaque(true);
        stateBadge.setBackground(CLR_INACTIVE);
        stateBadge.setBorder(new EmptyBorder(6, 16, 6, 16));
        stateBadge.setPreferredSize(new Dimension(150, 36));

        panel.add(textPanel, BorderLayout.WEST);
        panel.add(stateBadge, BorderLayout.EAST);
        return panel;
    }

    // ── Центральная панель: кнопки + карточки наблюдателей ───────────────────

    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(CLR_PANEL_BG);
        panel.setBorder(new EmptyBorder(14, 16, 10, 16));

        panel.add(buildButtonsPanel(), BorderLayout.NORTH);
        panel.add(buildObserversPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonsPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 8));
        wrapper.setBackground(CLR_PANEL_BG);

        JLabel sectionLabel = new JLabel("Переходы состояний");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(100, 100, 110));

        // Кнопки переходов
        JPanel buttons = new JPanel(new GridLayout(2, 3, 8, 8));
        buttons.setBackground(CLR_PANEL_BG);
        buttons.add(createButton("ACTIVATE", "Активировать", CLR_ACTIVE));
        buttons.add(createButton("PAUSE", "Приостановить", CLR_PAUSED));
        buttons.add(createButton("RESUME", "Возобновить", CLR_ACTIVE));
        buttons.add(createButton("PROCESS", "Обрабатывать", CLR_PROCESSING));
        buttons.add(createButton("COMPLETE", "Завершить", CLR_COMPLETED));
        buttons.add(createButton("RESET", "Сбросить", CLR_INACTIVE));

        wrapper.add(sectionLabel, BorderLayout.NORTH);
        wrapper.add(buttons, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildObserversPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 8));
        wrapper.setBackground(CLR_PANEL_BG);

        JLabel sectionLabel = new JLabel("Наблюдатели");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(100, 100, 110));

        JPanel cards = new JPanel(new GridLayout(1, 2, 12, 0));
        cards.setBackground(CLR_PANEL_BG);
        cards.add(buildTimeObserverCard());
        cards.add(buildStateObserverCard());

        wrapper.add(sectionLabel, BorderLayout.NORTH);
        wrapper.add(cards, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildTimeObserverCard() {
        // Карточка наблюдателя времени
        JPanel card = createCard(new Color(0, 123, 255, 20), new Color(0, 123, 255));

        checkBoxTime = new JCheckBox(timeObserver.getName(), true);
        styleCheckBox(checkBoxTime, new Color(0, 86, 179));
        checkBoxTime.addActionListener(e -> subject.setObserverActive(timeObserver, checkBoxTime.isSelected()));

        timeValueLabel = new JLabel("—");
        timeValueLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        timeValueLabel.setForeground(new Color(0, 86, 179));

        timeLastStateLabel = new JLabel(timeObserver.getLastState());
        timeLastStateLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        timeLastStateLabel.setForeground(new Color(120, 120, 130));

        card.add(checkBoxTime,       BorderLayout.NORTH);
        card.add(timeValueLabel,     BorderLayout.CENTER);
        card.add(timeLastStateLabel, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildStateObserverCard() {
        // Карточка наблюдателя состояния
        JPanel card = createCard(new Color(40, 167, 69, 20), new Color(40, 167, 69));

        checkBoxState = new JCheckBox(stateObserver.getName(), true);
        styleCheckBox(checkBoxState, new Color(25, 120, 50));
        checkBoxState.addActionListener(e -> subject.setObserverActive(stateObserver, checkBoxState.isSelected()));

        stateValueLabel = new JLabel("—");
        stateValueLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        stateValueLabel.setForeground(CLR_INACTIVE);

        stateLastStateLabel = new JLabel(stateObserver.getLastState());
        stateLastStateLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        stateLastStateLabel.setForeground(new Color(120, 120, 130));

        card.add(checkBoxState,       BorderLayout.NORTH);
        card.add(stateValueLabel,     BorderLayout.CENTER);
        card.add(stateLastStateLabel, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createCard(Color bg, Color borderColor) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1, true),
                new EmptyBorder(10, 14, 10, 14)
        ));
        return card;
    }

    // ── Лог событий ──────────────────────────────────────────────────────────

    private JPanel buildLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(CLR_PANEL_BG);
        panel.setBorder(new EmptyBorder(0, 16, 14, 16));

        JLabel sectionLabel = new JLabel("Журнал событий");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(100, 100, 110));

        logArea = new JTextArea(6, 0);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(CLR_LOG_BG);
        logArea.setForeground(CLR_LOG_FG);
        logArea.setCaretColor(CLR_LOG_FG);
        logArea.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 70)));

        panel.add(sectionLabel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ── Вспомогательные методы ────────────────────────────────────────────────

    private JButton createButton(String action, String text, Color color) {
        JButton button = new JButton(text);
        button.setActionCommand(action);
        button.addActionListener(this::onTransition);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(9, 12, 9, 12));
        return button;
    }

    private void styleCheckBox(JCheckBox cb, Color color) {
        cb.setFont(new Font("SansSerif", Font.BOLD, 12));
        cb.setForeground(color);
        cb.setOpaque(false);
    }

    // ── Логика переходов и обновления UI ─────────────────────────────────────

    private void onTransition(ActionEvent e) {
        String event = e.getActionCommand();
        if (subject.transition(event)) {
            updateUI();
            log("Событие: " + event + " → " + subject.getState().getDescription(), false);
        } else {
            log("Ошибка: переход «" + event + "» невозможен из состояния «" +
                    subject.getState().getDescription() + "»", true);
        }
    }

    private void updateUI() {
        SubjectState.State state = subject.getState().getState();
        Color stateColor = stateColor(state);

        // Бейдж состояния в заголовке
        stateBadge.setText(subject.getState().getDescription().toUpperCase());
        stateBadge.setBackground(stateColor);

        // Карточка наблюдателя времени
        String activationTime = timeObserver.getActivationTimeString();
        timeValueLabel.setText(activationTime);
        timeValueLabel.setForeground(activationTime.equals("—") ? CLR_INACTIVE : new Color(0, 86, 179));
        timeLastStateLabel.setText(timeObserver.getLastState());

        // Карточка наблюдателя состояния
        stateValueLabel.setText(stateObserver.getState());
        stateValueLabel.setForeground(stateColor);
        stateLastStateLabel.setText(stateObserver.getLastState());
    }

    private Color stateColor(SubjectState.State state) {
        return switch (state) {
            case ACTIVE -> CLR_ACTIVE;
            case PAUSED -> CLR_PAUSED;
            case PROCESSING -> CLR_PROCESSING;
            case COMPLETED -> CLR_COMPLETED;
            default -> CLR_INACTIVE;
        };
    }

    private void log(String message, boolean isError) {
        String time = LOG_TIME_FORMAT.format(LocalTime.now());
        String prefix = isError ? "✖" : "✔";
        logArea.append("[" + time + "] " + prefix + " " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Nimbus L&F для современного вида
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            ObserverDemo demo = new ObserverDemo();
            demo.setVisible(true);
        });
    }
}
