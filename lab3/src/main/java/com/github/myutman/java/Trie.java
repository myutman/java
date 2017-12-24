package com.github.myutman.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by myutman on 12/24/17.
 *
 * Serializable and deserializable trie.
 */
public class Trie implements MySerializable{

    private int size = 0;
    private Node root = new Node();

    /**
     * Does one step of serialization.
     * @param cur current Node
     * @param pr PrintWriter
     */
    private void outputNode(Node cur, PrintWriter pr) throws IOException {
        pr.printf("%d %b %d\n", cur.howMany, cur.term, cur.to.size());
        for (Character c: cur.to.keySet()) {
            pr.printf("%c\n", c);
            outputNode(cur.to.get(c), pr);
        }
    }

    /**
     * Outputs the Trie into OutputStream using PrintWriter in such a format:
     * Node.howMany Node.term Node.to.size()
     * and then for each child of Node it outputs character on the edge and child itself in the same format.
     * @param out OutputStream
     */
    public void serialize(OutputStream out) throws IOException {
        PrintWriter pr = new PrintWriter(out);
        outputNode(root, pr);
        pr.close();
    }

    /**
     * Does one step of deserialization.
     * @param sc Scanner
     * @return the Node of new Trie
     */
    private Node readNode(Scanner sc) {
        Node res = new Node();
        res.howMany = sc.nextInt();
        res.term = sc.nextBoolean();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            Character c = sc.next().charAt(0);
            res.to.put(c, readNode(sc));
        }
        return res;
    }

    /**
     * Reads the Trie given in format denoted in serialize Java doc.
     * @param in InputStream
     */
    public void deserialize(InputStream in) throws IOException {
        Scanner sc = new Scanner(in);
        root = readNode(sc);
    }

    /**
     * Does one step of addition String to the Trie.
     * @param cur current Node
     * @param element String given to be added
     * @param pos current position in String
     * @return boolean, is it right that this String wasn't added yet.
     */
    private boolean addToNode(Node cur, String element, int pos) {
        if (pos == element.length()) {
            if (!cur.term) {
                cur.term = true;
                cur.howMany++;
                size++;
                return true;
            }
            return false;
        }
        Character c = element.charAt(pos);
        if (!cur.to.containsKey(c)) {
            cur.to.put(c, new Node());
        }
        if (addToNode(cur.to.get(c), element, pos + 1)) {
            cur.howMany++;
            return true;
        }
        return false;
    }

    /**
     * Adds given String to the Trie and tells is it right that this String wasn't added yet.
     * @param element String given to be added
     * @return boolean, is it right that this String wasn't added yet.
     */
    public boolean add(String element) {
        return addToNode(root, element, 0);
    }

    /**
     * Checks that Trie contains given String.
     * @param element String given to be checked
     * @return boolean, is it right that the Trie contains this String.
     */
    public boolean contains(String element) {
        Node cur = root;
        for (int pos = 0; pos < element.length(); pos++) {
            Character c = element.charAt(pos);
            if (!cur.to.containsKey(c))
                return false;
            cur = cur.to.get(c);
        }
        return cur.term;
    }

    /**
     * Does one step of removing String
     * @param cur current Node
     * @param element String given to be removed
     * @param pos current position in String
     * @return boolean, is it right that this String is in Trie.
     */
    private boolean removeAtNode(Node cur, String element, int pos) {
        if (pos == element.length()) {
            if (cur.term) {
                size--;
                cur.term = false;
                cur.howMany--;
                return true;
            }
            return false;
        }
        Character c = element.charAt(pos);
        if (!cur.to.containsKey(c))
            return false;
        boolean b = removeAtNode(cur.to.get(c), element, pos + 1);
        if (b)
            cur.howMany--;
        return b;
    }

    /**
     * Removes String from the Trie and tells is it right that this String was in Trie.
     * @param element String given to be removed
     * @return boolean, is it right that this String is in Trie.
     */
    public boolean remove(String element) {
        return removeAtNode(root, element, 0);
    }

    /**
     * Returns the size of the Trie
     * @return int, size of the Trie
     */
    public int size() {
        return size;
    }


    /**
     * Returns number of Strings in the Trie which start with the given prefix.
     * @param prefix prefix needed to be checked
     * @return number of Strings in the Trie which start with prefix
     */
    public int howManyStartsWithPrefix(String prefix) {
        Node cur = root;
        for (int pos = 0; pos < prefix.length(); pos++) {
            Character c = prefix.charAt(pos);
            if (!cur.to.containsKey(c))
                return 0;
            cur = cur.to.get(c);
        }
        return cur.howMany;
    }

    /**
     * Node of Trie.
     */
    private static class Node{
        HashMap<Character, Node> to = new HashMap<Character, Node>();
        boolean term = false;
        int howMany = 0;
    }
}
