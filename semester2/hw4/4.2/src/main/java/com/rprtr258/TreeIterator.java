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
        /*if (node.node == null)
            return false;
        if (node.node.getR().node != null)
            return true;
        while (nextNode.node != null && nextNode.node.getParent().node != null && nextNode.node.getParent().node.getValue().compareTo(nextNode.node.getValue()) < 0)
            nextNode = nextNode.node.getParent();
        return (nextNode.node != null);
        */
        return false;
    }

    @Override
    public E next() {
        /*E value = node.node.getValue();
        NodeWrapper<E> nextNode = node;
        if (nextNode.node.getR().node != null) {
            nextNode = nextNode.node.getR();
            while (nextNode.node.getL().node != null)
                nextNode = nextNode.node.getL();
        } else {
            while (nextNode.node.getParent().node != null && nextNode.node.getParent().node.getValue().compareTo(nextNode.node.getValue()) < 0)
                nextNode = nextNode.node.getParent();
            nextNode = nextNode.node.getParent();
        }
        node = nextNode;
        return value;
        */
        return null;
    }
}
