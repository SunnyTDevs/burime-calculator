package com.burime.calculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
  наблюдатель для отображения времени активации субъекта
 */
@RequiredArgsConstructor
public class TimeObserver implements MyObserver {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Getter(onMethod_ = {@Override})
    private final String name;
    @Getter
    private String lastReceivedState = "—";
    @Getter
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
