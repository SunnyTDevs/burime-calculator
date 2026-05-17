package com.burime.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeObserverTest {

    @Test
    void constructor_shouldSetName() {
        TimeObserver observer = new TimeObserver("Тестовый наблюдатель");
        assertEquals("Тестовый наблюдатель", observer.getName());
    }

    @Test
    void constructor_shouldInitializeWithDashValues() {
        TimeObserver observer = new TimeObserver("Test");
        assertEquals("—", observer.getActivationTimeString());
    }

    @Test
    void update_withInactiveState_shouldShowDash() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.INACTIVE);

        observer.update(state, 0);

        assertEquals("—", observer.getActivationTimeString());
    }

    @Test
    void update_withZeroActivationTime_shouldShowDash() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, 0);

        assertEquals("—", observer.getActivationTimeString());
    }

    @Test
    void update_withPositiveActivationTime_shouldFormatTime() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);
        long activationTime = System.currentTimeMillis();

        observer.update(state, activationTime);

        assertNotEquals("—", observer.getActivationTimeString());
        assertTrue(observer.getActivationTimeString().matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"));
    }

    @Test
    void update_shouldUpdateLastReceivedState() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, 1000);

        assertEquals("Активен", observer.getLastReceivedState());
    }

    @Test
    void getLastState_shouldContainActivationTime() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, System.currentTimeMillis());

        String lastState = observer.getLastState();
        assertTrue(lastState.contains("Время активации:"));
        assertTrue(lastState.contains("Состояние:"));
    }

    @Test
    void getLastState_withInactive_shouldContainDash() {
        TimeObserver observer = new TimeObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.INACTIVE);

        observer.update(state, 0);

        String lastState = observer.getLastState();
        assertTrue(lastState.contains("—"));
    }

    @Test
    void update_multipleTimes_shouldUpdateState() {
        TimeObserver observer = new TimeObserver("Test");

        observer.update(new SubjectState(SubjectState.State.INACTIVE), 0);
        assertEquals("Неактивен", observer.getLastReceivedState());

        observer.update(new SubjectState(SubjectState.State.ACTIVE), 1000);
        assertEquals("Активен", observer.getLastReceivedState());

        observer.update(new SubjectState(SubjectState.State.PROCESSING), 2000);
        assertEquals("Обработка", observer.getLastReceivedState());

        observer.update(new SubjectState(SubjectState.State.COMPLETED), 3000);
        assertEquals("Завершён", observer.getLastReceivedState());
    }

    @Test
    void update_withAllStates_shouldWork() {
        TimeObserver observer = new TimeObserver("Test");
        long time = System.currentTimeMillis();

        for (SubjectState.State state : SubjectState.State.values()) {
            observer.update(new SubjectState(state), time);
            assertEquals(state.getDescription(), observer.getLastReceivedState());
        }
    }
}