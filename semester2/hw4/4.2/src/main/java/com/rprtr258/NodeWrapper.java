package com.rprtr258;

public class NodeWrapper<E extends Comparable<E>> {
    public Node<E> node;

    public NodeWrapper(Node<E> node) {
        this.node = node;
    }

    public boolean isNotNull() {
        return (node != null);
    }

    @Override
    public String toString() {
        return (node == null ? "null" : node.toString());
    }
}
