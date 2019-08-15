package com.github.myutman.java;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by myutman on 10/17/17.
 *
 * Console application that reads numbers from input.txt and writes them squared to output.txt.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String inputFilename = "src/main/resources/input.txt";
        String outputFilename = "src/main/resources/output.txt";

        ArrayList<Maybe<Integer>> arrayList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(inputFilename))) {
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                try {
                    Integer num = Integer.parseInt(s);
                    arrayList.add(Maybe.just(num));
                } catch (NumberFormatException e) {
                    arrayList.add(Maybe.nothing());
                }
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new File(outputFilename))) {
            for (Maybe<Integer> maybe : arrayList) {
                Maybe<Integer> maybe1 = maybe.map(x -> x * x);
                if (maybe1.isPresent()){
                    printWriter.println(maybe1.get());
                } else {
                    printWriter.println("null");
                }
            }
        }
    }
}
