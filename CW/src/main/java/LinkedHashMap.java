import javafx.util.Pair;

import java.util.*;

/**
 * Created by myutman on 10/11/17.
 */
public class LinkedHashMap<K, V> extends AbstractMap<K, V> {

    public class Pair<K, V> implements Entry<K, V>{

        private K key;
        private V value;
        boolean deleted = false;

        Pair(K key, V value){
            this.key = key;
            this.value = value;
        }

        /**
         * @return - Key
         */
        public K getKey() {
            return key;
        }

        /**
         * @return - Value
         */
        public V getValue() {
            return value;
        }

        /**
         * @param value - new Value
         * @return - old Value
         *
         * Sets new Value and returns old.
         */
        public V setValue(V value) {
            V ans = this.value;
            this.value = value;
            return ans;
        }
    }

    /**
     * Decorator class of LinkedList of Pair of K and V.
     */
    private class ListOfPairs{
        public LinkedList<Pair<K, V> > list;
        ListOfPairs(){
            list = new LinkedList<Pair<K, V> >();
        }
    }

    /**
     * Iterator through keyStore.
     */
    class MyIterator implements Iterator<Entry<K, V>>{

        Iterator<Pair<K, V>> iterator = keyStore.iterator();
        Pair<K, V> next = null;

        public boolean hasNext() {
            if (next != null){
                return true;
            }
            while (iterator.hasNext()){
                next = iterator.next();
                if (!next.deleted) {
                    return true;
                }
            }
            return false;
        }

        public Pair<K, V> next() {
            if (next == null){
                while (iterator.hasNext()){
                    next = iterator.next();
                    if (containsKey(next)) {
                        break;
                    }
                }
            }
            Pair<K, V> ans = next;
            next = null;
            return ans;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Class implements Set interface using MyIterator.
     * It's actually not a set and it works in constant time in average for each iteration.
     */
    class KeySet extends AbstractSet<Entry<K, V>>{

        MyIterator iterator;

        KeySet(){
            iterator = new MyIterator();
        }

        public Iterator<Entry<K, V>> iterator() {
            return iterator;
        }

        public int size() {
            return LinkedHashMap.this.size();
        }
    }

    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>) new KeySet();
    }

    private final int mod = (int) (1e6 + 7);
    private int size = 0;
    ArrayList<ListOfPairs> store = new ArrayList<ListOfPairs>();
    private LinkedList<Pair<K, V>> keyStore = new LinkedList<Pair<K, V>>();

    /**
     * Class constructor.
     */
    public LinkedHashMap() {
        for (int i = 0; i < mod; i++){
            store.add(new ListOfPairs());
        }
    }

    /**
     * @return - number of Keys in Map
     *
     * Returns size of Map.
     */
    @Override
    public int size() {
        return size;
    }

    private LinkedList<Pair<K, V>> getBucket(K key){
        return store.get((key.hashCode() % mod + mod) % mod).list;
    }

    /**
     * @param key - Key needed to be checked
     * @return - true if Key exists
     *
     * Checks whether Key exists in Map.
     */
    @Override
    public boolean containsKey(Object key) {
        List<Pair<K, V>> list = getBucket((K) key);
        for (Pair entry : list) {
            if (entry.deleted) continue;
            if (entry.getKey().equals(key))
                return true;
        }
        return false;
    }

    /**
     * @param key - Key to search Value for
     * @return - Value by Key
     *
     * Searches Value by Key.
     */
    @Override
    public V get(Object key) {
        List<Pair<K, V>> list = getBucket((K) key);
        for (Pair entry : list) {
            if (entry.deleted) continue;
            if (entry.getKey().equals(key))
                return (V) entry.getValue();
        }
        return null;
    }

    /**
     * @param key - Key to add Value
     * @param value - Value to be added
     * @return - old Value
     *
     * adds new Value to Key
     */
    @Override
    public V put(K key, V value) {
        List<Pair<K, V>> list = getBucket(key);
        for (Pair<K, V> entry : list) {
            if (entry.deleted) continue;
            if (entry.getKey().equals(key)) {
                V ans = entry.getValue();
                entry.setValue(value);
                return ans;
            }
        }
        size++;
        Pair<K, V> pair = new Pair(key, value);
        list.add(pair);
        keyStore.add(pair);
        return null;
    }

    /**
     * @param key - Key needed to be removed
     * @return - Value removed
     *
     * Removes Entry by Key.
     */
    @Override
    public V remove(Object key) {
        LinkedList<Pair<K, V>> list = getBucket((K) key);
        for (Pair<K, V> entry: list) {
            if (entry.deleted) continue;
            if (entry.getKey().equals(key)) {
                size--;
                entry.deleted = true;
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Deleting all Keys from Map.
     */
    public void clear() {
        for (Pair<K, V> entry: keyStore){
            getBucket(entry.getKey()).clear();
        }
        keyStore.clear();
        size = 0;
    }

}
