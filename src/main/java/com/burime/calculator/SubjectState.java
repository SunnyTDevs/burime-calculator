package com.burime.calculator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Класс, представляющий состояние субъекта
 * Используется конечным автоматом Subject
 */
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubjectState {

    @Getter
    @EqualsAndHashCode.Include
    private final State state;

    // Возможные состояния конечного автомата
    public enum State {
        INACTIVE("Неактивен"),
        ACTIVE("Активен"),
        PAUSED("Приостановлен"),
        PROCESSING("Обработка"),
        COMPLETED("Завершён");

        private final String description;
        private Map<String, State> transitions;

        State(String desc) {
            this.description = desc;
        }

        static {
            // Из INACTIVE можно перейти в ACTIVE
            INACTIVE.transitions = Map.of("ACTIVATE", ACTIVE);

            // Из ACTIVE можно перейти в PAUSED, PROCESSING или INACTIVE
            ACTIVE.transitions = Map.of(
                    "PAUSE", PAUSED,
                    "PROCESS", PROCESSING,
                    "DEACTIVATE", INACTIVE
            );

            // Из PAUSED можно перейти в ACTIVE или INACTIVE
            PAUSED.transitions = Map.of(
                    "RESUME", ACTIVE,
                    "DEACTIVATE", INACTIVE
            );

            // Из PROCESSING можно перейти в COMPLETED или INACTIVE
            PROCESSING.transitions = Map.of(
                    "COMPLETE", COMPLETED,
                    "DEACTIVATE", INACTIVE
            );

            // Из COMPLETED можно перейти только в INACTIVE
            COMPLETED.transitions = Map.of("RESET", INACTIVE);
        }

        public String getDescription() {
            return description;
        }
    }

    public String getDescription() {
        return state.getDescription();
    }

    @Override
    public String toString() {
        return state.name() + ": " + state.getDescription();
    }
}
