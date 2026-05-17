package com.burime.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject();
    }

    // ── Конструктор ───────────────────────────────────────────────────────────

    @Test
    void constructor_shouldInitializeWithInactiveState() {
        assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
    }

    @Test
    void constructor_shouldHaveEmptyObservers() {
        assertTrue(subject.getObservers().isEmpty());
    }

    @Test
    void constructor_shouldHaveZeroActivationTime() {
        assertEquals(0, subject.getActivationTime());
    }

    // ── attach() ─────────────────────────────────────────────────────────────

    @Test
    void attach_shouldAddObserver() {
        MyObserver observer = new TestObserver("Test");
        subject.attach(observer);
        assertEquals(1, subject.getObservers().size());
        assertTrue(subject.getObservers().containsKey(observer));
    }

    @Test
    void attach_observerShouldBeActiveByDefault() {
        MyObserver observer = new TestObserver("Test");
        subject.attach(observer);
        assertTrue(subject.getObservers().get(observer));
    }

    @Test
    void attach_sameObserverTwice_shouldNotDuplicate() {
        MyObserver observer = new TestObserver("Test");
        subject.attach(observer);
        subject.attach(observer);
        assertEquals(1, subject.getObservers().size());
    }

    @Test
    void attach_multipleObservers_shouldAddAll() {
        subject.attach(new TestObserver("Obs1"));
        subject.attach(new TestObserver("Obs2"));
        subject.attach(new TestObserver("Obs3"));
        assertEquals(3, subject.getObservers().size());
    }

    // ── setObserverActive() ───────────────────────────────────────────────────

    @Test
    void setObserverActive_shouldDeactivateObserver() {
        MyObserver observer = new TestObserver("Test");
        subject.attach(observer);
        subject.setObserverActive(observer, false);
        assertFalse(subject.getObservers().get(observer));
    }

    @Test
    void setObserverActive_shouldReactivateObserver() {
        MyObserver observer = new TestObserver("Test");
        subject.attach(observer);
        subject.setObserverActive(observer, false);
        subject.setObserverActive(observer, true);
        assertTrue(subject.getObservers().get(observer));
    }

    // ── toString() ─────────────────────────────────────────────────────────────

    @Test
    void toString_shouldContainStateInfo() {
        String str = subject.toString();
        assertTrue(str.contains("Subject"));
        assertTrue(str.contains("INACTIVE"));
    }

    // ── Переходы состояний (конечный автомат) ─────────────────────────────────

    @Nested
    class StateTransitions {

        @Test
        void activate_shouldTransitionFromInactiveToActive() {
            assertTrue(subject.transition("ACTIVATE"));
            assertEquals(SubjectState.State.ACTIVE, subject.getState().getState());
        }

        @Test
        void activate_shouldSetActivationTime() {
            subject.transition("ACTIVATE");
            assertTrue(subject.getActivationTime() > 0);
        }

        @Test
        void fromActive_shouldAllowPause() {
            subject.transition("ACTIVATE");
            assertTrue(subject.transition("PAUSE"));
            assertEquals(SubjectState.State.PAUSED, subject.getState().getState());
        }

        @Test
        void fromActive_shouldAllowProcess() {
            subject.transition("ACTIVATE");
            assertTrue(subject.transition("PROCESS"));
            assertEquals(SubjectState.State.PROCESSING, subject.getState().getState());
        }

        @Test
        void fromActive_shouldAllowDeactivate() {
            subject.transition("ACTIVATE");
            assertTrue(subject.transition("DEACTIVATE"));
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void fromPaused_shouldAllowResume() {
            subject.transition("ACTIVATE");
            subject.transition("PAUSE");
            assertTrue(subject.transition("RESUME"));
            assertEquals(SubjectState.State.ACTIVE, subject.getState().getState());
        }

        @Test
        void fromPaused_shouldAllowDeactivate() {
            subject.transition("ACTIVATE");
            subject.transition("PAUSE");
            assertTrue(subject.transition("DEACTIVATE"));
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void fromProcessing_shouldAllowComplete() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            assertTrue(subject.transition("COMPLETE"));
            assertEquals(SubjectState.State.COMPLETED, subject.getState().getState());
        }

        @Test
        void fromProcessing_shouldAllowDeactivate() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            assertTrue(subject.transition("DEACTIVATE"));
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void fromCompleted_shouldAllowReset() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            subject.transition("COMPLETE");
            assertTrue(subject.transition("RESET"));
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void reset_shouldClearActivationTime() {
            subject.transition("ACTIVATE");
            assertTrue(subject.getActivationTime() > 0);
            subject.transition("PROCESS");
            subject.transition("COMPLETE");
            subject.transition("RESET");
            assertEquals(0, subject.getActivationTime());
        }

        @Test
        void invalidTransition_shouldReturnFalse() {
            assertFalse(subject.transition("PAUSE")); // нельзя из INACTIVE
        }

        @Test
        void invalidTransition_shouldNotChangeState() {
            subject.transition("PAUSE"); // недопустимый переход
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void fromInactive_cannotPause() {
            assertFalse(subject.transition("PAUSE"));
        }

        @Test
        void fromInactive_cannotProcess() {
            assertFalse(subject.transition("PROCESS"));
        }

        @Test
        void fromInactive_cannotResume() {
            assertFalse(subject.transition("RESUME"));
        }

        @Test
        void fromInactive_cannotComplete() {
            assertFalse(subject.transition("COMPLETE"));
        }

        @Test
        void fromCompleted_cannotActivate() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            subject.transition("COMPLETE");
            assertFalse(subject.transition("ACTIVATE"));
        }

        @Test
        void fromCompleted_cannotPause() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            subject.transition("COMPLETE");
            assertFalse(subject.transition("PAUSE"));
        }

        @Test
        void fromCompleted_cannotProcess() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            subject.transition("COMPLETE");
            assertFalse(subject.transition("PROCESS"));
        }
    }

    // ── Полный цикл конечного автомата ───────────────────────────────────────

    @Nested
    class FullStateMachineCycle {

        @Test
        void completeCycle_shouldWork() {
            // INACTIVE -> ACTIVE -> PAUSED -> ACTIVE -> PROCESSING -> COMPLETED -> INACTIVE
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());

            subject.transition("ACTIVATE");
            assertEquals(SubjectState.State.ACTIVE, subject.getState().getState());

            subject.transition("PAUSE");
            assertEquals(SubjectState.State.PAUSED, subject.getState().getState());

            subject.transition("RESUME");
            assertEquals(SubjectState.State.ACTIVE, subject.getState().getState());

            subject.transition("PROCESS");
            assertEquals(SubjectState.State.PROCESSING, subject.getState().getState());

            subject.transition("COMPLETE");
            assertEquals(SubjectState.State.COMPLETED, subject.getState().getState());

            subject.transition("RESET");
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void directDeactivateFromActive_shouldResetState() {
            subject.transition("ACTIVATE");
            subject.transition("DEACTIVATE");
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void directDeactivateFromPaused_shouldResetState() {
            subject.transition("ACTIVATE");
            subject.transition("PAUSE");
            subject.transition("DEACTIVATE");
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }

        @Test
        void directDeactivateFromProcessing_shouldResetState() {
            subject.transition("ACTIVATE");
            subject.transition("PROCESS");
            subject.transition("DEACTIVATE");
            assertEquals(SubjectState.State.INACTIVE, subject.getState().getState());
        }
    }

    // ── Вспомогательный тестовый наблюдатель ─────────────────────────────────

    private static class TestObserver implements MyObserver {
        private final String name;
        private SubjectState lastState;
        private long lastActivationTime;

        TestObserver(String name) {
            this.name = name;
        }

        @Override
        public void update(SubjectState subjectState, long activationTime) {
            this.lastState = subjectState;
            this.lastActivationTime = activationTime;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getLastState() {
            return lastState != null ? lastState.getDescription() : "—";
        }
    }
}