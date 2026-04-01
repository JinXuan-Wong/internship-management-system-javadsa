package adt;

/**
 * @author Dorcas Lim Yuan Yao Individual Module: Interview Module
 */
public class PriorityQueue<T extends Comparable<T>> implements PriorityQueueInterface<T> {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 50;

    public PriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(int initialCapacity) {
        array = (T[]) new Comparable[initialCapacity];
        numberOfEntries = 0;
    }

    @Override
    public void enqueue(T newEntry) {
        ensureCapacity();
        array[numberOfEntries] = newEntry;
        int newIndex = numberOfEntries;
        int parentIndex = (newIndex - 1) / 2;

        while (newIndex > 0 && array[newIndex].compareTo(array[parentIndex]) > 0) {
            T temp = array[newIndex];
            array[newIndex] = array[parentIndex];
            array[parentIndex] = temp;
            newIndex = parentIndex;
            parentIndex = (newIndex - 1) / 2;
        }
        numberOfEntries++;
    }

    @Override
    public T dequeue() {
        T result = null;
        if (!isEmpty()) {
            result = array[0];
            array[0] = array[numberOfEntries - 1];
            numberOfEntries--;
            reheap(0);
        }
        return result;
    }

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = array[rootIndex];
        int leftChildIndex = 2 * rootIndex + 1;

        while (!done && leftChildIndex < numberOfEntries) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;

            if (rightChildIndex < numberOfEntries
                    && array[rightChildIndex].compareTo(array[leftChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }

            if (orphan.compareTo(array[largerChildIndex]) < 0) {
                array[rootIndex] = array[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex + 1;
            } else {
                done = true;
            }
        }
        array[rootIndex] = orphan;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (numberOfEntries == array.length) {
            T[] oldArray = array;
            array = (T[]) new Comparable[2 * oldArray.length];
            System.arraycopy(oldArray, 0, array, 0, oldArray.length);
        }
    }

    @Override
    public T peek() {
        return isEmpty() ? null : array[0];
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public int getSize() {
        return numberOfEntries;
    }

    @Override
    public void clear() {
        numberOfEntries = 0;
    }
}
