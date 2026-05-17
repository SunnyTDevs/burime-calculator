package com.burime.calculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * ConcreteObserver1 - наблюдатель для отображения времени активации субъекта
 */
@RequiredArgsConstructor
@Getter
public class TimeObserver implements MyObserver {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final String name;
    private String lastReceivedState = "—";
    private String activationTimeString = "—";

    @Override
    public void update(SubjectState subjectState, long activationTime) {
        lastReceivedState = subjectState.getDescription();
        activationTimeString = activationTime > 0
                ? TIME_FORMAT.format(Instant.ofEpochMilli(activationTime).atZone(ZoneId.systemDefault()).toLocalTime())
                : "—";
    }

    @Override
    public String getLastState() {
        return "Время активации: " + activationTimeString + ", Состояние: " + lastReceivedState;
    }
}
