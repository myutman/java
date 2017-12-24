package com.github.myutman.java;

/**
 * Created by myutman on 9/27/17.
 */
public class Main {
    public static void main(String[] args){
        int[][] a = { {0, 0, 0},
                      {1, 0, 1},
                      {2, 1, 3}};
        Matrix mt = new Matrix(a);
        mt.sortByFirst();
        mt.output();
        mt.outputSpiral();
    }
}
