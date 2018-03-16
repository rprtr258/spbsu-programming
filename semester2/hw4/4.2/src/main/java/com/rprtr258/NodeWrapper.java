package com.rprtr258;

public class NodeWrapper<E extends Comparable<E>> {
    public Node<E> node;

    public NodeWrapper(Node<E> node) {
        this.node = node;
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
            node.getParent().node = parent;
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
            node.getL().add(value, node);
        else
            node.getR().add(value, node);
        balance();
    }

    @Override
    public String toString() {
        return (node == null ? "null" : node.toString());
    }

    private NodeWrapper<E> getMaxNode() {
        NodeWrapper<E> result = node.getL();
        while (result.node.getR().isNotNull())
            result = result.node.getR();
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
            if (node.getQuantity() > 1) {
                node.changeQuantity(-1);
            } else if (node.getL().isNotNull() && node.getR().isNotNull()) {
                NodeWrapper<E> tmp = getMaxNode();
                E tempValue = tmp.node.getValue();
                node.copyData(tmp.node);
                node.getL().remove(tempValue);
            } else if (node.getL().isNotNull()) {
                node.getL().setParent(this);
                copyNode(node.getL());
            } else if (node.getR().isNotNull()) {
                node.getR().setParent(this);
                copyNode(node.getR());
            } else {
                node = null;
            }
        } else if (cmp > 0)
            node.getL().remove(value);
        else
            node.getR().remove(value);
        balance();
    }

    public boolean contains(E value) {
        if (node == null)
            return false;
        int cmp = compareValues(value);
        if (cmp == 0)
            return true;
        if (cmp > 0)
            return node.getL().contains(value);
        else
            return node.getR().contains(value);
    }

    public int getHeight() {
        return (node == null ? 0 : node.getHeight());
    }

    public int bFactor() {
        return node.getR().getHeight() - node.getL().getHeight();
    }

    private void setParent(NodeWrapper<E> parent) {
        if (isNotNull())
            node.setParent(parent);
    }

    private Node<E> rotateRight() {
        Node<E> pl = node.getL().node;
        pl.getR().setParent(this);
        node.getL().node = pl.getR().node;

        node.getParent().node = pl;
        pl.getR().node = node;

        node.fixHeight();
        pl.fixHeight();
        return pl;
    }

    private Node<E> rotateLeft() {
        Node<E> pr = node.getR().node;
        pr.getL().setParent(this);
        node.getR().node = pr.getL().node;

        node.getParent().node = pr;
        pr.getL().node = node;

        node.fixHeight();
        pr.fixHeight();
        return pr;
    }

    public void balance() {
        if (node == null)
            return;
        node.fixHeight();
        int balanceFactor = bFactor();
        if (balanceFactor == 2) {
            if (node.getR().bFactor() < 0) {
                node.getR().node = node.getR().rotateRight();
            }
            node = rotateLeft();
        } else if (balanceFactor == -2) {
            if (node.getL().bFactor() > 0) {
                node.getL().node = node.getL().rotateLeft();
            }
            node = rotateRight();
        }
    }
}
