package com.burime.list;

public class Terminator implements ListComponent {

    public Terminator() {
    }

    @Override
    public void display() {
        System.out.println("[END]");
    }

    @Override
    public void add(String data) {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}