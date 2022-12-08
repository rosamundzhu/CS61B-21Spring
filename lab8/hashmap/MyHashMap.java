package hashmap;

import java.util.*;


/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private static final int RESIZING_FACTOR = 2;
    private Collection<Node>[] buckets;
    // each Collection of Node represent a single bucket
    // e.g. LinkedList<Node> or ArrayList<Node>
    private int collectionsize;
    private double loadFactor;
    private int numBucket;
    private int size = 0;
    public ArrayList<Integer> list;



    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        if (initialSize < 1 || maxLoad <= 0.0) {
            throw new IllegalArgumentException ();
        }
        buckets = new Collection[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = this.createBucket();
        }
        collectionsize = initialSize;
        loadFactor = maxLoad;
    }


    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        if (key != null) {
            return new Node(key, value);
        }
        return null;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        for (int i = 0; i < collectionsize; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        numBucket = Math.floorMod(key.hashCode(), collectionsize);
        if (buckets[numBucket] != null) {
            for (Node s: buckets[numBucket]) {
                if (s.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        numBucket = Math.floorMod(key.hashCode(), collectionsize);
        if (buckets[numBucket] != null) {
            for (Node s: buckets[numBucket]) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        numBucket = Math.floorMod(key.hashCode(), collectionsize);
        if (containsKey(key)) {
            for (Node s: buckets[numBucket]) {
                if (s.key.equals(key)) {
                    s.value = value;
                    break;
                }
            }
        } else {
            Node m = createNode(key, value);
            buckets[numBucket].add(m);
            size += 1;
            if ((double) size / collectionsize > loadFactor) {
                resize();
            }
        }
    }

    private void resize() { // it should be made private
        int newsize = RESIZING_FACTOR * collectionsize;
        MyHashMap<K, V> newhashmap = new MyHashMap<>(newsize, loadFactor);
        Collection<Node>[] newbuckets = newhashmap.buckets;
        for (int i = 0; i < collectionsize; i++) {
            if (buckets[i] == null){
                continue;
            }
            for (Node item: buckets[i]) {
                newhashmap.put(item.key, item.value);
            }
        }
        buckets = newbuckets;
        collectionsize = newsize;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < collectionsize; i++) {
            if (buckets[i] == null){
                continue;
            }
            for (Node item: buckets[i]) {
                set.add(item.key);
            }
        }
        return set;
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> set = keySet();
        return set.iterator();
    }


    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        if (!containsKey(key)) {
            return null;
        }
        numBucket = Math.floorMod(key.hashCode(), collectionsize);
        for (Node s: buckets[numBucket]) {
            if (s.key.equals(key)) {
                V v = s.value;
                buckets[numBucket].remove(s);
                size -= 1;
                return v;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            return null;
        }
        if (!containsKey(key)) {
            return null;
        }
        numBucket = Math.floorMod(key.hashCode(), collectionsize);
        for (Node s: buckets[numBucket]) {
            if (s.key.equals(key) && s.value.equals(value)) {
                buckets[numBucket].remove(s);
                size -= 1;
                return value;
            }
        }
        return null;
    }
}
