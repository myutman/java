import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by myutman on 9/27/17.
 */
public class MatrixTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    /**
     * Test output.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void output() throws Exception {
        Matrix mt = new Matrix(new int[][]{{1, 2, 3},
                                           {4, 5, 6},
                                           {7, 8, 9}});
        mt.output();
        assertEquals(output.toString(), "1 2 3 \n4 5 6 \n7 8 9 \n");
    }

    /**
     * Test metod sortByFirst().
     *
     * @throws Exception
     */
    @org.junit.Test
    public void sortByFirst() throws Exception {
        Matrix mt = new Matrix(new int[][]{{3, 2, 1},
                                           {6, 5, 4},
                                           {9, 8, 7}});
        mt.sortByFirst();
        mt.output();
        assertEquals(output.toString(), "1 2 3 \n4 5 6 \n7 8 9 \n");
    }


    /**
     * Test metod outputSpiral().
     *
     * @throws Exception
     */
    @org.junit.Test
    public void outputSpitral() throws Exception {
        Matrix mt = new Matrix(new int[][]{{ 1,  2,  3,  4,  5},
                                           { 6,  7,  8,  9, 10},
                                           {11, 12, 13, 14, 15},
                                           {16, 17, 18, 19, 20},
                                           {21, 22, 23, 24, 25}});
        mt.outputSpitral();
        assertEquals(output.toString(), "13 18 17 12 7 8 9 14 19 24 23 22 21 16 11 6 1 2 3 4 5 10 15 20 25 \n");
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}