package com.burime.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateObserverTest {

    @Test
    void constructor_shouldSetName() {
        StateObserver observer = new StateObserver("Тестовый наблюдатель");
        assertEquals("Тестовый наблюдатель", observer.getName());
    }

    @Test
    void constructor_shouldInitializeWithDashState() {
        StateObserver observer = new StateObserver("Test");
        assertEquals("—", observer.getState());
    }

    @Test
    void constructor_shouldInitializeWithZeroUpdateCount() {
        StateObserver observer = new StateObserver("Test");
        assertEquals(0, observer.getUpdateCount());
    }

    @Test
    void update_shouldSetState() {
        StateObserver observer = new StateObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, 0);

        assertEquals("Активен", observer.getState());
    }

    @Test
    void update_shouldIncrementUpdateCount() {
        StateObserver observer = new StateObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, 0);
        assertEquals(1, observer.getUpdateCount());

        observer.update(new SubjectState(SubjectState.State.PAUSED), 0);
        assertEquals(2, observer.getUpdateCount());

        observer.update(new SubjectState(SubjectState.State.PROCESSING), 0);
        assertEquals(3, observer.getUpdateCount());
    }

    @Test
    void update_ignoresActivationTime() {
        StateObserver observer = new StateObserver("Test");
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);

        observer.update(state, 1234567890);

        // StateObserver не использует activationTime
        assertEquals("Активен", observer.getState());
    }

    @Test
    void getLastState_shouldContainStateAndCount() {
        StateObserver observer = new StateObserver("Test");
        observer.update(new SubjectState(SubjectState.State.ACTIVE), 0);

        String lastState = observer.getLastState();
        assertTrue(lastState.contains("Состояние:"));
        assertTrue(lastState.contains("обновлений:"));
    }

    @Test
    void getLastState_afterOneUpdate_shouldShowCountOne() {
        StateObserver observer = new StateObserver("Test");
        observer.update(new SubjectState(SubjectState.State.ACTIVE), 0);

        assertTrue(observer.getLastState().contains("обновлений: 1"));
    }

    @Test
    void update_withAllStates_shouldWork() {
        StateObserver observer = new StateObserver("Test");

        observer.update(new SubjectState(SubjectState.State.INACTIVE), 0);
        assertEquals("Неактивен", observer.getState());

        observer.update(new SubjectState(SubjectState.State.ACTIVE), 0);
        assertEquals("Активен", observer.getState());

        observer.update(new SubjectState(SubjectState.State.PAUSED), 0);
        assertEquals("Приостановлен", observer.getState());

        observer.update(new SubjectState(SubjectState.State.PROCESSING), 0);
        assertEquals("Обработка", observer.getState());

        observer.update(new SubjectState(SubjectState.State.COMPLETED), 0);
        assertEquals("Завершён", observer.getState());

        assertEquals(5, observer.getUpdateCount());
    }

    @Test
    void update_stateObserverVsTimeObserver_shouldBeIndependent() {
        StateObserver stateObs = new StateObserver("State");
        TimeObserver timeObs = new TimeObserver("Time");

        stateObs.update(new SubjectState(SubjectState.State.ACTIVE), 1000);
        timeObs.update(new SubjectState(SubjectState.State.ACTIVE), 1000);

        assertEquals("Активен", stateObs.getState());
        assertEquals("Активен", timeObs.getLastReceivedState());

        // timeObs хранит время, stateObs не хранит
        assertNotEquals("—", timeObs.getActivationTimeString());
    }
}