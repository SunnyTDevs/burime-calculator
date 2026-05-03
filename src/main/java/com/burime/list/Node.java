package com.burime.list;

/**
 * Узел списка (Composite в паттерне Composite).
 * Хранит данные и ссылку на следующий элемент списка.
 */
public class Node implements ListComponent {

    private final String data;
    private ListComponent next;

    public Node(String data) {
        this.data = data;
        this.next = new Terminator();
    }

    public Node(String data, ListComponent next) {
        this.data = data;
        this.next = next;
    }

    public String getData() {
        return data;
    }

    public ListComponent getNext() {
        return next;
    }

    public void setNext(ListComponent next) {
        this.next = next;
    }

    @Override
    public void display() {
        System.out.println("[Node: " + data + "]");
        next.display();
    }

    @Override
    public String toStringRepresentation() {
        return data + (next instanceof Terminator ? "" : " -> " + next.toStringRepresentation());
    }
}