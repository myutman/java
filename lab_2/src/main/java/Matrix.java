import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by myutman on 9/27/17.
 */
public class Matrix {

    /**
     * Store of matrix elements.
     */
    int[][] data;

    /**
     * Constructor.
     *
     * @param data - source int[][]
     */
    Matrix(int[][] data){
        int n = data.length;
        this.data = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                this.data[j][i] = data[i][j];
            }
        }
    }

    /**
     * Output matrix.
     */
    void output(){
        int n = data.length;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.printf("%d ", data[j][i]);
            }
            System.out.println();
        }
    }

    /**
     * Sort columns by first element in increasing order.
     */
    void sortByFirst(){
        Arrays.sort(data, new Comparator<int[]>(){
            public int compare(int[] a, int[] b){
                return a[0] - b[0];
            }
        });
    }

    /**
     * Output elements of matrix in clockwise spiral order.
     */
    void outputSpitral(){
        int n = data.length;
        int cx = n/2, cy = n/2;
        System.out.printf("%d ", data[cx][cy]);
        for (int i = 1; i < n - 1; i += 2){
            for (int j = 0; j < i; j++){
                cy++;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i; j++){
                cx--;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i + 1; j++){
                cy--;
                System.out.printf("%d ", data[cx][cy]);
            }
            for (int j = 0; j < i + 1; j++){
                cx++;
                System.out.printf("%d ", data[cx][cy]);
            }
        }
        for (int j = 0; j < n - 1; j++){
            cy++;
            System.out.printf("%d ", data[cx][cy]);
        }
        System.out.println();
    }
}
