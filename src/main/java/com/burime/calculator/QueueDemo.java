package com.burime.calculator;

/**
 * Demonstration program showing access to ArrayQueue through IQueue interface.
 */
public class QueueDemo {

    /**
     * Prints all elements of a queue by dequeuing them.
     * Demonstrates polymorphic access through the IQueue interface.
     */
    static void printQueue(IQueue<?> queue) {
        System.out.print("Queue contents: ");
        while (!queue.isEmpty()) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();
    }

    /**
     * Demonstrates basic queue operations through the IQueue interface.
     */
    static void demonstrateIntegerQueue() {
        System.out.println("=== Integer Queue Demo ===");

        // Access through interface type
        IQueue<Integer> queue = new ArrayQueue<>(5);

        System.out.println("Empty queue: isEmpty=" + queue.isEmpty() + ", size=" + queue.size());

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("After enqueue 10, 20, 30: " + queue);
        System.out.println("Size: " + queue.size());
        System.out.println("Peek (front element): " + queue.peek());

        int removed = queue.dequeue();
        System.out.println("Dequeued: " + removed);
        System.out.println("After dequeue: " + queue);

        queue.enqueue(40);
        queue.enqueue(50);
        System.out.println("After enqueue 40, 50: " + queue);
        System.out.println("isFull: " + queue.isFull());

        // Demonstrate FIFO order
        System.out.print("Dequeue all (FIFO order): ");
        while (!queue.isEmpty()) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();
        System.out.println("isEmpty after dequeue all: " + queue.isEmpty());
    }

    /**
     * Demonstrates that the same interface works with different element types.
     */
    static void demonstrateStringQueue() {
        System.out.println("\n=== String Queue Demo ===");

        IQueue<String> queue = new ArrayQueue<>(4);
        queue.enqueue("first");
        queue.enqueue("second");
        queue.enqueue("third");

        System.out.println("String queue: " + queue);
        printQueue(queue);
    }

    /**
     * Demonstrates circular buffer behavior of the array-based queue.
     */
    static void demonstrateCircularBehavior() {
        System.out.println("\n=== Circular Buffer Demo ===");

        IQueue<Integer> queue = new ArrayQueue<>(3);

        // Fill and partially empty several times to show wrap-around
        for (int round = 1; round <= 3; round++) {
            System.out.println("Round " + round + ":");
            queue.enqueue(round * 10);
            queue.enqueue(round * 10 + 1);
            System.out.println("  Enqueued: " + (round * 10) + ", " + (round * 10 + 1));
            System.out.println("  Queue: " + queue);
            System.out.println("  Dequeued: " + queue.dequeue());
            System.out.println("  Dequeued: " + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        demonstrateIntegerQueue();
        demonstrateStringQueue();
        demonstrateCircularBehavior();
    }
}
