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

    public boolean contains(E value) {
        if (this.value.compareTo(value) == 0)
            return true;
        if (this.value.compareTo(value) > 0) {
            if (l.node != null)
                return l.node.contains(value);
        } else {
            if (r.node != null)
                return r.node.contains(value);
        }
        return false;
    }

    public static <E extends Comparable<E>> void add(NodeWrapper<E> node, E value) {
        if (node.node == null) {
            node.node = new Node<>(value);
        } else if (node.node.value.compareTo(value) == 0) {
            node.node.quantity++;
        } else if (value.compareTo(node.node.value) < 0) {
            if (node.node.l.node == null) {
                node.node.l.node = new Node<>(value);
                node.node.l.node.parent.node = node.node;
            } else
                add(node.node.l, value);
        } else {
            if (node.node.r.node == null) {
                node.node.r.node = new Node<>(value);
                node.node.r.node.parent.node = node.node;
            } else
                add(node.node.r, value);
        }
        node.node = balance(node);
    }

    private static <E extends Comparable<E>> void removeFully(NodeWrapper<E> node, E value) {
        if (node.node.value.compareTo(value) == 0) {
            if (node.node.l.node != null) {
                node.node.l.node.parent = node;
                node.node = node.node.l.node;
            } else {
                node.node = null;
            }
        } else {
            removeFully(node.node.r, value);
        }
        if (node.node != null)
            node.node = balance(node);
    }
    public static <E extends Comparable<E>> void remove(NodeWrapper<E> node, E value) {
        if (node.node.value.compareTo(value) == 0) {
            if (node.node.quantity > 1) {
                node.node.quantity--;
            } else if (node.node.l.node != null && node.node.r.node != null) {
                NodeWrapper<E> tmp = node.node.l;
                while (tmp.node.r.node != null)
                    tmp = tmp.node.r;
                E tempValue = tmp.node.value;
                int tempQuantity = tmp.node.quantity;
                removeFully(node.node.l, tempValue);
                node.node.value = tempValue;
                node.node.quantity = tempQuantity;
            } else if (node.node.l.node != null) {
                    node.node.l.node.parent = node;
                node.node = node.node.l.node;
            } else if (node.node.r.node != null) {
                    node.node.r.node.parent = node;
                node.node = node.node.r.node;
            } else {
                node.node = null;
            }
        } else if (value.compareTo(node.node.value) < 0) {
            remove(node.node.l, value);
            //if (node.node.l.node.value.compareTo(value) == 0)
            //    if (node.node.l.node.quantity > 1)
            //        node.node.l.node.quantity--;
            //    else
            //        node.node.l.node = null;
            //else
            //    remove(node.node.l, value);
        } else {
            remove(node.node.r, value);
            //if (node.node.r.node.value.compareTo(value) == 0)
            //    if (node.node.r.node.quantity > 1)
            //        node.node.r.node.quantity--;
            //    else
            //        node.node.r.node = null;
            //else
            //    remove(node.node.r, value);
        }
        if (node.node != null)
            node.node = balance(node);
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

    public <T> void pushAll(ArrayList<T> list) {
        if (l.node != null)
            l.node.pushAll(list);
        for (int i = 0; i < quantity; i++)
            list.add((T)value);
        if (r.node != null)
            r.node.pushAll(list);
    }

    private static <E extends Comparable<E>> int getHeight(Node<E> node) {
        return (node == null ? 0 : node.height);
    }

    private void fixHeight() {
        height = max(getHeight(l.node), getHeight(r.node)) + 1;
    }

    private static <E extends Comparable<E>> Node<E> rotateRight(NodeWrapper<E> p) {
        Node<E> pl = p.node.l.node;
        if (pl.r.node != null)
            p.node.l.node.r.node.parent.node = p.node;
        p.node.l.node = pl.r.node;

        p.node.parent.node = pl;
        pl.r.node = p.node;

        p.node.fixHeight();
        pl.fixHeight();
        return pl;
    }

    private static <E extends Comparable<E>> Node<E> rotateLeft(NodeWrapper<E> p) {
        Node<E> pr = p.node.r.node;
        if (pr.l.node != null)
            pr.l.node.parent.node = p.node;
        p.node.r.node = pr.l.node;

        p.node.parent.node = pr;
        pr.l.node = p.node;

        p.node.fixHeight();
        pr.fixHeight();
        return pr;
    }

    private int bFactor() {
        return getHeight(r.node) - getHeight(l.node);
    }

    private static <E extends Comparable<E>> Node<E> balance(NodeWrapper<E> node) {
        node.node.fixHeight();
        int balanceFactor = node.node.bFactor();
        if (balanceFactor == 2) {
            if (node.node.r.node.bFactor() < 0) {
                node.node.r.node = rotateRight(node.node.r);
            }
            return rotateLeft(node);
        } else if (balanceFactor == -2) {
            if (node.node.l.node.bFactor() > 0) {
                node.node.l.node = rotateLeft(node.node.l);
            }
            return rotateRight(node);
        }
        return node.node;
    }
}

