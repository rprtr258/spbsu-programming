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

    public void incQuantity() {
        quantity++;
    }

    public boolean contains(E value) {
        if (this.value.compareTo(value) == 0)
            return true;
        if ((this.value.compareTo(value) > 0) && l.isNotNull())
            return l.node.contains(value);
        else if ((this.value.compareTo(value) < 0) && r.isNotNull())
            return r.node.contains(value);
        return false;
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

    private static <E extends Comparable<E>> int getHeight(Node<E> node) {
        return (node == null ? 0 : node.height);
    }

    public int bFactor() {
        return getHeight(r.node) - getHeight(l.node);
    }

    public void fixHeight() {
        height = max(getHeight(l.node), getHeight(r.node)) + 1;
    }
}

