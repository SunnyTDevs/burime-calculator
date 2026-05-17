package com.burime.calculator;

/**
 * Интерфейс MyObserver - паттерн Наблюдатель
 * Все конкретные наблюдатели должны реализовывать этот интерфейс
 */
public interface MyObserver {
    /**
     * Вызывается при изменении состояния субъекта
     */
    void update(SubjectState subjectState, long activationTime);

    /**
     * @return имя наблюдателя
     */
    String getName();

    /**
     * @return последнее полученное состояние
     */
    String getLastState();
}