package com.github.myutman.java;

import java.util.Iterator;

/**
 * Created by myutman on 11/1/17.
 *
 * Iterable List with operations add, clear, getTail and iterator.
 */
public class List<T> implements Iterable<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    /**
     * List Node.
     */
    private static class Node<T> {
        private T key = null;
        private Node<T> next = null;
        private Node<T> prev = null;

        /**
         * Class constructor.
         *
         * @param key T stored in this node.
         */
        public Node(T key) {
            this.key = key;
        }
    }

    /**
     * Iterator for List Nodes.
     */
    private class NodeIterator implements Iterator<T> {
        private Node<T> node;
        private Node<T> last = null;

        /**
         * Class constructor.
         */
        public NodeIterator() {
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
            last = node;
            T key = node.key;
            node = node.next;
            return key;
        }

        /**
         * remove from Iterator interface.
         */
        public void remove() {
            if (last.prev != null) {
                last.prev.next = last.next;
            } else {
                tail = last.next;
            }
            if (last.next != null) {
                last.next.prev = last.prev;
            } else {
                head = last.prev;
            }
        }
    }

    /**
     * Adds value to List.
     * @param key value to be added to the head of List
     */
    public void add(T key) {
        size++;
        Node newNode = new Node<T>(key);
        if (tail == null) {
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
     * iterator from Iterable interface.
     */
    public Iterator<T> iterator() {
        return new NodeIterator();
    }
}