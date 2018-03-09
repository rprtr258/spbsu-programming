package com.rprtr258;

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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public boolean contains(Object value) {
        return root.findNode((T)value);
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
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean remove(Object value) {
        if (contains(value)) {
            Node.remove(root, (T) value);
            return true;
        } else
            return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        for (Object element : c)
            result &= contains(element);
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = true;
        for (Object element : c)
            result &= add((T)element);
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true;
        for (Object element : c)
            result &= remove(element);
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
