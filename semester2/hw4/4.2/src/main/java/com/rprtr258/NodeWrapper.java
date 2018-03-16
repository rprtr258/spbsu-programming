package com.rprtr258;

import java.util.ArrayList;

public class NodeWrapper<E extends Comparable<E>> {
    public Node<E> node;

    public NodeWrapper(Node<E> node) {
        this.node = node;
    }

    public E getValue() {
        return node.getValue();
    }

    public int getQuantity() {
        return node.getQuantity();
    }

    public NodeWrapper<E> getParent() {
        return node.getParent();
    }

    public NodeWrapper<E> getL() {
        return node.getL();
    }

    public NodeWrapper<E> getR() {
        return node.getR();
    }

    public int compareValues(E value) {
        return node.compareValues(value);
    }

    public boolean isNotNull() {
        return (node != null);
    }

    private void add(E value, Node<E> parent) {
        if (isNotNull()) {
            add(value);
        } else {
            node = new Node<>(value);
            getParent().node = parent;
        }
    }

    public void add(E value) {
        if (node == null) {
            node = new Node<>(value);
            return;
        }
        int cmp = compareValues(value);
        if (cmp == 0)
            node.changeQuantity(1);
        else if (cmp > 0)
            getL().add(value, node);
        else
            getR().add(value, node);
        balance();
    }

    @Override
    public String toString() {
        if (node == null)
            return "null";
        String valueString = getValue().toString();
        String lString = getL().toString();
        String rString = getR().toString();
        return String.format("(%s[%d] %s %s)", valueString, getQuantity(), lString, rString);
    }

    private NodeWrapper<E> getMaxNode() {
        NodeWrapper<E> result = getL();
        while (result.getR().isNotNull())
            result = result.getR();
        return result;
    }

    private void copyNode(NodeWrapper<E> nodeWrapper) {
        node = nodeWrapper.node;
    }

    public void remove(E value) {
        if (node == null)
            return;
        int cmp = compareValues(value);
        if (cmp == 0) {
            boolean isLNotNull = getL().isNotNull();
            boolean isRNotNull = getR().isNotNull();
            if (getQuantity() > 1) {
                node.changeQuantity(-1);
            } else if (isLNotNull && isRNotNull) {
                NodeWrapper<E> tmp = getMaxNode();
                E tempValue = tmp.getValue();
                node.copyData(tmp.node);
                getL().remove(tempValue);
            } else if (isLNotNull) {
                getL().setParent(this);
                copyNode(getL());
            } else if (isRNotNull) {
                getR().setParent(this);
                copyNode(getR());
            } else {
                node = null;
            }
        } else if (cmp > 0)
            getL().remove(value);
        else
            getR().remove(value);
        balance();
    }

    public boolean contains(E value) {
        if (node == null)
            return false;
        int cmp = compareValues(value);
        if (cmp == 0)
            return true;
        if (cmp > 0)
            return getL().contains(value);
        else
            return getR().contains(value);
    }

    public int getHeight() {
        return (node == null ? 0 : node.getHeight());
    }

    private int bFactor() {
        return getR().getHeight() - getL().getHeight();
    }

    private void setParent(NodeWrapper<E> parent) {
        if (isNotNull())
            node.setParent(parent);
    }

    private Node<E> rotateRight() {
        Node<E> pl = getL().node;
        pl.getR().setParent(this);
        getL().node = pl.getR().node;

        getParent().node = pl;
        pl.getR().node = node;

        node.fixHeight();
        pl.fixHeight();
        return pl;
    }

    private Node<E> rotateLeft() {
        Node<E> pr = getR().node;
        pr.getL().setParent(this);
        getR().node = pr.getL().node;

        getParent().node = pr;
        pr.getL().node = node;

        node.fixHeight();
        pr.fixHeight();
        return pr;
    }

    private void balance() {
        if (node == null)
            return;
        node.fixHeight();
        int balanceFactor = bFactor();
        if (balanceFactor == 2) {
            if (getR().bFactor() < 0) {
                getR().node = getR().rotateRight();
            }
            node = rotateLeft();
        } else if (balanceFactor == -2) {
            if (getL().bFactor() > 0) {
                getL().node = getL().rotateLeft();
            }
            node = rotateRight();
        }
    }

    public void pushAll(ArrayList<E> list) {
        if (node == null)
            return;
        getL().pushAll(list);
        for (int i = 0; i < getQuantity(); i++)
            list.add(getValue());
        getR().pushAll(list);
    }
}
