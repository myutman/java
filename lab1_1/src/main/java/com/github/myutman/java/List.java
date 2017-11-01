package com.github.myutman.java;

import java.util.Iterator;

/**
 * Created by myutman on 11/1/17.
 *
 * Iterable List with operations add, clear, getTail and iterator.
 */
public class List<T> implements Iterable<T> {

    private Node head = null, tail = null;
    private int size = 0;

    /**
     * List Node.
     */
    public class Node {
        private T key = null;
        private Node next = null;
        private Node prev = null;

        public Node getNext() { return next; }
        public T getKey() { return key; }

        /**
         * Class constructor.
         *
         * @param key - T stored in this node.
         */
        public Node(T key) {
            this.key = key;
        }

        public void remove() {
            if (prev != null){
                prev.next = next;
            } else {
                tail = next;
            }
            if (next != null) {
                next.prev = prev;
            } else {
                head = prev;
            }
        }
    }

    /**
     * Iterator for List Nodes.
     */
    private class NodeIterator implements Iterator<T> {
        private Node node;

        /**
         * Class constructor.
         */
        NodeIterator(){
            this.node = tail;
        }

        /**
         * hasNext from Iterator interface.
         */
        public boolean hasNext() {
            return node != null;
        }

        /**
         * next from Iterator interface.
         */
        public T next() {
            T key = node.key;
            node = node.next;
            return key;
        }

        /**
         * remove from Iterator interface.
         */
        public void remove() { }
    }

    /**
     * @param key - value to be added to the head of List
     */
    public void add(T key) {
        size++;
        Node newNode = new Node(key);
        if (tail == null){
            head = tail = newNode;
        } else {
            head.next = newNode;
            newNode.prev = head;
            head = newNode;
        }
    }

    /**
     * Removes all the values from List.
     */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    /**
     * @return - leftmost(tail) Node of the List.
     */
    public Node getTail(){
        return tail;
    }

    /**
     * iterator from Iterable interface.
     */
    public Iterator<T> iterator() {
        return new NodeIterator();
    }
}