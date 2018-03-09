package com.rprtr258;

import java.util.Iterator;

public class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
    private Node<E> node = null;

    public TreeIterator(Node<E> node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        Node<E> nextNode = node;
        if (node == null)
            return false;
        if (node.getR() != null)
            return true;
        while (nextNode != null && nextNode.getParent() != null && nextNode.getParent().getValue().compareTo(nextNode.getValue()) < 0)
            nextNode = nextNode.getParent();
        return (nextNode != null);
    }

    @Override
    public E next() {
        E value = node.getValue();
        Node<E> nextNode = node;
        if (nextNode.getR() != null) {
            nextNode = nextNode.getR();
            while (nextNode.getL() != null)
                nextNode = nextNode.getL();
        } else {
            while (nextNode.getParent() != null && nextNode.getParent().getValue().compareTo(nextNode.getValue()) < 0)
                nextNode = nextNode.getParent();
            nextNode = nextNode.getParent();
        }
        node = nextNode;
        return value;
    }
}
