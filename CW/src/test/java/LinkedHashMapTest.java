import org.junit.Before;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by myutman on 10/11/17.
 */
public class LinkedHashMapTest {

    LinkedHashMap<String, String> hs;

    @Before
    public void before(){
        hs = new LinkedHashMap<String, String>();
    }

    /**
     * Putting and erasing some Values. Then checks correctness of EntrySet.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testEntrySet() throws Exception {
        hs.put("aba", "caba");
        hs.put("aba", "daba");
        hs.put("daba", "Hey, you!");
        hs.put("Out", "there");
        hs.remove("daba");
        String s = "";
        String s1 = "";
        for (Map.Entry entry: hs.entrySet()){
            s = s + entry.getValue();
            s1 = s1 + entry.getKey();
        }
        assertEquals(s, "dabathere");
        assertEquals(s1, "abaOut");
        assertEquals(hs.entrySet().size(), 2);
    }

    /**
     * Putting different values and checking size.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void size() throws Exception {
        assertEquals(hs.size(), 0);
        hs.put("aba", "caba");
        assertEquals(hs.size(), 1);
        hs.put("aba", "daba");
        assertEquals(hs.size(), 1);
        hs.put("aba", "caba");
        assertEquals(hs.size(), 1);
        hs.put("daba", "Hey, you!");
        assertEquals(hs.size(), 2);
        hs.put("Out", "there");
        assertEquals(hs.size(), 3);
        hs.put("in the", "cold!");
        assertEquals(hs.size(), 4);
        hs.clear();
        assertEquals(hs.size(), 0);
    }

    /**
     * Putting and erasing same value and checking for containing in HashTable.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void contains() throws Exception {
        hs.put("Out", "there");
        assertEquals(hs.containsKey("Out"), true);
        hs.clear();
        assertEquals(hs.containsKey("Out"), false);
    }

    /**
     * Putting different values and checking the value by key.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void get() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        hs.size();
        assertEquals(hs.get("int the"), null);
        assertEquals(hs.get("in the"), "cold!");
    }

    /**
     * Putting different values and checking previous values by keys.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void put() throws Exception {
        assertEquals(hs.put("aba", "caba"), null);
        assertEquals(hs.put("aba", "daba"), "caba");
        assertEquals(hs.put("aba", "caba"), "daba");
        assertEquals(hs.put("daba", "Hey, you!"), null);
        assertEquals(hs.put("Out", "there"), null);
        assertEquals(hs.put("in the", "cold!"), null);
        assertEquals(hs.size(), 4);
    }


    /**
     * Putting values then erasing these values and checking previous values by keys.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void remove() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals(hs.remove("out"), null);
        assertEquals(hs.remove("Out"), "there");
    }

    /**
     * Putting values and checking what they are and then clearing HashTable and checking that there are these values in HashTable no more.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void clear() throws Exception {
        hs.put("aba", "caba");
        hs.put("daba", "Hey, you!");
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals(hs.get("aba"), "caba");
        assertEquals(hs.get("daba"), "Hey, you!");
        assertEquals(hs.get("Out"), "there");
        assertEquals(hs.get("in the"), "cold!");
        hs.clear();
        assertEquals(hs.get("aba"), null);
        assertEquals(hs.get("daba"), null);
        assertEquals(hs.get("Out"), null);
        assertEquals(hs.get("in the"), null);
    }

}