import static org.junit.Assert.*;

/**
 * Created by myutman on 10/17/17.
 */
public class TreeTest {

    Tree<Integer> tr = new Tree<Integer>();

    /**
     * @throws Exception
     *
     * Adds many elements and checks containing and size.
     */
    @org.junit.Test
    public void add() throws Exception {
        tr.add(7);
        tr.add(4);
        tr.add(8);
        tr.add(4);
        tr.add(7);
        tr.add(9);
        tr.add(24);
        tr.add(2048);
        tr.add(-15);
        tr.add(24);
        tr.add(322);
        assertEquals(tr.contains(7), true);
        assertEquals(tr.contains(-15), true);
        assertEquals(tr.contains(2048), true);
        assertEquals(tr.contains(322), true);
        assertEquals(tr.contains(6), false);
        assertEquals(tr.contains(228), false);
        assertEquals(tr.contains(239), false);
        assertEquals(tr.contains(2049), false);
        assertEquals(tr.size(), 8);
    }

    /**
     * @throws Exception
     *
     * Adds many elements and checks containing.
     */
    @org.junit.Test
    public void contains() throws Exception {
        assertEquals(tr.contains(7), false);
        tr.add(7);
        assertEquals(tr.contains(7), true);
        tr.add(4);
        assertEquals(tr.contains(8), false);
        tr.add(8);
        assertEquals(tr.contains(8), true);
        tr.add(4);
        tr.add(7);
        assertEquals(tr.contains(7), true);
        assertEquals(tr.contains(9), false);
        tr.add(9);
        assertEquals(tr.contains(9), true);
        assertEquals(tr.contains(4), true);
        assertEquals(tr.contains(5), false);
    }

    /**
     * @throws Exception
     *
     * Adds many elements and checks size.
     */
    @org.junit.Test
    public void size() throws Exception {
        assertEquals(tr.size(), 0);
        tr.add(7);
        assertEquals(tr.size(), 1);
        tr.add(4);
        assertEquals(tr.size(), 2);
        tr.add(8);
        assertEquals(tr.size(), 3);
        tr.add(4);
        assertEquals(tr.size(), 3);
        tr.add(7);
        assertEquals(tr.size(), 3);
        tr.add(9);
        assertEquals(tr.size(), 4);
    }

}