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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setValue(E value) {
        this.value = value;
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
        String result = String.format("(%s[%d] ", value, quantity);
        if (l.isNotNull() && r.isNotNull())
            result += String.format("%s %s)", l.toString(), r.toString());
        else if (l.isNotNull())
            result += String.format("%s null)", l.toString());
        else if (r.isNotNull())
            result += String.format("null %s)", r.toString());
        else
            result += "null null)";
        return result;
    }

    public <T> void pushAll(ArrayList<T> list) {
        if (l.isNotNull())
            l.node.pushAll(list);
        for (int i = 0; i < quantity; i++)
            list.add((T)value);
        if (r.isNotNull())
            r.node.pushAll(list);
    }

    public void fixHeight() {
        height = max(l.getHeight(), r.getHeight()) + 1;
    }
}

