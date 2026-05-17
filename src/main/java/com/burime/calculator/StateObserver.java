package com.burime.calculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
  наблюдатель для отображения текущего состояния субъекта
 */
@RequiredArgsConstructor
public class StateObserver implements MyObserver {

    @Getter(onMethod_ = {@Override})
    private final String name;
    @Getter
    private String state = "—";
    @Getter
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

}
