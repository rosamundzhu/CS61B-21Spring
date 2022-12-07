package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;
    private class BSTNode {
        private K key;  // sorted by key
        private V val;  // associated data
        private BSTNode left, right;  // left and right subtrees
        private int size = 0;  // number of nodes in subtree

        public BSTNode(K key, V val,int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    @Override
    public V get(K k) {
        return get(root, k);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    private V get(BSTNode x, K k) {
        if (x == null || k == null) {
            return null;
        }
        int cmp = k.compareTo(x.key);
        if (cmp == 0) {
            return x.val;
        } else if (k.compareTo(x.key) < 0) {
            return get(x.left, k);
        } else {
            return get(x.right, k);
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode x, K key) {
        if (x == null || key == null) {
            return false;
        }
        int cmp = key.compareTo(x.key);
        if ( cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsKey(x.left, key);
        } else {
            return containsKey(x.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("calls put with a null key");
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode x, K key, V value) {
        if (containsKey(key)) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                x.val = value;
                return x;
            } else if (cmp > 0) {
                put(x.right, key, value);
            } else {
                put(x.left, key, value);
            }
            return x;
        } else {
            if (x == null) {
                x = new BSTNode(key, value, 1);
                return x;
            }
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x.right = put(x.right, key, value);
            } else {
                x.left = put(x.left, key, value);
            }
            x.size++;
            return x;
        }
    }



    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     *  prints out your BSTMap in order of increasing Key.
     */
    public void printInOrder() {
        printInOrder(this.root);
    }

    private void printInOrder(BSTNode x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        System.out.println(x.key + " -> " + x.val);
        printInOrder(x.right);
    }
}
