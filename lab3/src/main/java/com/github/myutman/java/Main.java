package com.github.myutman.java;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Trie tr = new Trie();
        tr.add("abaca");
        tr.add("aba");
        tr.add("abada");
        tr.serialize(new FileOutputStream(new File("input.txt")));
        tr.deserialize(new FileInputStream(new File("input.txt")));
        System.out.println(tr.contains("abada"));
        System.out.println(tr.howManyStartsWithPrefix("ab"));
        System.out.println(tr.size());
        System.out.println(tr.remove("aba"));
        System.out.println(tr.howManyStartsWithPrefix("ab"));
        System.out.println(tr.size());
    }
}
