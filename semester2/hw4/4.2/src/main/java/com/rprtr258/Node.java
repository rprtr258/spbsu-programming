package com.rprtr258;

import java.util.ArrayList;

import static java.lang.Math.max;

public class Node<E extends Comparable<E>> {
    private NodeWrapper<E> l = new NodeWrapper<>(null);
    private NodeWrapper<E> r = new NodeWrapper<>(null);
    private NodeWrapper<E> parent = new NodeWrapper<>(null);
    private E value = null;
    private int height = 1;
    private int quantity = 1;

    public Node(E value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void copyData(Node<E> node) {
        this.value = node.value;
        this.quantity = node.quantity;
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
    }

    public NodeWrapper<E> getL() {
        return l;
    }

    public NodeWrapper<E> getR() {
        return r;
    }

    public E getValue() {
        return value;
    }

    public NodeWrapper<E> getParent() {
        return parent;
    }

    public void setParent(NodeWrapper<E> parent) {
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    public int compareValues(E value) {
        return this.value.compareTo(value);
    }

    @Override
    public String toString() {
        return String.format("(%s[%d] %s %s)", value, quantity, l.toString(), r.toString());
    }

    public void pushAll(ArrayList<E> list) {
        l.pushAll(list);
        for (int i = 0; i < quantity; i++)
            list.add(value);
        r.pushAll(list);
    }

    public void fixHeight() {
        height = max(l.getHeight(), r.getHeight()) + 1;
    }
}

