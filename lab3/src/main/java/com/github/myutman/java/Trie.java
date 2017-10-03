package com.github.myutman.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

interface MySerializable{
    void serialize(OutputStream out) throws IOException;
    void deserialize(InputStream in) throws IOException;
}

public class Trie implements MySerializable{

    /**
     * @param cur - current Node
     * @param pr - PrintWriter
     * @throws IOException
     *
     * Does one step of serialization.
     */
    private void outputNode(Node cur, PrintWriter pr) throws IOException {
        pr.printf("%d %b %d\n", cur.howMany, cur.term, cur.to.size());
        for (Character c: cur.to.keySet()){
            pr.printf("%c\n", c);
            outputNode(cur.to.get(c), pr);
        }
    }

    /**
     * @param out - OutputStream
     * @throws IOException
     *
     * Outputs the Trie into OutputStream using PrintWriter in such a format:
     * Node.howMany Node.term Node.to.size()
     *
     * and then for each child of Node it outputs character on the edge and child itself in the same format.
     */
    public void serialize(OutputStream out) throws IOException {
        PrintWriter pr = new PrintWriter(out);
        outputNode(root, pr);
        pr.close();
    }

    /**
     * @param sc - Scanner
     * @return - the Node of new Trie
     *
     * Does one step of deserialization.
     */
    private Node readNode(Scanner sc){
        Node res = new Node();
        res.howMany = sc.nextInt();
        res.term = sc.nextBoolean();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++){
            Character c = sc.next().charAt(0);
            res.to.put(c, readNode(sc));
        }
        return res;
    }

    /**
     * @param in - InputStream
     * @throws IOException
     *
     * Reads the Trie given in format denoted in serialize Java doc.
     */
    public void deserialize(InputStream in) throws IOException {
        Scanner sc = new Scanner(in);
        root = readNode(sc);
    }

    /**
     * Node of Trie.
     */
    private class Node{
        HashMap<Character, Node> to = new HashMap<Character, Node>();
        boolean term = false;
        int howMany = 0;
    }

    private int size = 0;
    private Node root = new Node();

    /**
     * @param cur - current Node
     * @param element - String given to be added
     * @param pos - current position in String
     * @return - boolean, is it right that this String wasn't added yet.
     *
     * Does one step of addition String to the Trie.
     */
    private boolean addToNode(Node cur, String element, int pos){
        if (pos == element.length()){
            if (!cur.term) {
                cur.term = true;
                cur.howMany++;
                size++;
                return true;
            }
            return false;
        }
        Character c = element.charAt(pos);
        if (!cur.to.containsKey(c)){
            cur.to.put(c, new Node());
        }
        if (addToNode(cur.to.get(c), element, pos + 1)){
            cur.howMany++;
            return true;
        }
        return false;
    }

    /**
     * @param element - String given to be added
     * @return - boolean, is it right that this String wasn't added yet.
     *
     * Adds given String to the Trie and tells is it right that this String wasn't added yet.
     */
    public boolean add(String element){
        return addToNode(root, element, 0);
    }

    /**
     * @param element - String given to be checked
     * @return - boolean, is it right that the Trie contains this String.
     *
     * Checks that Trie contains given String.
     */
    public boolean contains(String element){
        Node cur = root;
        for (int pos = 0; pos < element.length(); pos++){
            Character c = element.charAt(pos);
            if (!cur.to.containsKey(c))
                return false;
            cur = cur.to.get(c);
        }
        return cur.term;
    }

    /**
     * @param cur - current Node
     * @param element - String given to be removed
     * @param pos - current position in String
     * @return - boolean, is it right that this String is in Trie.
     *
     * Does one step of removing String
     */
    private boolean removeAtNode(Node cur, String element, int pos){
        if (pos == element.length()){
            if (cur.term){
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
     * @param element - String given to be removed
     * @return - boolean, is it right that this String is in Trie.
     *
     * Removes String from the Trie and tells is it right that this String was in Trie.
     */
    public boolean remove(String element){
        return removeAtNode(root, element, 0);
    }

    /**
     * @return - int, size of the Trie
     *
     * Returns the size of the Trie
     */
    public int size(){
        return size;
    }


    /**
     * @param prefix - prefix needed to be checked
     * @return - number of Strings in the Trie which start with prefix
     *
     * Returns number of Strings in the Trie which start with the given prefix.
     */
    public int howManyStartsWithPrefix(String prefix){
        Node cur = root;
        for (int pos = 0; pos < prefix.length(); pos++){
            Character c = prefix.charAt(pos);
            if (!cur.to.containsKey(c))
                return 0;
            cur = cur.to.get(c);
        }
        return cur.howMany;
    }

}
