package com.rprtr258;

import java.util.Collection;
import java.util.Iterator;

import static java.lang.Math.max;

class AVLTree<T extends Comparable<T>> implements Collection<T> {
    private Node<T> root = null;
    private int size = 0;

    @Override
    public boolean add(T value) {
        addNode(root, value);
        size++;
        return true;
    }

    @Override
    public boolean contains(Object value) {
        return root.findNode((T)value);
    }

    @Override
    public boolean remove(Object value) {
        if (contains(value))
            removeNode(root, (T)value);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public T[] toArray() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<? extends T> c) {
        c.forAll();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
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
        return toString(root);
    }

    private int getHeight(Node node) {
        return (node == null ? 0 : node.height);
    }

    private void fixHeight(Node node) {
        node.height = max(getHeight(node.l), getHeight(node.r)) + 1;
    }

    private Node rotateRight(Node p) {
        Node q = p.l;
        p.l = q.r;
        q.r = p;
        fixHeight(p);
        fixHeight(q);
        return q;
    }

    private Node rotateLeft(Node p) {
        Node  q = p.r;
        p.r = q.l;
        q.l = p;
        fixHeight(p);
        fixHeight(q);
        return q;
    }

    private int bFactor(Node p) {
        return getHeight(p.r) - getHeight(p.l);
    }

    private Node balanceNode(Node p) {
        fixHeight(p);
        int balanceFactor = bFactor(p);
        if (balanceFactor == 2) {
            if (bFactor(p.r) < 0) {
                p.r = rotateRight(p.r);
            }
            return rotateLeft(p);
        } else if (balanceFactor == -2) {
            if (bFactor(p.l) > 0) {
                p.l = rotateLeft(p.l);
            }
            return rotateRight(p);
        }
        return p;
    }

    private void addNode(Node<T> node, T value) {
        if (node == null) {
            node = new Node<T>();
            node.value = value;
            return;
        }
        if (node.value == value) {
            node.quantity++;
            return;
        }
        if (value.compareTo(node.value) < 0)
            addNode(node.l, value);
        else
            addNode(node.r, value);
        node = balanceNode(node);
    }


    private void removeNode(Node<T> node, T value) {
        if (node.value == value) {
            if (node.quantity > 1) {
                node.quantity--;
                return;
            }
            if (node.l == null && node.r == null) {
                node = null;
            } else if (node.l != null && node.r != null) {
                Node<T> tmp = node.l;
                while (tmp.r != null)
                    tmp = tmp.r;
                T tempValue = tmp.value;
                removeNode(node, tmp.value);
                node.value = tempValue;
                node = balanceNode(node);
            } else if (node.l != null) {
                Node  child = node.l;
                node = child;
                node = balanceNode(node);
            } else if (node.r != null) {
                Node  child = node.r;
                node = child;
                node = balanceNode(node);
            }
            return;
        }
        if (value.compareTo(node.value) < 0)
            removeNode(node.l, value);
        else
            removeNode(node.r, value);
        node = balanceNode(node);
    }

    private String toString(Node<T> node) {
        if (node == null)
            return "null";
        if (node.l == null && node.r == null)
            return String.format("{%s[%d]}", node.value, node.quantity);
        return String.format("(%s[%d, %d] %s %s)", node.value, node.height, node.quantity, toString(node.l), toString(node.r));
    }

    private class Node<E extends Comparable<E>> {
        private Node<E> l = null;
        private Node<E> r = null;
        private E value = null;
        private int height = 1;
        private int quantity = 1;

        private boolean findNode(E value) {
            if (this.value.equals(value))
                return true;
            if (this.value.compareTo(value) < 0)
                return l.findNode(value);
            else
                return r.findNode(value);
        }
    }
}
