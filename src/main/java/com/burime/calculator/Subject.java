package com.burime.calculator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс Subject - субъект паттерна Observer
 * Реализует конечный автомат для управления состояниями
 */
@Getter
public class Subject {
    private final LinkedHashMap<MyObserver, Boolean> observers = new LinkedHashMap<>();
    private SubjectState state;
    private long activationTime = 0;

    public Subject() {
        this.state = new SubjectState(SubjectState.State.INACTIVE);
    }

    /**
     * Прикрепить наблюдателя к субъекту
     */
    public void attach(MyObserver observer) {
        observers.putIfAbsent(observer, true); // по умолчанию активен
    }

    /**
     * Открепить наблюдателя от субъекта
     */
    public void detach(MyObserver observer) {
        observers.remove(observer);
    }

    /**
     * Установить активность наблюдателя (для CheckBox)
     */
    public void setObserverActive(MyObserver observer, boolean active) {
        observers.put(observer, active);
    }

    /**
     * Проверить, активен ли наблюдатель
     */
    public boolean isObserverActive(MyObserver observer) {
        return observers.getOrDefault(observer, false);
    }

    /**
     * Получить список всех наблюдателей
     */
    public List<MyObserver> getObservers() {
        return new ArrayList<>(observers.keySet());
    }

    /**
     * Переход к новому состоянию (событие конечного автомата)
     *
     * @param event событие, которое вызывает переход
     * @return true если переход выполнен успешно
     */
    public boolean transition(String event) {
        Map<String, SubjectState.State> possibleTransitions = state.getState().getTransitions();
        SubjectState.State newState = possibleTransitions.get(event);

        if (newState == null) {
            return false; // Переход невозможен
        }

        // При переходе в ACTIVE сохраняем время активации
        if (newState == SubjectState.State.ACTIVE) {
            activationTime = System.currentTimeMillis();
        }

        // При переходе из COMPLETED в INACTIVE сбрасываем время активации
        if (newState == SubjectState.State.INACTIVE) {
            activationTime = 0;
        }

        state = new SubjectState(newState);
        notifyObservers();
        return true;
    }

    /**
     * Проверить, возможен ли переход
     */
    public boolean canTransition(String event) {
        return state.getState().getTransitions().containsKey(event);
    }

    /**
     * Получить список возможных переходов из текущего состояния
     */
    public List<String> getPossibleTransitions() {
        return List.copyOf(state.getState().getTransitions().keySet());
    }

    /**
     * Уведомить всех активных наблюдателей об изменении состояния
     */
    private void notifyObservers() {
        for (Map.Entry<MyObserver, Boolean> entry : observers.entrySet()) {
            if (entry.getValue()) {
                entry.getKey().update(state, activationTime);
            }
        }
    }

    @Override
    public String toString() {
        return "Subject: " + state.toString();
    }
}
