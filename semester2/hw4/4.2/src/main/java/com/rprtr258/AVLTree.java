package com.rprtr258;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class AVLTree<T extends Comparable<T>> implements Collection<T> {
    private Node<T> root = null;
    private int size = 0;

    public boolean add(T value) {
        if (size == 0) {
            root = new Node<>(value);
            root.setParent(null);
        } else
            Node.add(root, value);
        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean result = true;
        for (Object element : collection)
            result &= add((T)element);
        return result;
    }

    @Override
    public boolean contains(Object value) {
        return root.contains((T)value);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean result = true;
        for (Object element : collection)
            result &= contains(element);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        Node<T> tmp = root;
        while (tmp.getL() != null)
            tmp = tmp.getL();
        return new TreeIterator<>(tmp);
    }

    @Override
    public Object[] toArray() {
        ArrayList<T> result = new ArrayList<>();
        root.pushAll(result);
        return result.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        ArrayList<T1> result = new ArrayList<>();
        root.pushAll(result);
        return result.toArray(a);
    }

    @Override
    public boolean remove(Object value) {
        if (contains(value)) {
            Node.remove(root, (T) value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean result = true;
        for (Object element : collection)
            result &= remove(element);
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        ArrayList<T> excluded = new ArrayList<>();
        for(T value : this)
            if (!collection.contains(value))
                excluded.add(value);
        excluded.forEach(this::remove);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
