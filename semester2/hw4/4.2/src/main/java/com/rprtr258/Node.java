package com.rprtr258;

import static java.lang.Math.max;

public class Node<E extends Comparable<E>> {
    private Node<E> l = null;
    private Node<E> r = null;
    private Node<E> parent = null;
    private E value = null;
    private int height = 1;
    private int quantity = 1;

    public Node(E value) {
        this.value = value;
    }

    public Node<E> getL() {
        return l;
    }

    public Node<E> getR() {
        return r;
    }

    public E getValue() {
        return value;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String result = String.format("(%s[%d] ", value, quantity);
        if (l == null && r == null)
            result += "null null)";
        else if (l == null)
            result += String.format("null %s)", r.toString());
        else if (r == null)
            result += String.format("%s null)", l.toString());
        else
            result += String.format("%s %s)", l.toString(), r.toString());
        return result;
    }

    public boolean findNode(E value) {
        if (this.value.equals(value))
            return true;
        if (this.value.compareTo(value) < 0)
            return l.findNode(value);
        else
            return r.findNode(value);
    }

    public static <E extends Comparable<E>> void add(Node<E> node, E value) {
        if (node.value == value) {
            node.quantity++;
            return;
        }
        if (value.compareTo(node.value) < 0) {
            if (node.l == null) {
                node.l = new Node<>(value);
                node.l.setParent(node);
            } else
                add(node.l, value);
        } else {
            if (node.r == null) {
                node.r = new Node<>(value);
                node.r.setParent(node);
            } else
                add(node.r, value);
        }
        node.balance();
    }

    private static <E extends Comparable<E>> int getHeight(Node<E> node) {
        return (node == null ? 0 : node.height);
    }

    private void fixHeight() {
        height = max(getHeight(l), getHeight(r)) + 1;
    }

    private static <E extends Comparable<E>> Node<E> rotateRight(Node<E> p) {
        Node<E> q = p.l;
        p.l = q.r;
        q.r = p;
        p.fixHeight();
        q.fixHeight();
        return q;
    }

    private static <E extends Comparable<E>> Node<E> rotateLeft(Node<E> p) {
        Node<E> q = p.r;
        p.r = q.l;
        q.l = p;
        p.fixHeight();
        q.fixHeight();
        return q;
    }

    private int bFactor() {
        return getHeight(r) - getHeight(l);
    }

    private void balance() {
        fixHeight();
        int balanceFactor = bFactor();
        if (balanceFactor == 2) {
            if (r.bFactor() < 0)
                r = rotateRight(r);
        } else if (balanceFactor == -2) {
            if (l.bFactor() > 0)
                l = rotateLeft(l);
        }
    }

    public static <E extends Comparable<E>> void remove(Node<E> node, E value) {
        if (node.value.equals(value)) {
            if (node.quantity > 1) {
                node.quantity--;
                return;
            } else if (node.l != null && node.r != null) {
                Node<E> tmp = node.l;
                while (tmp.r != null)
                    tmp = tmp.r;
                E tempValue = tmp.value;
                remove(node, tmp.value);
                node.value = tempValue;
                node.balance();
            } else if (node.l != null) {
                node = node.l;
                node.balance();
            } else if (node.r != null) {
                node = node.r;
                node.balance();
            }
            return;
        }
        if (value.compareTo(node.value) < 0) {
            if (node.l.value.equals(value))
                if (node.l.quantity > 1)
                    node.l.quantity--;
                else
                    node.l = null;
            else
                remove(node.l, value);
        } else {
            if (node.r.value.equals(value))
                if (node.r.quantity > 1)
                    node.r.quantity--;
                else
                    node.r = null;
            else
                remove(node.r, value);
        }
        node.balance();
    }
}

