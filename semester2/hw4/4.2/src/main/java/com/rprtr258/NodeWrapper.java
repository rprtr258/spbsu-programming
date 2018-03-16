package com.rprtr258;

import java.util.ArrayList;
import static java.lang.Math.max;

/**
 * Public class that holds AVLTree nodes.
 */
public class NodeWrapper<E extends Comparable<E>> {
    /**
     * Contained node.
     */
    private Node<E> node;

    /**
     * Constructs holder of given node.
     * @param node node that will be held.
     */
    public NodeWrapper(Node<E> node) {
        this.node = node;
    }

    /**
     * Getter for node value.
     * @return node value.
     */
    public E getValue() {
        return node.value;
    }

    /**
     * Getter for quantity of values in node.
     * @return quantity of values.
     */
    public int getQuantity() {
        return node.quantity;
    }

    /**
     * Getter for left child of node.
     * @return left child.
     */
    public NodeWrapper<E> getL() {
        return node.l;
    }

    /**
     * Getter for right child of node.
     * @return right child.
     */
    public NodeWrapper<E> getR() {
        return node.r;
    }

    /**
     * Compares node value with given value.
     * @param value value to compare node value with.
     * @return negative int if node value is less,
     *         0 if equal,
     *         positive if larger
     */
    private int compareValues(E value) {
        return node.value.compareTo(value);
    }

    /**
     * Checks if node held is null(not present).
     * @return true if some node held, false otherwise.
     */
    private boolean isNotNull() {
        return (node != null);
    }

    /**
     * Adds given value among subtree of that node.
     * @param value value to add.
     */
    public void add(E value) {
        if (node == null) {
            node = new Node<>(value);
            return;
        }
        int cmp = compareValues(value);
        if (cmp == 0)
            node.quantity++;
        else if (cmp > 0)
            getL().add(value, node);
        else
            getR().add(value, node);
        balance();
    }

    /**
     * Pushes all values of that node's subtree into given list.
     * @param list list to insert values.
     */
    public void pushAll(ArrayList<E> list) {
        if (node == null)
            return;
        getL().pushAll(list);
        for (int i = 0; i < getQuantity(); i++)
            list.add(getValue());
        getR().pushAll(list);
    }

    /**
     * Removes value from subtree of that node.
     * @param value value to remove.
     */
    public void remove(E value) {
        if (node == null)
            return;
        int cmp = compareValues(value);
        if (cmp == 0) {
            boolean isLNotNull = getL().isNotNull();
            boolean isRNotNull = getR().isNotNull();
            if (getQuantity() > 1) {
                node.quantity--;
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

    /**
     * Checks if subtree of that node contains given value.
     * @param value value to check.
     * @return true if exists node with this value in subtree, false otherwise.
     */
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

    /**
     * Getter for node height in tree(max dist to any leaf).
     * @return height of node.
     */
    private int getHeight() {
        return (node == null ? 0 : node.height);
    }

    /**
     * String representation of tree in infix form.
     * @return string representation.
     */
    @Override
    public String toString() {
        if (node == null)
            return "null";
        String valueString = getValue().toString();
        String lString = getL().toString();
        String rString = getR().toString();
        return String.format("(%s[%d] %s %s)", valueString, getQuantity(), lString, rString);
    }

    /**
     * Returns node with maximum value in that node subtree.
     * @return node with maximum value.
     */
    private NodeWrapper<E> getMaxNode() {
        NodeWrapper<E> result = this;
        while (result.getR().isNotNull())
            result = result.getR();
        return result;
    }

    /**
     * Copies another inner node to that inner node.
     * @param nodeWrapper other node.
     */
    private void copyNode(NodeWrapper<E> nodeWrapper) {
        node = nodeWrapper.node;
    }

    /**
     * Calculates difference between left node and right node heights to balance that node.
     * @return child heights difference.
     */
    private int bFactor() {
        return getR().getHeight() - getL().getHeight();
    }

    /**
     * Getter for node parent.
     * @return node parent.
     */
    private NodeWrapper<E> getParent() {
        return node.parent;
    }

    /**
     * Setter for parent.
     * @param parent new parent.
     */
    private void setParent(NodeWrapper<E> parent) {
        if (isNotNull())
            node.parent = parent;
    }

    /**
     * Right rotation in AVL tree data structure.
     * @return new node that replaces previous.
     */
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

    /**
     * Left rotation in AVL tree data structure.
     * @return new node that replaces previous.
     */
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

    /**
     * Balances node in AVL tree data structure.
     */
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

    /**
     * Adds value to node which should contain it.
     * @param value value to add.
     * @param parent new parent of node.
     */
    private void add(E value, Node<E> parent) {
        if (isNotNull()) {
            add(value);
        } else {
            node = new Node<>(value);
            getParent().node = parent;
        }
    }

    /**
     * Erases node.
     */
    public void erase() {
        node = null;
    }

    /**
     * Node data container.
     * @param <T> Type of held value.
     */
    class Node<T extends Comparable<T>> {
        /**
         * Left child.
         */
        private NodeWrapper<T> l = new NodeWrapper<>(null);
        /**
         * Right child.
         */
        private NodeWrapper<T> r = new NodeWrapper<>(null);
        /**
         * Parent of node.
         */
        private NodeWrapper<T> parent = new NodeWrapper<>(null);
        /**
         * Value held.
         */
        private T value = null;
        /**
         * Height of node in tree(max dist till some leaf).
         */
        private int height = 1;
        /**
         * Quantity of held values.
         */
        private int quantity = 1;

        /**
         * Constructs node with given value.
         * @param value value to be stored in node.
         */
        private Node(T value) {
            this.value = value;
        }

        /**
         * Copies value data from another node.
         * @param node other node.
         */
        public void copyData(Node<T> node) {
            this.value = node.value;
            this.quantity = node.quantity;
        }

        /**
         * Getter for left children.
         * @return left children.
         */
        public NodeWrapper<T> getL() {
            return l;
        }

        /**
         * Getter for right children.
         * @return left children.
         */
        public NodeWrapper<T> getR() {
            return r;
        }

        /**
         * Fixes height of node based on height of child.
         */
        public void fixHeight() {
            height = max(l.getHeight(), r.getHeight()) + 1;
        }
    }

}

