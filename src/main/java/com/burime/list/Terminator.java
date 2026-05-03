package com.burime.list;

/**
 * Конечная точка списка (Leaf в паттерне Composite).
 * Завершает цепочку связанного списка.
 */
public class Terminator implements ListComponent {

    @Override
    public void display() {
        System.out.println("[END]");
    }

    @Override
    public String toStringRepresentation() {
        return "";
    }
}