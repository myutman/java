package com.github.myutman.java;

/**
 * Created by myutman on 9/9/17.
 *
 * HashTable with operations put, get, contains, size, remove and clear.
 */
public class HashTable {

    /**
     * List of Pair of Strings wrapper.
     */
    private class ListOfPairs {
        private List<Pair<String>> list;

        /**
         * Class constructor.
         */
        public ListOfPairs() {
            list = new List<>();
        }
    }

    private final int mod = (int) (1e6 + 7);
    private int size = 0;
    private ListOfPairs[] store = new ListOfPairs[mod];
    private List<String> keyStore = new List<>();

    /**
     * Class constructor. Creates Lists to store elements.
     */
    public HashTable() {
        for (int i = 0; i < mod; i++){
            store[i] = new ListOfPairs();
        }
    }

    /**
     * Size of HashTable.
     *
     * @return size of HashTable
     */
    public int size() {
        return size;
    }

    private List<Pair<String>> getBucket(String key) {
        return store[(key.hashCode() % mod + mod) % mod].list;
    }

    /**
     * Checks whether given key is in HashTable.
     *
     * @param key String needed to be checked
     * @return true if HashTable contains String and false otherwise
     */
    public boolean contains(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.getFirst().equals(key))
                    return true;
            }
        }
        return false;
    }

    /**
     * @param key String to get value by
     * @return value by this key or null if it doesn't exist
     */
    public String get(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.getFirst().equals(key))
                    return pair.getSecond();
            }
        }
        return null;
    }

    /**
     * @param key String to put value by
     * @param value String to be put
     * @return previous value by key or null if it doesn't exist
     */
    public String put(String key, String value) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (Pair<String> pair : list) {
                if (pair.getFirst().equals(key)) {
                    String ans = pair.getSecond();
                    pair.setSecond(value);
                    return ans;
                }
            }
        }
        size++;
        list.add(new Pair<>(key, value));
        keyStore.add(key);
        return null;
    }

    /**
     * @param key String to remove value by
     * @return value by this key or null if it doesn't exist
     */
    public String remove(String key) {
        List<Pair<String>> list = getBucket(key);
        if (list.getTail() != null) {
            for (List<Pair<String>>.Node node = list.getTail(); node != null; node = node.getNext()) {
                if (node.getKey().getFirst().equals(key)) {
                    size--;
                    String ans = node.getKey().getSecond();
                    node.remove();
                    return ans;
                }
            }
        }
        return null;
    }

    /**
     * Removes all the values from the HashTable.
     */
    public void clear() {
        for (String s: keyStore) {
            getBucket(s).clear();
        }
        keyStore.clear();
        size = 0;
    }
}
