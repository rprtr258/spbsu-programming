package com.rprtr258.util;

/**
 * Singly connected linked list.
 *
 * @param <T> Type of values stored in list.
 */
public class SinglyLinkedList<T> {
    private ListNode<T> head = null;
    private int size = 0;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Returns size of linked list(number of nodes in it).
     *
     * @return Size of list.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks whether list is empty.
     *
     * @return True if list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Checks if linked list contains value.
     *
     * @param value Value to be checked.
     * @return True if value is in list, false otherwise.
     */
    public boolean contains(T value) {
        if (isEmpty())
            return false;
        ListNode<T> temp = head;
        while (temp != null) {
            if (value.equals(temp.getValue()))
                return true;
            temp = temp.getNext();
        }
        return false;
    }

    /**
     * Returns last value in list.
     *
     * @return Last value in list.
     * @throws EmptyListException When list does not have any values.
     */
    public T peek() throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException();
        else
            return head.getValue();
    }

    /**
     * Adds value to the end of list.
     *
     * @param value Value to be added.
     */
    public void append(T value) {
        if (size == 0) {
            head = new ListNode<>(value);
        } else {
            ListNode<T> newNode = new ListNode<>(value);
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    /**
     * Removes last added element in list.
     *
     * @throws EmptyListException When there is no elements to remove.
     */
    public void removeLast() throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException();
        if (size == 1)
            head = null;
        else
            head = head.getNext();
        size--;
    }

    @Override
    public String toString() {
        ListNode<T> tmp = head;
        StringBuilder result = new StringBuilder();
        result.append("[");
        while (tmp != null) {
            result.append(tmp.getValue());
            if (tmp.getNext() != null)
                result.append(", ");
            tmp = tmp.getNext();
        }
        result.append("]");
        return result.toString();
    }
    
    /**
     * @param <E> Type of value stored in each node.
     */
    private class ListNode<E> {
        private ListNode<E> next = null;
        private E value = null;

        private ListNode(E value) {
            this.next = null;
            this.value = value;
        }

        /**
         * Sets neighbour node that goes after.
         *
         * @param node New next node.
         */
        private void setNext(ListNode<E> node) {
            next = node;
        }

        /**
         * Returns node that goes after this node.
         *
         * @return Next node of this node, null if it isn't set.
         */
        private ListNode<E> getNext() {
            return next;
        }

        /**
         * Getter for stored value.
         *
         * @return Value stored in node.
         */
        private E getValue() {
            return value;
        }
    }
}
