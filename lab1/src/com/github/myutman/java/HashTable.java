package com.github.myutman.java;

import javax.management.openmbean.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by myutman on 9/9/17.
 */

class List<T> implements Iterable<T>{
    class Node{
        public T key = null;
        public Node next = null, prev = null;

        public Node(T key) {
            this.key = key;
        }

        public Node() {

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

    class NodeIterator implements Iterator<T>{
        Node node;

        NodeIterator(Node node){
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            T key = node.key;
            node = node.next;
            return key;
        }

        @Override
        public void remove() {

        }
    }

    private Node head = null, tail = null;
    private int size = 0;

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

    public void clear() {
        size = 0;
        head = tail = null;
    }

    public Node getTail(){
        return tail;
    }

    @Override
    public Iterator<T> iterator() {
        return new NodeIterator(tail);
    }
}

class Pair<T> {
    public T first, second;
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
}

public class HashTable {

    class ListOfPairs{
        List<Pair<String> > list;
        ListOfPairs(){
            list = new List<Pair<String> >();
        }
    }

    private final int mod = (int) (1e6 + 7);
    private int size = 0;
    private ListOfPairs[] store = new ListOfPairs[mod];
    private List<String> keyStore = new List<String>();

    public HashTable() {
        for (int i = 0; i < mod; i++){
            store[i] = new ListOfPairs();
        }
    }

    public int size() {
        return size;
    }

    private List<Pair<String>> getBucket(String key){
        return store[(key.hashCode() % mod + mod) % mod].list;
    }

    public boolean contains(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.first == key)
                    return true;
            }
        }
        return false;
    }

    public String get(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.first == key)
                    return pair.second;
            }
        }
        return null;
    }

    public String put(String key, String value) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.first == key) {
                    String ans = pair.second;
                    pair.second = value;
                    return ans;
                }
            }
        }
        size++;
        list.add(new Pair(key, value));
        keyStore.add(key);
        return null;
    }

    public String remove(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (List<Pair<String>>.Node node = list.getTail(); node != null; node = node.next) {
                if (node.key.first == key) {
                    size--;
                    String ans = node.key.second;
                    node.remove();
                    return ans;
                }
            }
        }
        return null;
    }

    public void clear() {
        for (String s: keyStore){
            getBucket(s).clear();
        }
        keyStore.clear();
        size = 0;
    }

    public static void main(String[] args) {
        HashTable hs = new HashTable();
        System.out.println(hs.put("aba", "caba"));
        System.out.println(hs.put("aba", "daba"));
        System.out.println(hs.put("aba", "caba"));
        System.out.println(hs.put("daba", "Hey, you!"));
        System.out.println(hs.put("Out", "there"));
        System.out.println(hs.put("in the", "cold!"));
        System.out.println(hs.size());
        System.out.println(hs.get("int the"));
        System.out.println(hs.get("in the"));
        System.out.println(hs.remove("out"));
        System.out.println(hs.remove("Out"));
        System.out.println(hs.size());
        System.out.println(hs.remove("Out"));
        System.out.println(hs.size());
        hs.clear();
        System.out.println(hs.size());
        System.out.println(hs.remove("Out"));
    }
}
