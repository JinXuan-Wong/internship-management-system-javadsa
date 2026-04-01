package adt;

/**
 * @author Wong Jin Xuan Individual Module: Applicant Module Array
 * Implementations of HashedSetList
 *
 * HashedArrayList is an array-based implementation that uses open addressing to
 * store entries by their hash values, whereas HashedSetList stores entries
 * sequentially with hash values tracked separately.
 */
import java.util.Arrays;
import java.util.Iterator;

public class HashedArrayList<T> implements HashedSetListInterface<T> {

    private static final int INITIAL_CAPACITY = 50;
    private T[] elements;
    private boolean[] occupied;
    private int count;

    @SuppressWarnings("unchecked")
    public HashedArrayList() {
        elements = (T[]) new Object[INITIAL_CAPACITY];
        occupied = new boolean[INITIAL_CAPACITY];
        count = 0;
    }

    @Override
    public boolean add(T newEntry) {
        if (newEntry == null || contains(newEntry)) {
            return false;
        }
        if (count >= elements.length) {
            resizeArray();
        }
        int index = findAvailableIndex(newEntry.hashCode());
        elements[index] = newEntry;
        occupied[index] = true;
        count++;
        return true;
    }

    @Override
    public boolean replace(Integer entryID, T updatedEntry) {
        if (entryID < 0 || entryID >= elements.length || !occupied[entryID] || updatedEntry == null) {
            return false;
        }
        // Allow updating the same entry without duplicate rejection
        if (!elements[entryID].equals(updatedEntry) && contains(updatedEntry)) {
            return false;   // Prevent duplicate entries
        }
        elements[entryID] = updatedEntry;
        return true;
    }

    @Override
    public boolean remove(Integer entryID) {
        if (entryID < 0 || entryID >= elements.length || !occupied[entryID]) {
            return false;
        }
        elements[entryID] = null;
        occupied[entryID] = false;
        count--;
        return true;
    }

    @Override
    public T getEntry(Integer entryID) {
        int foundCount = 0;
        for (int i = 0; i < elements.length; i++) {
            if (occupied[i]) {
                if (foundCount == entryID) {
                    return elements[i];
                }
                foundCount++;
            }
        }
        return null;
    }

    @Override
    public boolean contains(T anEntry) {
        if (anEntry == null) {
            return false;
        }
        for (int i = 0; i < elements.length; i++) {
            if (occupied[i] && elements[i].equals(anEntry)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return count;
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        Arrays.fill(occupied, false);
        count = 0;
    }

    @Override
    public int indexOf(T anEntry) {
        if (anEntry == null) {
            return -1;
        }
        for (int i = 0; i < elements.length; i++) {
            if (occupied[i] && elements[i].equals(anEntry)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public T[] filter(String criteria, Object value) {
        @SuppressWarnings("unchecked")
        T[] results = (T[]) new Object[count];
        int resultCount = 0;

        String valueStr = value.toString().toLowerCase();

        for (int i = 0; i < elements.length; i++) {
            if (occupied[i] && elements[i].toString().toLowerCase().contains(valueStr)) {
                results[resultCount++] = elements[i];
            }
        }
        return Arrays.copyOf(results, resultCount);
    }

    @Override
    public void sortBy(String criteria, boolean ascending) {
        for (int i = 0; i < elements.length - 1; i++) {
            if (!occupied[i]) {
                continue;
            }

            int minMaxIndex = i;
            for (int j = i + 1; j < elements.length; j++) {
                if (!occupied[j]) {
                    continue;
                }

                int comparison = compareByCriteria(elements[j], elements[minMaxIndex], criteria);
                if ((ascending && comparison < 0) || (!ascending && comparison > 0)) {
                    minMaxIndex = j;
                }
            }

            // Swap
            T temp = elements[i];
            elements[i] = elements[minMaxIndex];
            elements[minMaxIndex] = temp;
        }
    }

    @Override
    public T keywordSearch(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        for (int i = 0; i < elements.length; i++) {
            if (occupied[i] && elements[i].toString().toLowerCase().contains(keyword.toLowerCase())) {
                return elements[i]; // Return first match
            }
        }

        // If no match is found, return null
        return null;
    }


    public boolean setEntry(int index, T element) {
        int foundCount = 0;
        for (int i = 0; i < elements.length; i++) {
            if (occupied[i]) {
                if (foundCount == index) {
                    elements[i] = element; // Directly update the element
                    return true;
                }
                foundCount++;
            }
        }
        return false; // Index out of range
    }

    // -------------- Iterator Implementation -------------
    @Override
    public Iterator<T> getIterator() {
        return new HashedArrayListIterator();
    }

    private class HashedArrayListIterator implements Iterator<T> {

        private int currentIndex;

        public HashedArrayListIterator() {
            currentIndex = -1;
        }

        @Override
        public boolean hasNext() {
            int nextIndex = currentIndex + 1;
            while (nextIndex < elements.length) {
                if (occupied[nextIndex]) {
                    return true;
                }
                nextIndex++;
            }
            return false;
        }

        @Override
        public T next() {
            currentIndex++;
            while (currentIndex < elements.length && !occupied[currentIndex]) {
                currentIndex++;
            }
            if (currentIndex < elements.length) {
                return elements[currentIndex];
            }
            return null; // End of iteration
        }
    }

    // ------------------ Helper Methods ------------------
    private void resizeArray() {
        int newSize = elements.length * 2;
        T[] oldElements = elements;
        boolean[] oldOccupied = occupied;

        elements = (T[]) new Object[newSize];
        occupied = new boolean[newSize];
        count = 0;

        for (int i = 0; i < oldElements.length; i++) {
            if (oldOccupied[i] && oldElements[i] != null) {
                add(oldElements[i]); // Reinserting to maintain hash integrity
            }
        }
    }

    private int findAvailableIndex(int hashCode) {
        int index = Math.abs(hashCode) % elements.length;
        while (occupied[index]) {
            index = (index + 1) % elements.length; // Linear probing
        }
        return index;
    }

    public int compareByCriteria(T a, T b, String criteria) {
        // Default to string comparison
        return a.toString().compareTo(b.toString());
    }
}
