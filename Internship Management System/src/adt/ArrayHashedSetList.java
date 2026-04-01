package adt;

/**
 * Team's Collection ADT Hash Map + Set + List
 */
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayHashedSetList<T> implements HashedSetListInterface<T> {

    private static final int DEFAULT_CAPACITY = 100;
    private T[] setList;
    private int[] hashTable;
    private boolean[] occupied;
    private int count;

    // Constructor
    @SuppressWarnings("unchecked")
    public ArrayHashedSetList() {
        setList = (T[]) new Object[DEFAULT_CAPACITY];
        hashTable = new int[DEFAULT_CAPACITY];
        Arrays.fill(hashTable, -1);
        occupied = new boolean[DEFAULT_CAPACITY];
        count = 0;
    }

    @Override
    public boolean add(T newEntry) {
        if (newEntry == null || contains(newEntry)) {
            return false;
        }
        if (count >= setList.length) {
            expandCapacity();
        }
        setList[count] = newEntry;
        hashTable[count] = newEntry.hashCode();
        occupied[count] = true;
        count++;
        return true;
    }

    @Override
    public boolean replace(Integer entryID, T updatedEntry) {
        if (entryID < 0 || entryID >= count || updatedEntry == null) {
            return false;
        }
        T currentEntry = setList[entryID];
        setList[entryID] = null;
        boolean duplicateExists = contains(updatedEntry);
        setList[entryID] = currentEntry;

        if (duplicateExists) {
            return false;
        }
        setList[entryID] = updatedEntry;
        hashTable[entryID] = updatedEntry.hashCode();
        return true;
    }

    @Override
    public boolean remove(Integer entryID) {
        if (entryID < 0 || entryID >= count) {
            return false;
        }

        System.arraycopy(setList, entryID + 1, setList, entryID, count - entryID - 1);
        System.arraycopy(hashTable, entryID + 1, hashTable, entryID, count - entryID - 1);
        setList[count - 1] = null;
        hashTable[count - 1] = -1;
        count--;
        return true;
    }

    @Override
    public T getEntry(Integer entryID) {
        if (entryID < 0 || entryID >= count) {
            return null;
        }
        return setList[entryID];
    }

    @Override
    public boolean contains(T anEntry) {
        if (anEntry == null) {
            return false;
        }
        for (int i = 0; i < count; i++) {
            if (setList[i] != null && setList[i].equals(anEntry)) {
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
    public T[] filter(String criteria, Object value) {
        @SuppressWarnings("unchecked")
        T[] results = (T[]) Array.newInstance(setList.getClass().
                getComponentType(), count);
        int resultCount = 0;

        for (int i = 0; i < count; i++) {
            if (setList[i].toString().contains(value.toString())) {
                results[resultCount++] = setList[i];
            }
        }
        return Arrays.copyOf(results, resultCount);
    }

    @Override
    public void sortBy(String criteria, boolean ascending) {
        for (int i = 0; i < count - 1; i++) {
            int minMaxIndex = i;
            for (int j = i + 1; j < count; j++) {
                int comparison = compareByCriteria(setList[j],
                        setList[minMaxIndex], criteria);
                if ((ascending && comparison < 0)
                        || (!ascending && comparison > 0)) {
                    minMaxIndex = j;
                }
            }
            T temp = setList[i];
            setList[i] = setList[minMaxIndex];
            setList[minMaxIndex] = temp;
        }
    }

    @Override
    public T keywordSearch(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            if (setList[i].toString().toLowerCase().
                    contains(keyword.toLowerCase())) {
                return setList[i];
            }
        }
        return null;
    }

    

    @Override
    public void clear() {
        for (int i = 0; i < count; i++) {
            setList[i] = null;
            hashTable[i] = -1;
        }
        count = 0;
    }

    // -------------- Iterator Implementation -------------
    @Override
    public Iterator<T> getIterator() {
        return new ArrayHashedSetListIterator();
    }

    private class ArrayHashedSetListIterator implements Iterator<T> {

        private int currentIndex;

        public ArrayHashedSetListIterator() {
            currentIndex = -1;
        }

        @Override
        public boolean hasNext() {
            int nextIndex = currentIndex + 1;
            while (nextIndex < setList.length) {
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
            while (currentIndex < setList.length && !occupied[currentIndex]) {
                currentIndex++;
            }
            if (currentIndex < setList.length) {
                return setList[currentIndex];
            }
            return null;
        }
    }

    @Override
    public int indexOf(T anEntry) {
        if (anEntry == null) {
            return -1;
        }
        int hashValue = anEntry.hashCode();
        for (int i = 0; i < count; i++) {
            if (hashTable[i] == hashValue && setList[i].equals(anEntry)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    //-------------------Helper Methods--------------------------------------------------------
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        int newSize = setList.length * 2;
        setList = Arrays.copyOf(setList, newSize);
        hashTable = Arrays.copyOf(hashTable, newSize);
        occupied = Arrays.copyOf(occupied, newSize);
        Arrays.fill(hashTable, count, newSize, -1);
    }

    public int compareByCriteria(T object1, T object2, String criteria) {
        return object1.toString().compareTo(object2.toString());
    }

}
