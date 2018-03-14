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

    public void add(E value) {
        if (node == null) {
            node = new Node<>(value);
        } else if (compareValues(value) == 0) {
            node.changeQuantity(1);
        } else if (compareValues(value) > 0) {
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

    private void removeFully(E value) {
        if (compareValues(value) == 0) {
            if (node.getL().isNotNull()) {
                node.getL().node.setParent(this);
                node = node.getL().node;
            } else {
                node = null;
            }
        } else {
            node.getR().removeFully(value);
        }
        if (isNotNull())
            node = balance();
    }

    public void remove(E value) {
        if (compareValues(value) == 0) {
            if (node.getQuantity() > 1) {
                node.changeQuantity(-1);
            } else if (node.getL().isNotNull() && node.getR().isNotNull()) {
                NodeWrapper<E> tmp = node.getL();
                while (tmp.node.getR().isNotNull())
                    tmp = tmp.node.getR();
                E tempValue = tmp.node.getValue();
                int tempQuantity = tmp.node.getQuantity();
                node.getL().removeFully(tempValue);
                node.setValue(tempValue);
                node.setQuantity(tempQuantity);
            } else if (node.getL().isNotNull()) {
                node.getL().node.setParent(this);
                node = node.getL().node;
            } else if (node.getR().isNotNull()) {
                node.getR().node.setParent(this);
                node = node.getR().node;
            } else {
                node = null;
            }
        } else if (compareValues(value) > 0) {
            node.getL().remove(value);
        } else {
            node.getR().remove(value);
        }
        if (isNotNull())
            node = balance();
    }

    public boolean contains(E value) {
        if (compareValues(value) == 0)
            return true;
        if ((compareValues(value) > 0) && node.getL().isNotNull())
            return node.getL().contains(value);
        else if ((compareValues(value) < 0) && node.getR().isNotNull())
            return node.getR().contains(value);
        return false;
    }

    public int getHeight() {
        return (node == null ? 0 : node.getHeight());
    }

    public int bFactor() {
        return node.getR().getHeight() - node.getL().getHeight();
    }

    public Node<E> balance() {
        node.fixHeight();
        int balanceFactor = bFactor();
        if (balanceFactor == 2) {
            if (node.getR().bFactor() < 0) {
                node.getR().node = node.getR().rotateRight();
            }
            return rotateLeft();
        } else if (balanceFactor == -2) {
            if (node.getL().bFactor() > 0) {
                node.getL().node = node.getL().rotateLeft();
            }
            return rotateRight();
        }
        return node;
    }
}
