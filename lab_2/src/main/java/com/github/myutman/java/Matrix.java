package com.github.myutman.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by myutman on 9/27/17.
 *
 * Two dimensioned array wrapper with methods output, sortByFirst and outputSpiral.
 */
public class Matrix {

    /**
     * Store of matrix elements.
     */
    private int[][] data;

    /**
     * Constructor.
     *
     * @param data - source int[][]
     */
    public Matrix(int[][] data) {
        int n = data.length;
        this.data = new int[n][n];
        for (int i = 0; i < n; i++) {
            if (data[i].length != n)
                throw new UnsupportedOperationException();
            for (int j = 0; j < n; j++) {
                this.data[j][i] = data[i][j];
            }
        }
    }

    /**
     * Output matrix.
     */
    public void output() {
        int n = data.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%d ", data[j][i]);
            }
            System.out.println();
        }
    }

    /**
     * Sort columns by first element in increasing order.
     */
    public void sortByFirst() {
        Arrays.sort(data, Comparator.comparingInt(a -> a[0]));
    }

    /**
     * Output elements of matrix in clockwise spiral order.
     */
    public void outputSpiral() {
        int n = data.length;
        int cx = n / 2;
        int cy = n / 2;
        System.out.printf("%d ", data[cx][cy]);
        for (int i = 1; i < n - 1; i += 2) {
            for (int j = 0; j < i; j++) {
                cy++;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i; j++) {
                cx--;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i + 1; j++) {
                cy--;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i + 1; j++) {
                cx++;
                System.out.printf("%d ", data[cx][cy]);
            }
        }
        for (int j = 0; j < n - 1; j++) {
            cy++;
            System.out.printf("%d ", data[cx][cy]);
        }
        System.out.println();
    }
}
