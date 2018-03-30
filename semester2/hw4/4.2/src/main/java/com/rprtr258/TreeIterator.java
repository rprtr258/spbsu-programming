package com.rprtr258;

import java.util.Iterator;

public class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
    private NodeWrapper<E> node = null;

    public TreeIterator(NodeWrapper<E> node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        NodeWrapper<E> nextNode = node;
        if (!node.isNotNull())
            return false;
        if (node.getR().isNotNull())
            return true;
        while (nextNode.isNotNull() && nextNode.getParent().isNotNull() && nextNode.getParent().compareValues(nextNode.getValue()) < 0)
            nextNode = nextNode.getParent();
        return nextNode.isNotNull();
    }

    @Override
    public E next() {
        E value = node.getValue();
        NodeWrapper<E> nextNode = node;
        if (nextNode.getR().isNotNull()) {
            nextNode = nextNode.getR();
            while (nextNode.getL().isNotNull())
                nextNode = nextNode.getL();
        } else {
            while (nextNode.getParent().isNotNull() && nextNode.getParent().compareValues(nextNode.getValue()) < 0)
                nextNode = nextNode.getParent();
            nextNode = nextNode.getParent();
        }
        node = nextNode;
        return value;
    }
}
