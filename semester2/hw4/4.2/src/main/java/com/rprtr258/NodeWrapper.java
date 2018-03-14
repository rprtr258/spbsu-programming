package com.rprtr258;

public class NodeWrapper<E extends Comparable<E>> {
    public Node<E> node;

    public NodeWrapper(Node<E> node) {
        this.node = node;
    }

    public boolean isNotNull() {
        return (node != null);
    }

    public void add(E value) {
        if (node == null) {
            node = new Node<>(value);
        } else if (node.getValue().compareTo(value) == 0) {
            node.incQuantity();
        } else if (value.compareTo(node.getValue()) < 0) {
            if (node.getL().isNotNull()) {
                node.getL().add(value);
            } else {
                node.getL().node = new Node<>(value);
                node.getL().node.getParent().node = node;
            }
        } else {
            if (node.getR().isNotNull()) {
                node.getR().add(value);
            } else {
                node.getR().node = new Node<>(value);
                node.getR().node.getParent().node = node;
            }
        }
        node = balance();
    }

    @Override
    public String toString() {
        return (node == null ? "null" : node.toString());
    }

    private Node<E> rotateRight() {
        Node<E> pl = node.getL().node;
        if (pl.getR().isNotNull())
            node.getL().node.getR().node.getParent().node = node;
        node.getL().node = pl.getR().node;

        node.getParent().node = pl;
        pl.getR().node = node;

        node.fixHeight();
        pl.fixHeight();
        return pl;
    }

    private Node<E> rotateLeft() {
        Node<E> pr = node.getR().node;
        if (pr.getL().isNotNull())
            pr.getL().node.getParent().node = node;
        node.getR().node = pr.getL().node;

        node.getParent().node = pr;
        pr.getL().node = node;

        node.fixHeight();
        pr.fixHeight();
        return pr;
    }

    public Node<E> balance() {
        node.fixHeight();
        int balanceFactor = node.bFactor();
        if (balanceFactor == 2) {
            if (node.getR().node.bFactor() < 0) {
                node.getR().node = node.getR().rotateRight();
            }
            return rotateLeft();
        } else if (balanceFactor == -2) {
            if (node.getL().node.bFactor() > 0) {
                node.getL().node = node.getL().rotateLeft();
            }
            return rotateRight();
        }
        return node;
    }
}
