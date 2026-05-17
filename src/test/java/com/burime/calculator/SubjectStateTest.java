package com.burime.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class SubjectStateTest {

    @Test
    void constructor_shouldSetState() {
        SubjectState state = new SubjectState(SubjectState.State.INACTIVE);
        assertEquals(SubjectState.State.INACTIVE, state.getState());
    }

    @Test
    void getDescription_shouldReturnStateDescription() {
        SubjectState state = new SubjectState(SubjectState.State.ACTIVE);
        assertEquals("Активен", state.getDescription());
    }

    @ParameterizedTest
    @EnumSource(SubjectState.State.class)
    void allStates_shouldHaveDescription(SubjectState.State state) {
        SubjectState subjectState = new SubjectState(state);
        assertNotNull(subjectState.getDescription());
        assertFalse(subjectState.getDescription().isEmpty());
    }

    @Test
    void equals_sameState_shouldBeEqual() {
        SubjectState state1 = new SubjectState(SubjectState.State.INACTIVE);
        SubjectState state2 = new SubjectState(SubjectState.State.INACTIVE);
        assertEquals(state1, state2);
    }

    @Test
    void equals_differentState_shouldNotBeEqual() {
        SubjectState state1 = new SubjectState(SubjectState.State.INACTIVE);
        SubjectState state2 = new SubjectState(SubjectState.State.ACTIVE);
        assertNotEquals(state1, state2);
    }

    @Test
    void hashCode_sameState_sameHash() {
        SubjectState state1 = new SubjectState(SubjectState.State.ACTIVE);
        SubjectState state2 = new SubjectState(SubjectState.State.ACTIVE);
        assertEquals(state1.hashCode(), state2.hashCode());
    }

    @Test
    void toString_shouldContainStateNameAndDescription() {
        SubjectState state = new SubjectState(SubjectState.State.PROCESSING);
        String str = state.toString();
        assertTrue(str.contains("PROCESSING"));
        assertTrue(str.contains("Обработка"));
    }

    @Test
    void stateEnums_shouldHaveCorrectDescriptions() {
        assertEquals("Неактивен", SubjectState.State.INACTIVE.getDescription());
        assertEquals("Активен", SubjectState.State.ACTIVE.getDescription());
        assertEquals("Приостановлен", SubjectState.State.PAUSED.getDescription());
        assertEquals("Обработка", SubjectState.State.PROCESSING.getDescription());
        assertEquals("Завершён", SubjectState.State.COMPLETED.getDescription());
    }
}