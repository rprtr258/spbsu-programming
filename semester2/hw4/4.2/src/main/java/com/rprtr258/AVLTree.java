package com.rprtr258;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * AVLTree data structure.
 * <a href = "https://en.wikipedia.org/wiki/AVL_tree">Wikipedia</a>
 * @param <T> Type of values stored.
 */
class AVLTree<T extends Comparable<T>> implements Collection<T> {
    /**
     * Root of tree.
     */
    private NodeWrapper<T> root = new NodeWrapper<>(null);
    /**
     * Number of elements in tree.
     */
    private int size = 0;

    /**
     * Adds value to tree.
     * @param value value to add.
     * @return true.
     */
    @Override
    public boolean add(T value) {
        root.add(value);
        size++;
        return true;
    }

    /**
     * Adds all elements from given collection.
     * @param collection collection with values.
     * @return true.
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (T element : collection)
            add(element);
        return true;
    }

    /**
     * Checks if tree contains given value.
     * @param value value to check.
     * @return true if value is in tree, false otherwise.
     */
    @Override
    public boolean contains(Object value) {
        return root.contains((T)value);
    }

    /**
     * Checks if all elements of collection are in tree.
     * @param collection collection with values to check.
     * @return true if all elements of collection are in tree, false otherwise.
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean result = true;
        for (Object element : collection)
            result &= contains(element);
        return result;
    }

    /**
     * Getter for iterator of tree.
     * @return Iterator pointing to least element.
     */
    @Override
    public Iterator<T> iterator() {
        NodeWrapper<T> tmp = root;
        while (tmp.getL().isNotNull())
            tmp = tmp.getL();
        return new TreeIterator<>(tmp);
    }

    /**
     * Makes object array of values stored in tree.
     * Values in array stored in non-decreasing order.
     * @return tree objects array.
     */
    @Override
    public Object[] toArray() {
        ArrayList<T> result = new ArrayList<>();
        root.pushAll(result);
        return result.toArray();
    }

    /**
     * Writes values of tree in given array.
     * @param a array to write in.
     * @param <T1> array values type.
     * @return all tree elements array.
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        ArrayList<T1> result = new ArrayList<>();
        ArrayList<T> data = new ArrayList<>();
        root.pushAll(data);
        for (T value : data)
            result.add((T1)value);
        return result.toArray(a);
    }

    /**
     * Removes value from tree.
     * @param value value to remove.
     * @return true if element was removed.
     */
    @Override
    public boolean remove(Object value) {
        if (contains(value)) {
            root.remove((T) value);
            size--;
            return true;
        } else
            return false;
    }

    /**
     * Removes all element of collection from tree.
     * @return true if at least one element was removed.
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean result = false;
        for (Object element : collection)
            result |= remove(element);
        return result;
    }

    /**
     * Removes all values from tree except present in given collection.
     * @param collection collection of values that will not be removed.
     * @return true if at least one element was removed.
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        Boolean result = false;
        ArrayList<T> excluded = new ArrayList<>();
        for(T value : this)
            if (!collection.contains(value))
                excluded.add(value);
        for (Object value : excluded)
            result |= remove(value);
        return result;
    }

    /**
     * Getter for tree size.
     * @return size of tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if there is no elements in tree.
     * @return true if tree contains no values, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    /**
     * Clears tree.
     */
    @Override
    public void clear() {
        root.erase();
        size = 0;
    }

    /**
     * String representation of tree in infix form.
     * @return string representation.
     */
    @Override
    public String toString() {
        return root.toString();
    }
}
