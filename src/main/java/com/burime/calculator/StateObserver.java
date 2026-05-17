package com.burime.calculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ConcreteObserver2 - наблюдатель для отображения текущего состояния субъекта
 */
@RequiredArgsConstructor
@Getter
public class StateObserver implements MyObserver {

    private final String name;
    private String state = "—";
    private int updateCount = 0;

    @Override
    public void update(SubjectState subjectState, long activationTime) {
        state = subjectState.getDescription();
        updateCount++;
    }

    @Override
    public String getLastState() {
        return "Состояние: " + state + " (обновлений: " + updateCount + ")";
    }

    /**
     * Сбросить счётчик обновлений
     */
    public void resetCount() {
        updateCount = 0;
    }
}
