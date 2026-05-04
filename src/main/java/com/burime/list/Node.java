package com.burime.list;

public class Node implements ListComponent {

    private final String data;
    private ListComponent next;

    public Node(String data) {
        this.data = data;
        this.next = new Terminator();
    }

    public String getData() {
        return data;
    }

    public ListComponent getNext() {
        return next;
    }

    @Override
    public void display() {
        System.out.println("[Node: " + data + "]");
        next.display();
    }

    @Override
    public void add(String data) {
        if (next.isEmpty()) {
            next = new Node(data);
        } else {
            next.add(data);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        String nextStr = next.toString();
        return nextStr.isEmpty() ? data : data + " -> " + nextStr;
    }
}