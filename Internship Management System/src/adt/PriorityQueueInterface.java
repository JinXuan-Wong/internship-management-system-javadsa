package adt;

/**
 * @author Dorcas Lim Yuan Yao Individual Module: Interview Module
 */
public interface PriorityQueueInterface<T extends Comparable<T>> {

    void enqueue(T newEntry);

    T dequeue();

    T peek();

    boolean isEmpty();

    int getSize();

    void clear();
}
