package adt;

/**
 * The HashedSetListInterface defines a contract for a custom data structure
 * that combines the functionalities of a hash set and a list to store and
 * manage entries. It supports operations like adding, updating, removing,
 * filtering, sorting, searching, and generating reports.
 *
 * Dependencies: - utility.Report: Used for generating reports based on
 * specified criteria.
 *
 * @param <T> The type of objects stored in the data structure.
 */
import java.util.Iterator;

public interface HashedSetListInterface<T> {

    public boolean add(T newEntry);

    public boolean replace(Integer entryID, T updatedEntry);

    public boolean remove(Integer entryID);

    public T getEntry(Integer entryID);

    public boolean contains(T anEntry);

    public int getNumberOfEntries();

    public T[] filter(String criteria, Object value);

    public void sortBy(String criteria, boolean ascending);

    public T keywordSearch(String keyword);

    public void clear();

    public int indexOf(T anEntry);

    public boolean isEmpty();

    public Iterator<T> getIterator();
}
