package com.rprtr258;

import java.util.Iterator;

public class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
    private NodeWrapper<E> node = null;
    private boolean isLast = true;

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
        while (nextNode.isNotNull()) {
            if (!nextNode.getParent().isNotNull()) {
                if (isLast) {
                    isLast = false;
                    return true;
                } else
                    return false;
            }
            if (nextNode.getParent().compareValues(nextNode.getValue()) > 0)
                return true;
            nextNode = nextNode.getParent();
        }
        return false;
    }

    @Override
    public E next() {
        E value = node.getValue();
        moveToNext();
        return value;
    }

    private void moveToNext() {
        node = getNextNode(node);
    }

    private NodeWrapper<E> getNextNode(NodeWrapper<E> node) {
        if (node.getR().isNotNull()) {
            node = node.getR();
            while (node.getL().isNotNull())
                node = node.getL();
        } else {
            while (node.getParent().isNotNull() && node.getParent().compareValues(node.getValue()) < 0)
                node = node.getParent();
            node = node.getParent();
        }
        return node;
    }
}
