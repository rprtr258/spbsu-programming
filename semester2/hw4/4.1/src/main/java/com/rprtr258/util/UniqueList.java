package com.rprtr258.util;

/**
 * Linked list that holds only unique values
 *
 * @param <T> Type of values stored in UniqueList
 */
public class UniqueList<T> extends SinglyLinkedList<T> {
    /**
     * Append value in the end of list.
     *
     * @param value Value to add in list.
     * @throws UniquenessException When value is already in list.
     */
    @Override
    public void append(T value) {
        if (contains(value))
            throw new UniquenessException();
        super.append(value);
    }
}
