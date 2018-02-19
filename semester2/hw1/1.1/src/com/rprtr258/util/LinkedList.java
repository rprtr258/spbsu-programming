package com.rprtr258.util;

public class LinkedList {
    private ListNode head = null;
    private ListNode tail = null;
    private int size = 0;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int peekBegin() {
        if (size == 0)
            return 999999;
        else
            return head.getValue();
    }

    public int peekEnd() {
        if (size == 0)
            return 999999;
        else
            return tail.getValue();
    }

    public void insertAtBegin(int value) {
        if (size == 0) {
            head = new ListNode(value);
            tail = head;
        } else {
            ListNode newNode = new ListNode(value);
            head.setPrev(newNode);
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    public void insertAtEnd(int value) {
        if (size == 0) {
            tail = new ListNode(value);
            head = tail;
        } else {
            ListNode newNode = new ListNode(value);
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    public void deleteAtEnd() {
        if (size == 0)
            return;

        if (size == 1) {
            erase();
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
            size--;
        }
    }

    public void deleteAtBegin() {
        if (size == 0)
            return;

        if (size == 1) {
            erase();
        } else {
            head = head.getNext();
            head.setPrev(null);
            size--;
        }
    }

    public void erase() {
        head = null;
        tail = null;
        size = 0;
    }

    public void print() {
        System.out.print("[");
        if (head != null)
            head.print();
        System.out.print("]\n");
    }
}
